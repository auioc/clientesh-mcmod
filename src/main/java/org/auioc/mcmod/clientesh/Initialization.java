package org.auioc.mcmod.clientesh;


import org.auioc.mcmod.arnicalib.game.mod.ExtensionPointUtils;
import org.auioc.mcmod.clientesh.config.CEConfig;
import org.auioc.mcmod.clientesh.event.CEForgeEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused")
public final class Initialization {

    private Initialization() {}

    private static final IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
    private static final IEventBus FORGE_BUS = MinecraftForge.EVENT_BUS;

    public static void init() {
        ExtensionPointUtils.clientOnly();
        modSetup();
        forgeSetup();
        registerConfig();
    }

    private static void registerConfig() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CEConfig.SPEC);
    }

    private static void modSetup() {}

    private static void forgeSetup() {
        FORGE_BUS.register(CEForgeEventHandler.class);
    }

}
