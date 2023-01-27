package org.auioc.mcmod.clientesh.content.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.auioc.mcmod.arnicalib.game.config.ConfigUtils;
import org.auioc.mcmod.arnicalib.game.gui.component.CloseButton;
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
        if (event.getScreen() instanceof AbstractContainerScreen<?> screen) {
            var padding = Config.getPadding(screen);
            int x = screen.getGuiLeft() + screen.getXSize() - CloseButton.CROSS_SIZE - padding.x();
            int y = screen.getGuiTop() + padding.y();
            event.addListener(new CloseButton(x, y, screen));
        }
        return;
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    public static class Config {

        private static final Pattern PADDING_CONFIG_STRING_PATTERN = Pattern.compile("[\\w\\.\\$]+:\\d+,\\d+");
        private static final Padding DEFAULT_PADDING = new Padding(6, 6);

        public static BooleanValue enabled;
        public static ConfigValue<List<? extends String>> paddings;
        public static final Map<String, Padding> PADDING_MAP = new HashMap<>() {
            {
                put("net.minecraft.client.gui.screens.inventory.EnchantmentScreen", new Padding(8, 8));
            }
        };

        public static void build(final ForgeConfigSpec.Builder b) {
            enabled = b.define("enabled", true);
            paddings = b.defineListAllowEmpty(
                ConfigUtils.split("paddings"),
                Config::paddingMapToStringList,
                (o) -> (o instanceof String str) ? PADDING_CONFIG_STRING_PATTERN.matcher(str).matches() : false
            ); // TODO arnicalib
        }

        public static void onLoad(CommentedConfig config) {
            PADDING_MAP.clear();
            PADDING_MAP.putAll(paddingStringListToMapEntries());
        }

        public static <T extends AbstractContainerScreen<?>> Padding getPadding(T screen) {
            return PADDING_MAP.getOrDefault(screen.getClass().getName(), DEFAULT_PADDING);
        }

        private static List<String> paddingMapToStringList() {
            return PADDING_MAP.entrySet()
                .stream()
                .map((entry) -> String.format("%s:%d,%d", entry.getKey(), entry.getValue().x(), entry.getValue().y()))
                .toList();
        }

        private static Map<String, Padding> paddingStringListToMapEntries() {
            return paddings.get()
                .stream()
                .map((str) -> str.split(":|,"))
                .collect(
                    Collectors.toMap(
                        (strArr) -> strArr[0],
                        (strArr) -> new Padding(Integer.parseInt(strArr[1]), Integer.parseInt(strArr[2])),
                        (v1, v2) -> v1
                    )
                );
        }

    }

    // ============================================================================================================== //

    private static record Padding(int x, int y) {} // TODO arnicalib

}
