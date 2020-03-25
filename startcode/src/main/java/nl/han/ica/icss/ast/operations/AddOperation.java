package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

public class AddOperation extends Operation {

    @Override
    public String getNodeLabel() {
        return "Add";
    }


    @Override
    public void check(VariableStore<ExpressionType> variableTypes) {
        super.check(variableTypes);
        String errorMessage = "Operands must be of equal type. ";
        if(lhs.getType(variableTypes) != rhs.getType(variableTypes)){
            setError(errorMessage);
            lhs.setError(errorMessage + lhs.getType(variableTypes));
            rhs.setError(errorMessage + rhs.getType(variableTypes));
        }
        setType(variableTypes);
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
