package org.auioc.mcmod.clientesh.api.hud.element.base;

import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbsIntegerHudElement extends AbsHudElement implements IOperableValue.Number {

    public AbsIntegerHudElement(JsonObject json) {
        super(json);
    }

    @Override
    public abstract double doubleValue();

    @Override
    public MutableComponent getRawText() {
        return new TextComponent(String.valueOf((int) doubleValue()));
    }

}
