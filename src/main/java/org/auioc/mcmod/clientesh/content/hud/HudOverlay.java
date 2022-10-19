package org.auioc.mcmod.clientesh.content.hud;

import java.util.ArrayList;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.HudConfig;
import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import org.auioc.mcmod.clientesh.api.hud.HudLines;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

@OnlyIn(Dist.CLIENT)
public class HudOverlay extends GuiComponent implements IIngameOverlay {

    private static final Minecraft MC = Minecraft.getInstance();
    public static final String NAME = ClientEsh.MOD_NAME + HudOverlay.class.getSimpleName();

    private int xOffset;
    private int yOffset;
    private Font font;
    private boolean background;
    private boolean fullBackground;
    private int backgroundColor;
    private int fontColor;

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if (MC.options.renderDebug) return;
        if (MC.cameraEntity == null) return;

        this.xOffset = HudConfig.xOffset.get();
        this.yOffset = HudConfig.yOffset.get();
        this.font = MC.font;
        this.background = HudConfig.background.get();
        this.fullBackground = HudConfig.fullBackground.get();
        this.backgroundColor = HudConfig.backgroundColor.get();
        this.fontColor = HudConfig.fontColor.get();

        drawText(poseStack, getLines(HudLines.getLeft()), xOffset, yOffset, false);
        drawText(poseStack, getLines(HudLines.getRight()), width - xOffset, yOffset, true);

    }

    private static ArrayList<Component> getLines(ArrayList<HudInfo> infoList) {
        var lines = new ArrayList<Component>();
        infoList.stream().map(HudInfo::getText).forEach(lines::addAll);
        return lines;
    }

    private void drawText(PoseStack poseStack, ArrayList<Component> lines, final int x0, final int y0, boolean right) {
        if (this.background && this.fullBackground) drawFullBackground(poseStack, lines, x0, y0, right);

        int y = y0;
        for (int i = 0, l = lines.size(); i < l; ++i) {
            var line = lines.get(i);

            int w = font.width(line);

            if (this.background && !this.fullBackground) {
                int x1 = (right) ? x0 + 1 : x0 - 1;
                int y1 = y - 1;
                int x2 = (right) ? x0 - w - 1 : x0 + w + 1;
                int y2 = y + font.lineHeight + ((i + 1 == l) ? 0 : -1);
                fill(poseStack, x1, y1, x2, y2, backgroundColor);
            }

            int x = (right) ? x0 - w : x0;
            font.drawShadow(poseStack, line, x, y, this.fontColor);

            y += this.font.lineHeight;
        }
    }

    private void drawFullBackground(PoseStack poseStack, ArrayList<Component> lines, final int x0, final int y0, boolean right) {
        int maxWidth = 0;
        for (var line : lines) {
            int w = this.font.width(line);
            if (w > maxWidth) maxWidth = w;
        }

        int height = this.font.lineHeight * lines.size() + 1;

        int x1 = (right) ? x0 - maxWidth - 1 : x0 - 1;
        int y1 = y0 - 1;
        int x2 = (right) ? x0 + 1 : x0 + maxWidth + 1;
        int y2 = y0 + height - 1;
        fill(poseStack, x1, y1, x2, y2, this.backgroundColor);
    }

}
