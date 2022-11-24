package org.auioc.mcmod.clientesh.content.hud.element.basic;

import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LiteralHudElement extends AbsHudElement.Literal {

    private final String text;

    public LiteralHudElement(String text) {
        this.text = text;
    }

    public LiteralHudElement(JsonObject json) {
        super(json);
        this.text = GsonHelper.getAsString(json, "text");
    }

    @Override
    public String getString() {
        return text;
    }

}
