package org.auioc.mcmod.clientesh.event;

import org.auioc.mcmod.clientesh.content.adapter.SeedGetter;
import org.auioc.mcmod.clientesh.content.command.CECommands;
import org.auioc.mcmod.clientesh.content.tweak.CETweakersConfig;
import org.auioc.mcmod.clientesh.content.tweak.PauseScreenTweaker;
import org.auioc.mcmod.clientesh.content.widget.AdditionalItemTooltip;
import org.auioc.mcmod.clientesh.event.impl.ClientPlayerPermissionLevelChangedEvent;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class CEForgeEventHandler {

    @SubscribeEvent
    public static void onRegisterCommand(final RegisterClientCommandsEvent event) {
        CECommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPostScreenInit(final ScreenEvent.InitScreenEvent.Post event) {
        var screen = event.getScreen();

        if (screen instanceof PauseScreen pauseScreen) {
            if (CETweakersConfig.enablePauseScreenTweaker.get()) PauseScreenTweaker.handle(event, pauseScreen);
        }
    }

    @SubscribeEvent
    public static void onLoggedOut(final ClientPlayerNetworkEvent.LoggedOutEvent event) {
        SeedGetter.clear();
    }

    @SubscribeEvent
    public static void onMessageReceived(final ClientChatReceivedEvent event) {
        SeedGetter.handleResultMessage(event);
    }

    @SubscribeEvent
    public static void onPermissionLevelChanged(final ClientPlayerPermissionLevelChangedEvent event) {
        if (event.getOldLevel() < 2 && event.getNewLevel() >= 2) SeedGetter.sendQueryCommand(event.getPlayer());
    }

    @SubscribeEvent
    public static void onItemTooltipEvent(final ItemTooltipEvent event) {
        AdditionalItemTooltip.handle(event);
    }

}
