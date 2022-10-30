package org.auioc.mcmod.clientesh.content.hud.info;

import java.util.Date;
import org.auioc.mcmod.arnicalib.game.world.LevelUtils;
import org.auioc.mcmod.arnicalib.game.world.MCTimeUtils;
import org.auioc.mcmod.clientesh.api.hud.HudInfo;
import com.ibm.icu.text.SimpleDateFormat;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

@OnlyIn(Dist.CLIENT)
public class CEHudTimeInfo extends CEHudInfo {

    protected static void init() {}

    // ============================================================================================================== //

    public static final HudInfo SYSTEM_TIME = HudInfo.create("SYSTEM_TIME", SystemTimeRC::build, CEHudTimeInfo::systemTime);
    public static final HudInfo GAME_TIME = HudInfo.create("GAME_TIME", GameTimeRC::build, CEHudTimeInfo::gameTime, true);
    public static final HudInfo MOONPHASE = HudInfo.create("MOONPHASE", CEHudTimeInfo::moonphase, true);

    // ============================================================================================================== //
    //#region supplier

    private static Component systemTime() {
        return label("system_time").append(new SimpleDateFormat(SystemTimeRC.format.get()).format(new Date(System.currentTimeMillis())));
    }

    private static Component gameTime() {
        var t = MCTimeUtils.formatDayTime(level().getDayTime());
        return label("game_time").append(format(GameTimeRC.format, valueString("game_time", "day", t[0]), t[1], t[2], t[3]));
    }

    private static Component moonphase() {
        return label("moonphase").append(LevelUtils.getMoonphaseName(level()));
    }

    //#endregion supplier

    // ============================================================================================================== //
    //#region config

    private static class SystemTimeRC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b
                .comment("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/text/SimpleDateFormat.html")
                .define("format", "yyyy-MM-dd'T'HH:mm:ssX");
        }
    }

    private static class GameTimeRC {
        public static ConfigValue<String> format;

        public static void build(final Builder b) {
            format = b.comment("1s: day", "2d: hour", "3d: minute").define("format", "%1$s %2$02d:%3$02d");
        }
    }

    //#endregion config

}
