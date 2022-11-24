package org.auioc.mcmod.clientesh.api.hud.element;

import com.google.gson.JsonObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@FunctionalInterface
public interface IHudElementDeserializer {

    IHudElement deserialize(JsonObject json);

}
