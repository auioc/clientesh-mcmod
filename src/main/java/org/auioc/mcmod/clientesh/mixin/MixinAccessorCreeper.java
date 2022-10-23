package org.auioc.mcmod.clientesh.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.world.entity.monster.Creeper;

@Mixin(Creeper.class)
public interface MixinAccessorCreeper {

    @Accessor("swell")
    int getSwell();

    @Accessor("maxSwell")
    int getMaxSwell();

}
