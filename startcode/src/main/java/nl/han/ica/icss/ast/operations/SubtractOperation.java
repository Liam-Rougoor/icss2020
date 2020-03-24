package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

public class SubtractOperation extends Operation {

    @Override
    public String getNodeLabel() {
        return "Subtract";
    }

    @Override
    public void check(VariableStore<ExpressionType> variableTypes) {
        super.check(variableTypes);
        String errorMessage = "Operands must be of equal type. ";
        if (lhs.getType(variableTypes) != rhs.getType(variableTypes)) {
            setError(errorMessage);
            lhs.setError(errorMessage + lhs.getType(variableTypes));
            rhs.setError(errorMessage + rhs.getType(variableTypes));
        }
    }

    @Override
    public ExpressionType getType(VariableStore<ExpressionType> variableTypes) {
        return lhs.getType(variableTypes);
    }
}
