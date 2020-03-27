package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

public class NotOperation extends Comparison {

    @Override
    protected boolean compare() {
        return new EqualStrategy().compare(lhs, new BoolLiteral(false));
    }

    @Override
    public void enterCheck(VariableStore<ExpressionType> variableTypes) {
        checkOnlyType(lhs, variableTypes, ExpressionType.BOOL);
    }

    @Override
    protected void setType(VariableStore<ExpressionType> variableTypes) {
        type = ExpressionType.BOOL;
    }
}
