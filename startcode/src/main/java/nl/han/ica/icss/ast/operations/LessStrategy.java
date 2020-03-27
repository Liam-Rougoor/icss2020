package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Literal;

public class LessStrategy implements ComparisonStrategy {
    @Override
    public boolean compare(Expression lhs, Expression rhs) {
        return ((Literal) lhs).getIntValue() < ((Literal) rhs).getIntValue();
    }
}
