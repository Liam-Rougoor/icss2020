package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Stylerule;
import nl.han.ica.icss.ast.VariableAssignment;

public class VariableAssignmentRemover implements Transform {
    @Override
    public void apply(AST ast) {
        removeVariableAssignment(ast.root, null);
    }

    private void removeVariableAssignment(ASTNode node, ASTNode parent) {
        for(ASTNode child : node.getChildren()){
            removeVariableAssignment(child, node);
        }
        if(node instanceof VariableAssignment) {
            parent.removeChild(node);
        }
    }


}
