package org.auioc.mcmod.clientesh.content.hud.info;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

@OnlyIn(Dist.CLIENT)
public class CEHudConfig {


    protected static class DimensionRC {

        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b
                .comment("1: name", "2: id")
                .define("format", "%1$s (%2$s)");
        }

    }
    protected static class BiomeRC {

        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b
                .comment("1: name", "2: id")
                .define("format", "%1$s (%2$s)");
        }

    }

    protected static class LightRC {

        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b
                .comment("1: sky", "2: block")
                .define("format", "sky %1$d, block %2$d");
        }

    }

    protected static class TargetedBlockRC {

        public static DoubleValue length;

        public static void build(final Builder b) {
            length = b.defineInRange("ray_length", 20.0D, 0.0D, Double.MAX_VALUE);
        }

    }


}
