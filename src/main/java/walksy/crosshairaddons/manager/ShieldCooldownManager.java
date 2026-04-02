package walksy.crosshairaddons.manager;

import net.minecraft.world.entity.player.Player;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ShieldCooldownManager {

    private final Map<UUID, ShieldCooldown> cooldowns = new ConcurrentHashMap<>();

    public void setCooldown(Player player) {
        setCooldown(player.getUUID(), 100);
    }

    public void setCooldown(Player player, int ticks) {
        setCooldown(player.getUUID(), ticks);
    }

    public void setCooldown(UUID playerUuid) {
        setCooldown(playerUuid, 100);
    }

    public void setCooldown(UUID playerUuid, int ticks) {
        if (playerUuid == null) return;
        cooldowns.put(playerUuid, new ShieldCooldown(Math.max(0, ticks)));
    }

    public boolean isCoolingDown(Player player) {
        if (player == null) return false;
        return isCoolingDown(player.getUUID());
    }

    public boolean isCoolingDown(UUID playerUuid) {
        if (playerUuid == null) return false;
        ShieldCooldown cd = cooldowns.get(playerUuid);
        return cd != null && cd.getTicks() > 0;
    }

    public int getRemainingTicks(Player player) {
        if (player == null) return 0;
        return getRemainingTicks(player.getUUID());
    }

    public int getRemainingTicks(UUID playerUuid) {
        if (playerUuid == null) return 0;
        ShieldCooldown cd = cooldowns.get(playerUuid);
        return cd == null ? 0 : Math.max(0, cd.getTicks());
    }

    public void remove(Player player) {
        if (player == null) return;
        remove(player.getUUID());
    }

    public void remove(UUID playerUuid) {
        if (playerUuid == null) return;
        cooldowns.remove(playerUuid);
    }

    public void clear() {
        cooldowns.clear();
    }

    public void tick() {
        Iterator<Map.Entry<UUID, ShieldCooldown>> it = cooldowns.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, ShieldCooldown> e = it.next();
            ShieldCooldown cd = e.getValue();
            cd.tick();
            if (cd.isDone()) it.remove();
        }
    }

    public static final class ShieldCooldown {
        private int ticks;

        public ShieldCooldown(int ticks) {
            this.ticks = Math.max(0, ticks);
        }

        public int getTicks() {
            return ticks;
        }

        public void tick() {
            if (ticks > 0) ticks--;
        }

        public boolean isDone() {
            return ticks <= 0;
        }
    }
}
