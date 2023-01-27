package org.auioc.mcmod.clientesh.content.widget;

import org.auioc.mcmod.arnicalib.game.gui.component.CloseButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

@OnlyIn(Dist.CLIENT)
public class ScreenCloseButton {

    public static void handle(ScreenEvent.InitScreenEvent.Post event) {
        if (event.getScreen() instanceof AbstractContainerScreen<?> screen) {
            int x = screen.getGuiLeft() + screen.getXSize() - CloseButton.CROSS_SIZE - 8;
            int y = screen.getGuiTop() + 8;
            event.addListener(new CloseButton(x, y, screen));
        }
        return;
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static class Config {

        public static BooleanValue enabled;

        public static void build(final ForgeConfigSpec.Builder b) {
            enabled = b.define("enabled", true);
        }

    }

}
