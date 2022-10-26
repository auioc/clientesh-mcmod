package org.auioc.mcmod.clientesh.content.tweak;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.client.gui.OverlayRegistry.OverlayEntry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

@OnlyIn(Dist.CLIENT)
public class OverlayTweaks {

    public static void toggleOverlays() {
        var overlays = OverlayRegistry.orderedEntries();
        if (!overlays.isEmpty()) {
            var toggleMap = overlays.stream().collect(Collectors.partitioningBy((o) -> !Config.disabledOverlays.get().contains(o.getDisplayName())));
            toggle(toggleMap.get(true), true);
            toggle(toggleMap.get(false), false);
        }
    }

    private static void toggle(List<OverlayEntry> entries, boolean enable) {
        entries.stream().map(OverlayEntry::getOverlay).forEach((o) -> OverlayRegistry.enableOverlay(o, enable));
    }


    public static class Config {

        public static ConfigValue<List<? extends String>> disabledOverlays;

        public static void build(final ForgeConfigSpec.Builder b) {
            disabledOverlays = b.define("disabled_overlays", new ArrayList<String>(), OverlayTweaks::checkStringList);
        }

    }

    // TODO
    private static boolean checkStringList(Object o) {
        if (o instanceof ArrayList<?> v) {
            for (var i : v) if (!(i instanceof String)) return false;
            return true;
        }
        return false;
    }

}
