package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableTypeStore;

public class AddOperation extends Operation {

    @Override
    public String getNodeLabel() {
        return "Add";
    }

    @Override
    public void check(VariableTypeStore variableTypes) {
        super.check(variableTypes);
        String errorMessage = "Operands must be of equal type. ";
        if(lhs.getType(variableTypes) != rhs.getType(variableTypes)){
            setError(errorMessage);
            lhs.setError(errorMessage + lhs.getType(variableTypes));
            rhs.setError(errorMessage + rhs.getType(variableTypes));
        }
    }

    @Override
    public ExpressionType getType(VariableTypeStore variableTypes) {
        return lhs.getType(variableTypes);
    }
}
