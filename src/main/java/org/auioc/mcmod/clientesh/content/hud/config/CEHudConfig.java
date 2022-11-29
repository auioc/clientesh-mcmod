package org.auioc.mcmod.clientesh.content.hud.config;

import org.auioc.mcmod.clientesh.api.hud.layout.HudAlignment;
import com.electronwill.nightconfig.core.CommentedConfig;
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
    public static BooleanValue autoReloadLayout;
    public static DoubleValue scale;
    public static BooleanValue background;
    public static BooleanValue fullBackground;
    public static ConfigValue<Integer> backgroundColor;
    public static ConfigValue<Integer> fontColor;

    public static void build(final ForgeConfigSpec.Builder b) {
        enabled = b.define("enabled", true);

        b.push("layout");
        {
            autoReloadLayout = b.define("autoReload", true);
        }
        b.pop();

        b.push("render");
        {
            scale = b.defineInRange("scale", 1.0D, 0.0D, 4.0D);
            background = b.define("background", true);
            fullBackground = b.define("fullBackground", true);
            backgroundColor = b.define("backgroundColor", -1873784752);
            fontColor = b.define("fontColor", 14737632);
            for (var alignment : HudAlignment.values()) {
                b.push(alignment.camelName());
                {
                    b.define("xOffset", 2, (o) -> validateScreenSize(o, false));
                    b.define("yOffset", 2, (o) -> validateScreenSize(o, true));
                }
                b.pop();
            }
        }
        b.pop();
    }

    public static boolean validateScreenSize(Object o, boolean h) {
        if (Integer.class.isInstance(o)) {
            int i = (int) o;
            var windows = Minecraft.getInstance().getWindow();
            int max = (h) ? windows.getGuiScaledHeight() : windows.getGuiScaledWidth();
            return i >= 0 && i <= max;
        }
        return false;
    }

    public static void onLoad(CommentedConfig config) {
        config.getOptional("hud.render")
            .ifPresent((c0) -> {
                if (c0 instanceof CommentedConfig c1) {
                    for (var alignment : HudAlignment.values()) {
                        c1.getOptional(alignment.camelName())
                            .ifPresent((c2) -> {
                                if (c2 instanceof CommentedConfig c3) {
                                    alignment.xOffset(c3.getIntOrElse("xOffset", 2));
                                    alignment.yOffset(c3.getIntOrElse("yOffset", 2));
                                }
                            });
                    }
                }
            });
    }

}
