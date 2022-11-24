package org.auioc.mcmod.clientesh.content.hud.element;

import org.auioc.mcmod.clientesh.ClientEsh;
import org.auioc.mcmod.clientesh.api.hud.element.HudElementTypeRegistry;
import org.auioc.mcmod.clientesh.api.hud.element.HudElementTypeRegistry.HudElementEntry;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElementDeserializer;
import org.auioc.mcmod.clientesh.content.hud.element.basic.LiteralHudElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.TranslatableHudElement;
import org.auioc.mcmod.clientesh.content.hud.element.complex.ConditionalHudElement;
import org.auioc.mcmod.clientesh.content.hud.element.complex.FormatterHudElement;
import org.auioc.mcmod.clientesh.content.hud.element.complex.MultielementHudElement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class CEHudElements {

    public static void register() {}

    // ============================================================================================================== //

    public static final HudElementEntry LITERAL = register("literal", LiteralHudElement::new);
    public static final HudElementEntry TRANSLATABLE = register("translatable", TranslatableHudElement::new);

    public static final HudElementEntry MULTIELEMENT = register("multielement", MultielementHudElement::new);
    public static final HudElementEntry FORMATTER = register("formatter", FormatterHudElement::new);
    public static final HudElementEntry CONDITIONAL = register("conditional", ConditionalHudElement::new);

    // ============================================================================================================== //

    private static HudElementEntry register(String id, IHudElementDeserializer deserializer) {
        return HudElementTypeRegistry.register(ClientEsh.id(id), deserializer);
    }

}
