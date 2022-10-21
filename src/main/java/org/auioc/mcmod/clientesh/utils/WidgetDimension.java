package org.auioc.mcmod.clientesh.utils;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// TODO
@OnlyIn(Dist.CLIENT)
public class WidgetDimension {

    public int x;
    public int y;
    public int w;
    public int h;

    public static WidgetDimension zero() {
        return new WidgetDimension();
    }

}
