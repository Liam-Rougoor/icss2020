package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableTypeStore;

public abstract class Expression extends ASTNode {
    public ExpressionType getType() {
        return ExpressionType.UNDEFINED;
    }

    //TODO Navragen bij Michel, voelt als een aparte constructie.
    public boolean isValidVariableExpression(VariableTypeStore variableTypes, ExpressionType validType){
        if(!(this instanceof VariableReference)){
            return false;
        }
        VariableReference variable = (VariableReference) this;
        return variableTypes.getVariableType(variable.name) == validType;
    }
}
