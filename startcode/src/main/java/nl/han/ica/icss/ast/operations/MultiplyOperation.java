package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

public class MultiplyOperation extends Operation {

    @Override
    public String getNodeLabel() {
        return "Multiply";
    }

    @Override
    public void enterCheck(VariableStore<ExpressionType> variableTypes) {
        String errorMessage = "Multiplication must contain scalar value.";
        if (lhs.getType(variableTypes) != ExpressionType.SCALAR && rhs.getType(variableTypes) != ExpressionType.SCALAR) {
            setError(errorMessage);
            lhs.setError(errorMessage);
            rhs.setError(errorMessage);
        }
        setType(variableTypes);
        super.enterCheck(variableTypes);
    }

    @Override
    protected void setType(VariableStore<ExpressionType> variableTypes) {
        type = lhs.getType(variableTypes) != ExpressionType.SCALAR ? lhs.getType(variableTypes) : rhs.getType(variableTypes);

    }

    @Override
    protected int calculate(Literal lhsLiteral, Literal rhsLiteral) {
        return lhsLiteral.getIntValue() * rhsLiteral.getIntValue();
    }
}
