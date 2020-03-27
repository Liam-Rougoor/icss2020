package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.CheckEntry;
import nl.han.ica.icss.checker.VariableStore;
import nl.han.ica.icss.transforms.TransformExit;

import java.util.ArrayList;

public abstract class Operation extends Expression implements TransformExit, CheckEntry {

    public Expression lhs;
    public Expression rhs;
    public ExpressionType type;

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        if (lhs != null)
            children.add(lhs);
        if (rhs != null)
            children.add(rhs);
        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if (lhs == null) {
            lhs = (Expression) child;
        } else if (rhs == null) {
            rhs = (Expression) child;
        }
        return this;
    }

    @Override
    public ASTNode removeChild(ASTNode child) {
        if (lhs == child) {
            lhs = null;
        } else if (rhs == child) {
            rhs = null;
        }
        return this;
    }

    @Override
    public void enterCheck(VariableStore<ExpressionType> variableTypes) {
        checkType(variableTypes, ExpressionType.COLOR);
        checkType(variableTypes, ExpressionType.BOOL);
    }

    protected void checkType(VariableStore<ExpressionType> variableTypes, ExpressionType illegalType) {
        boolean error = false;
        String errorMessage = "Operand cannot be of type " + illegalType;
        if (lhs.getType(variableTypes) == illegalType) {
            lhs.setError(errorMessage);
            error = true;
        }
        if (rhs.getType(variableTypes) == illegalType) {
            rhs.setError(errorMessage);
            error = true;
        }
        if (error) {
            setError("Invalid operation types.");
        }
    }

    protected void checkType(Expression expression, VariableStore<ExpressionType> variableTypes, ExpressionType illegalType){
        String errorMessage = "Operand cannot be of type " + illegalType;
        if(expression.getType(variableTypes) == illegalType){
            expression.setError(errorMessage);
            setError(errorMessage);
        }
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

    @Override
    public ExpressionType getType(VariableStore<ExpressionType> variableTypes) {
        if (type == null) {
            setType(variableTypes);
        }
        return getType();
    }

    @Override
    public ExpressionType getType() {
        return type;
    }

    protected abstract void setType(VariableStore<ExpressionType> variableTypes);

    protected abstract int calculate(Literal lhsLiteral, Literal rhsLiteral);
}
