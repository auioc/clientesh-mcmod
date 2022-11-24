package org.auioc.mcmod.clientesh.api.hud.element.base;

import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbsBooleanHudElement extends AbsHudElement implements IOperableValue.Boolean {

    public AbsBooleanHudElement(JsonObject json) {
        super(json);
    }

    @Override
    public abstract boolean booleanValue();

    @Override
    public MutableComponent getRawText() {
        return new TextComponent(String.valueOf(booleanValue()));
    }

}
