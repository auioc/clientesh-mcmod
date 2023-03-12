package org.auioc.mcmod.clientesh.mixin;

import java.util.Map;
import org.auioc.mcmod.clientesh.event.CEForgeEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import net.minecraft.client.resources.language.ClientLanguage;

@Mixin(value = ClientLanguage.class)
public class MixinClientLanguage {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @ModifyArg(
        method = "Lnet/minecraft/client/resources/language/ClientLanguage;loadFrom(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;)Lnet/minecraft/client/resources/language/ClientLanguage;",
        at = @At(
            value = "INVOKE",
            target = "Lcom/google/common/collect/ImmutableMap;copyOf(Ljava/util/Map;)Lcom/google/common/collect/ImmutableMap;",
            remap = false
        ),
        index = 0,
        require = 1,
        allow = 1
    )
    private static Map loadFrom(Map map) {
        return CEForgeEventHandler.onLoadClientLanguage(map);
    }

}
