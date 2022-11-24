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
    public Component getText() {
        return (this.style != null) ? getRawText().withStyle(style) : getRawText();
    }

    protected abstract MutableComponent getRawText();

    public void setStyle(Style style) { this.style = style; }

    @Nullable
    public Style getStyle() { return this.style; }

    protected static MutableComponent format(java.lang.String format, Object... args) {
        return new TextComponent(java.lang.String.format(format, args));
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static abstract class Boolean extends AbsHudElement implements IOperableValue.Boolean {

        public Boolean() {}

        public Boolean(JsonObject json) { super(json); }

        @Override
        public abstract boolean booleanValue();

        @Override
        public MutableComponent getRawText() {
            return new TextComponent(java.lang.String.valueOf(booleanValue()));
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class Integer extends AbsHudElement implements IOperableValue.Integer {

        public Integer() {}

        public Integer(JsonObject json) { super(json); }

        public abstract int intValue();

        @Override
        public MutableComponent getRawText() {
            return new TextComponent(java.lang.String.valueOf(intValue()));
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class Double extends AbsHudElement implements IOperableValue.Double {

        public Double() {}

        public Double(JsonObject json) { super(json); }

        @Override
        public abstract double doubleValue();

        @Override
        public MutableComponent getRawText() {
            return new TextComponent(java.lang.String.valueOf(doubleValue()));
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static abstract class String extends AbsHudElement implements IOperableValue.String {

        public String() {}

        public String(JsonObject json) { super(json); }

        @Override
        public abstract java.lang.String stringValue();

        @Override
        public MutableComponent getRawText() {
            return new TextComponent(stringValue());
        }

    }

}
