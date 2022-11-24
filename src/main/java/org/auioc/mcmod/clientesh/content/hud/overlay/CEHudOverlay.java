package org.auioc.mcmod.clientesh.content.hud.overlay;

import java.util.ArrayList;
import java.util.List;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.layout.HudLayout;
import org.auioc.mcmod.clientesh.content.hud.config.CEHudConfig;
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
public class CEHudOverlay extends GuiComponent implements IIngameOverlay {

    public static final CEHudOverlay INSTANCE = new CEHudOverlay();

    private CEHudOverlay() {}

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
    // private boolean chunkLoaded;

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if (MC.options.renderDebug) return;
        if (MC.cameraEntity == null) return;
        if (!CEHudConfig.enabled.get()) return;

        MC.getProfiler().push("clienteshHud");
        {
            this.leftColXOffset = CEHudConfig.leftColXOffset.get().intValue();
            this.leftColYOffset = CEHudConfig.leftColYOffset.get().intValue();
            this.rightColXOffset = CEHudConfig.rightColXOffset.get().intValue();
            this.rightColYOffset = CEHudConfig.rightColYOffset.get().intValue();
            this.scale = CEHudConfig.scale.get().floatValue();
            this.font = MC.font;
            this.background = CEHudConfig.background.get().booleanValue();
            this.fullBackground = CEHudConfig.fullBackground.get().booleanValue();
            this.backgroundColor = CEHudConfig.backgroundColor.get().intValue();
            this.fontColor = CEHudConfig.fontColor.get().intValue();
            // var pos = MC.cameraEntity.blockPosition();
            // this.chunkLoaded = MC.level.getChunkSource().hasChunk(pos.getX() >> 4, pos.getZ() >> 4);

            poseStack.pushPose();
            {
                poseStack.scale(this.scale, this.scale, this.scale);
                render(poseStack, getLines(HudLayout.getLeft()), this.leftColXOffset, this.leftColYOffset, false);
                render(poseStack, getLines(HudLayout.getRight()), width - this.rightColXOffset, this.rightColYOffset, true);
            }
            poseStack.popPose();
        }
        MC.getProfiler().pop();
    }

    private ArrayList<Component> getLines(List<List<IHudElement>> side) {
        var lines = new ArrayList<Component>();
        for (var lineElements : side) {
            var line = TextUtils.empty();
            for (var element : lineElements) {
                Component text;
                try {
                    text = element.getText();
                } catch (Exception e) {
                    text = TextUtils.literal("§c§l(" + e.getClass().getSimpleName() + ": " + e.getMessage() + ")");
                }
                line.append(text);
            }
            lines.add(line);
        }
        return lines;
    }

    private void render(PoseStack poseStack, ArrayList<Component> lines, int x0, int y0, boolean right) {
        if (lines.isEmpty()) return;

        if (this.background && this.fullBackground) {
            int maxW = 0;
            for (var line : lines) {
                int w = this.font.width(line);
                if (w > maxW) maxW = w;
            }
            int x1 = (right) ? (int) ((x0 - (maxW * this.scale) - 1) / this.scale) : (x0 - 1);
            int y1 = y0 - 1;
            int x2 = (right) ? (int) ((x0 + 1) / this.scale) : (x0 + maxW + 1);
            int y2 = y0 + (this.font.lineHeight * lines.size() + 1) - 1;
            fill(poseStack, x1, y1, x2, y2, this.backgroundColor);
        }

        int y = y0;
        for (int i = 0, l = lines.size(); i < l; ++i) {
            var line = lines.get(i);
            if (line != null) {
                int w = this.font.width(line);
                if (w > 0) {
                    if (this.background && !this.fullBackground) {
                        int x1 = (right) ? (int) ((x0 + 1) / this.scale) : (x0 - 1);
                        int y1 = y - 1;
                        int x2 = (right) ? (int) ((x0 - (w * this.scale) - 1) / this.scale) : (x0 + w + 1);
                        int y2 = y + font.lineHeight + ((i + 1 == l) ? 0 : -1);
                        fill(poseStack, x1, y1, x2, y2, this.backgroundColor);
                    }
                    int x = (right) ? (int) ((x0 - (w * this.scale)) / this.scale) : x0;
                    this.font.drawShadow(poseStack, line, x, y, this.fontColor);
                }
            }
            y += this.font.lineHeight;
        }
    }

}