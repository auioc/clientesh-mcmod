package org.auioc.mcmod.clientesh.compat;

import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import org.auioc.mcmod.arnicalib.game.compat.ModCompat;

public final class HulseaCompat {

    public static final boolean IS_LOADED = ModCompat.isModLoaded("hulsealib");

    public static int getInt(IntSupplier ifLoaded, int fallback) {
        return IS_LOADED ? ifLoaded.getAsInt() : fallback;
    }

    public static double getDouble(DoubleSupplier ifLoaded, double fallback) {
        return IS_LOADED ? ifLoaded.getAsDouble() : fallback;
    }

}
