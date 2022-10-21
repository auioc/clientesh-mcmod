package org.auioc.mcmod.clientesh.event;

import org.auioc.mcmod.clientesh.event.impl.ClientPlayerPermissionLevelChangedEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class CEForgeEventFactory {

    private static final IEventBus BUS = MinecraftForge.EVENT_BUS;

    // TODO
    public static void onPermissionLevelChanged(LocalPlayer player, int oldLevel, int newLevel) {
        BUS.post(new ClientPlayerPermissionLevelChangedEvent(player, oldLevel, newLevel));
    }

}
