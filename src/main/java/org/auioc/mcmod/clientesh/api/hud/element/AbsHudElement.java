package org.auioc.mcmod.clientesh.api.hud.element;

import javax.annotation.Nullable;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
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

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static abstract class Boolean extends AbsHudElement implements IOperableValue.Boolean {

        public Boolean(JsonObject json) {
            super(json);
        }

        @Override
        public abstract boolean booleanValue();

        @Override
        public MutableComponent getRawText() {
            return new TextComponent(String.valueOf(booleanValue()));
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class Integer extends AbsHudElement implements IOperableValue.Number {

        public Integer(JsonObject json) {
            super(json);
        }

        @Override
        public abstract double doubleValue();

        @Override
        public MutableComponent getRawText() {
            return new TextComponent(String.valueOf((int) doubleValue()));
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class Double extends AbsHudElement implements IOperableValue.Number {

        public Double(JsonObject json) {
            super(json);
        }

        @Override
        public abstract double doubleValue();

        @Override
        public MutableComponent getRawText() {
            return new TextComponent(String.valueOf(doubleValue()));
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class Literal extends AbsHudElement {

        public Literal(JsonObject json) {
            super(json);
        }

        public abstract String getString();

        @Override
        public MutableComponent getRawText() {
            return new TextComponent(getString());
        }

    }

}
