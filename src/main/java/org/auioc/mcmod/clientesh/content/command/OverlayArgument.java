package org.auioc.mcmod.clientesh.content.command;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import org.auioc.mcmod.arnicalib.base.word.WordUtils;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.client.gui.OverlayRegistry.OverlayEntry;

// TODO
@OnlyIn(Dist.CLIENT)
public class OverlayArgument implements ArgumentType<OverlayEntry> {

    private static final DynamicCommandExceptionType UNKNOWN_OVERLAY = new DynamicCommandExceptionType(
        (name) -> TextUtils.literal(String.format("unknown overlay %s", name))
    );

    public static OverlayArgument overlay() {
        return new OverlayArgument();
    }

    public static OverlayEntry getOverlay(CommandContext<CommandSourceStack> ctx, String argument) {
        return ctx.getArgument(argument, OverlayEntry.class);
    }

    @Override
    public OverlayEntry parse(StringReader reader) throws CommandSyntaxException {
        String name = reader.readString();
        return getOverlays().filter((entry) -> formatName(entry.getDisplayName()).equals(name)).findAny().orElseThrow(() -> UNKNOWN_OVERLAY.create(name));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(getOverlays().map(OverlayEntry::getDisplayName).map(OverlayArgument::formatName), builder);
    }

    private static Stream<OverlayEntry> getOverlays() {
        return OverlayRegistry.orderedEntries().stream();
    }

    private static String formatName(String name) {
        return WordUtils.toCamelCase(name.toLowerCase().replace(" ", "_"));
    }

}
