package org.auioc.mcmod.clientesh.content.hud;

import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.base.log.LogUtil;
import org.auioc.mcmod.clientesh.ClientEsh;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class CEHud {

    public static final Marker MARKER = LogUtil.getMarker("Hud");

    public static void info(String msg) {
        ClientEsh.LOGGER.info(MARKER, msg);
    }

    public static void error(String msg, Throwable exception) {
        ClientEsh.LOGGER.error(MARKER, msg, exception);
    }

    public static String i18n(String key) {
        return ClientEsh.i18n("hud." + key);
    }

}
