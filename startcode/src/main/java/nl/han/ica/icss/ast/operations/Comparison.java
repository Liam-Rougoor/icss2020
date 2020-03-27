package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.CheckEntry;
import nl.han.ica.icss.checker.VariableStore;
import nl.han.ica.icss.transforms.TransformExit;

import java.util.ArrayList;
import java.util.List;

public class Comparison extends Operation implements CheckEntry, TransformExit {
    List<ComparisonStrategy> comparisonStrategies = new ArrayList<>();

    protected boolean compare(){
        for(ComparisonStrategy comparison : comparisonStrategies){
            if(comparison.compare(lhs, rhs)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(child instanceof Expression){
            if(lhs == null){
                lhs = (Expression)child;
            } else if (rhs == null){
                rhs = (Expression)child;
            }
        }
        if(child instanceof ComparisonStrategy){
            comparisonStrategies.add((ComparisonStrategy)child);
        }
        return this;
    }

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
        checkType(variableTypes, ExpressionType.BOOL);
        checkType(variableTypes, ExpressionType.COLOR);
        checkType(variableTypes, ExpressionType.UNDEFINED);
    }

    @Override
    public void exitTransform(VariableStore<Literal> variableValues, ASTNode parent) {

    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.BOOL;
    }

    @Override
    protected void setType(VariableStore<ExpressionType> variableTypes) {
        type = ExpressionType.BOOL;
    }

    @Override
    protected int calculate(Literal lhsLiteral, Literal rhsLiteral) {
        return 0;
    }
}
