package org.auioc.mcmod.clientesh.api.hud.value;

import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IBooleanValue;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IDoubleValue;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IIntegerValue;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IStringValue;
import org.auioc.mcmod.clientesh.content.hud.layout.CEHudLayoutParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OperableValue {

    public static IOperableValue parseValue(JsonElement json, boolean objectOnly) {
        if (json != null) {
            if (!objectOnly) {
                if (GsonHelper.isNumberValue(json)) return new DoubleValue(json.getAsDouble());
                if (GsonHelper.isBooleanValue(json)) return new BooleanValue(json.getAsBoolean());
                if (GsonHelper.isStringValue(json)) return new StringValue(json.getAsString());
            }
            var element = CEHudLayoutParser.parseElement(json);
            if (element instanceof IOperableValue) return (IOperableValue) element;
            throw new JsonParseException("Not a operable value: " + json);
        }
        throw new JsonParseException("Not a operable value: null");
    }

    public static IOperableValue parseValue(JsonElement json) {
        return parseValue(json, false);
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static record DoubleValue(double value) implements IDoubleValue {
        @Override
        public double doubleValue() { return value; }
    }

    @OnlyIn(Dist.CLIENT)
    public static record IntegerValue(int value) implements IIntegerValue {
        @Override
        public int intValue() { return value; }
    }

    @OnlyIn(Dist.CLIENT)
    public static record BooleanValue(boolean value) implements IBooleanValue {
        @Override
        public boolean booleanValue() { return value; }
    }

    @OnlyIn(Dist.CLIENT)
    public static record StringValue(String value) implements IStringValue {
        @Override
        public String stringValue() { return value; }
    }

}
