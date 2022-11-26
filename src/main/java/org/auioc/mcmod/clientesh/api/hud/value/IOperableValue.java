package org.auioc.mcmod.clientesh.api.hud.value;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IOperableValue {

    double doubleValue();

    boolean booleanValue();

    default boolean equals(IOperableValue other) {
        return this.doubleValue() == other.doubleValue();
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static interface IDoubleValue extends IOperableValue {

        @Override
        default boolean booleanValue() { return doubleValue() == 0.0D; }

    }

    @OnlyIn(Dist.CLIENT)
    public static interface IIntegerValue extends IOperableValue {

        int intValue();

        @Override
        default double doubleValue() { return intValue(); }

        @Override
        default boolean booleanValue() { return intValue() == 0; }

    }

    @OnlyIn(Dist.CLIENT)
    public static interface IBooleanValue extends IOperableValue {

        @Override
        default double doubleValue() { return booleanValue() ? 1.0D : 0.0D; }

    }

    @OnlyIn(Dist.CLIENT)
    public static interface IStringValue extends IIntegerValue {

        String stringValue();

        @Override
        default int intValue() { return stringValue().length(); }

        @Override
        default boolean equals(IOperableValue other) {
            return (other instanceof IStringValue _o) ? this.stringValue().equals(_o.stringValue()) : false;
        }

    }

}
