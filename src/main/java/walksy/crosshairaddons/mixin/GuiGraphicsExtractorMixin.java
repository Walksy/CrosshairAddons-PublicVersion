package walksy.crosshairaddons.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import main.walksy.lib.core.config.local.options.type.PixelGridAnimation;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.gui.GuiMetadataSection;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.crosshairaddons.CrosshairAddons;
import walksy.crosshairaddons.config.Config;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {

    @Shadow
    public abstract int guiWidth();

    @Shadow
    public abstract int guiHeight();

    @Shadow
    @Final
    private TextureAtlas guiSprites;

    @Shadow
    public abstract void blitSprite(RenderPipeline renderPipeline, TextureAtlasSprite sprite, int x, int y, int width, int height, int color);

    @Shadow
    protected abstract void blitTiledSprite(RenderPipeline renderPipeline, TextureAtlasSprite sprite, int x, int y, int width, int height, int textureX, int textureY, int tileWidth, int tileHeight, int spriteWidth, int spriteHeight, int color);

    @Shadow
    protected abstract void blitNineSlicedSprite(RenderPipeline renderPipeline, TextureAtlasSprite sprite, GuiSpriteScaling.NineSlice nineSlice, int x, int y, int width, int height, int color);

    @Shadow
    protected abstract void blitSprite(RenderPipeline renderPipeline, TextureAtlasSprite sprite, int spriteWidth, int spriteHeight, int textureX, int textureY, int x, int y, int width, int height, int color);

    @Shadow
    public abstract void enableScissor(int x0, int y0, int x1, int y1);

    @Shadow
    public abstract void disableScissor();

    @Shadow
    public abstract void blitSprite(RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, int color);

    @Unique
    private int getAttackIndicatorY() {
        int w = this.guiWidth();
        int h = this.guiHeight();

        int ix = w / 2 - 8;
        int iy = h / 2 - 7 + 16;

        int iL = ix, iR = ix + 16, iT = iy, iB = iy + 16;

        final int pad = 0;
        final int gap = Config.attackIndicatorGap;

        for (Map.Entry<Supplier<PixelGridAnimation>, Supplier<Boolean>> e : CrosshairAddons.getAddons().entrySet()) {
            Supplier<Boolean> enabledSupplier = e.getValue();
            if (enabledSupplier == null || !Boolean.TRUE.equals(enabledSupplier.get())) continue;

            PixelGridAnimation a = e.getKey().get();
            Vec2 p = a.getAbsolutePosition();
            int s = Math.max(1, Math.round(a.getSize() * 16f));

            int aL = (int) (p.x - pad);
            int aR = (int) (p.x + s + pad);
            int aT = (int) (p.y - pad);
            int aB = (int) (p.y + s + pad);

            if (aL < iR && aR > iL && aT < iB && aB > iT) {
                int candidateIy = aB + gap;
                if (candidateIy > iy) {
                    iy = candidateIy;
                    iT = iy;
                    iB = iy + 16;
                }
            }
        }

        int defaultIy = h / 2 - 7 + 16;
        return iy == defaultIy ? -1 : iy;
    }

    @Inject(method = "blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V",
            at = @At("HEAD"), cancellable = true)
    public void blitSprite(RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, int color, CallbackInfo ci) {
        if (!sprite.getPath().contains("hud/crosshair_attack")) return;

        int newY = this.getAttackIndicatorY();
        if (newY == -1) {
            return;
        }

        ci.cancel();
        TextureAtlasSprite sprite2 = this.guiSprites.getSprite(sprite);
        GuiSpriteScaling scaling = this.crosshairaddons$getSpriteScaling(sprite2);
        switch (scaling) {
            case GuiSpriteScaling.Stretch _ -> this.blitSprite(pipeline, sprite2, x, newY, width, height, color);
            case GuiSpriteScaling.Tile(int width1, int height1) -> this.blitTiledSprite(pipeline, sprite2, x, newY, width, height, 0, 0, width1, height1, width1, height1, color);
            case GuiSpriteScaling.NineSlice nineSlice -> this.blitNineSlicedSprite(pipeline, sprite2, nineSlice, x, newY, width, height, color);
            default -> {}
        }
    }

    @Inject(method = "blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIIII)V",
            at = @At("HEAD"), cancellable = true)
    public void blitSprite2(RenderPipeline pipeline, Identifier sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height, int color, CallbackInfo ci) {
        if (!sprite.getPath().contains("hud/crosshair_attack")) return;

        int newY = this.getAttackIndicatorY();
        if (newY == -1) {
            return;
        }

        ci.cancel();
        TextureAtlasSprite sprite2 = this.guiSprites.getSprite(sprite);
        GuiSpriteScaling scaling = this.crosshairaddons$getSpriteScaling(sprite2);
        if (scaling instanceof GuiSpriteScaling.Stretch) {
            this.blitSprite(pipeline, sprite2, textureWidth, textureHeight, u, v, x, newY, width, height, -1);
        } else {
            this.enableScissor(x, newY, x + width, newY + height);
            this.blitSprite(pipeline, sprite, x - u, newY - v, textureWidth, textureHeight, color);
            this.disableScissor();
        }
    }

    @Unique
    private GuiSpriteScaling crosshairaddons$getSpriteScaling(final TextureAtlasSprite sprite) {
        return ((GuiMetadataSection) sprite.contents().getAdditionalMetadata(GuiMetadataSection.TYPE).orElse(GuiMetadataSection.DEFAULT)).scaling();
    }
}
