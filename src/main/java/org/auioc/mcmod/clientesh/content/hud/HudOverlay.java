package org.auioc.mcmod.clientesh.content.hud;

import java.util.ArrayList;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.HudConfig;
import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import org.auioc.mcmod.clientesh.api.hud.HudLines;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
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

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if (MC.options.renderDebug) return;
        if (MC.cameraEntity == null) return;

        final var font = MC.font;
        final boolean background = HudConfig.background.get();
        final int backgroundColor = HudConfig.backgroundColor.get();
        final int fontColor = HudConfig.fontColor.get();

        int top = 2;
        for (var text : getLines(HudLines.getLeft())) {
            if (background) fill(poseStack, 1, top - 1, 2 + font.width(text) + 1, top + font.lineHeight - 1, backgroundColor);
            font.drawShadow(poseStack, text, 2, top, fontColor);
            top += font.lineHeight;
        }

        top = 2;
        for (var text : getLines(HudLines.getRight())) {
            int w = font.width(text);
            int left = width - 2 - w;
            if (background) fill(poseStack, left - 1, top - 1, left + w + 1, top + font.lineHeight - 1, backgroundColor);
            font.drawShadow(poseStack, text, left, top, fontColor);
            top += font.lineHeight;
        }
    }

    private static ArrayList<Component> getLines(ArrayList<HudInfo> infoList) {
        var lines = new ArrayList<Component>();
        infoList.stream().map(HudInfo::getText).forEach(lines::addAll);
        return lines;
    }

}
