package org.auioc.mcmod.clientesh.event;

import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.config.CEConfig;
import org.auioc.mcmod.clientesh.content.hud.config.CEHudLayoutConfig;
import org.auioc.mcmod.clientesh.content.hud.overlay.CEHudOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class CEModEventHandler {

    @SubscribeEvent
    public static void onConfigEvent(final ModConfigEvent event) {
        var config = event.getConfig();
        if (config.getModId().equals(ClientEsh.MOD_ID) && config.getType() == ModConfig.Type.CLIENT) {
            CEConfig.onLoad(config.getConfigData());
        }
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        OverlayRegistry.registerOverlayTop(CEHudOverlay.NAME, CEHudOverlay.INSTANCE);
        CEHudLayoutConfig.init();
    }

}
