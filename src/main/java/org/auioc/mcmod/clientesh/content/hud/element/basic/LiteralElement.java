package org.auioc.mcmod.clientesh.content.hud.element.basic;

import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LiteralElement extends AbsHudElement.String {

    private final java.lang.String text;

    public LiteralElement(java.lang.String text) {
        this.text = text;
    }

    public LiteralElement(JsonObject json) {
        super(json);
        this.text = GsonHelper.getAsString(json, "text");
    }

    @Override
    public java.lang.String stringValue() {
        return text;
    }

}
