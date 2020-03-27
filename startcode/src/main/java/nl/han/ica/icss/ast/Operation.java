package nl.han.ica.icss.ast;

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
        checkIllegalType(variableTypes, ExpressionType.COLOR);
    }

    protected void checkIllegalType(VariableStore<ExpressionType> variableTypes, ExpressionType illegalType) {
        checkIllegalType(lhs, variableTypes, illegalType);
        checkIllegalType(rhs, variableTypes, illegalType);
    }

    protected void checkIllegalType(Expression expression, VariableStore<ExpressionType> variableTypes, ExpressionType illegalType){
        String errorMessage = "Operand cannot be of type " + illegalType;
        if(expression.getType(variableTypes) == illegalType){
            expression.setError(errorMessage);
            setError(errorMessage);
        }
    }

    protected void checkEqualType(VariableStore<ExpressionType> variableTypes){
        String errorMessage = "Operands must be of equal type. ";
        if(lhs.getType(variableTypes) != rhs.getType(variableTypes)){
            setError(errorMessage);
            lhs.setError(errorMessage + lhs.getType(variableTypes));
            rhs.setError(errorMessage + rhs.getType(variableTypes));
        }
        setType(variableTypes);
    }

    protected void checkOnlyType(Expression expression, VariableStore<ExpressionType> variableTypes, ExpressionType allowedType){
        String errorMessage = "Operand must be of type " + allowedType;
        if(expression.getType(variableTypes) != allowedType){
            expression.setError(errorMessage);
            setError(errorMessage);
        }
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

}
