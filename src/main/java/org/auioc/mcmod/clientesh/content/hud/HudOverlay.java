package org.auioc.mcmod.clientesh.content.hud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IllegalFormatException;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.HudConfig;
import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import org.auioc.mcmod.clientesh.api.hud.HudLines;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
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

    public static final HudOverlay INSTANCE = new HudOverlay();

    private HudOverlay() {}

    private static final Minecraft MC = Minecraft.getInstance();
    public static final String NAME = "ClientEsh Hud Overlay";

    private int leftColXOffset;
    private int leftColYOffset;
    private int rightColXOffset;
    private int rightColYOffset;
    private float scale;
    private Font font;
    private boolean background;
    private boolean fullBackground;
    private int backgroundColor;
    private int fontColor;
    private boolean chunkLoaded;

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if (MC.options.renderDebug) return;
        if (MC.cameraEntity == null) return;
        if (!HudConfig.enabled.get()) return;

        MC.getProfiler().push("clienteshHud");
        {
            this.leftColXOffset = HudConfig.leftColXOffset.get();
            this.leftColYOffset = HudConfig.leftColYOffset.get();
            this.rightColXOffset = HudConfig.rightColXOffset.get();
            this.rightColYOffset = HudConfig.rightColYOffset.get();
            this.scale = (float) (double) HudConfig.scale.get();
            this.font = MC.font;
            this.background = HudConfig.background.get();
            this.fullBackground = HudConfig.fullBackground.get();
            this.backgroundColor = HudConfig.backgroundColor.get();
            this.fontColor = HudConfig.fontColor.get();
            var pos = MC.cameraEntity.blockPosition();
            this.chunkLoaded = MC.level.getChunkSource().hasChunk(pos.getX() >> 4, pos.getZ() >> 4);

            poseStack.pushPose();
            {
                poseStack.scale(scale, scale, scale);
                drawText(poseStack, getLines(HudLines.getLeft()), leftColXOffset, leftColYOffset, false);
                drawText(poseStack, getLines(HudLines.getRight()), width - rightColXOffset, rightColYOffset, true);
            }
            poseStack.popPose();
        }
        MC.getProfiler().pop();
    }

    private ArrayList<Component> getLines(ArrayList<HudInfo> infoList) {
        var lines = new ArrayList<Component>();
        boolean waiting = false;
        for (var info : infoList) {
            if (!info.requiresChunk() || (info.requiresChunk() && this.chunkLoaded)) {
                try {
                    var text = info.getText();
                    if (text != null) Collections.addAll(lines, text);
                    else lines.add(null);
                } catch (IllegalFormatException e) {
                    lines.add(TextUtils.translatable(ClientEsh.i18n("hud._illegal_format"), e.getClass().getSimpleName()).withStyle(ChatFormatting.RED));
                }
            } else waiting = true;
        }
        if (waiting) {
            lines.add(null);
            lines.add(TextUtils.translatable(ClientEsh.i18n("hud._waiting_for_chunk")).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        }
        return lines;
    }

    private void drawText(PoseStack poseStack, ArrayList<Component> lines, final int x0, final int y0, boolean right) {
        if (lines.isEmpty()) return;

        if (this.background && this.fullBackground) drawFullBackground(poseStack, lines, x0, y0, right);

        int y = y0;
        for (int i = 0, l = lines.size(); i < l; ++i) {
            var line = lines.get(i);

            if (line != null) {
                int w = font.width(line);

                if (this.background && !this.fullBackground) {
                    int x1 = (right) ? (int) ((x0 + 1) / this.scale) : x0 - 1;
                    int y1 = y - 1;
                    int x2 = (right) ? (int) ((x0 - (w * this.scale) - 1) / this.scale) : x0 + w + 1;
                    int y2 = y + font.lineHeight + ((i + 1 == l) ? 0 : -1);
                    fill(poseStack, x1, y1, x2, y2, backgroundColor);
                }

                int x = (right) ? (int) ((x0 - (w * this.scale)) / this.scale) : x0;
                font.drawShadow(poseStack, line, x, y, this.fontColor);
            }

            y += this.font.lineHeight;
        }
    }

    private void drawFullBackground(PoseStack poseStack, ArrayList<Component> lines, final int x0, final int y0, boolean right) {
        int maxWidth = 0;
        for (var line : lines) {
            if (line != null) {
                int w = this.font.width(line);
                if (w > maxWidth) maxWidth = w;
            }
        }

        int height = this.font.lineHeight * lines.size() + 1;

        int x1 = (right) ? (int) ((x0 - (maxWidth * this.scale) - 1) / this.scale) : x0 - 1;
        int y1 = y0 - 1;
        int x2 = (right) ? (int) ((x0 + 1) / this.scale) : x0 + maxWidth + 1;
        int y2 = y0 + height - 1;
        fill(poseStack, x1, y1, x2, y2, this.backgroundColor);
    }

}
