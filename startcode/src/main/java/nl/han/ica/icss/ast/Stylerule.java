package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;
import nl.han.ica.icss.checker.VariableStore;
import nl.han.ica.icss.generator.CSSBuilder;

import java.util.ArrayList;
import java.util.Objects;

public class Stylerule extends ASTNode {
	
	public ArrayList<Selector> selectors = new ArrayList<>();
	public ArrayList<ASTNode> body = new ArrayList<>();

    public Stylerule() { }

    public Stylerule(Selector selector, ArrayList<ASTNode> body) {

    	this.selectors = new ArrayList<>();
    	this.selectors.add(selector);
    	this.body = body;
    }

    @Override
	public String getNodeLabel() {
		return "Stylerule";
	}
	@Override
	public ArrayList<ASTNode> getChildren() {
		ArrayList<ASTNode> children = new ArrayList<>();
		children.addAll(selectors);
		children.addAll(body);

		return children;
	}

    @Override
    public ASTNode addChild(ASTNode child) {
		if(child instanceof Selector)
			selectors.add((Selector) child);
		else
        	body.add(child);

		return this;
    }

	@Override
	public ASTNode removeChild(ASTNode child) {
		if(child instanceof Selector){
			selectors.remove((Selector) child);
		} else {
			body.remove(child);
		}

		return this;
	}

	@Override
	public void enterCheck(VariableStore<ExpressionType> variableTypes) {
		variableTypes.addScopeLevel();
	}

	@Override
	public void exitCheck(VariableStore<ExpressionType> variableTypes) {
		variableTypes.removeScopeLevel();
	}

	@Override
	public void enterTransform(VariableStore<Literal> variableValues, ASTNode parent) {
		variableValues.addScopeLevel();
	}

	@Override
	public void exitTransform(VariableStore<Literal> variableValues, ASTNode parent) {
		variableValues.removeScopeLevel();
	}

	@Override
	public void enterCSS(CSSBuilder builder) {
    	builder.applyScope();
		builder.append(selectors.get(0));
		builder.append(" {\n");
		builder.addScope();
	}

	@Override
	public void exitCSS(CSSBuilder builder) {
		builder.removeScope();
    	builder.append("}\n");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Stylerule stylerule = (Stylerule) o;
		return Objects.equals(selectors, stylerule.selectors) &&
				Objects.equals(body, stylerule.body);
	}

	@Override
	public int hashCode() {
		return Objects.hash(selectors, body);
	}
}
