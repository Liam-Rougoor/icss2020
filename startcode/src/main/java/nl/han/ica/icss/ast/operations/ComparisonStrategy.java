package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Expression;

public interface ComparisonStrategy {
    boolean compare(Expression lhs, Expression rhs);
}
