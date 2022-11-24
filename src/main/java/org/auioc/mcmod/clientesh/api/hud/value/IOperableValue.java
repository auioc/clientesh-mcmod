package org.auioc.mcmod.clientesh.api.hud.value;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IOperableValue {

    double doubleValue();

    boolean booleanValue();

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static interface Number extends IOperableValue {

        @Override
        default boolean booleanValue() {
            return doubleValue() == 0.0D;
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static interface Boolean extends IOperableValue {

        @Override
        default double doubleValue() {
            return booleanValue() ? 1.0D : 0.0D;
        }

    }

}