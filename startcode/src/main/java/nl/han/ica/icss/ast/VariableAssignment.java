package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.CheckEntry;
import nl.han.ica.icss.checker.VariableStore;
import nl.han.ica.icss.transforms.TransformExit;

import java.util.ArrayList;
import java.util.Objects;

/**
 * An assignment binds a expression to an identifier.
 *
 */
public class VariableAssignment extends ASTNode implements TransformExit, CheckEntry {
	
	public VariableReference name;
	public Expression expression;

	@Override
	public String getNodeLabel() {
		return "VariableAssignment (" + name.name + ")";
	}

	@Override
	public ASTNode addChild(ASTNode child) {
		if(child instanceof VariableReference) {
			name = (VariableReference) child;
		} else if(child instanceof Expression) {
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
	public void enterCheck(VariableStore<ExpressionType> variableTypes) {
		variableTypes.storeVariable(name.name, expression.getType(variableTypes));
	}

	@Override
	public void exitTransform(VariableStore<Literal> variableValues, ASTNode parent) {
		variableValues.storeVariable(name.name, (Literal)expression);
	}
}
