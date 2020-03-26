package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.checker.VariableStore;

public interface TransformExit {
    void exitTransform(VariableStore<Literal> variableValues, ASTNode parent);
}
