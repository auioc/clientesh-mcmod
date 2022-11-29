package org.auioc.mcmod.clientesh.api.hud.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HudLayout {

    private static final Map<HudAlignment, List<List<IHudElement>>> LAYOUT = new HashMap<>();

    public synchronized static Map<HudAlignment, List<List<IHudElement>>> getLayout() {
        return LAYOUT;
    }

    public synchronized static void clear() {
        LAYOUT.clear();
    }

    public synchronized static void load(Entry<HudAlignment, List<List<IHudElement>>> columnEntry) {
        if (columnEntry.getValue() != null) LAYOUT.put(columnEntry.getKey(), columnEntry.getValue());
    }

    public synchronized static void load(HudAlignment alignment, Optional<List<List<IHudElement>>> column) {
        column.ifPresent((c) -> LAYOUT.put(alignment, c));
    }

    public static void load(Map<HudAlignment, List<List<IHudElement>>> layout) {
        clear();
        for (var columnEntry : layout.entrySet()) load(columnEntry);
    }

    public static void load(HudAlignment alignment, List<List<IHudElement>> column) {
        load(alignment, Optional.ofNullable(column));
    }

}
