package walksy.crosshairaddons.graphics;

import main.walksy.lib.core.config.local.options.type.PixelGridAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.profiling.Profiler;
import walksy.crosshairaddons.CrosshairAddons;
import walksy.crosshairaddons.config.Config;

import java.util.Map;
import java.util.function.Supplier;

public class CrosshairExtractor  {

    public void extract(GuiGraphicsExtractor extractor) {
        if (!Config.modEnabled || Config.isEditing() || !this.perspectiveCheck()) return;
        Profiler.get().push("walksyCrosshairAddons");
        final boolean bl = Config.environmentBlend;

        for (Map.Entry<Supplier<PixelGridAnimation>, Supplier<Boolean>> e : CrosshairAddons.getAddons().entrySet()) {
            final PixelGridAnimation addon = e.getKey().get();
            final boolean enabled = e.getValue().get();
            if (!enabled) {
                continue;
            }
            if (addon == Config.entityIndicatorAddon) {
                CrosshairAddons.getStateManager().checkTarget();
            }
            addon.render(extractor, bl);
        }

        Profiler.get().pop();
    }

    private boolean perspectiveCheck() {
        final boolean thirdPerson = !Minecraft.getInstance().options.getCameraType().isFirstPerson();
        return !thirdPerson || Config.showInThirdPerson;
    }
}
