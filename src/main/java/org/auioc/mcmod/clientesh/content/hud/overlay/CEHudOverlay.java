package org.auioc.mcmod.clientesh.content.hud.overlay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.IFunctionElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.IMultilineElement;
import org.auioc.mcmod.clientesh.api.hud.layout.HudAlignment;
import org.auioc.mcmod.clientesh.api.hud.layout.HudLayout;
import org.auioc.mcmod.clientesh.content.hud.config.CEHudConfig;
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
public class CEHudOverlay extends GuiComponent implements IIngameOverlay {

    public static final CEHudOverlay INSTANCE = new CEHudOverlay();

    private CEHudOverlay() {}

    private static final Minecraft MC = Minecraft.getInstance();
    public static final String NAME = "ClientEsh Hud Overlay";

    private float scale;
    private Font font;
    private boolean background;
    private boolean fullBackground;
    private int backgroundColor;
    private int fontColor;

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if (MC.options.renderDebug) return;
        if (!CEHudConfig.enabled.get()) return;

        MC.getProfiler().push("clienteshHud");
        {
            if (MC.level == null) return;
            AbsHudElement.setLevel(MC.level);
            if (MC.player == null) return;
            AbsHudElement.setPlayer(MC.player);
            if (MC.cameraEntity == null) return;
            AbsHudElement.setCameraEntity(MC.cameraEntity);
            AbsHudElement.setWaiting(false);
        }
        {
            this.scale = CEHudConfig.scale.get().floatValue();
            this.font = MC.font;
            this.background = CEHudConfig.background.get().booleanValue();
            this.fullBackground = CEHudConfig.fullBackground.get().booleanValue();
            this.backgroundColor = CEHudConfig.backgroundColor.get().intValue();
            this.fontColor = CEHudConfig.fontColor.get().intValue();
        }
        {
            poseStack.pushPose();
            poseStack.scale(this.scale, this.scale, this.scale);
            render(poseStack, width, height);
            poseStack.popPose();
        }
        MC.getProfiler().pop();

    }

    private void render(PoseStack poseStack, int width, int height) {
        for (var column : HudLayout.getLayout().entrySet()) {
            var alignment = column.getKey();
            render(
                poseStack,
                getLines(column.getValue()),
                alignment, alignment.offsetX(width), alignment.offsetY(height)
            );
        }
    }

    private void render(PoseStack poseStack, ArrayList<Component> lines, HudAlignment alignment, int x0, int y0) {
        if (lines.isEmpty()) return;

        int wM = -1;
        if (this.background && this.fullBackground) {
            for (var line : lines) {
                int w = this.font.width(line);
                if (w > wM) wM = w;
            }
        }

        for (int i = 0, yI = 0, yC = lines.size(); i < yC; ++i) {
            var line = lines.get(i);
            if (line != null) {
                try {
                    render(poseStack, line, alignment, x0, y0, yI, yC, wM);
                } catch (Exception e) {
                    lines.set(i, errorMessage(e));
                    --i;
                    continue;
                }
                yI++;
            }
        }
    }

    private void render(PoseStack poseStack, Component line, HudAlignment alignment, int x0, int y0, int yI, int yC, int wM) {
        final int wL = this.font.width(line);
        final int wB = (wM < 0) ? wL : wM;
        if (wB <= 0) return;

        final int h = this.font.lineHeight;
        final float s = (float) this.scale;

        final int xL = (alignment.left) ? (x0) : ((int) ((x0 - (wL * s)) / s));
        final int yL = (alignment.top) ? (y0 + (yI * h)) : (y0 - ((yC - yI) * h) - 1);

        if (this.background) {
            final boolean eL = (yI + 1 == yC);
            int x1, y1, x2, y2;
            if (alignment.left) {
                x1 = x0 - 1;
                x2 = x0 + wB + 1;
            } else {
                x1 = (int) ((x0 + 1) / s);
                x2 = (int) ((x0 - (wB * s) - 1) / s);
            }
            if (alignment.top) {
                y1 = yL - 1;
                y2 = yL + h + (eL ? 0 : -1);
            } else {
                y1 = yL - 1;
                y2 = yL + h + (eL ? 0 : -1);
            }
            fill(poseStack, x1, y1, x2, y2, this.backgroundColor);
        }

        this.font.drawShadow(poseStack, line, xL, yL, this.fontColor);
    }

    // ====================================================================== //

    private static ArrayList<Component> getLines(List<List<IHudElement>> column) {
        final var lines = new ArrayList<Component>();
        f1: for (var row : column) {
            var line = TextUtils.empty();
            for (int i = 0, l = row.size(); i < l; ++i) {
                var element = IFunctionElement.resolve(row.get(i));
                if (IMultilineElement.resolve(element, (me) -> Collections.addAll(lines, me.getLines()))) continue f1;
                Component text;
                try {
                    text = element.getText();
                } catch (Exception e) {
                    text = errorMessage(e);
                }
                if (text == null) {
                    if (i == 0) {
                        lines.add(null);
                        continue f1;
                    }
                } else {
                    line.append(text);
                }
            }
            lines.add(line);
        }
        if (AbsHudElement.isWaiting()) {
            lines.add(TextUtils.empty());
            lines.add(TextUtils.literal("§7§oWaiting for chunk.."));
        }
        return lines;
    }

    private static Component errorMessage(Exception e) {
        return TextUtils.literal("(" + e.getClass().getSimpleName() + ": " + e.getMessage() + ")").withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
    }

}
