package org.auioc.mcmod.clientesh.content.hud.element.complex;

import java.util.function.BiPredicate;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import org.auioc.mcmod.clientesh.content.hud.layout.CEHudLayoutParser;
import org.auioc.mcmod.clientesh.utils.GsonHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConditionalElement implements IHudElement {

    private final Condition condition;
    private final IOperableValue value;
    private final IOperableValue[] cases;
    private final int caseCount;
    private final IHudElement[] results;
    private final int defaultResultIndex;

    public ConditionalElement(Condition condition, IOperableValue value, IOperableValue[] cases, IHudElement[] results) {
        this.condition = condition;
        this.value = value;
        this.cases = (condition == Condition.BOOL) ? new IOperableValue[] {new BooleanValue(true)} : cases;
        this.results = results;
        this.caseCount = this.cases.length;
        if (results.length < caseCount) throw new IllegalArgumentException("The number of results must be greater than or equal to the number of cases");
        this.defaultResultIndex = (results.length > caseCount) ? caseCount : -1;
    }

    public ConditionalElement(JsonObject json) {
        this(
            Condition.valueOf(GsonHelper.getAsString(json, "condition").toUpperCase()),
            loadValue(json.get("value")),
            GsonHelper.getAsArray(json, "cases", IOperableValue[]::new, ConditionalElement::loadValue, true),
            GsonHelper.getAsArray(json, "results", IHudElement[]::new, CEHudLayoutParser::parseElement, true)
        );
    }

    @Override
    public Component getText() {
        for (int i = 0; i < caseCount; ++i) if (condition.test(value, cases[i])) return results[i].getText();
        return (defaultResultIndex >= 0) ? results[defaultResultIndex].getText() : new TextComponent("§7null");
    }

    private static IOperableValue loadValue(JsonElement json) {
        if (GsonHelper.isNumberValue(json)) return new NumberValue(json.getAsDouble());
        if (GsonHelper.isBooleanValue(json)) return new BooleanValue(json.getAsBoolean());
        if (GsonHelper.isStringValue(json)) return new StringValue(json.getAsString());
        var element = CEHudLayoutParser.parseElement(json);
        if (element instanceof IOperableValue) return (IOperableValue) element;
        throw new JsonParseException("Not a operable value");
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    private static enum Condition {
        EQ((l, r) -> l.doubleValue() == r.doubleValue()),
        NE((l, r) -> l.doubleValue() != r.doubleValue()),
        GT((l, r) -> l.doubleValue() > r.doubleValue()),
        LT((l, r) -> l.doubleValue() < r.doubleValue()),
        GE((l, r) -> l.doubleValue() >= r.doubleValue()),
        LE((l, r) -> l.doubleValue() <= r.doubleValue()),
        BOOL((l, r) -> l.booleanValue()),
        EQUAL((l, r) -> l.equals(r));

        private final BiPredicate<IOperableValue, IOperableValue> predicate;

        private Condition(BiPredicate<IOperableValue, IOperableValue> predicate) {
            this.predicate = predicate;
        }

        public boolean test(IOperableValue a, IOperableValue b) {
            return predicate.test(a, b);
        }
    }

    // ============================================================================================================== //

    @OnlyIn(Dist.CLIENT)
    private static record NumberValue(double value) implements IOperableValue.DoubleValue {
        @Override
        public double doubleValue() { return value; }
    }

    @OnlyIn(Dist.CLIENT)
    private static record BooleanValue(boolean value) implements IOperableValue.BooleanValue {
        @Override
        public boolean booleanValue() { return value; }
    }

    @OnlyIn(Dist.CLIENT)
    private static record StringValue(String value) implements IOperableValue.StringValue {
        @Override
        public String stringValue() { return value; }
    }

}
