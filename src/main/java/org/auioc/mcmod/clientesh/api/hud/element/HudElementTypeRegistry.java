package org.auioc.mcmod.clientesh.api.hud.element;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.auioc.mcmod.arnicalib.game.registry.RegistryEntryException;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HudElementTypeRegistry {

    private static final Map<ResourceLocation, IHudElementDeserializer> R = new HashMap<>();

    public synchronized static HudElementEntry register(ResourceLocation id, IHudElementDeserializer deserializer) {
        R.put(id, deserializer);
        return new HudElementEntry(id, deserializer);
    }

    public static Optional<IHudElementDeserializer> get(ResourceLocation id) {
        return Optional.ofNullable(R.get(id));
    }

    public static IHudElementDeserializer getOrElseThrow(ResourceLocation id) {
        var v = R.get(id);
        if (v != null) return v;
        throw new RegistryEntryException.Unknown("hud element", id.toString());
    }


    public static record HudElementEntry(ResourceLocation id, IHudElementDeserializer deserializer) {

    }


    public static final HudElementEntry EMPTY = register(
        new ResourceLocation("clientesh:empty"),
        (j) -> new IHudElement() {
            @Override
            public Component getText() {
                return new TextComponent("");
            }
        }
    );

}
