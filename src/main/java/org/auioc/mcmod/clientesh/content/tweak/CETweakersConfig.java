package org.auioc.mcmod.clientesh.content.tweak;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class CETweakersConfig {

    public static BooleanValue enablePauseScreenTweaker;

    public static void build(final ForgeConfigSpec.Builder b) {
        enablePauseScreenTweaker = b.define("enablePauseScreenTweaker", true);
    }

}
