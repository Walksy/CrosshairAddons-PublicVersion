package walksy.crosshairaddons.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.crosshairaddons.CrosshairAddons;
import walksy.crosshairaddons.config.Config;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @Inject(method = "attack", at = @At("HEAD"))
    public void onAttackEntity(Player player, Entity target, CallbackInfo ci) {
        if (!Config.modEnabled) return;
        if (target instanceof LivingEntity livingEntity) {
            CrosshairAddons.getStateManager().onAttackEntity(livingEntity);
        }
    }
}
