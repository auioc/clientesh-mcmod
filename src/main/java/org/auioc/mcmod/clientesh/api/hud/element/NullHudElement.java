package org.auioc.mcmod.clientesh.api.hud.element;

import javax.annotation.Nullable;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NullHudElement implements IHudElement, IOperableValue {

    @Override
    public double doubleValue() {
        return 0;
    }

    @Override
    public boolean booleanValue() {
        return false;
    }

    @Override
    public boolean equals(IOperableValue other) {
        return other instanceof NullHudElement;
    }

    @Override
    @Nullable
    public Component getText() {
        return null;
    }

}
