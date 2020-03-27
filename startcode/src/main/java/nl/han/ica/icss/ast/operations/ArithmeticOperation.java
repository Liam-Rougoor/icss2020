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

public abstract class ArithmeticOperation extends Operation {
    @Override
    public void enterCheck(VariableStore<ExpressionType> variableTypes) {
        checkIllegalType(variableTypes, ExpressionType.COLOR);
        checkIllegalType(variableTypes, ExpressionType.BOOL);
        setType(variableTypes);
    }

    @Override
    public void exitTransform(VariableStore<Literal> variableValues, ASTNode parent) {
        Literal literal;
        Literal lhsLiteral = lhs.getType() != ExpressionType.VARIABLE ? (Literal) lhs : variableValues.getVariableType(((VariableReference) lhs).name);
        Literal rhsLiteral = rhs.getType() != ExpressionType.VARIABLE ? (Literal) rhs : variableValues.getVariableType(((VariableReference) rhs).name);
        int calculation = calculate(lhsLiteral, rhsLiteral);
        switch (type) {
            case SCALAR:
                literal = new ScalarLiteral(calculation);
                break;
            case PIXEL:
                literal = new PixelLiteral(calculation);
                break;
            case PERCENTAGE:
                literal = new PercentageLiteral(calculation);
                break;
            default:
                literal = null;
        }
        parent.removeChild(this);
        parent.addChild(literal);
    }

    protected abstract int calculate(Literal lhsLiteral, Literal rhsLiteral);
}
