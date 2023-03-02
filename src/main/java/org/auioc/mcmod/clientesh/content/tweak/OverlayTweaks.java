package org.auioc.mcmod.clientesh.content.tweak;

import java.util.List;
import java.util.stream.Collectors;
import org.auioc.mcmod.arnicalib.game.config.ConfigUtils;
import org.auioc.mcmod.arnicalib.game.gui.OverlayUtils;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt;
import org.auioc.mcmod.clientesh.api.config.CEConfigAt.Type;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

@OnlyIn(Dist.CLIENT)
public class OverlayTweaks {

    public static void toggleOverlays() {
        var overlays = OverlayRegistry.orderedEntries();
        if (!overlays.isEmpty()) {
            var toggleMap = overlays.stream().collect(Collectors.partitioningBy((o) -> !Config.disabledOverlays.get().contains(o.getDisplayName())));
            OverlayUtils.enableOverlays(toggleMap.get(true), true);
            OverlayUtils.enableOverlays(toggleMap.get(false), false);
        }
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    @CEConfigAt(type = Type.TWEAKS, path = "overlay")
    public static class Config {

        public static ConfigValue<List<? extends String>> disabledOverlays;

        public static void build(final ForgeConfigSpec.Builder b) {
            disabledOverlays = ConfigUtils.defineStringList(b, "disabled_overlays");
        }

        public static void onLoad(CommentedConfig config) {
            OverlayTweaks.toggleOverlays();
        }

    }

}
