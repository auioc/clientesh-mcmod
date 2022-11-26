package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.arnicalib.game.world.LevelUtils;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsIntegerElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsStringElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LevelElement {

    public static IHudElement skyLight(JsonObject json) {
        return new AbsIntegerElement(json, true, -1) {
            @Override
            public int value() { return level.getBrightness(LightLayer.SKY, blockPosition); }
        };
    }

    public static IHudElement blockLight(JsonObject json) {
        return new AbsIntegerElement(json, true, -1) {
            @Override
            public int value() { return level.getBrightness(LightLayer.BLOCK, blockPosition); }
        };
    }

    public static IHudElement biomeId(JsonObject json) {
        return new AbsStringElement(json) {
            @Override
            public String value() { return LevelUtils.getBiomeId(level, blockPosition).toString(); };
        };
    }

    public static IHudElement biomeName(JsonObject json) {
        return new AbsHudElement(json, true) {
            @Override
            protected MutableComponent getRawText() { return (MutableComponent) LevelUtils.getBiomeName(level, blockPosition); };
        };
    }

    public static IHudElement dimensionId(JsonObject json) {
        return new AbsStringElement(json) {
            @Override
            public String value() { return level.dimension().location().toString(); };
        };
    }

    public static IHudElement dimensionName(JsonObject json) {
        return new AbsHudElement(json, true) {
            @Override
            protected MutableComponent getRawText() { return (MutableComponent) LevelUtils.getDimensionName(level); };
        };
    }

}
