package org.auioc.mcmod.clientesh.mixin;

import org.auioc.mcmod.clientesh.content.widget.EnchantmentLevelNames;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;

@Mixin(value = EffectRenderingInventoryScreen.class)
public class MixinEffectRenderingInventoryScreen {

    /**
     * @author WakelessSloth56
     * @reason {@link org.auioc.mcmod.clientesh.content.widget.EnchantmentLevelNames#getEffectLevelName}
     */
    @Overwrite
    private Component getEffectName(MobEffectInstance p_194001_) {
        MutableComponent mutablecomponent = p_194001_.getEffect().getDisplayName().copy();
        mutablecomponent
            .append(" ")
            .append(EnchantmentLevelNames.getEffectLevelName(p_194001_.getAmplifier() + 1));
        return mutablecomponent;
    }

}
