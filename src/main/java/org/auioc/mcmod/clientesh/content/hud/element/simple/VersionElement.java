package org.auioc.mcmod.clientesh.content.hud.element.simple;

import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsStringElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import com.google.gson.JsonObject;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VersionElement {

    public static IHudElement currentVersion(JsonObject json) {
        return new AbsStringElement(json) {
            @Override
            public String value() { return SharedConstants.getCurrentVersion().getName(); }
        };
    }

    public static IHudElement launchedVersion(JsonObject json) {
        return new AbsStringElement(json) {
            @Override
            public String value() { return MC.getLaunchedVersion(); }
        };
    }

    public static IHudElement clientModName(JsonObject json) {
        return new AbsStringElement(json) {
            @Override
            public String value() { return ClientBrandRetriever.getClientModName(); }
        };
    }

    public static IHudElement versionType(JsonObject json) {
        return new AbsStringElement(json) {
            @Override
            public String value() { return MC.getVersionType(); }
        };
    }

}
