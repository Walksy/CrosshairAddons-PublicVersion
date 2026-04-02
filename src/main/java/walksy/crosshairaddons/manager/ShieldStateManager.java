package walksy.crosshairaddons.manager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ShieldStateManager {

    private final long ATTACK_ENTRY_TTL_MS = 1000;
    private final Map<Player, Integer> shieldUseTicks = new HashMap<>();
    private final Map<Player, AttackEntry> attackedPlayerEntries = new HashMap<>();
    private final Minecraft client = Minecraft.getInstance();
    private record AttackEntry(Vec3 attackPos, Vec3 targetPos, long time, boolean wasBlocking) {}
    private final ShieldCooldownManager cooldownManager = new ShieldCooldownManager();
    private final Consumer<Player> onShieldBreak;

    public ShieldStateManager(Consumer<Player> onShieldBreak) {
        this.onShieldBreak = onShieldBreak;
    }

    public void set(Player player) {
        if (player == null) return;
        this.cooldownManager.setCooldown(player);
        if (this.attackedPlayerEntries.containsKey(player)) {
            this.onShieldBreak.accept(player);
        }
    }

    public void update() {
        long now = System.currentTimeMillis();
        if (this.client.level == null) return;

        for (Player player : this.client.level.players()) {
            if (this.isHoldingUsableShield(player) && player.isUsingItem()) {
                int current = this.shieldUseTicks.getOrDefault(player, 0);
                this.shieldUseTicks.put(player, current + 1);
            } else {
                this.shieldUseTicks.put(player, 0);
            }
        }

        this.attackedPlayerEntries.entrySet().removeIf(e -> now - e.getValue().time > this.ATTACK_ENTRY_TTL_MS);

        this.cooldownManager.tick();
    }

    public void handleBreakPacket(double x, double y, double z) {
        if (this.client == null || this.client.level == null || this.client.player == null) return;

        LocalPlayer local = this.client.player;
        long now = System.currentTimeMillis();

        final double matchRadiusSq = 36.0;
        Player best = null;
        double bestDistSq = Double.POSITIVE_INFINITY;

        for (Map.Entry<Player, AttackEntry> e : this.attackedPlayerEntries.entrySet()) {
            Player candidate = e.getKey();
            AttackEntry ae = e.getValue();
            if (candidate == null || candidate == local) continue;
            if (now - ae.time > this.ATTACK_ENTRY_TTL_MS) continue;
            if (!ae.wasBlocking) continue;

            double dx = candidate.getX() - x;
            double dy = candidate.getY() - y;
            double dz = candidate.getZ() - z;
            double currentDistSq = dx * dx + dy * dy + dz * dz;

            double sdx = ae.targetPos.x - x;
            double sdy = ae.targetPos.y - y;
            double sdz = ae.targetPos.z - z;
            double storedDistSq = sdx * sdx + sdy * sdy + sdz * sdz;

            double effectiveDistSq = Math.min(currentDistSq, storedDistSq);
            if (effectiveDistSq > matchRadiusSq) continue;

            if (effectiveDistSq < bestDistSq) {
                bestDistSq = effectiveDistSq;
                best = candidate;
            }
        }

        if (best == null) return;

        double selfDx = local.getX() - x;
        double selfDy = local.getY() - y;
        double selfDz = local.getZ() - z;
        double selfDistSq = selfDx * selfDx + selfDy * selfDy + selfDz * selfDz;
        if (selfDistSq < bestDistSq) return;

        this.set(best);
        this.attackedPlayerEntries.remove(best);
    }

    public void handleEntityStatus(Player player, byte status) {
        if (status == 30 && player != this.client.player) {
            this.set(player);
        }
    }

    public void handlePlayerAttack(Player target) {
        boolean estBlocking = this.shieldUseTicks.getOrDefault(target, 0) >= 3;
        if (this.client.player == null) return;
        if (this.disablesShield(this.client.player)) {
            this.attackedPlayerEntries.put(
                    target,
                    new AttackEntry(
                            this.client.player.position(),
                            target.position(),
                            System.currentTimeMillis(),
                            estBlocking
                    )
            );
        }
    }

    public boolean isCoolingDown(Player player) {
        if (player == this.client.player) {
            return this.client.player.getCooldowns().isOnCooldown(new ItemStack(Items.SHIELD));
        }
        return this.cooldownManager.isCoolingDown(player);
    }

    public float getCooldownProgress(Player player) {
        if (player == null) return 0.0f;
        if (player == this.client.player) {
            return this.client.player.getCooldowns().getCooldownPercent(new ItemStack(Items.SHIELD), 0);
        }
        int remaining = this.cooldownManager.getRemainingTicks(player);
        if (remaining <= 0) return 0.0f;
        float frac = remaining / (float) 100;
        return Math.max(0f, Math.min(1f, frac));
    }

    public boolean isUsingShield(Player player, boolean delay) {
        return this.shieldUseTicks.getOrDefault(player, 0) >= (delay ? 5 : 1);
    }

    public boolean isHoldingUsableShield(Player entity) {
        return (entity.getMainHandItem().is(Items.SHIELD)
                || entity.getOffhandItem().is(Items.SHIELD))
                && !this.isHoldingAnimationItemMainHand(entity);
    }

    private boolean isHoldingAnimationItemMainHand(Player entity) {
        return entity.getMainHandItem().getUseDuration(entity) != 0
                && !entity.getOffhandItem().is(Items.SHIELD);
    }

    public boolean disablesShield(Player player) {
        return player.getWeaponItem().getItem() instanceof AxeItem;
    }
}