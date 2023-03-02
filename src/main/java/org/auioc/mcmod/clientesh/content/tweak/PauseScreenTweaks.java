package org.auioc.mcmod.clientesh.content.tweak;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.gui.GuiUtils;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt.Type;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent.InitScreenEvent;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

@OnlyIn(Dist.CLIENT)
public class PauseScreenTweaks {

    public static void handle(final InitScreenEvent event, final PauseScreen screen) {
        var bFeedbackDim = new Rect2i(0, 0, 0, 0);
        GuiUtils.getButton(event, "menu.sendFeedback").ifPresent((b) -> backupAndRemove(event, bFeedbackDim, b));

        var bReportDim = new Rect2i(0, 0, 0, 0);
        GuiUtils.getButton(event, "menu.reportBugs").ifPresent((b) -> backupAndRemove(event, bReportDim, b));

        GuiUtils.getButton(event, "menu.options").ifPresent((b) -> {
            b.x = bFeedbackDim.getX();
            b.y = bFeedbackDim.getY();
        });

        event.addListener(
            new Button(
                bReportDim.getX(), bReportDim.getY(), bReportDim.getWidth(), bReportDim.getHeight(),
                TextUtils.translatable("fml.menu.mods"), (b) -> screen.getMinecraft().setScreen(new ModListScreen(screen))
            )
        );

        GuiUtils.getButton(event, "menu.shareToLan").ifPresent((b) -> {
            b.x = bFeedbackDim.getX();
            b.setWidth(bFeedbackDim.getWidth() + bReportDim.getWidth() + (bReportDim.getX() - bFeedbackDim.getWidth() - bFeedbackDim.getX()));
        });
    }

    private static void backupAndRemove(InitScreenEvent event, Rect2i rect, AbstractWidget widget) {
        rect.setX(widget.x);
        rect.setY(widget.y);
        rect.setWidth(widget.getWidth());
        rect.setHeight(widget.getHeight());
        event.removeListener(widget);
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    @CEConfigAt(type = Type.TWEAKS, path = "pause_screen")
    public static class Config {

        public static BooleanValue enabled;

        public static void build(final Builder b) {
            enabled = b.define("enabled", true);
        }

    }

}
