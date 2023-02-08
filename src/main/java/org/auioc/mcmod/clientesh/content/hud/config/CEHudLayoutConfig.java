package org.auioc.mcmod.clientesh.content.hud.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.auioc.mcmod.arnicalib.base.file.FileUtils;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.layout.HudAlignment;
import org.auioc.mcmod.clientesh.api.hud.layout.HudLayout;
import org.auioc.mcmod.clientesh.content.hud.CEHud;
import org.auioc.mcmod.clientesh.content.hud.element.basic.LiteralElement;
import org.auioc.mcmod.clientesh.content.hud.layout.CEHudLayoutParser;
import com.electronwill.nightconfig.core.file.FileWatcher;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;

@OnlyIn(Dist.CLIENT)
public class CEHudLayoutConfig {

    private static final String FILE_NAME = "clientesh-hud.json";
    private static final String DEFAULT = "{ \"columns\": {} }";

    public static void init() {
        loadFromFile();
        if (CEHudConfig.autoReloadLayout.get()) watchFile();
    }

    public static void loadFromFile() {
        try {
            HudLayout.load(CEHudLayoutParser.parse(readFile()));
        } catch (Exception e) {
            CEHud.error("Failed to load layout config", e);
            HudLayout.clear();
            HudLayout.load(HudAlignment.TOP_LEFT, createErrorMessage(e));
        }
    }

    private static List<List<IHudElement>> createErrorMessage(Exception e) {
        return List.of(
            List.of(new LiteralElement("§c§lError: " + e.getClass().getName())),
            List.of(new LiteralElement("  §c§l" + e.getMessage()))
        );
    }

    private static File getFile() {
        return FMLPaths.CONFIGDIR.get().resolve(FILE_NAME).toFile();
    }

    private synchronized static JsonObject readFile() {
        var file = getFile();
        try {
            var jsonString = FileUtils.readFileToString(file);
            return GsonHelper.parse(jsonString);
        } catch (FileNotFoundException e) {
            try {
                FileUtils.writeStringToFile(file, DEFAULT);
                return readFile();
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
                CEHudLayoutConfig.loadFromFile();
            });
        } catch (IOException e) {
            CEHud.error("Failed to watch config file", e);
        }
    }

}
