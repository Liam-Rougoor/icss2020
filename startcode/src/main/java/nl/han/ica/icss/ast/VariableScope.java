package nl.han.ica.icss.ast;

import nl.han.ica.icss.checker.CheckEntry;
import nl.han.ica.icss.checker.CheckExit;
import nl.han.ica.icss.transforms.TransformEntry;
import nl.han.ica.icss.transforms.TransformExit;

public interface VariableScope extends TransformEntry, TransformExit, CheckEntry, CheckExit {
    void removeVariableAssignments(ASTNode node, ASTNode parent);
}
