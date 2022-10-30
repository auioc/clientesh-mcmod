package org.auioc.mcmod.clientesh.content.command.impl;

import static net.minecraft.commands.Commands.literal;
import java.util.ArrayList;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.command.CommandSourceUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HandCommand {

    public static final CommandNode<CommandSourceStack> NODE = literal("hand").executes(HandCommand::execute).build();

    @SuppressWarnings("deprecation")
    private static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = CommandSourceUtils.getLocalPlayerOrException(ctx.getSource());

        var stack = player.getMainHandItem();
        var item = stack.getItem();
        if (stack.isEmpty()) return Command.SINGLE_SUCCESS;

        var msg = new ArrayList<Component>();

        msg.add(TextUtils.copyable(stack.getHoverName()));
        msg.add(TextUtils.copyable(item.getRegistryName().toString()));
        item.builtInRegistryHolder().tags()
            .map(TagKey::location)
            .map(ResourceLocation::toString)
            .map((s) -> "#" + s)
            .map(TextUtils::copyable)
            .forEach(msg::add);
        if (stack.hasTag()) msg.add(TextUtils.copyable(NbtUtils.toPrettyComponent(stack.getTag())));

        for (var c : msg) ctx.getSource().sendSuccess(c, false);

        return Command.SINGLE_SUCCESS;
    }

}
