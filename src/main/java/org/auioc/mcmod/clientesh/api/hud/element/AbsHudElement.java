package org.auioc.mcmod.clientesh.api.hud.element;

import javax.annotation.Nonnull;
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

    protected static final Minecraft MC = Minecraft.getInstance();

    // ====================================================================== //

    private static final Style.Serializer STYLE = new Style.Serializer();

    @Nullable
    private Style style;

    public AbsHudElement() {}

    public AbsHudElement(JsonObject json) {
        this.style = (json.has("style")) ? STYLE.deserialize(json.get("style"), null, null) : null;
    }

    @Override
    @Nonnull
    public Component getText() {
        return (this.style != null) ? getRawText().withStyle(style) : getRawText();
    }

    @Nonnull
    protected abstract MutableComponent getRawText();

    public void setStyle(Style style) { this.style = style; }

    @Nullable
    public Style getStyle() { return this.style; }

    protected static MutableComponent format(String format, Object... args) {
        return new TextComponent(String.format(format, args));
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static abstract class BooleanElement extends AbsHudElement implements IOperableValue.BooleanValue {

        public BooleanElement() {}

        public BooleanElement(JsonObject json) { super(json); }

        @Override
        public abstract boolean booleanValue();

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(booleanValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class IntegerElement extends AbsHudElement implements IOperableValue.IntegerValue {

        public IntegerElement() {}

        public IntegerElement(JsonObject json) { super(json); }

        public abstract int intValue();

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(intValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class DoubleElement extends AbsHudElement implements IOperableValue.DoubleValue {

        public DoubleElement() {}

        public DoubleElement(JsonObject json) { super(json); }

        @Override
        public abstract double doubleValue();

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(String.valueOf(doubleValue())); }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class StringElement extends AbsHudElement implements IOperableValue.StringValue {

        public StringElement() {}

        public StringElement(JsonObject json) { super(json); }

        @Override
        public abstract String stringValue();

        @Override
        @Nonnull
        public MutableComponent getRawText() { return new TextComponent(stringValue()); }

    }

}
