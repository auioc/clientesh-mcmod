package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.AbsCEHudElement.CEDoubleElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.AbsCEHudElement.CEIntegerElement;
import com.google.gson.JsonObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerElement {

    public static IHudElement airSupply(JsonObject json) {
        return new CEIntegerElement(json) {
            @Override
            public int intValue(int defaultValue) { return player.getAirSupply(); }
        };
    }

    public static IHudElement maxAirSupply(JsonObject json) {
        return new CEIntegerElement(json) {
            @Override
            public int intValue(int defaultValue) { return player.getMaxAirSupply(); }
        };
    }

    public static IHudElement frozenTicks(JsonObject json) {
        return new CEIntegerElement(json) {
            @Override
            public int intValue(int defaultValue) { return player.getTicksFrozen(); }
        };
    }

    public static IHudElement ticksRequiredToFreeze(JsonObject json) {
        return new CEIntegerElement(json) {
            @Override
            public int intValue(int defaultValue) { return player.getTicksRequiredToFreeze(); }
        };
    }

    public static IHudElement attackCooldown(JsonObject json) {
        return new CEDoubleElement(json) {
            @Override
            public double doubleValue(double defaultValue) { return player.getAttackStrengthScale(0.0F) * 100.0D; }
        };
    }

}
