package org.auioc.mcmod.clientesh.event;

import org.auioc.mcmod.clientesh.content.tweak.CETweakersConfig;
import org.auioc.mcmod.clientesh.content.tweak.PauseScreenTweaker;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class CEForgeEventHandler {

    @SubscribeEvent
    public static void onPostScreenInit(final ScreenEvent.InitScreenEvent.Post event) {
        var screen = event.getScreen();

        if (screen instanceof PauseScreen pauseScreen) {
            if (CETweakersConfig.enablePauseScreenTweaker.get()) PauseScreenTweaker.handle(event, pauseScreen);
        }
    }

}
