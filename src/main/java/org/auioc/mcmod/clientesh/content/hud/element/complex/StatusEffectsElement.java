package org.auioc.mcmod.clientesh.content.hud.element.complex;

import java.util.Collection;
import org.auioc.mcmod.arnicalib.game.chat.TextUtils;
import org.auioc.mcmod.arnicalib.game.effect.MobEffectUtils;
import org.auioc.mcmod.clientesh.api.hud.element.IMultilineElement;
import org.auioc.mcmod.clientesh.content.hud.element.basic.AbsCEHudElement;
import com.google.gson.JsonObject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StatusEffectsElement extends AbsCEHudElement implements IMultilineElement {

    private final boolean hideInvisibleEffects;
    private final boolean colorByCategory;

    public StatusEffectsElement(JsonObject json) {
        super(json);
        this.hideInvisibleEffects = GsonHelper.getAsBoolean(json, "hideInvisibleEffects", true);
        this.colorByCategory = GsonHelper.getAsBoolean(json, "colorByCategory", true);
    }

    @Override
    public Component[] getLines() {
        var _effects = player.getActiveEffects();
        if (_effects.isEmpty()) return new Component[0];

        Collection<MobEffectInstance> effects = hideInvisibleEffects
            ? _effects.stream().filter(MobEffectInstance::isVisible).toList()
            : _effects;
        if (effects.isEmpty()) return new Component[0];

        var lines = new Component[effects.size()];
        int i = 0;
        var it = effects.iterator();
        while (it.hasNext()) {
            var instance = it.next();
            var text = TextUtils.empty().append(MobEffectUtils.getDisplayString(instance));
            if (!instance.isVisible()) text.withStyle(ChatFormatting.STRIKETHROUGH, ChatFormatting.ITALIC);
            if (instance.isAmbient()) text.withStyle(ChatFormatting.ITALIC);
            if (colorByCategory) switch (instance.getEffect().getCategory()) {
                case BENEFICIAL -> text.withStyle(ChatFormatting.GREEN);
                case HARMFUL -> text.withStyle(ChatFormatting.RED);
                case NEUTRAL -> text.withStyle(ChatFormatting.AQUA);
            }
            lines[i] = text;
            i++;
        }
        return lines;
    }

    @Override
    public Component getText() { throw new UnsupportedOperationException(); }

    @Override
    protected MutableComponent getRawText() { throw new UnsupportedOperationException(); }

}
