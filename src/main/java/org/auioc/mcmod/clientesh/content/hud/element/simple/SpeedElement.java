package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.arnicalib.game.world.position.SpeedUnit;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsDoubleElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpeedElement {

    public static IHudElement speed(JsonObject json) {
        return new AbsSpeedElement(json) {
            @Override
            public double rawValue() { return velocity.length(); }
        };
    }

    public static IHudElement velocityX(JsonObject json) {
        return new AbsSpeedElement(json) {
            @Override
            public double rawValue() { return velocity.x; }
        };
    }

    public static IHudElement velocityY(JsonObject json) {
        return new AbsSpeedElement(json) {
            @Override
            public double rawValue() { return velocity.y; }
        };
    }

    public static IHudElement velocityZ(JsonObject json) {
        return new AbsSpeedElement(json) {
            @Override
            public double rawValue() { return velocity.z; }
        };
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    private static abstract class AbsSpeedElement extends AbsDoubleElement {

        protected final SpeedUnit unit;

        public AbsSpeedElement(JsonObject json) {
            super(json);
            this.unit = SpeedUnit.valueOf(GsonHelper.getAsString(json, "unit", "METRES_PER_SECOND").toUpperCase());
        }

        protected abstract double rawValue();

        @Override
        public double value() { return unit.convertFrom(velocity.length()); }

        @Override
        public MutableComponent getRawText() {
            return format("%1$.3f %2$s", value(), unit.getSymbol());
        }

    }

}
