package org.auioc.mcmod.clientesh.content.hud.element.function;

import org.auioc.mcmod.clientesh.api.hud.element.IFunctionElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.NullHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.OperableValueElement.BooleanElement;
import org.auioc.mcmod.clientesh.api.hud.element.OperableValueElement.DoubleElement;
import org.auioc.mcmod.clientesh.api.hud.element.OperableValueElement.IntegerElement;
import org.auioc.mcmod.clientesh.api.hud.element.OperableValueElement.StringElement;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import org.auioc.mcmod.clientesh.api.hud.value.OperableValue;
import com.google.gson.JsonObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BiFunctionElement implements IFunctionElement, IOperableValue {

    protected final IOperableValue a;
    protected final IOperableValue b;

    public BiFunctionElement(JsonObject json) {
        this.a = OperableValue.parseValue(json.get("a"));
        this.b = OperableValue.parseValue(json.get("b"));
    }

    protected abstract IOperableValue apply();

    @Override
    public IHudElement getResult() {
        var r = apply();
        if (r instanceof IHudElement v) return v;
        if (r instanceof StringValue v) return new StringElement(v.stringValue());
        if (r instanceof IntegerValue v) return new IntegerElement(v.intValue());
        if (r instanceof DoubleValue v) return new DoubleElement(v.doubleValue());
        if (r instanceof BooleanValue v) return new BooleanElement(v.booleanValue());
        return new NullHudElement();
    }

    @Override
    public double doubleValue() { return apply().doubleValue(); }

    @Override
    public boolean booleanValue() { return apply().booleanValue(); }

    @Override
    public boolean equals(IOperableValue other) { return apply().equals(other); }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static class Max extends BiFunctionElement {

        public Max(JsonObject json) { super(json); }

        @Override
        protected IOperableValue apply() { return (a.doubleValue() >= b.doubleValue()) ? a : b; }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Min extends BiFunctionElement {

        public Min(JsonObject json) { super(json); }

        @Override
        protected IOperableValue apply() { return (a.doubleValue() <= b.doubleValue()) ? a : b; }

    }

}
