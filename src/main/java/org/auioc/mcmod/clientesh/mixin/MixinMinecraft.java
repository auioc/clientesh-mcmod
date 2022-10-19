package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.api.mixin.IMixinMinecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class MixinMinecraft implements IMixinMinecraft {

    @Shadow
    private static int fps;

    @Override
    public int getFps() {
        return fps;
    }

}
