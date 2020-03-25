package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.checker.VariableStore;

public class EvalExpressions implements Transform {

    private VariableStore<Literal> variableValues;

    public EvalExpressions() {
        variableValues = new VariableStore<>();
    }

    @Override
    public void apply(AST ast) {
        variableValues = new VariableStore<>();
        transform(ast.root, null);
    }

    private void transform(ASTNode node, ASTNode parent){
        if(node instanceof IfClause || node instanceof Stylerule || node instanceof Stylesheet){
            variableValues.addScopeLevel();
        }

        for(ASTNode child : node.getChildren()){
            transform(child, node);
        }
        node.transform(variableValues, parent);

        //TODO Refactor, niet SOLID
        if(node instanceof IfClause || node instanceof Stylerule || node instanceof Stylesheet){
            variableValues.removeScopeLevel();
        }
    }
}
