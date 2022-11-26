package org.auioc.mcmod.clientesh.content.hud.element.function;

import org.auioc.mcmod.clientesh.api.hud.element.IOperableFunctionElement;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import org.auioc.mcmod.clientesh.api.hud.value.OperableValue;
import com.google.gson.JsonObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BiFunctionElement implements IOperableFunctionElement {

    protected final IOperableValue a;
    protected final IOperableValue b;

    public BiFunctionElement(JsonObject json) {
        this.a = OperableValue.parseValue(json.get("a"));
        this.b = OperableValue.parseValue(json.get("b"));
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static class Max extends BiFunctionElement {

        public Max(JsonObject json) { super(json); }

        @Override
        public IOperableValue apply() { return (a.doubleValue() >= b.doubleValue()) ? a : b; }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Min extends BiFunctionElement {

        public Min(JsonObject json) { super(json); }

        @Override
        public IOperableValue apply() { return (a.doubleValue() <= b.doubleValue()) ? a : b; }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Add extends BiFunctionElement implements IMathFunction {

        public Add(JsonObject json) { super(json); }

        @Override
        public double calculate() { return a.doubleValue() + b.doubleValue(); }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Subtract extends BiFunctionElement implements IMathFunction {

        public Subtract(JsonObject json) { super(json); }

        @Override
        public double calculate() { return a.doubleValue() - b.doubleValue(); }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Multiply extends BiFunctionElement implements IMathFunction {

        public Multiply(JsonObject json) { super(json); }

        @Override
        public double calculate() { return a.doubleValue() * b.doubleValue(); }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Divide extends BiFunctionElement implements IMathFunction {

        public Divide(JsonObject json) { super(json); }

        @Override
        public double calculate() { return a.doubleValue() / b.doubleValue(); }

    }

}
