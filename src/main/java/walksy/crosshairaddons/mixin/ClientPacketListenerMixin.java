package walksy.crosshairaddons.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.crosshairaddons.CrosshairAddons;
import walksy.crosshairaddons.config.Config;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(method = "handleGameEvent", at = @At("HEAD"))
    private void onGameStateChange(ClientboundGameEventPacket packet, CallbackInfo ci) {
        if (!Config.modEnabled) return;
        if (packet.getEvent() == ClientboundGameEventPacket.PLAY_ARROW_HIT_SOUND) {
            CrosshairAddons.getStateManager().onArrowHit();
        }
    }

    @Inject(method = "handleSoundEvent", at = @At("HEAD"))
    public void onSound(ClientboundSoundPacket packet, CallbackInfo ci) {
        if (!Config.modEnabled) return;
        if (packet.getSound().getRegisteredName().toLowerCase().contains("shield.break")) {
            CrosshairAddons.getStateManager().handleBreakPacket(packet.getX(), packet.getY(), packet.getZ());
        }
    }
}
