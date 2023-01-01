package org.auioc.mcmod.clientesh.content.widget;

import net.minecraft.Util;
import net.minecraft.client.gui.components.SubtitleOverlay.Subtitle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;

@OnlyIn(Dist.CLIENT)
public class SubtitleHighlight {

    public static Component highlight(Subtitle subtitle) {
        var text = subtitle.getText();
        // var source = ((IMixinSubtitleOverlaySubtitle) subtitle).getSource();
        return new TextComponent("").append(text);
    }

    // ====================================================================== //

    private static final float SCALE_2 = 75.0F / 255.0F;

    public static int adjustColor(int color, long time) {
        int r = (color >> 16) & 255;
        int g = (color >> 8) & 255;
        int b = color & 255;
        float t = (float) ((Util.getMillis() - time) / 3000.0F);
        int rL = Mth.floor(Mth.clampedLerp(r, r * SCALE_2, t));
        int gL = Mth.floor(Mth.clampedLerp(g, g * SCALE_2, t));
        int bL = Mth.floor(Mth.clampedLerp(b, b * SCALE_2, t));
        return rL << 16 | gL << 8 | bL;
    }

    // Coremod clientesh.subtitle_overlay.render
    public static int adjustColor(Component text, long time, int i2) {
        var color = text.getStyle().getColor();
        return (color == null) ? i2 : adjustColor(color.getValue(), time);
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static class Config {

        public static void build(final ForgeConfigSpec.Builder b) {

        }

    }

}
