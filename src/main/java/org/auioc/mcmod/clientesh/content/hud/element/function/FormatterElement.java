package org.auioc.mcmod.clientesh.content.hud.element.function;

import javax.annotation.Nonnull;
import org.auioc.mcmod.arnicalib.game.data.GsonHelper;
import org.auioc.mcmod.clientesh.api.hud.element.IFunctionElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IBooleanValue;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IDoubleValue;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IIntegerValue;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue.IStringValue;
import org.auioc.mcmod.clientesh.content.hud.layout.CEHudLayoutParser;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FormatterElement implements IHudElement {

    private final String format;
    private final IHudElement[] elements;

    public FormatterElement(String format, IHudElement... element) {
        this.format = format;
        this.elements = element;
    }

    public FormatterElement(JsonObject json) {
        this(
            GsonHelper.getAsString(json, "format"),
            GsonHelper.getAsArray(json, "elements", IHudElement[]::new, CEHudLayoutParser::parseElement)
        );
    }

    @Override
    @Nonnull
    public Component getText() {
        var args = new Object[elements.length];
        for (int i = 0; i < elements.length; ++i) {
            var element = IFunctionElement.reslove(elements[i]);
            if (element instanceof IStringValue v) args[i] = v.stringValue();
            else if (element instanceof IIntegerValue v) args[i] = v.intValue();
            else if (element instanceof IDoubleValue v) args[i] = v.doubleValue();
            else if (element instanceof IBooleanValue v) args[i] = v.booleanValue();
            else {
                var text = element.getText();
                args[i] = (text != null) ? text.getString() : "";
            }
        }
        return new TextComponent(String.format(format, args));
    }

}
