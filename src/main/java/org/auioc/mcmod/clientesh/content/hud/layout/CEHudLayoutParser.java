package org.auioc.mcmod.clientesh.content.hud.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.auioc.mcmod.clientesh.api.hud.element.EmptyHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.HudElementTypeRegistry;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.NullHudElement;
import org.auioc.mcmod.clientesh.api.hud.layout.HudAlignment;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CEHudLayoutParser {

    public static Map<HudAlignment, List<List<IHudElement>>> parse(JsonObject json) {
        var columns = GsonHelper.getAsJsonObject(json, "columns");
        var layout = new HashMap<HudAlignment, List<List<IHudElement>>>(4);
        for (var alignment : HudAlignment.values()) {
            var jC = columns.get(alignment.camelName());
            if (jC != null && jC.isJsonArray()) {
                layout.put(alignment, parseColumn(jC.getAsJsonArray()));
            }
        }
        return layout;
    }

    public static List<List<IHudElement>> parseColumn(JsonArray json) {
        var side = new ArrayList<List<IHudElement>>();
        for (var jLine : json) side.add(parseRow(jLine.getAsJsonArray()));
        return side;
    }

    public static List<IHudElement> parseRow(JsonArray json) {
        var line = new ArrayList<IHudElement>();
        for (var jE : json) line.add(parseElement(jE));
        return line;
    }

    public static IHudElement parseElement(JsonObject json) {
        return (json.keySet().isEmpty())
            ? new EmptyHudElement()
            : HudElementTypeRegistry
                .getOrElseThrow(new ResourceLocation(GsonHelper.getAsString(json, "type")))
                .deserialize(json);
    }

    public static IHudElement parseElement(JsonElement json) {
        return (json == null || json.isJsonNull())
            ? new NullHudElement()
            : parseElement(json.getAsJsonObject());
    }

}
