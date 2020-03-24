package nl.han.ica.icss.checker;

import java.util.HashMap;
import java.util.LinkedList;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.types.*;

public class Checker {

    private VariableStore<ExpressionType> variableTypes;

    public void check(AST ast) {
        variableTypes = new VariableStore<ExpressionType>();
        variableTypes.addScopeLevel();
        check(ast.root);
    }

    private void check(ASTNode node){
        //TODO Refactor, niet SOLID
        if(node instanceof IfClause || node instanceof Stylerule){
            variableTypes.addScopeLevel();
        }

        node.check(variableTypes);
        for(ASTNode child : node.getChildren()){
            check(child);
        }

        //TODO Refactor, niet SOLID
        if(node instanceof IfClause || node instanceof Stylerule){
            variableTypes.removeScopeLevel();
        }
    }
}
