package org.auioc.mcmod.clientesh.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.Minecraft;

@Mixin(value = Minecraft.class)
public interface MixinAccessorMinecraft {

    @Accessor("fps")
    int getFps();

}
