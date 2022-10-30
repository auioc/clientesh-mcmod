package org.auioc.mcmod.clientesh.content.tweak;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.gui.GuiComponentUtils;
import org.auioc.mcmod.arnicalib.game.gui.WidgetPlot;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent.InitScreenEvent;
import net.minecraftforge.client.gui.ModListScreen;

@OnlyIn(Dist.CLIENT)
public class PauseScreenTweaks {

    public static void handle(final InitScreenEvent event, final PauseScreen screen) {
        var bFeedbackDim = WidgetPlot.zero();
        GuiComponentUtils.getButton(event, "menu.sendFeedback").ifPresent((b) -> backupAndRemove(event, bFeedbackDim, b));

        var bReportDim = WidgetPlot.zero();
        GuiComponentUtils.getButton(event, "menu.reportBugs").ifPresent((b) -> backupAndRemove(event, bReportDim, b));

        GuiComponentUtils.getButton(event, "menu.options").ifPresent((b) -> {
            b.x = bFeedbackDim.x;
            b.y = bFeedbackDim.y;
        });

        event.addListener(
            new Button(
                bReportDim.x, bReportDim.y, bReportDim.w, bReportDim.h,
                TextUtils.translatable("fml.menu.mods"), (b) -> screen.getMinecraft().setScreen(new ModListScreen(screen))
            )
        );

        GuiComponentUtils.getButton(event, "menu.shareToLan").ifPresent((b) -> {
            b.x = bFeedbackDim.x;
            b.setWidth(bFeedbackDim.w + bReportDim.w + (bReportDim.x - bFeedbackDim.w - bFeedbackDim.x));
        });
    }

    private static void backupAndRemove(InitScreenEvent event, WidgetPlot dim, AbstractWidget widget) {
        dim.x = widget.x;
        dim.y = widget.y;
        dim.w = widget.getWidth();
        dim.h = widget.getHeight();
        event.removeListener(widget);
    }

}
