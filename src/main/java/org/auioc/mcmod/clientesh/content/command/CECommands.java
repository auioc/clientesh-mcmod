package org.auioc.mcmod.clientesh.content.command;

import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.arnicalib.game.command.AHCommands;
import org.auioc.mcmod.arnicalib.game.command.node.VersionCommand;
import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.content.command.impl.HandCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CECommands {

    private static final CommandNode<CommandSourceStack> NODE = literal(ClientEsh.MOD_ID).build();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        VersionCommand.addVersionNode(NODE, ClientEsh.class);
        NODE.addChild(HandCommand.NODE);

        AHCommands.getClientNode(dispatcher).addChild(NODE);
        dispatcher.register(literal(ClientEsh.MOD_ID).redirect(NODE));
    }

}
