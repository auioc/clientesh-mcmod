package org.auioc.mcmod.clientesh.content.hud.element.basic;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StyleElement extends AbsHudElement {

    public StyleElement(JsonObject json) {
        super(
            TextUtils.deserializeStyle(json),
            false
        );
    }

    @Override
    protected MutableComponent getRawText() {
        return TextUtils.empty();
    }

}
