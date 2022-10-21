package org.auioc.mcmod.clientesh.api.hud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.auioc.mcmod.arnicalib.base.word.WordUtils;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

@OnlyIn(Dist.CLIENT)
public class HudConfig {

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
    public static ConfigValue<List<? extends String>> left;
    public static ConfigValue<List<? extends String>> right;

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


        b.push("info");
        var _allowedInfo = Arrays.asList(HudInfo.values()).stream().sorted(Comparator.comparing(Enum::name)).toList();
        {
            var _allowedInfoNames = _allowedInfo.stream().map(Enum::name).toList();
            left = b
                .comment("Allowed values: " + _allowedInfoNames.stream().collect(Collectors.joining(", ")))
                .define("left", new ArrayList<String>(), (o) -> checkStringList(o, _allowedInfoNames));
            right = b.define("right", new ArrayList<String>(), (o) -> checkStringList(o, _allowedInfoNames));
        }
        {
            for (var _row : _allowedInfo) {
                if (_row.hasConfig()) {
                    b.push(WordUtils.toCamelCase(_row.name().toLowerCase()));
                    _row.buildConfig(b);
                    b.pop();
                }
            }
        }
        b.pop();
    }

    public static void onLoad(CommentedConfig config) {
        List<String> l = config.get("hud.info.left");
        List<String> r = config.get("hud.info.right");
        if (l != null && r != null) {
            HudLines.load(HudInfo.valueOf(l), HudInfo.valueOf(r));
        }
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

    private static boolean checkStringList(Object o, List<String> t) {
        if (o instanceof ArrayList<?> v) {
            int r = 0;
            for (var i : v) {
                if (i instanceof String str) {
                    if (t.contains(str)) {
                        r++;
                    }
                }
            }
            return r == v.size();
        }
        return false;
    }

}
