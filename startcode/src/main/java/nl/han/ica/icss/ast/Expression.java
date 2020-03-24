package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

public abstract class Expression extends ASTNode {
    public ExpressionType getType() {
        return ExpressionType.UNDEFINED;
    }

    public ExpressionType getType(VariableStore<ExpressionType> variableTypes){
        return getType();
    }
}
