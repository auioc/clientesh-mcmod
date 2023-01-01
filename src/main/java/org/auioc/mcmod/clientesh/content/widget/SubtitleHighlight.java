package org.auioc.mcmod.clientesh.content.widget;

import java.util.HashMap;
import java.util.Map;
import org.auioc.mcmod.clientesh.api.mixin.IMixinSubtitleOverlaySubtitle;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.components.SubtitleOverlay.Subtitle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;

@OnlyIn(Dist.CLIENT)
public class SubtitleHighlight {

    public static Component highlight(Subtitle subtitle) {
        var text = subtitle.getText();
        if (!Config.enabled.get()) return text;
        var source = ((IMixinSubtitleOverlaySubtitle) subtitle).getSource();
        return new TextComponent("").withStyle(Config.COLOR_MAP.get(source).get()).append(text);
    }

    public static Component clearComponentColor(Component text) {
        return (Config.enabled.get())
            ? new TextComponent(text.getString()).setStyle(text.getStyle().withColor((TextColor) null))
            : text;
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

        public static BooleanValue enabled;
        public static final Map<SoundSource, EnumValue<ChatFormatting>> COLOR_MAP = new HashMap<>();

        private static final Map<SoundSource, ChatFormatting> DEFAULT_COLOR_MAP = new HashMap<>() {
            {
                // put(SoundSource.MASTER, ChatFormatting.WHITE);
                // put(SoundSource.MUSIC, ChatFormatting.WHITE);
                put(SoundSource.RECORDS, ChatFormatting.DARK_PURPLE);
                put(SoundSource.WEATHER, ChatFormatting.BLUE);
                put(SoundSource.BLOCKS, ChatFormatting.AQUA);
                put(SoundSource.HOSTILE, ChatFormatting.RED);
                put(SoundSource.NEUTRAL, ChatFormatting.GREEN);
                put(SoundSource.PLAYERS, ChatFormatting.GOLD);
                put(SoundSource.AMBIENT, ChatFormatting.AQUA);
                // put(SoundSource.VOICE, ChatFormatting.AQUA);
            }
        };

        public static void build(final ForgeConfigSpec.Builder b) {
            enabled = b.define("enabled", true);
            b.push("color_map");
            {
                b.push("source");
                {
                    for (var source : SoundSource.values()) {
                        COLOR_MAP.put(
                            source,
                            b.defineEnum(
                                source.getName(),
                                DEFAULT_COLOR_MAP.getOrDefault(source, ChatFormatting.WHITE),
                                (o) -> (o instanceof ChatFormatting format) ? format.isColor() : false
                            )
                        );
                    }
                }
                b.pop();
            }
            b.pop();
        }

    }

}
