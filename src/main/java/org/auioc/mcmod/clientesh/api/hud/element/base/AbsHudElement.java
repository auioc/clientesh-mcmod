package org.auioc.mcmod.clientesh.api.hud.element.base;

import javax.annotation.Nullable;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbsHudElement implements IHudElement {

    private static final Style.Serializer STYLE_SERIALIZER = new Style.Serializer();

    protected static final Minecraft MC = Minecraft.getInstance();

    @Nullable
    protected Style style;

    public AbsHudElement() {}

    public AbsHudElement(JsonObject json) {
        this.style = (json.has("style")) ? STYLE_SERIALIZER.deserialize(json.get("style"), null, null) : null;
    }

    @Override
    public Component getText() {
        return (this.style != null) ? getRawText().withStyle(style) : getRawText();
    }

    protected abstract MutableComponent getRawText();

    public void setStyle(Style style) {
        this.style = style;
    }

    @Nullable
    public Style getStyle() {
        return this.style;
    }

    protected static MutableComponent format(String format, Object... args) {
        return new TextComponent(String.format(format, args));
    }

}
