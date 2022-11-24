package org.auioc.mcmod.clientesh.content.hud.element.complex;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.content.hud.config.CEHudLayoutConfig;
import org.auioc.mcmod.clientesh.utils.GsonHelper;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MultielementHudElement implements IHudElement {

    private final boolean inheritStyle;
    private final IHudElement[] elements;

    public MultielementHudElement(boolean inheritStyle, IHudElement... elements) {
        this.inheritStyle = inheritStyle;
        this.elements = elements;
    }

    public MultielementHudElement(JsonObject json) {
        this(
            GsonHelper.getAsBoolean(json, "inheritStyle", false),
            GsonHelper.getAsArray(
                json, "elements", IHudElement[]::new,
                (jE) -> CEHudLayoutConfig.loadElement(jE.getAsJsonObject())
            )
        );
    }

    @Override
    public Component getText() {
        var r = TextUtils.empty();
        if (inheritStyle) {
            MutableComponent prev = r;
            for (var e : elements) {
                var rawText = e.getText();
                var text = (rawText instanceof MutableComponent) ? (MutableComponent) rawText : TextUtils.empty().append(rawText);
                prev.append(text);
                prev = text;
            }
        } else {
            for (var e : elements) r.append(e.getText());
        }
        return r;
    }

}
