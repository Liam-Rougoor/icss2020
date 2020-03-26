package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.*;

public class Checker {

    private VariableStore<ExpressionType> variableTypes;

    public void check(AST ast) {
        variableTypes = new VariableStore<>();
        variableTypes.addScopeLevel();
        check(ast.root);
    }

    private void check(ASTNode node){
        if(node instanceof CheckEntry) {
            ((CheckEntry)node).enterCheck(variableTypes);
        }
        for(ASTNode child : node.getChildren()){
            check(child);
        }
        if(node instanceof CheckExit) {
            ((CheckExit)node).exitCheck(variableTypes);
        }
    }
}
