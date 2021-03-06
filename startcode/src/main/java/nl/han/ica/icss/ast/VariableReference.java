package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.CheckEntry;
import nl.han.ica.icss.checker.VariableStore;
import nl.han.ica.icss.transforms.TransformExit;

import java.util.Objects;

public class VariableReference extends Expression implements TransformExit, CheckEntry {

	public String name;

	public VariableReference(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getNodeLabel() {
		return "VariableReference (" + name + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		VariableReference that = (VariableReference) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {

		return Objects.hash(name);
	}

	@Override
	public void enterCheck(VariableStore<ExpressionType> variableTypes) {
		if(!variableTypes.isDefined(name)) {
			setError("Variable is undefined within scope.");
		}
	}

	@Override
	public void exitTransform(VariableStore<Literal> variableValues, ASTNode parent) {
		parent.removeChild(this);
		parent.addChild(variableValues.getVariableType(name));
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.VARIABLE;
	}

	@Override
	public ExpressionType getType(VariableStore<ExpressionType> variableTypes) {
		return variableTypes.getVariableType(name);
	}
}
