package org.auioc.mcmod.clientesh.content.widget;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.mixin.MixinAccessorCreeper;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.eventbus.api.Event.Result;

@OnlyIn(Dist.CLIENT)
public class ExplosionCountdown {

    public static void handle(final RenderNameplateEvent event) {
        var entity = event.getEntity();
        if (entity instanceof PrimedTnt tnt) {
            addCountdownText(event, tnt.getFuse());
        } else if (entity instanceof Creeper creeper && (creeper.getSwellDir() > 0 || creeper.isIgnited())) {
            addCountdownText(event, ((MixinAccessorCreeper) creeper).getMaxSwell() - ((MixinAccessorCreeper) creeper).getSwell());
        }
    }

    private static void addCountdownText(final RenderNameplateEvent event, int countdown) {
        var countdownStr = (Config.unit.get() == TimeUnit.TICK) ? String.valueOf(countdown) : String.format("%.1fs", countdown / 20.0D);
        var text = TextUtils.empty();
        if (event.getEntity().hasCustomName()) {
            text.append(event.getContent()).append(String.format(" (%s)", countdownStr));
        } else {
            text.append(countdownStr);
        }
        event.setContent(text);
        event.setResult(Result.ALLOW);
    }

    // TODO
    private static enum TimeUnit {
        TICK, SECOND
    }

    public static class Config {

        public static BooleanValue enabled;
        public static EnumValue<TimeUnit> unit;

        public static void build(final ForgeConfigSpec.Builder b) {
            enabled = b.define("enabled", true);
            unit = b.defineEnum("unit", TimeUnit.TICK);
        }

    }

}
