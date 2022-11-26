package org.auioc.mcmod.clientesh.api.hud.element;

import javax.annotation.Nonnull;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface OperableValueElement extends IOperableValue, IHudElement {

    @OnlyIn(Dist.CLIENT)
    public static record DoubleElement(double value) implements OperableValueElement, IDoubleValue {

        @Override
        public double doubleValue() { return value; }

        @Override
        @Nonnull
        public Component getText() { return new TextComponent(String.valueOf(value)); }

    }

    @OnlyIn(Dist.CLIENT)
    public static record IntegerElement(int value) implements OperableValueElement, IIntegerValue {

        @Override
        public int intValue() { return value; }

        @Override
        @Nonnull
        public Component getText() { return new TextComponent(String.valueOf(value)); }

    }

    @OnlyIn(Dist.CLIENT)
    public static record BooleanElement(boolean value) implements OperableValueElement, IBooleanValue {

        @Override
        public boolean booleanValue() { return value; }

        @Override
        @Nonnull
        public Component getText() { return new TextComponent(String.valueOf(value)); }

    }

    @OnlyIn(Dist.CLIENT)
    public static record StringElement(String value) implements OperableValueElement, IStringValue {

        @Override
        public String stringValue() { return value; }

        @Override
        @Nonnull
        public Component getText() { return new TextComponent(value); }

    }

}
