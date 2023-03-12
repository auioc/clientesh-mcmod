package org.auioc.mcmod.clientesh.content.widget;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import org.auioc.mcmod.arnicalib.base.cache.LoadingCache;
import org.auioc.mcmod.arnicalib.base.cache.PlainLoadingCache;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt.Type;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;

@OnlyIn(Dist.CLIENT)
public class EnchantmentLevelNames {

    public static void handleClientLanguage(Map<String, String> map) {
        if (!Config.enabled.get()) return;

        var names = Config.type.get().create256();
        if (Config.replace.get()) {
            map.putAll(names);
        } else {
            for (var name : names.entrySet()) {
                map.putIfAbsent(name.getKey(), name.getValue());
            }
        }
    }

    public static Component getLevelName(int lvl, Component langBasedName) {
        return (Config.enabled.get() && Config.supportHigherValues.get())
            ? new TextComponent(Config.type.get().get(lvl))
            : langBasedName;
    }

    // ============================================================================================================== //

    private static final int[] ROMAN_VALUES = {1000000, 900000, 500000, 400000, 100000, 90000, 50000, 40000, 10000, 9000, 5000, 4000, 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ROMAN_SYMBOLS = {"M·", "C·M·", "D·", "C·D·", "C·", "X·C·", "L·", "X·L·", "X·", "MX·", "V·", "MV·", "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"}; // "M\u0305", "C\u0305M\u0305",

    @OnlyIn(Dist.CLIENT)
    public enum NumeralType implements IntFunction<String> { // TODO ArnicaLib

        ARABIC {
            @Override
            public String apply(int lvl) { return String.valueOf(lvl); }
        },
        ROMAN {
            @Override
            public String apply(int lvl) {
                if (lvl > 3999999) return String.valueOf(lvl);
                if (lvl == 0) return "N";
                var sb = new StringBuilder();
                int i = 0;
                while (i < ROMAN_VALUES.length) {
                    while (lvl >= ROMAN_VALUES[i]) {
                        sb.append(ROMAN_SYMBOLS[i]);
                        lvl -= ROMAN_VALUES[i];
                    }
                    i++;
                }
                return sb.toString();
            }
        };

        private final LoadingCache<Integer, String> cache = new PlainLoadingCache<>((lvl) -> apply(lvl));

        public Map<String, String> create256() {
            var map = new HashMap<String, String>(256, 1);
            for (int lvl = 0; lvl < 256; ++lvl) {
                map.put("enchantment.level." + lvl, get(lvl));
            }
            return map;
        }

        public String get(int lvl) {
            return cache.get(lvl);
        }

    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    @CEConfigAt(type = Type.WIDGETS, path = "enchantment_level_names")
    public static class Config {

        public static BooleanValue enabled;
        public static BooleanValue replace;
        public static BooleanValue supportHigherValues;
        public static EnumValue<NumeralType> type;

        public static void build(final ForgeConfigSpec.Builder b) {
            enabled = b.define("enabled", true);
            replace = b.define("replace", false);
            type = b.defineEnum("type", NumeralType.ROMAN);
            supportHigherValues = b.define("support_higher_values", true);
        }

    }

}
