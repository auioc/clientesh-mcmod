package org.auioc.mcmod.clientesh.config;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

@OnlyIn(Dist.CLIENT)
public class CEConfig {

    public static final ForgeConfigSpec SPEC;

    public static final BooleanValue enablePauseScreenTweaker;

    static {
        final ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();

        b.push("tweaks");
        enablePauseScreenTweaker = b.define("enablePauseScreenTweaker", true);
        b.pop();

        SPEC = b.build();
    }

}
