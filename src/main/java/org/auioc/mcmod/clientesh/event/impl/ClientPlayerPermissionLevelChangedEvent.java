package org.auioc.mcmod.clientesh.event.impl;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;

@OnlyIn(Dist.CLIENT)
public class ClientPlayerPermissionLevelChangedEvent extends Event {

    private final int oldLevel;
    private final int newLevel;
    private final LocalPlayer player;

    public ClientPlayerPermissionLevelChangedEvent(LocalPlayer player, int oldLevel, int newLevel) {
        this.player = player;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }

    public LocalPlayer getPlayer() {
        return player;
    }

    public int getOldLevel() {
        return oldLevel;
    }

    public int getNewLevel() {
        return newLevel;
    }

}
