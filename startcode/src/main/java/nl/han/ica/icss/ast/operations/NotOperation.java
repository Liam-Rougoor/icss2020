package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

public class NotOperation extends Comparison {

    @Override
    protected boolean compare() {
        return new EqualStrategy().compare(lhs, new BoolLiteral(true));
    }

    @Override
    public void enterCheck(VariableStore<ExpressionType> variableTypes) {
        checkType(lhs, variableTypes, ExpressionType.BOOL);
    }

    @Override
    protected void setType(VariableStore<ExpressionType> variableTypes) {
        type = ExpressionType.BOOL;
    }

    @Override
    protected int calculate(Literal lhsLiteral, Literal rhsLiteral) {
        return 0;
    }
}
