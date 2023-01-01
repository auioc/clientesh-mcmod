package org.auioc.mcmod.clientesh.content.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.auioc.mcmod.arnicalib.game.config.ConfigUtils;
import org.auioc.mcmod.clientesh.api.mixin.IMixinSubtitleOverlaySubtitle;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.components.SubtitleOverlay.Subtitle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;

@OnlyIn(Dist.CLIENT)
public class SubtitleHighlight {

    public static Component highlight(Subtitle subtitle) {
        var text = subtitle.getText();

        if (!Config.enabled.get()) return text;

        var mixinSubtitle = ((IMixinSubtitleOverlaySubtitle) subtitle);

        var langKey = (text instanceof TranslatableComponent _t) ? _t.getKey() : null;
        boolean flagLangKey = (langKey != null);
        var event = mixinSubtitle.getSoundEvent().toString();
        for (var pattern : Config.BLACKLIST) {
            if (pattern.matcher(event).matches()) return text;
            if (flagLangKey && pattern.matcher(langKey).matches()) return text;
        }

        var source = mixinSubtitle.getSource();
        if (Config.COLOR_MAP.containsKey(source)) {
            return new TextComponent("").withStyle(Config.COLOR_MAP.get(source).get()).append(text);
        }
        return text;
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
        public static ConfigValue<List<? extends String>> blacklist;
        private static final List<Pattern> BLACKLIST = new ArrayList<>();
        public static final Map<SoundSource, EnumValue<ChatFormatting>> COLOR_MAP = new HashMap<>();

        public static void build(final ForgeConfigSpec.Builder b) {
            enabled = b.define("enabled", true);
            blacklist = ConfigUtils.defineStringList(b, "blacklist");
            b.push("color_map");
            {
                defineColor(b, SoundSource.RECORDS, ChatFormatting.LIGHT_PURPLE);
                defineColor(b, SoundSource.WEATHER, ChatFormatting.BLUE);
                defineColor(b, SoundSource.BLOCKS, ChatFormatting.AQUA);
                defineColor(b, SoundSource.HOSTILE, ChatFormatting.RED);
                defineColor(b, SoundSource.NEUTRAL, ChatFormatting.GREEN);
                defineColor(b, SoundSource.PLAYERS, ChatFormatting.GOLD);
                defineColor(b, SoundSource.AMBIENT, ChatFormatting.AQUA);
            }
            b.pop();
        }

        private static EnumValue<ChatFormatting> defineColor(ForgeConfigSpec.Builder b, SoundSource source, ChatFormatting defaultColor) {
            return COLOR_MAP.put(
                source, b.defineEnum(
                    source.getName(),
                    defaultColor,
                    (o) -> (o instanceof ChatFormatting format) ? format.isColor() : (o instanceof String s) ? ChatFormatting.valueOf(s).isColor() : false
                )
            );
        }

        public static void onLoad(CommentedConfig config) {
            BLACKLIST.clear();
            blacklist.get().stream().map(Pattern::compile).forEach(BLACKLIST::add);
        }

    }

}
