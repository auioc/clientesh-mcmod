package org.auioc.mcmod.clientesh.mixin;

import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;

@Mixin(AbstractClientPlayer.class)
public interface MixinAccessorAbstractClientPlayer {

    // @Nullable
    // @Invoker("getPlayerInfo")
    // PlayerInfo invokeGetPlayerInfo();

    @Nullable
    @Accessor("playerInfo")
    PlayerInfo getPlayerInfoDirectly();

}
