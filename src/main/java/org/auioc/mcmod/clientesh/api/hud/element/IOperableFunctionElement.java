package org.auioc.mcmod.clientesh.api.hud.element;

import org.auioc.mcmod.clientesh.api.hud.element.OperableValueElement.BooleanElement;
import org.auioc.mcmod.clientesh.api.hud.element.OperableValueElement.DoubleElement;
import org.auioc.mcmod.clientesh.api.hud.element.OperableValueElement.IntegerElement;
import org.auioc.mcmod.clientesh.api.hud.element.OperableValueElement.StringElement;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IOperableFunctionElement extends IFunctionElement, IOperableValue {

    IOperableValue apply();

    @Override
    default IHudElement getResult() {
        var r = apply();
        if (r instanceof IHudElement v) return v;
        if (r instanceof StringValue v) return new StringElement(v.stringValue());
        if (r instanceof IntegerValue v) return new IntegerElement(v.intValue());
        if (r instanceof DoubleValue v) return new DoubleElement(v.doubleValue());
        if (r instanceof BooleanValue v) return new BooleanElement(v.booleanValue());
        return new NullHudElement();
    }

    @Override
    default double doubleValue() {
        return apply().doubleValue();
    }

    @Override
    default boolean booleanValue() {
        return apply().booleanValue();
    }

    @Override
    default boolean equals(IOperableValue other) {
        return apply().equals(other);
    }

}
