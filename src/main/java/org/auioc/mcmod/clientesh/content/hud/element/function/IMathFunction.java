package org.auioc.mcmod.clientesh.content.hud.element.function;

import org.auioc.mcmod.clientesh.api.hud.element.IOperableFunctionElement;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import org.auioc.mcmod.clientesh.api.hud.value.OperableValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IMathFunction extends IOperableFunctionElement {

    double calculate();

    @Override
    default IOperableValue apply() {
        return new OperableValue.DoubleValue(calculate());
    }

}
