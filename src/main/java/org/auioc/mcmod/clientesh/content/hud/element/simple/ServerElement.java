package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.arnicalib.game.shared.ClientSharedData;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsDoubleElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsIntegerElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.mixin.MixinAccessorAbstractClientPlayer;
import com.google.gson.JsonObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ServerElement {

    // TODO support integrated server
    public static IHudElement tps(JsonObject json) {
        return new AbsDoubleElement(json) {
            @Override
            public double value() { return ClientSharedData.tps; }
        };
    }

    // TODO support integrated server
    public static IHudElement mspt(JsonObject json) {
        return new AbsDoubleElement(json) {
            @Override
            public double value() { return ClientSharedData.mspt; }
        };
    }

    public static IHudElement latency(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() {
                int latency = -1;
                var playerInfo = ((MixinAccessorAbstractClientPlayer) player).getPlayerInfoDirectly();
                if (playerInfo != null) latency = playerInfo.getLatency();
                return latency;
            }
        };
    }

}
