package org.auioc.mcmod.clientesh.content.tweak;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class CETweaksConfig {

    public static BooleanValue enablePauseScreenTweaks;

    public static void build(final ForgeConfigSpec.Builder b) {
        enablePauseScreenTweaks = b.define("enablePauseScreenTweaks", true);
    }

}
