package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

public class MultiplyOperation extends Operation {

    @Override
    public String getNodeLabel() {
        return "Multiply";
    }

    @Override
    public void check(VariableStore<ExpressionType> variableTypes) {
        super.check(variableTypes);
        String errorMessage = "Multiplication must contain scalar value.";
        if (lhs.getType(variableTypes) != ExpressionType.SCALAR && rhs.getType(variableTypes) != ExpressionType.SCALAR) {
            setError(errorMessage);
            lhs.setError(errorMessage);
            rhs.setError(errorMessage);
        }
    }

    @Override
    public ExpressionType getType(VariableStore<ExpressionType> variableTypes) {
        return lhs.getType(variableTypes) != ExpressionType.SCALAR ? lhs.getType(variableTypes) : rhs.getType(variableTypes);
    }
}
