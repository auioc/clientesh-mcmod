package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.content.widget.EnchantmentLevelNames;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;

@Mixin(value = Enchantment.class)
public class MixinEnchantment {

    @Redirect(
        method = "Lnet/minecraft/world/item/enchantment/Enchantment;getFullname(I)Lnet/minecraft/network/chat/Component;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/chat/MutableComponent;append(Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/MutableComponent;"
        ),
        require = 1,
        allow = 1
    )
    private MutableComponent getFullname_ModifyArgAppendLevelName(MutableComponent mutablecomponent, Component langBasedName, int p_44701_) {
        return mutablecomponent.append(EnchantmentLevelNames.getEnchantmentLevelName(p_44701_, langBasedName));
    }

}
