package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.types.ExpressionType;

public interface CheckEntry {
    void enterCheck(VariableStore<ExpressionType> variableTypes);
}
