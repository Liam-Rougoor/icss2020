package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

import java.util.ArrayList;

public abstract class Operation extends Expression {

    public Expression lhs;
    public Expression rhs;

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        if(lhs != null)
            children.add(lhs);
        if(rhs != null)
            children.add(rhs);
        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(lhs == null) {
            lhs = (Expression) child;
        } else if(rhs == null) {
            rhs = (Expression) child;
        }
        return this;
    }

    @Override
    public void check(VariableStore<ExpressionType> variableTypes) {
        boolean error = false;
        if(lhs.getType(variableTypes) == ExpressionType.COLOR){
            lhs.setError("Cannot use arithmetic operations on colors");
            error = true;
        } if(rhs.getType(variableTypes) == ExpressionType.COLOR){
            rhs.setError("Cannot use arithmetic operations on colors.");
            error = true;
        }
        if(error){
            setError("Cannot use arithmetic operations on colors.");
        }
    }
}
