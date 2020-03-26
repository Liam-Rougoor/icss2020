package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.checker.VariableStore;

public interface TransformEntry {
    void enterTransform(VariableStore<Literal> variableValues, ASTNode parent);
}
