package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.checker.VariableTypeStore;

public class MultiplyOperation extends Operation {

    @Override
    public String getNodeLabel() {
        return "Multiply";
    }

    @Override
    public void check(VariableTypeStore variableTypes) {
        super.check(variableTypes);
        String errorMessage = "Multiplication must contain scalar value.";
        boolean error;
        if (!(lhs instanceof ScalarLiteral && !(rhs instanceof ScalarLiteral))) {
        }
    }
}
