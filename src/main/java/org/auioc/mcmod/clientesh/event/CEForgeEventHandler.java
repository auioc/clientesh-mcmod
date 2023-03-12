package org.auioc.mcmod.clientesh.event;

import java.util.Map;
import org.auioc.mcmod.arnicalib.game.event.client.ClientPermissionsChangedEvent;
import org.auioc.mcmod.clientesh.content.adapter.SeedGetter;
import org.auioc.mcmod.clientesh.content.command.CECommands;
import org.auioc.mcmod.clientesh.content.tweak.OverlayTweaks;
import org.auioc.mcmod.clientesh.content.tweak.PauseScreenTweaks;
import org.auioc.mcmod.clientesh.content.tweak.ScreenEffectTweaks;
import org.auioc.mcmod.clientesh.content.widget.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
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
            if (PauseScreenTweaks.Config.enabled.get()) PauseScreenTweaks.handle(event, pauseScreen);
        }

        if (ScreenCloseButton.Config.enabled.get()) ScreenCloseButton.handle(event);
    }

    @SubscribeEvent
    public static void onLoggedIn(final ClientPlayerNetworkEvent.LoggedInEvent event) {
        OverlayTweaks.toggleOverlays();
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
    public static void onPermissionLevelChanged(final ClientPermissionsChangedEvent event) {
        if (event.getOldLevel() < 2 && event.getNewLevel() >= 2) SeedGetter.sendQueryCommand(event.getClientPlayer());
    }

    @SubscribeEvent
    public static void onItemTooltipEvent(final ItemTooltipEvent event) {
        AdditionalItemTooltip.handle(event);
    }

    @SubscribeEvent
    public static void onRenderNameplate(final RenderNameplateEvent event) {
        if (ExplosionCountdown.Config.enabled.get()) ExplosionCountdown.handle(event);
    }

    @SubscribeEvent
    public static void onRenderBlockOverlay(final RenderBlockOverlayEvent event) {
        switch (event.getOverlayType()) {
            case FIRE -> ScreenEffectTweaks.handleFireOverlay(event);
            case WATER -> ScreenEffectTweaks.handleWaterOverlay(event);
            case BLOCK -> ScreenEffectTweaks.handleBlockOverlay(event);
            default -> {}
        }
    }

    @SubscribeEvent
    public static void onMouseScroll(final InputEvent.MouseScrollEvent event) {
        final var minecraft = Minecraft.getInstance();

        boolean cancel = SpyglassZoom.onMouseScroll(minecraft, event.getScrollDelta());

        if (cancel) event.setCanceled(cancel);
    }

    // TODO ArnicaLib: LoadClientLanguageEvent
    public static Map<String, String> onLoadClientLanguage(Map<String, String> map) {
        EnchantmentLevelNames.handleClientLanguage(map);
        return map;
    }

}
