package org.auioc.mcmod.clientesh.content.hud.command;

import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.clientesh.content.hud.config.CEHudLayoutConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CEHudCommand {

    public static final CommandNode<CommandSourceStack> NODE = literal("hud")
        .then(literal("reload").executes(CEHudCommand::reload))
        .build();

    private static int reload(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CEHudLayoutConfig.load();
        return Command.SINGLE_SUCCESS;
    }

}
