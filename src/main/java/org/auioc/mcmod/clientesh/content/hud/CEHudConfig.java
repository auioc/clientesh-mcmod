package org.auioc.mcmod.clientesh.content.hud;

import org.auioc.mcmod.arnicalib.game.world.position.SpeedUnit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;

@OnlyIn(Dist.CLIENT)
public class CEHudConfig {

    protected static class CoordinatesRC {

        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b
                .comment("1: x", "2: y", "3: z")
                .define("format", "%1$.3f / %2$.3f / %3$.3f");
        }

    }

    protected static class FacingRC {

        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b
                .comment("1: direction", "2: axis", "3: yaw", "4: pitch")
                .define("format", "%1$s (%2$s) (%3$.1f / %4$.1f)");
        }

    }

    protected static class BlockPositionRC {

        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b
                .comment("1: x", "2: y", "3: z", "4: in chunk x", "5: in chunk y", "6: in chunk z")
                .define("format", "%1$d %2$d %3$d [%4$d %5$d %6$d]");
        }

    }

    protected static class ChunkPositionRC {

        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b
                .comment("1: x", "2: y", "3: z", "4: slimechunk", "5: region local x", "6: region local z", "7: region file")
                .define("format", "%1$d %2$d %3$d %4$s [%5$d %6$d in %7$s]");
        }

    }

    protected static class SpeedRC {

        public static EnumValue<SpeedUnit> unit;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            unit = b.defineEnum("unit", SpeedUnit.METRES_PER_SECOND);
            format = b
                .comment("1: speed", "2: unit")
                .define("format", "%1$.3f (%2$s)");
        }

    }

    protected static class VelocityRC {

        public static EnumValue<SpeedUnit> unit;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            unit = b.defineEnum("unit", SpeedUnit.METRES_PER_SECOND);
            format = b
                .comment("1: x", "2: y", "3: z", "4: unit")
                .define("format", "%1$.3f / %2$.3f / %3$.3f (%4$s)");
        }

    }

    protected static class SystemTimeRC {

        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b
                .comment("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/text/SimpleDateFormat.html")
                .define("format", "yyyy-MM-dd'T'HH:mm:ssX");
        }

    }

    protected static class GameTimeRC {

        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b
                .comment("1: day", "2: hour", "3: minute")
                .define("format", "Day %1$d, %2$02d:%3$02d");
        }

    }

}
