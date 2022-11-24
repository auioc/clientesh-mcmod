package org.auioc.mcmod.clientesh.content.hud.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.auioc.mcmod.arnicalib.base.file.FileUtils;
import org.auioc.mcmod.clientesh.api.hud.element.HudElementTypeRegistry;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.layout.HudLayout;
import org.auioc.mcmod.clientesh.content.hud.CEHud;
import org.auioc.mcmod.clientesh.content.hud.element.basic.LiteralHudElement;
import com.electronwill.nightconfig.core.file.FileWatcher;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fml.loading.FMLPaths;

public class CEHudLayoutConfig {

    private static final String FILE_NAME = "clientesh-hud.json";
    private static final String DEFAULT = "{ \"left\": [], \"right\": [] }";

    public static void load() {
        try {
            HudLayout.load(loadPair(loadFile()));
        } catch (Exception e) {
            CEHud.error("Failed to load layout config", e);
            HudLayout.load(createErrorMessage(e), null);
        }
    }

    private static List<List<IHudElement>> createErrorMessage(Exception e) {
        return List.of(
            List.of(new LiteralHudElement("§c§lError: " + e.getClass().getName())),
            List.of(new LiteralHudElement("  §c§l" + e.getMessage()))
        );
    }

    private static File getFile() {
        return FMLPaths.CONFIGDIR.get().resolve(FILE_NAME).toFile();
    }

    //TODO log exception
    private synchronized static JsonObject loadFile() {
        var file = getFile();
        try {
            var jsonString = FileUtils.readFileToString(file);
            return GsonHelper.parse(jsonString);
        } catch (FileNotFoundException e) {
            try {
                FileUtils.writeStringToFile(file, DEFAULT);
                return loadFile();
            } catch (IOException e1) {
                CEHud.error("Failed to create layout config file", e);
            }
        } catch (IOException e) {
            CEHud.error("Failed to read layout config file", e);
        }
        return null;
    }

    public static void watchFile() {
        try {
            FileWatcher.defaultInstance().addWatch(getFile(), () -> {
                CEHud.info("Layout config file changed, reloading");
                CEHudLayoutConfig.load();
            });
        } catch (IOException e) {
            CEHud.error("Failed to watch config file", e);
        }
    }

    public static Pair<List<List<IHudElement>>, List<List<IHudElement>>> loadPair(JsonObject json) {
        return Pair.of(
            loadSide(GsonHelper.getAsJsonArray(json, "left")),
            loadSide(GsonHelper.getAsJsonArray(json, "right"))
        );
    }

    private static List<List<IHudElement>> loadSide(JsonArray json) {
        var side = new ArrayList<List<IHudElement>>();
        for (var jLine : json) side.add(loadLine(jLine.getAsJsonArray()));
        return side;
    }

    private static List<IHudElement> loadLine(JsonArray json) {
        var line = new ArrayList<IHudElement>();
        for (var jHudE : json) line.add(loadElement(jHudE.getAsJsonObject()));
        return line;
    }

    public static IHudElement loadElement(JsonObject json) {
        var id = new ResourceLocation(GsonHelper.getAsString(json, "type"));
        var type = HudElementTypeRegistry.getOrElseThrow(id);
        return type.deserialize(json);
    }

}
