package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableTypeStore;

import java.util.ArrayList;
import java.util.Objects;

/**
 * An assignment binds a expression to an identifier.
 *
 */
public class VariableAssignment extends ASTNode {
	
	public VariableReference name;
	public Expression expression;

	@Override
	public String getNodeLabel() {
		return "VariableAssignment (" + name.name + ")";
	}

	@Override
	public ASTNode addChild(ASTNode child) {
		if(name == null) {
			name = (VariableReference) child;
		} else if(expression == null) {
			expression = (Expression) child;
		}

		return this;
	}

	@Override
	public ArrayList<ASTNode> getChildren() {

		ArrayList<ASTNode> children = new ArrayList<>();
		if(name != null)
			children.add(name);
		if(expression != null)
			children.add(expression);
		return children;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		VariableAssignment that = (VariableAssignment) o;
		return Objects.equals(name, that.name) &&
				Objects.equals(expression, that.expression);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, expression);
	}

	@Override
	public void check(VariableTypeStore variableTypes) {
		ExpressionType expressionType = ExpressionType.UNDEFINED;
		//TODO Refactor if..else constructie, niet SOLID
		if(expression instanceof BoolLiteral){
			expressionType = ExpressionType.BOOL;
		} else if(expression instanceof ColorLiteral){
			expressionType = ExpressionType.COLOR;
		} else if(expression instanceof PercentageLiteral){
			expressionType = ExpressionType.PERCENTAGE;
		} else if(expression instanceof PixelLiteral){
			expressionType = ExpressionType.PIXEL;
		} else if(expression instanceof ScalarLiteral){
			expressionType = ExpressionType.SCALAR;
		}
		variableTypes.storeVariable(name.name, expressionType);
	}
}
