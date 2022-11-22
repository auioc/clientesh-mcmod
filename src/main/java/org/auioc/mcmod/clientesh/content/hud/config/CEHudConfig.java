package org.auioc.mcmod.clientesh.content.hud.config;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

@OnlyIn(Dist.CLIENT)
public class CEHudConfig {

    public static BooleanValue enabled;
    public static ConfigValue<Integer> leftColXOffset;
    public static ConfigValue<Integer> leftColYOffset;
    public static ConfigValue<Integer> rightColXOffset;
    public static ConfigValue<Integer> rightColYOffset;
    public static DoubleValue scale;
    public static BooleanValue background;
    public static BooleanValue fullBackground;
    public static ConfigValue<Integer> backgroundColor;
    public static ConfigValue<Integer> fontColor;

    public static void build(final ForgeConfigSpec.Builder b) {
        enabled = b.define("enabled", true);

        b.push("render");
        {
            leftColXOffset = b.define("left.xOffset", 2, (o) -> validateScreenSize(o, false));
            leftColYOffset = b.define("left.yOffset", 2, (o) -> validateScreenSize(o, true));
            rightColXOffset = b.define("right.xOffset", 2, (o) -> validateScreenSize(o, false));
            rightColYOffset = b.define("right.yOffset", 2, (o) -> validateScreenSize(o, true));
            scale = b.defineInRange("scale", 1.0D, 0.0D, 4.0D);
            background = b.define("background", true);
            fullBackground = b.define("fullBackground", true);
            backgroundColor = b.define("backgroundColor", -1873784752);
            fontColor = b.define("fontColor", 14737632);
        }
        b.pop();
    }

    public static boolean validateScreenSize(Object o, boolean height) {
        if (Integer.class.isInstance(o)) {
            int i = (int) o;
            var windows = Minecraft.getInstance().getWindow();
            int max = (height) ? windows.getGuiScaledHeight() : windows.getGuiScaledWidth();
            return i >= 0 && i <= max;
        }
        return false;
    }

}
