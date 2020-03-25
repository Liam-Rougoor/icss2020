package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.checker.VariableStore;

public class Transformer implements Transform {

    private VariableStore<Literal> variableValues;

    public Transformer() {
        variableValues = new VariableStore<>();
    }

    @Override
    public void apply(AST ast) {
        variableValues = new VariableStore<>();
        transform(ast.root, null);
    }

    private void transform(ASTNode node, ASTNode parent){
        node.enterTransform(variableValues, parent);

        for(ASTNode child : node.getChildren()){
            transform(child, node);
        }

        node.exitTransform(variableValues, parent);
    }
}
