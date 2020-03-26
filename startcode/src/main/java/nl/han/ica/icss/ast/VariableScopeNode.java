package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

public abstract class VariableScopeNode extends ASTNode implements VariableScope {
    @Override
    public void removeVariableAssignments(ASTNode node, ASTNode parent) {
        if (node.variablesRemoved) {
            return;
        }

        for (ASTNode child : node.getChildren()) {
            removeVariableAssignments(child, node);
        }

        if (node instanceof VariableAssignment) {
            parent.removeChild(node);
        }
        node.variablesRemoved = true;
    }

    @Override
    public void enterTransform(VariableStore<Literal> variableValues, ASTNode parent) {
        variableValues.addScopeLevel();
    }

    @Override
    public void exitTransform(VariableStore<Literal> variableValues, ASTNode parent) {
        variableValues.removeScopeLevel();
        removeVariableAssignments(this, null);
    }

    @Override
    public void enterCheck(VariableStore<ExpressionType> variableTypes) {
        variableTypes.addScopeLevel();
    }

    @Override
    public void exitCheck(VariableStore<ExpressionType> variableTypes) {
        variableTypes.removeScopeLevel();
    }
}
