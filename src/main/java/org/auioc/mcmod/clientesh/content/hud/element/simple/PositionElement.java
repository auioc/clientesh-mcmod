package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsDoubleElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsIntegerElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import com.google.gson.JsonObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PositionElement {

    public static IHudElement positionX(JsonObject json) {
        return new AbsDoubleElement(json) {
            @Override
            public double value() { return position.x; }
        };
    }

    public static IHudElement positionY(JsonObject json) {
        return new AbsDoubleElement(json) {
            @Override
            public double value() { return position.y; }
        };
    }

    public static IHudElement positionZ(JsonObject json) {
        return new AbsDoubleElement(json) {
            @Override
            public double value() { return position.z; }
        };
    }

    public static IHudElement blockPositionX(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return blockPosition.getX(); }
        };
    }

    public static IHudElement blockPositionY(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return blockPosition.getY(); }
        };
    }

    public static IHudElement blockPositionZ(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return blockPosition.getZ(); }
        };
    }

}
