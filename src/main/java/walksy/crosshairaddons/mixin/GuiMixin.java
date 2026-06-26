package walksy.crosshairaddons.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import walksy.crosshairaddons.config.Config;
import walksy.crosshairaddons.graphics.CrosshairExtractor;

@Mixin(value={Gui.class})
public class GuiMixin {

    @Inject(at={@At(value="CONSTANT", args={"stringValue=hud"})}, method={"extractRenderState"}, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void render(DeltaTracker deltaTracker, boolean shouldRenderLevel, boolean resourcesLoaded, CallbackInfo ci, ProfilerFiller profiler, int xMouse, int yMouse, GuiGraphicsExtractor graphics) {
        if (!Config.modEnabled) return;
        CrosshairExtractor crosshair = new CrosshairExtractor();
        crosshair.extract(graphics);
    }
}
