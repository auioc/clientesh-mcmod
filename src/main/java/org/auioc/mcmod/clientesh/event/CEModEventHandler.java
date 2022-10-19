package org.auioc.mcmod.clientesh.event;

import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.config.CEConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class CEModEventHandler {

    @SubscribeEvent
    public static void onConfigEvent(final ModConfigEvent event) {
        var config = event.getConfig();
        if (config.getModId().equals(ClientEsh.MOD_ID) && config.getType() == ModConfig.Type.CLIENT) {
            CEConfig.onLoad(config.getConfigData());
        }
    }

}
