package org.auioc.mcmod.clientesh.api.hud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class HudConfig {

    public static BooleanValue background;
    public static ConfigValue<List<? extends String>> left;
    public static ConfigValue<List<? extends String>> right;

    public static void build(final ForgeConfigSpec.Builder b) {
        background = b.define("background", true);


        var allowedInfo = Arrays.asList(HudInfo.values()).stream().sorted(Comparator.comparing(Enum::name)).toList();
        var allowedInfoNames = allowedInfo.stream().map(Enum::name).toList();
        left = b
            .comment("Allowed values: " + allowedInfoNames.stream().collect(Collectors.joining(", ")))
            .define("left", new ArrayList<String>(), (o) -> checkStringList(o, allowedInfoNames));
        right = b
            .comment("Allowed values: See above")
            .define("right", new ArrayList<String>(), (o) -> checkStringList(o, allowedInfoNames));

        b.push("info");
        for (var row : allowedInfo) {
            if (row.hasConfig()) {
                b.push(row.name().toLowerCase());
                row.buildConfig(b);
                b.pop();
            }
        }
        b.pop();

    }

    @SuppressWarnings("unchecked")
    public static void onLoad(CommentedConfig config) {
        HudLines.load(
            HudInfo.valueOf((List<String>) config.get("hud.left")),
            HudInfo.valueOf((List<String>) config.get("hud.right"))
        );
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
