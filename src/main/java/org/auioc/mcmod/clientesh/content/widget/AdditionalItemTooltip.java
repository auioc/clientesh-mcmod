package org.auioc.mcmod.clientesh.content.widget;

import java.util.List;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.effect.MobEffectUtils;
import org.auioc.mcmod.arnicalib.game.entity.EntityUtils;
import org.auioc.mcmod.arnicalib.game.input.KeyDownRule;
import org.auioc.mcmod.arnicalib.game.item.ItemNbtUtils;
import org.auioc.mcmod.clientesh.ClientEsh;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

@OnlyIn(Dist.CLIENT)
public class AdditionalItemTooltip {

    private static final Minecraft MC = Minecraft.getInstance();

    private static final Style GARY = Style.EMPTY.withColor(ChatFormatting.DARK_GRAY);

    public static void handle(final ItemTooltipEvent event) {
        if (!Config.enabled.get()) return;
        if (Config.onlyOnDebug.get() && !MC.options.advancedItemTooltips) return;

        ItemStack itemStack = event.getItemStack();
        if (itemStack.isEmpty()) return;

        final List<Component> tooltip = event.getToolTip();

        var nbt = itemStack.getTag();

        if (Config.axolotlVariant.get().test()) {
            ItemNbtUtils.getAxolotlVariant(itemStack).ifPresent((variant) -> tooltip.add(1, translatable("axolotl.variant").append(EntityUtils.getAxolotlVariantName(variant)).withStyle(GARY)));
        }

        if (Config.foodProperties.get().test() && itemStack.isEdible()) {
            var food = itemStack.getFoodProperties(MC.player);
            int nutrition = food.getNutrition();
            String saturation = String.format("%.1f", ((float) nutrition) * food.getSaturationModifier() * 2.0F);
            tooltip.add(translatable("nutrition").append(String.valueOf(nutrition)).setStyle(GARY));
            tooltip.add(translatable("saturation").append(saturation).setStyle(GARY));
            var effects = food.getEffects();
            if (!effects.isEmpty()) {
                tooltip.add(translatable("food_effect").setStyle(GARY));
                for (Pair<MobEffectInstance, Float> pair : effects) {
                    tooltip.add(foodEffect(pair.getSecond(), pair.getFirst()));
                }
            } else {
                ItemNbtUtils.getSuspiciousStewEffects(itemStack).ifPresent((el) -> el.forEach((ep) -> tooltip.add(foodEffect(1.0F, ep.getLeft(), 0, ep.getRight()))));
            }
        }

        if (Config.nbt.get().test() && nbt != null) {
            tooltip.add(
                translatable("nbt").setStyle(GARY)
                    .append(
                        TextUtils.empty()
                            .setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE))
                            .append(NbtUtils.toPrettyComponent(nbt))
                    )
            );
        }

        if (Config.tags.get().test()) {
            var tags = itemStack.getTags().toList();
            if (tags.size() > 0) {
                tooltip.add(translatable("tag").setStyle(GARY));
                for (var tag : tags) {
                    tooltip.add(TextUtils.literal("  " + tag.location()).setStyle(GARY));
                }
            }
        }
    }

    private static TranslatableComponent translatable(String key) {
        return TextUtils.translatable(ClientEsh.i18n("additional_tooltip." + key));
    }


    private static Component foodEffect(float chance, MobEffect effect, int amplifier, int duration) {
        return TextUtils.literal("  ")
            .append(String.format("%.0f%% ", chance * 100.0F))
            .append(MobEffectUtils.getDisplayString(effect, amplifier, duration))
            .setStyle(GARY);
    }

    private static Component foodEffect(float chance, MobEffectInstance effect) {
        return foodEffect(chance, effect.getEffect(), effect.getAmplifier(), effect.getDuration());
    }


    public static class Config {

        public static BooleanValue enabled;
        public static BooleanValue onlyOnDebug;
        public static EnumValue<KeyDownRule> nbt;
        public static EnumValue<KeyDownRule> tags;
        public static EnumValue<KeyDownRule> foodProperties;
        public static EnumValue<KeyDownRule> axolotlVariant;

        public static void build(final ForgeConfigSpec.Builder b) {
            enabled = b.define("enabled", true);
            onlyOnDebug = b.define("only_on_debug", true);
            b.push("category");
            {
                nbt = b.defineEnum("nbt", KeyDownRule.ALWAYS);
                tags = b.defineEnum("tags", KeyDownRule.ALWAYS);
                foodProperties = b.defineEnum("food_properties", KeyDownRule.ALWAYS);
                axolotlVariant = b.defineEnum("axolotl_variant", KeyDownRule.ALWAYS);
            }
            b.pop();
        }

    }

}
