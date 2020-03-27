package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;

import java.util.ArrayList;
import java.util.Objects;

public class IfClause extends VariableScopeNode {


    public Expression conditionalExpression;
    public ArrayList<ASTNode> body = new ArrayList<>();
    public ElseClause elseClause;

    public IfClause() {
    }

    public IfClause(Expression conditionalExpression, ArrayList<ASTNode> body) {
        this.conditionalExpression = conditionalExpression;
        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "If_Clause";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(conditionalExpression);
        children.addAll(body);
        if (elseClause != null) {
            children.add(elseClause);
        }

        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if (child instanceof Expression)
            conditionalExpression = (Expression) child;
        else if (child instanceof ElseClause)
            elseClause = (ElseClause) child;
        else
            body.add(child);

        return this;
    }

    @Override
    public ASTNode removeChild(ASTNode child) {
        if (child instanceof Expression) {
            conditionalExpression = null;
        } else if (child instanceof ElseClause) {
            elseClause = null;
        } else {
            body.remove(child);
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IfClause ifClause = (IfClause) o;
        return Objects.equals(conditionalExpression, ifClause.getConditionalExpression()) &&
                Objects.equals(body, ifClause.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionalExpression, body);
    }

    public Expression getConditionalExpression() {
        return conditionalExpression;
    }

    @Override
    public void enterCheck(VariableStore<ExpressionType> variableTypes) {
        ExpressionType conditionType = conditionalExpression.getType(variableTypes);
        if (conditionType != ExpressionType.BOOL) {
            String errorMessage = "Expression must be of type boolean. ";
            setError(errorMessage);
            conditionalExpression.setError(errorMessage + conditionType);
        }
        super.enterCheck(variableTypes);
    }

    @Override
    public void exitTransform(VariableStore<Literal> variableValues, ASTNode parent) {
        parent.removeChild(this);
        if (((BoolLiteral) conditionalExpression).value) {
            for (ASTNode bodyNode : body) {
                parent.addChild(bodyNode);
            }
        } else if (elseClause != null) {
            for(ASTNode bodyNode : elseClause.getChildren()){
                parent.addChild(bodyNode);
            }
            elseClause = null;
        }
        super.exitTransform(variableValues, parent);
    }
}
