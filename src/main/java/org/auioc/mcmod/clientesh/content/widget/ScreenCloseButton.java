package org.auioc.mcmod.clientesh.content.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.auioc.mcmod.arnicalib.base.tuple.IntPair;
import org.auioc.mcmod.arnicalib.game.config.ConfigUtils;
import org.auioc.mcmod.arnicalib.game.gui.component.ContainerCloseButton;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt.Type;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

@OnlyIn(Dist.CLIENT)
public class ScreenCloseButton {

    public static void handle(ScreenEvent.InitScreenEvent.Post event) {
        if (event.getScreen() instanceof AbstractContainerScreen<?> screen && !Config.isInBlacklist(screen)) {
            var padding = Config.getPadding(screen);
            event.addListener(new ContainerCloseButton(padding.x(), padding.y(), screen));
        }
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    @CEConfigAt(type = Type.WIDGETS, path = "screen_close_button")
    public static class Config {

        private static final Pattern PADDING_CONFIG_STRING_PATTERN = Pattern.compile("[\\w\\.\\$]+:\\d+,\\d+");
        private static final IntPair DEFAULT_PADDING = new IntPair(6, 6);

        public static BooleanValue enabled;
        public static ConfigValue<List<? extends String>> blacklist;
        public static ConfigValue<List<? extends String>> paddings;
        public static final Map<String, IntPair> PADDING_MAP = new HashMap<>() {
            {
                put("net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen", new IntPair(8, 8));
                put("net.minecraft.client.gui.screens.inventory.ContainerScreen", new IntPair(8, 8));
            }
        };

        public static void build(final ForgeConfigSpec.Builder b) {
            enabled = b.define("enabled", true);
            blacklist = ConfigUtils.defineStringList(
                b, "blacklist",
                () -> List.of("net.minecraft.client.gui.screens.inventory.BeaconScreen")
            );
            paddings = ConfigUtils.defineStringList(
                b, "paddings",
                Config::paddingMapToStringList,
                (str) -> PADDING_CONFIG_STRING_PATTERN.matcher(str).matches()
            );
        }

        public static void onLoad(CommentedConfig config) {
            PADDING_MAP.clear();
            PADDING_MAP.putAll(paddingStringListToMapEntries());
        }

        public static <T extends AbstractContainerScreen<?>> IntPair getPadding(T screen) {
            return PADDING_MAP.getOrDefault(screen.getClass().getName(), DEFAULT_PADDING);
        }

        public static <T extends AbstractContainerScreen<?>> boolean isInBlacklist(T screen) {
            return blacklist.get().contains(screen.getClass().getName());
        }

        private static List<String> paddingMapToStringList() {
            return PADDING_MAP.entrySet()
                .stream()
                .map((entry) -> String.format("%s:%d,%d", entry.getKey(), entry.getValue().x(), entry.getValue().y()))
                .toList();
        }

        private static Map<String, IntPair> paddingStringListToMapEntries() {
            return paddings.get()
                .stream()
                .map((str) -> str.split(":|,"))
                .collect(
                    Collectors.toMap(
                        (strArr) -> strArr[0],
                        (strArr) -> new IntPair(Integer.parseInt(strArr[1]), Integer.parseInt(strArr[2])),
                        (v1, v2) -> v1
                    )
                );
        }

    }

}
