package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.content.tweak.ItemTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;

@Mixin(value = PotionItem.class)
public abstract class MixinPotionItem extends Item {

    public MixinPotionItem(Properties properties) {
        super(properties);
    }

    /**
     * @deprecated 1.19.4 Pre1
     */
    @Deprecated()
    @Inject(
        method = "Lnet/minecraft/world/item/PotionItem;isFoil(Lnet/minecraft/world/item/ItemStack;)Z",
        at = @At("HEAD"),
        require = 1,
        allow = 1,
        cancellable = true
    )
    private void isFoil(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (!ItemTweaks.shouldPotionGlint()) {
            cir.setReturnValue(super.isFoil(itemStack));
        }
    }

}
