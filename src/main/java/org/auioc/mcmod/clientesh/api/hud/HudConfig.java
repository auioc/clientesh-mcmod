package org.auioc.mcmod.clientesh.api.hud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.auioc.mcmod.arnicalib.base.word.WordUtils;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

@OnlyIn(Dist.CLIENT)
public class HudConfig {

    public static BooleanValue enabled;
    public static IntValue xOffset;
    public static IntValue yOffset;
    public static BooleanValue background;
    public static BooleanValue fullBackground;
    public static IntValue backgroundColor;
    public static IntValue fontColor;
    public static ConfigValue<List<? extends String>> left;
    public static ConfigValue<List<? extends String>> right;

    public static void build(final ForgeConfigSpec.Builder b) {
        enabled = b.define("enabled", true);

        b.push("render");
        {
            xOffset = b.defineInRange("xOffset", 2, 0, Integer.MAX_VALUE);
            yOffset = b.defineInRange("yOffset", 2, 0, Integer.MAX_VALUE);
            background = b.define("background", true);
            fullBackground = b.define("fullBackground", true);
            backgroundColor = b.defineInRange("backgroundColor", -1873784752, Integer.MIN_VALUE, Integer.MAX_VALUE);
            fontColor = b.defineInRange("fontColor", 14737632, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        b.pop();


        b.push("info");
        var allowedInfo = Arrays.asList(HudInfo.values()).stream().sorted(Comparator.comparing(Enum::name)).toList();
        {
            var allowedInfoNames = allowedInfo.stream().map(Enum::name).toList();
            left = b
                .comment("Allowed values: " + allowedInfoNames.stream().collect(Collectors.joining(", ")))
                .define("left", new ArrayList<String>(), (o) -> checkStringList(o, allowedInfoNames));
            right = b.define("right", new ArrayList<String>(), (o) -> checkStringList(o, allowedInfoNames));
        }
        {
            for (var row : allowedInfo) {
                if (row.hasConfig()) {
                    b.push(WordUtils.toCamelCase(row.name().toLowerCase()));
                    row.buildConfig(b);
                    b.pop();
                }
            }
        }
        b.pop();

    }

    public static void onLoad(CommentedConfig config) {
        List<String> l = config.get("hud.info.left");
        List<String> r = config.get("hud.info.right");
        if (l != null && r != null) HudLines.load(HudInfo.valueOf(l), HudInfo.valueOf(r));

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
