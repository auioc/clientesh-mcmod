package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.world.LevelUtils;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.AbsCEHudElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.AbsCEHudElement.CEIntegerElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.AbsCEHudElement.CEStringElement;
import com.google.gson.JsonObject;
import net.minecraft.Util;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LevelElement {

    public static IHudElement skyLight(JsonObject json) {
        return new CEIntegerElement(json, true, -1) {
            @Override
            public int intValue(int defaultValue) { return level.getBrightness(LightLayer.SKY, blockPosition); }
        };
    }

    public static IHudElement blockLight(JsonObject json) {
        return new CEIntegerElement(json, true, -1) {
            @Override
            public int intValue(int defaultValue) { return level.getBrightness(LightLayer.BLOCK, blockPosition); }
        };
    }

    public static IHudElement biomeId(JsonObject json) {
        return new CEStringElement(json) {
            @Override
            public String stringValue(String defaultValue) {
                var b = level.getBiome(blockPosition).unwrapKey();
                return (b.isPresent()) ? b.get().location().toString() : "";
            };
        };
    }

    public static IHudElement biomeName(JsonObject json) {
        return new AbsCEHudElement(json, true) {
            @Override
            protected MutableComponent getRawText() { return (MutableComponent) LevelUtils.getBiomeName(level, blockPosition); };
        };
    }

    public static IHudElement dimensionId(JsonObject json) {
        return new CEStringElement(json) {
            @Override
            public String stringValue(String defaultValue) { return level.dimension().location().toString(); };
        };
    }

    // TODO arnicalib
    public static IHudElement dimensionName(JsonObject json) {
        return new AbsCEHudElement(json, true) {
            @Override
            protected MutableComponent getRawText() { return TextUtils.translatable(Util.makeDescriptionId("dimension", level.dimension().location())); };
        };
    }

}