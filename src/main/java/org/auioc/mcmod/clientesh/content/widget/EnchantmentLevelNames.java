package org.auioc.mcmod.clientesh.content.widget;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import org.auioc.mcmod.arnicalib.base.cache.LoadingCache;
import org.auioc.mcmod.arnicalib.base.cache.PlainLoadingCache;
import org.auioc.mcmod.arnicalib.base.math.NumberUtils;
import org.auioc.mcmod.arnicalib.base.math.NumeralUtils;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt.Type;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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

    /**
     * @see org.auioc.mcmod.clientesh.mixin.MixinEnchantment#getFullname_ModifyArgAppendLevelName
     */
    public static Component getEnchantmentLevelName(int lvl, Component langBasedName) {
        return ((lvl > 255 || lvl < 0) && Config.enabled.get() && Config.supportAllValues.get())
            ? new TextComponent(Config.type.get().get(lvl))
            : langBasedName;
    }

    /**
     * @see org.auioc.mcmod.clientesh.mixin.MixinEffectRenderingInventoryScreen#getEffectName
     */
    public static Component getEffectLevelName(int lvl) {
        return ((lvl > 128 || lvl < 0) && Config.enabled.get() && Config.supportAllValues.get())
            ? new TextComponent(Config.type.get().get(lvl))
            : new TranslatableComponent("enchantment.level." + lvl);
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public enum NumeralType implements IntFunction<String> {

        ARABIC {
            @Override
            public String apply(int lvl) { return String.valueOf(lvl); }
        },
        ROMAN {
            @Override
            public String apply(int lvl) { return (lvl < 0 ? "-" : "") + NumeralUtils.toRoman(lvl, true); }
        },
        CHINESE_BIG {
            @Override
            public String apply(int lvl) { return NumeralUtils.toChinese(lvl, true); }
        },
        CHINESE_SMALL {
            @Override
            public String apply(int lvl) { return NumeralUtils.toChinese(lvl, false); }
        },
        BINARY {
            @Override
            public String apply(int lvl) { return NumberUtils.toBinaryString(lvl, 32); }
        },
        HEXADECIMAL {
            @Override
            public String apply(int lvl) { return NumberUtils.toHexString(lvl, 8); }
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
        public static BooleanValue supportAllValues;
        public static EnumValue<NumeralType> type;

        public static void build(final ForgeConfigSpec.Builder b) {
            enabled = b.define("enabled", true);
            replace = b.define("replace", false);
            type = b.defineEnum("type", NumeralType.ROMAN);
            supportAllValues = b.define("support_all_values", true);
        }

    }

}
