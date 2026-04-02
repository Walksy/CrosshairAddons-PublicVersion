package walksy.crosshairaddons.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.crosshairaddons.CrosshairAddons;
import walksy.crosshairaddons.config.Config;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "handleEntityEvent", at = @At("HEAD"))
    public void onHandleStatusUpdate(byte status, CallbackInfo ci) {
        if (!Config.modEnabled) return;
        LivingEntity entity = LivingEntity.class.cast(this);
        if (entity instanceof Player player) {
            CrosshairAddons.getStateManager().onByteStatusUpdate(player, status);
        }
    }
}
