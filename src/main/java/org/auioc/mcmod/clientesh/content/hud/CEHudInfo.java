package org.auioc.mcmod.clientesh.content.hud;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.ClientEsh;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;

public class CEHudInfo {

    public static void init() {}

    private static final Minecraft MC = Minecraft.getInstance();


    private static MutableComponent label(String key) {
        return TextUtils.translatable(ClientEsh.i18n("hud.label.") + key);
    }

    private static MutableComponent str(String str) {
        return TextUtils.literal(str);
    }

    private static MutableComponent format(String format, Object... args) {
        return TextUtils.literal(String.format(format, args));
    }

}
