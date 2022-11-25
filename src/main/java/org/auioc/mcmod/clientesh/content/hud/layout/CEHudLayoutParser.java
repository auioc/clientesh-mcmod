package org.auioc.mcmod.clientesh.content.hud.layout;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.auioc.mcmod.clientesh.api.hud.element.EmptyHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.HudElementTypeRegistry;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.NullHudElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CEHudLayoutParser {

    public static Pair<List<List<IHudElement>>, List<List<IHudElement>>> parse(JsonObject json) {
        var columns = GsonHelper.getAsJsonObject(json, "columns");
        return Pair.of(
            parseColumn(GsonHelper.getAsJsonArray(columns, "left")),
            parseColumn(GsonHelper.getAsJsonArray(columns, "right"))
        );
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
