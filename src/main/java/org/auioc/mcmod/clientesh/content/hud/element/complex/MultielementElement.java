package org.auioc.mcmod.clientesh.content.hud.element.complex;

import javax.annotation.Nonnull;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.content.hud.layout.CEHudLayoutParser;
import org.auioc.mcmod.clientesh.utils.GsonHelper;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MultielementElement implements IHudElement {

    private final boolean inheritStyle;
    private final IHudElement[] elements;

    public MultielementElement(boolean inheritStyle, IHudElement... elements) {
        this.inheritStyle = inheritStyle;
        this.elements = elements;
    }

    public MultielementElement(JsonObject json) {
        this(
            GsonHelper.getAsBoolean(json, "inheritStyle", false),
            GsonHelper.getAsArray(json, "elements", IHudElement[]::new, CEHudLayoutParser::parseElement)
        );
    }

    @Override
    @Nonnull
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
