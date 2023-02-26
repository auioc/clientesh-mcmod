package org.auioc.mcmod.clientesh.content.tweak;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

@OnlyIn(Dist.CLIENT)
public class ScopingTweaks {

    public static float getFovModifier() {
        return Config.fovModifier.get().floatValue();
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static class Config {

        public static DoubleValue fovModifier;

        public static void build(final ForgeConfigSpec.Builder b) {
            fovModifier = b.defineInRange("fovModifier", 0.1D, 0.0D, 1.0D);
        }

    }

}
