package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.types.ExpressionType;

public interface CheckExit {
    void exitCheck(VariableStore<ExpressionType> variableTypes);
}
