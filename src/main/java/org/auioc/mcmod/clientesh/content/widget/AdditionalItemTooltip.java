package org.auioc.mcmod.clientesh.content.widget;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

@OnlyIn(Dist.CLIENT)
public class AdditionalItemTooltip {

    private static final Minecraft MC = Minecraft.getInstance();

    public static void handle(final ItemTooltipEvent event) {
        if (!Config.enabled.get()) return;
        if (Config.onlyOnDebug.get() && !MC.options.advancedItemTooltips) return;
        if (Config.onlyOnShift.get() && !isShiftKeyDown()) return;

        ItemStack itemStack = event.getItemStack();
        if (itemStack.isEmpty()) return;

        var darkGary = Style.EMPTY.withColor(ChatFormatting.DARK_GRAY);

        if (itemStack.hasTag()) {
            addLine(
                event,
                TextUtils.translatable(ClientEsh.i18n("additional_tooltip.nbt"))
                    .setStyle(darkGary)
                    .append(
                        TextUtils.empty()
                            .setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE))
                            .append(NbtUtils.toPrettyComponent(itemStack.getTag()))
                    )
            );
        }

        var tags = itemStack.getTags().toList();
        if (tags.size() > 0) {
            addLine(event, TextUtils.translatable(ClientEsh.i18n("additional_tooltip.tag")).setStyle(darkGary));
            for (var tag : tags) {
                addLine(event, TextUtils.literal("  " + tag.location()).setStyle(darkGary));
            }
        }
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
