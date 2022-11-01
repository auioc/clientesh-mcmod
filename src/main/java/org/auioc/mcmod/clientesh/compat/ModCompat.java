package org.auioc.mcmod.clientesh.compat;

import net.minecraftforge.fml.ModList;

public class ModCompat {

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static boolean isClassLoaded(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
