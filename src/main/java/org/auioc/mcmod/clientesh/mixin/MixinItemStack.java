package org.auioc.mcmod.clientesh.mixin;

import java.util.List;
import org.auioc.mcmod.arnicalib.game.enchantment.EnchantmentRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(value = ItemStack.class)
public class MixinItemStack {

    /**
     * TODO : Mixin lambda?
     *
     * @author WakelessSloth56
     * @reason Display the real enchantment level in the tooltip even if it is greater than 255.
     */
    @Overwrite
    public static void appendEnchantmentNames(List<Component> p_41710_, ListTag p_41711_) {
        for (int i = 0; i < p_41711_.size(); ++i) {
            CompoundTag compoundtag = p_41711_.getCompound(i);
            EnchantmentRegistry.get(EnchantmentHelper.getEnchantmentId(compoundtag)).ifPresent(
                (p_41708_) -> {
                    p_41710_.add(
                        p_41708_.getFullname(
                            //- EnchantmentHelper.getEnchantmentLevel(compoundtag)
                            compoundtag.getInt("lvl")
                        )
                    );
                }
            );
        }

    }

}
