package org.auioc.mcmod.clientesh.content.hud.element.simple;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.auioc.mcmod.arnicalib.game.world.LevelUtils;
import org.auioc.mcmod.arnicalib.game.world.MCTimeUtils;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsIntegerElement;
import org.auioc.mcmod.clientesh.api.hud.element.AbsHudElement.AbsStringElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TimeElement {

    public static IHudElement gameTimeDay(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return MCTimeUtils.getDay(level.getDayTime()); }
        };
    }

    // TODO arnicalib
    public static IHudElement gameTimeHour(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return (int) (level.getDayTime() / 1000 + 6) % 24; }
        };
    }

    // TODO arnicalib
    public static IHudElement gameTimeMinute(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return (int) (level.getDayTime() % 1000) * 60 / 1000; }
        };
    }

    public static IHudElement moonphase(JsonObject json) {
        return new AbsIntegerElement(json) {
            @Override
            public int value() { return level.getMoonPhase(); }
        };
    }

    public static IHudElement moonphaseName(JsonObject json) {
        return new AbsHudElement(json) {
            @Override
            protected MutableComponent getRawText() { return (MutableComponent) LevelUtils.getMoonphaseName(level); }
        };
    }

    @OnlyIn(Dist.CLIENT)
    public static class SystemTimeElement extends AbsStringElement {

        private final SimpleDateFormat formatter;

        public SystemTimeElement(JsonObject json) {
            super(json);
            var format = GsonHelper.getAsString(json, "format", "yyyy-MM-dd'T'HH:mm:ssX");
            this.formatter = new SimpleDateFormat(format);
        }

        @Override
        public String value() {
            return formatter.format(new Date(System.currentTimeMillis()));
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class GameTimeElement extends AbsStringElement {

        private final String format;

        public GameTimeElement(JsonObject json) {
            super(json);
            this.format = GsonHelper.getAsString(json, "format", "%1$s %2$02d:%3$02d");
        }

        @Override
        public String value() {
            var time = MCTimeUtils.formatDayTime(level.getDayTime());
            return String.format(format, translatable("clientesh.hud.game_time.value.day", time[0]), time[1], time[2], time[3]);
        }

    }

}
