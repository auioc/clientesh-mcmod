package org.auioc.mcmod.clientesh.api.hud.element;

import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DefaultHudElement implements IHudElement, IOperableValue {

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
        return other instanceof DefaultHudElement;
    }

    @Override
    public Component getText() {
        return new TextComponent("");
    }

}
