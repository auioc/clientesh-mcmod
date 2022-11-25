package org.auioc.mcmod.clientesh.content.hud.element.function;

import java.util.function.BiPredicate;
import org.auioc.mcmod.clientesh.api.hud.element.IFunctionElement;
import org.auioc.mcmod.clientesh.api.hud.element.IHudElement;
import org.auioc.mcmod.clientesh.api.hud.element.NullHudElement;
import org.auioc.mcmod.clientesh.api.hud.value.IOperableValue;
import org.auioc.mcmod.clientesh.api.hud.value.OperableValue;
import org.auioc.mcmod.clientesh.api.hud.value.OperableValue.BooleanValue;
import org.auioc.mcmod.clientesh.content.hud.layout.CEHudLayoutParser;
import org.auioc.mcmod.clientesh.utils.GsonHelper;
import com.google.gson.JsonObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConditionalElement implements IFunctionElement {

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
            OperableValue.parseValue(json.get("value")),
            GsonHelper.getAsArray(json, "cases", IOperableValue[]::new, OperableValue::parseValue, true),
            GsonHelper.getAsArray(json, "results", IHudElement[]::new, CEHudLayoutParser::parseElement, true)
        );
    }

    @Override
    public IHudElement getResult() {
        for (int i = 0; i < caseCount; ++i) if (condition.test(value, cases[i])) return results[i];
        return (defaultResultIndex >= 0) ? results[defaultResultIndex] : new NullHudElement();
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

}
