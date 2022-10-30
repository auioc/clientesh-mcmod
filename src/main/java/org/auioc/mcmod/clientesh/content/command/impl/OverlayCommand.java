package org.auioc.mcmod.clientesh.content.command.impl;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.command.argument.OverlayArgument;
import org.auioc.mcmod.arnicalib.game.gui.OverlayUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.OverlayRegistry;

@OnlyIn(Dist.CLIENT)
public class OverlayCommand {

    public static final CommandNode<CommandSourceStack> NODE = literal("overlay")
        .then(literal("list").executes(OverlayCommand::list))
        .then(
            literal("toggle").then(
                argument("overlay", OverlayArgument.overlay()).executes(OverlayCommand::toggle)
                    .then(argument("enabled", BoolArgumentType.bool()).executes(OverlayCommand::toggle))
            )
        )
        .build();


    private static int list(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var list = OverlayRegistry.orderedEntries();
        for (var entry : list) {
            ctx.getSource().sendSuccess(
                TextUtils.literal(
                    String.format("%s (%s): %b", entry.getDisplayName(), OverlayUtils.getName(entry), entry.isEnabled())
                ), false
            );
        }
        return list.size();
    }

    private static int toggle(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var overlay = OverlayArgument.getOverlay(ctx, "overlay");
        boolean enabled;
        try {
            enabled = BoolArgumentType.getBool(ctx, "enabled");
        } catch (IllegalArgumentException e) {
            enabled = !overlay.isEnabled();
        }
        OverlayRegistry.enableOverlay(overlay.getOverlay(), enabled);
        return Command.SINGLE_SUCCESS;
    }


}
