package org.auioc.mcmod.clientesh.content.hud;

import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.HudConfig;
import org.auioc.mcmod.clientesh.api.hud.HudLines;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class HudOverlay extends GuiComponent implements IIngameOverlay {

    private static final Minecraft MC = Minecraft.getInstance();
    public static final String NAME = ClientEsh.MOD_NAME + HudOverlay.class.getSimpleName();
    private static final int BG_COLOR = -1873784752;
    private static final int FG_COLOR = 14737632;

    private Font font;

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if (MC.options.renderDebug) return;
        if (MC.cameraEntity == null) return;

        this.font = MC.font;

        int top = 2;
        for (var info : HudLines.getLeft()) {
            var text = info.getText();
            if (HudConfig.background.get()) fill(poseStack, 1, top - 1, 2 + font.width(text) + 1, top + font.lineHeight - 1, BG_COLOR);
            font.draw(poseStack, text, 2, top, FG_COLOR);
            top += font.lineHeight;
        }

        top = 2;
        for (var info : HudLines.getRight()) {
            var text = info.getText();
            int w = font.width(text);
            int left = width - 2 - w;
            fill(poseStack, left - 1, top - 1, left + w + 1, top + font.lineHeight - 1, BG_COLOR);
            font.draw(poseStack, text, left, top, FG_COLOR);
            top += font.lineHeight;
        }
    }

}