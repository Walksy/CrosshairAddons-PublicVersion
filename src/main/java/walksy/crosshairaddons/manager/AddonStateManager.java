package walksy.crosshairaddons.manager;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import walksy.crosshairaddons.CrosshairAddons;
import walksy.crosshairaddons.config.Config;
import walksy.crosshairaddons.config.State;

import java.util.HashSet;
import java.util.Set;

public class AddonStateManager {

    private int hitmarkerTime;
    private int shieldBreakTime;
    private int hoveredEntityTime;
    private int cacheUpdateTick;
    private final ShieldStateManager shieldStateManager;
    private final Set<EntityType<?>> cachedIndicatorEntities;

    public AddonStateManager() {
        this.hitmarkerTime = 0;
        this.shieldBreakTime = 0;
        this.hoveredEntityTime = 0;
        this.cacheUpdateTick = 0;
        this.shieldStateManager = new ShieldStateManager(this::onShieldBreak);
        this.cachedIndicatorEntities = new HashSet<>();
    }

    public void updateCachedEntityTypes() {
        this.cachedIndicatorEntities.clear();
        for (String entityID : Config.entityIndicatorEntityList) {
            EntityType<?> type = CrosshairAddons.getEntityTypeFromString(entityID);
            if (type != null) {
                this.cachedIndicatorEntities.add(type);
            }
        }
    }

    private void onShieldBreak(Player player) {
        this.shieldBreakTime = Config.shieldBreakDuration;
        Config.shieldBreakAddon.resetAnimation();
    }

    public void onAttackEntity(LivingEntity entity) {
        if (entity instanceof Player player) {
            this.shieldStateManager.handlePlayerAttack(player);
            if (Config.hitmarkerToggleOnAttack) {
                this.hitmarkerTime = Config.hitmarkerDuration;
                Config.hitmarkerAddon.resetAnimation();
            }
        }
    }

    public void onByteStatusUpdate(Player player, byte arg) {
        this.shieldStateManager.handleEntityStatus(player, arg);
    }

    public void handleBreakPacket(double x, double y, double z) {
        this.shieldStateManager.handleBreakPacket(x, y, z);
    }

    public void onArrowHit() {
        this.hitmarkerTime = Config.hitmarkerDuration;
        Config.hitmarkerAddon.resetAnimation();
    }

    public void tick() {
        this.shieldStateManager.update();
        if (this.hitmarkerTime > 0) {
            this.hitmarkerTime--;
        }
        if (this.shieldBreakTime > 0) {
            this.shieldBreakTime--;
        }

        this.cacheUpdateTick++;
        if (this.cacheUpdateTick >= 100) {
            this.updateCachedEntityTypes();
            this.cacheUpdateTick = 0;
        }
    }

    public void checkTarget() {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && client.level != null) {
            if (client.crosshairPickEntity != null) {
                this.hoveredEntityTime++;
                if (this.hoveredEntityTime == 1) {
                    Config.entityIndicatorAddon.resetAnimation();
                    if (client.crosshairPickEntity instanceof Player player) {
                        if (this.shieldStateManager.isUsingShield(player, Config.shieldIndicatorFactorDelay)) {
                            Config.shieldIndicatorAddon.resetAnimation();
                        }
                    }
                }
            } else {
                this.hoveredEntityTime = 0;
            }
        }
    }

    public boolean get(State state) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.level == null) return false;

        return switch (state) {
            case ELYTRA -> Config.elytraAddonEnabled
                    && client.player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA);

            case HITMARKER -> Config.hitmarkerAddonEnabled
                    && (!Config.hitmarkerStopOnAnimationEnd && this.hitmarkerTime > 0 || Config.hitmarkerStopOnAnimationEnd && !Config.hitmarkerAddon.hasPlayedOnce());

            case SHIELD_BREAK -> Config.shieldBreakAddonEnabled
                    && (!Config.shieldBreakStopOnAnimationEnd && this.shieldBreakTime > 0 || Config.shieldBreakStopOnAnimationEnd && !Config.shieldBreakAddon.hasPlayedOnce());

            case SHIELD_INDICATOR -> {
                if (!Config.shieldIndicatorEnabled) yield false;
                Entity target = client.crosshairPickEntity;
                if (target instanceof Player player) {
                    yield this.shieldStateManager.isUsingShield(player, Config.shieldIndicatorFactorDelay);
                }
                yield false;
            }

            case INDICATOR -> {
                if (!Config.entityIndicatorAddonEnabled) yield false;
                Entity target = client.crosshairPickEntity;
                if (Config.entityIndicatorShowAll && target != null) yield true;
                if (target == null) yield false;
                yield this.cachedIndicatorEntities.contains(target.getType());
            }
        };
    }
}