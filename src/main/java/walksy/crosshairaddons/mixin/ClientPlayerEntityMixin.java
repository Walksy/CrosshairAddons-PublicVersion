package walksy.crosshairaddons.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.crosshairaddons.manager.ConfigManager;
import walksy.crosshairaddons.manager.CrosshairRendererManager;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci)
    {
        if (!ConfigManager.modEnabled) return;
        CrosshairRendererManager.INSTANCE.onTick();
        FallDistanceManager.getInstance().onClientTick();
    }

}
