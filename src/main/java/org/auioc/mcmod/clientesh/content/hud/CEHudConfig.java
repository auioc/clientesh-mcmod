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
            format = b.define("format", "%.3f / %.3f / %.3f");
        }

    }

    protected static class SpeedRC {

        public static EnumValue<SpeedUnit> unit;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            unit = b.defineEnum("unit", SpeedUnit.METRES_PER_SECOND);
            format = b.define("format", "%.3f (%s)");
        }

    }

    protected static class VelocityRC {

        public static EnumValue<SpeedUnit> unit;
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            unit = b.defineEnum("unit", SpeedUnit.METRES_PER_SECOND);
            format = b.define("format", "%.3f / %.3f / %.3f (%s)");
        }

    }

    protected static class SystemTimeRC {

        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.define("format", "yyyy-MM-dd'T'HH:mm:ssX");
        }

    }

}
