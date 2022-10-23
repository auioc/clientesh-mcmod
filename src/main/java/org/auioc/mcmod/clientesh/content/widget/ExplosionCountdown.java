package org.auioc.mcmod.clientesh.content.widget;

import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.clientesh.mixin.MixinAccessorCreeper;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.eventbus.api.Event.Result;

@OnlyIn(Dist.CLIENT)
public class ExplosionCountdown {

    public static void handle(final RenderNameplateEvent event) {
        var entity = event.getEntity();
        if (entity instanceof PrimedTnt tnt) {
            addCountdownText(event, tnt.getFuse());
        } else if (entity instanceof Creeper creeper && creeper.isIgnited()) {
            addCountdownText(event, ((MixinAccessorCreeper) creeper).getMaxSwell() - ((MixinAccessorCreeper) creeper).getSwell());
        }
    }

    private static void addCountdownText(final RenderNameplateEvent event, int countdown) {
        if (event.getEntity().hasCustomName()) {
            event.setContent(TextUtils.empty().append(event.getContent()).append(String.format(" (%d)", countdown)));
        } else {
            event.setContent(TextUtils.empty().append(String.valueOf(countdown)));
        }
        event.setResult(Result.ALLOW);
    }

}
