package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.CheckEntry;
import nl.han.ica.icss.checker.VariableStore;
import nl.han.ica.icss.transforms.TransformExit;

import java.util.ArrayList;
import java.util.List;

public class Comparison extends Operation implements CheckEntry, TransformExit {
    private List<ComparisonStrategy> comparisonStrategies = new ArrayList<>();

    protected boolean compare(){
        for(ComparisonStrategy comparison : comparisonStrategies){
            if(comparison.compare(lhs, rhs)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNodeLabel() {
        return "Comparison";
    }

    public void addComparisonStrategy(ComparisonStrategy strategy){
        comparisonStrategies.add(strategy);
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
        checkEqualType(variableTypes);
        checkIllegalType(variableTypes, ExpressionType.COLOR);
        checkIllegalType(variableTypes, ExpressionType.UNDEFINED);
    }

    @Override
    public void exitTransform(VariableStore<Literal> variableValues, ASTNode parent) {
        parent.removeChild(this);
        parent.addChild(new BoolLiteral(compare()));
    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.BOOL;
    }

    @Override
    public ExpressionType getType(VariableStore<ExpressionType> variableTypes) {
        return ExpressionType.BOOL;
    }

    @Override
    protected void setType(VariableStore<ExpressionType> variableTypes) {
        type = ExpressionType.BOOL;
    }
}
