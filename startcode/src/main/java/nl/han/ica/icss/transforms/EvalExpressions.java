package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.checker.VariableStore;

import java.util.HashMap;
import java.util.LinkedList;

public class EvalExpressions implements Transform {

    private VariableStore<Literal> variableValues;

    public EvalExpressions() {
        variableValues = new VariableStore<>();
    }

    @Override
    public void apply(AST ast) {
        variableValues = new VariableStore<>();
        transform(ast.root);
    }

    private void transform(ASTNode node){
        if(node instanceof IfClause || node instanceof Stylerule){
            variableValues.addScopeLevel();
        }

        node.transform(variableValues);
        for(ASTNode child : node.getChildren()){
            transform(child);
        }

        //TODO Refactor, niet SOLID
        if(node instanceof IfClause || node instanceof Stylerule){
            variableValues.removeScopeLevel();
        }
    }
}
