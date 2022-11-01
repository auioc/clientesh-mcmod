package org.auioc.mcmod.clientesh.compat;

public class HulsealibCompat {

    public static final boolean IS_LOADED = ModCompat.isModLoaded("hulsealib");
    public static final boolean FOUND_ADDITIONAL_SERVER_DATA = ModCompat.isClassLoaded("org.auioc.mcmod.hulsealib.game.client.AdditionalServerData");

    public static void init() {}

}
