package org.auioc.mcmod.clientesh.content.adapter;

import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.base.holder.ObjectHolder;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

@OnlyIn(Dist.CLIENT)
public class SeedGetter {

    private static final ObjectHolder<Long> SEED = new ObjectHolder<>(null);

    public static void clear() {
        SEED.set((Long) null);
    }

    public static void set(long seed) {
        SEED.set(seed);
    }

    public static boolean hasSeed() {
        return SEED.get() != null;
    }

    @Nullable
    public static Long get() {
        return SEED.get();
    }

    public static void sendQueryCommand(LocalPlayer player) {
        if (!hasSeed()) player.chat("/seed");
    }

    public static void handleResultMessage(final ClientChatReceivedEvent event) {
        if (
            !hasSeed()
                && event.getType() == ChatType.SYSTEM
                && event.getMessage() instanceof TranslatableComponent msg
                && msg.getKey().equals("commands.seed.success")
        ) {
            long seed = Long.valueOf(((TextComponent) (((TranslatableComponent) msg.getArgs()[0]).getArgs()[0])).getText());
            SEED.set(seed);
            event.setCanceled(true);
        }
    }

}
