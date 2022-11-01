package org.auioc.mcmod.clientesh.mixin.compat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(targets = "org.auioc.mcmod.hulsealib.game.client.AdditionalServerData", remap = false)
public interface MixinHLAdditionalServerData {

    @Accessor("tps")
    public static double getTps() {
        throw new AssertionError();
    }

    @Accessor("mspt")
    public static double getMspt() {
        throw new AssertionError();
    }

}
