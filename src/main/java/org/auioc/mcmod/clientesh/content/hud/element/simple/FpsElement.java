package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsBooleanElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsIntegerElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.mixin.MixinAccessorMinecraft;
import com.google.gson.JsonObject;
import net.minecraft.client.Option;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FpsElement {

    public static IHudElement current(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return ((MixinAccessorMinecraft) MC).getFps(); }
        };
    }

    public static IHudElement limit(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() {
                return ((double) MC.options.framerateLimit == Option.FRAMERATE_LIMIT.getMaxValue()) ? 0 : MC.options.framerateLimit;
            }
        };
    }

    public static IHudElement vsync(JsonObject json) {
        return new AbsBooleanElement(json) {
            @Override
            public boolean value() { return MC.options.enableVsync; }
        };
    }

}
