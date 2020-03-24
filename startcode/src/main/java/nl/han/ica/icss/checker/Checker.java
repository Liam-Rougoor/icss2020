package nl.han.ica.icss.checker;

import java.util.HashMap;
import java.util.LinkedList;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.types.*;

public class Checker {

    private LinkedList<HashMap<String,ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new LinkedList<>();
        variableTypes.addFirst(new HashMap<>());
        check(ast.root);
    }

    private void check(ASTNode node){
        //TODO Refactor, niet SOLID
        if(node instanceof IfClause || node instanceof Stylerule){
            variableTypes.addLast(new HashMap<>()); // TODO Misschien wisselen naar first?
        }

        node.check(variableTypes);
        for(ASTNode child : node.getChildren()){
            check(child);
        }

        //TODO Refactor, niet SOLID
        if(node instanceof IfClause || node instanceof Stylerule){
            variableTypes.removeLast(); // TODO Misschien wisselen naar first?
        }
    }
}
