package org.auioc.mcmod.clientesh.content.hud.element.basic;

import java.util.ArrayList;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TranslatableElement extends AbsHudElement {

    private final java.lang.String key;
    private final Object[] args;

    public TranslatableElement(java.lang.String key, Object[] args) {
        this.key = key;
        this.args = args;
    }

    public TranslatableElement(java.lang.String key) {
        this(key, TextUtils.NO_ARGS);
    }

    public TranslatableElement(JsonObject json) {
        super(json);
        this.key = GsonHelper.getAsString(json, "key");
        Object[] args = TextUtils.NO_ARGS;
        if (GsonHelper.isArrayNode(json, "args")) {
            var jArgs = GsonHelper.getAsJsonArray(json, "args");
            if (!jArgs.isEmpty()) {
                var argList = new ArrayList<java.lang.String>(jArgs.size());
                jArgs.forEach((e) -> argList.add(e.getAsString()));
                args = argList.toArray();
            }
        }
        this.args = args;
    }

    @Override
    public MutableComponent getRawText() {
        return TextUtils.translatable(key, args);
    }

}
