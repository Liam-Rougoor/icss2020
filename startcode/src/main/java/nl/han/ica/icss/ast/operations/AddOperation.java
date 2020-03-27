package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

public class AddOperation extends ArithmeticOperation {

    @Override
    public String getNodeLabel() {
        return "Add";
    }

    @Override
    public void enterCheck(VariableStore<ExpressionType> variableTypes) {
        checkEqualType(variableTypes);
        super.enterCheck(variableTypes);
    }

    @Override
    protected void setType(VariableStore<ExpressionType> variableTypes) {
        type = lhs.getType(variableTypes);
    }

    @Override
    protected int calculate(Literal lhsLiteral, Literal rhsLiteral) {
        return lhsLiteral.getIntValue() + rhsLiteral.getIntValue();
    }
}
