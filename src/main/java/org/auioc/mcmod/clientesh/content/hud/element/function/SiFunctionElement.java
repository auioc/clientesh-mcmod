package org.auioc.mcmod.clientesh.content.hud.element.function;

import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.IOperableFunctionElement;
import org.auioc.mcmod.clientesh.api.hud.element.OperableValueElement.DoubleElement;
import org.auioc.mcmod.clientesh.api.hud.element.OperableValueElement.IntegerElement;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import org.auioc.mcmod.clientesh.api.hud.value.OperableValue;
import com.google.gson.JsonObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SiFunctionElement implements IOperableFunctionElement {

    protected final IOperableValue value;

    public SiFunctionElement(JsonObject json) {
        this.value = OperableValue.parseValue(json.get("value"));
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static class ToInt extends SiFunctionElement {

        public ToInt(JsonObject json) { super(json); }

        @Override
        public IOperableValue apply() {
            if (value instanceof IIntegerValue v) return new IntegerElement(v.intValue());
            return new IntegerElement((int) value.doubleValue());
        }

        @Override
        public IHudElement getResult() { return (IHudElement) apply(); }

    }

    @OnlyIn(Dist.CLIENT)
    public static class ToDouble extends SiFunctionElement {

        public ToDouble(JsonObject json) { super(json); }

        @Override
        public IOperableValue apply() { return new DoubleElement(value.doubleValue()); }

        @Override
        public IHudElement getResult() { return (IHudElement) apply(); }

    }

}
