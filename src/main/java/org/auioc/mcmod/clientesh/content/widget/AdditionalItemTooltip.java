package org.auioc.mcmod.clientesh.content.widget;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

@OnlyIn(Dist.CLIENT)
public class AdditionalItemTooltip {

    private static final Minecraft MC = Minecraft.getInstance();

    private static final Style GARY = Style.EMPTY.withColor(ChatFormatting.DARK_GRAY);

    public static void handle(final ItemTooltipEvent event) {
        if (!Config.enabled.get()) return;
        if (Config.onlyOnDebug.get() && !MC.options.advancedItemTooltips) return;
        if (Config.onlyOnShift.get() && !isShiftKeyDown()) return;

        ItemStack itemStack = event.getItemStack();
        if (itemStack.isEmpty()) return;

        if (itemStack.isEdible()) {
            var food = itemStack.getFoodProperties(MC.player);
            int nutrition = food.getNutrition();
            String saturation = String.format("%.1f", ((float) nutrition) * food.getSaturationModifier() * 2.0F);
            addLine(event, TextUtils.translatable(ClientEsh.i18n("additional_tooltip.nutrition"), nutrition).setStyle(GARY));
            addLine(event, TextUtils.translatable(ClientEsh.i18n("additional_tooltip.saturation"), saturation).setStyle(GARY));
            var effects = food.getEffects();
            if (!effects.isEmpty()) {
                addLine(event, TextUtils.translatable(ClientEsh.i18n("additional_tooltip.food_effect")).setStyle(GARY));
                for (Pair<MobEffectInstance, Float> pair : effects) {
                    addLine(event, foodEffect(pair.getSecond(), pair.getFirst().getEffect(), pair.getFirst().getDuration(), pair.getFirst().getAmplifier()));
                }
            } else if (itemStack.getItem() instanceof SuspiciousStewItem) {
                var nbt = itemStack.getTag();
                if (nbt != null && nbt.contains("Effects", 9)) {
                    var effectsTag = nbt.getList("Effects", 10);
                    if (!effectsTag.isEmpty()) {
                        addLine(event, TextUtils.translatable(ClientEsh.i18n("additional_tooltip.food_effect")).setStyle(GARY));
                        for (int i = 0, l = effectsTag.size(); i < l; ++i) {
                            // TODO
                            var effectNbt = effectsTag.getCompound(i);
                            int duration = 160;
                            if (effectNbt.contains("EffectDuration", 3)) duration = effectNbt.getInt("EffectDuration");
                            var effect = MobEffect.byId(effectNbt.getByte("EffectId"));
                            effect = net.minecraftforge.common.ForgeHooks.loadMobEffect(effectNbt, "forge:effect_id", effect);
                            addLine(event, foodEffect(1.0F, effect, duration, 0));
                        }
                    }
                }
            }
        }

        if (itemStack.hasTag()) {
            addLine(
                event,
                TextUtils.translatable(ClientEsh.i18n("additional_tooltip.nbt"))
                    .setStyle(GARY)
                    .append(
                        TextUtils.empty()
                            .setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE))
                            .append(NbtUtils.toPrettyComponent(itemStack.getTag()))
                    )
            );
        }

        var tags = itemStack.getTags().toList();
        if (tags.size() > 0) {
            addLine(event, TextUtils.translatable(ClientEsh.i18n("additional_tooltip.tag")).setStyle(GARY));
            for (var tag : tags) {
                addLine(event, TextUtils.literal("  " + tag.location()).setStyle(GARY));
            }
        }
    }

    // TODO
    private static Component foodEffect(float chance, MobEffect effect, int duration, int amplifier) {
        return TextUtils.literal("  ")
            .append(String.format("%.0f%% ", chance * 100.0F))
            .append(effect.getDisplayName())
            .append(" ")
            .append(TextUtils.translatable("enchantment.level." + (amplifier + 1)))
            .append(DurationFormatUtils.formatDuration(duration / 20 * 1000, " mm:ss", true))
            .setStyle(GARY);
    }

    private static void addLine(ItemTooltipEvent event, Component tooltip) {
        event.getToolTip().add(tooltip);
    }

    private static boolean isShiftKeyDown() {
        return InputConstants.isKeyDown(MC.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) ||
            InputConstants.isKeyDown(MC.getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT);
    }


    public static class Config {

        public static BooleanValue enabled;
        public static BooleanValue onlyOnDebug;
        public static BooleanValue onlyOnShift;

        public static void build(final ForgeConfigSpec.Builder b) {
            enabled = b.define("enabled", true);
            onlyOnDebug = b.define("only_on_debug", true);
            onlyOnShift = b.define("only_on_shift", false);
        }

    }

}
