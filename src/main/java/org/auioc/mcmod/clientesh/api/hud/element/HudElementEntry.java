package org.auioc.mcmod.clientesh.api.hud.element;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record HudElementEntry(ResourceLocation id, IHudElementDeserializer deserializer) {

}
