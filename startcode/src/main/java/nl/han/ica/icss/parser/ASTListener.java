package nl.han.ica.icss.parser;

import java.util.ArrayList;
import java.util.Stack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private Stack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new Stack<>();
	}
    public AST getAST() {
        return ast;
    }

	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet stylesheet = new Stylesheet();
		ast.root = stylesheet;
		currentContainer.push(stylesheet);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterStylesheet_rule(ICSSParser.Stylesheet_ruleContext ctx) {
		Stylerule rule = new Stylerule();
		currentContainer.peek().addChild(rule);
		currentContainer.push(rule);
	}

	@Override
	public void exitStylesheet_rule(ICSSParser.Stylesheet_ruleContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterSelector(ICSSParser.SelectorContext ctx) {
		TerminalNode tag = ctx.LOWER_IDENT();
		Selector selector = null;
		if(ctx.LOWER_IDENT() != null){
			selector = new TagSelector(ctx.getText());
		} else if(ctx.CLASS_IDENT() != null){
			selector = new ClassSelector(ctx.getText());
		} else if(ctx.ID_IDENT() != null){
			selector = new IdSelector(ctx.getText());
		}
		currentContainer.peek().addChild(selector);
	}

	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration declaration = new Declaration();
		currentContainer.peek().addChild(declaration);
		currentContainer.push(declaration);
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterProperty(ICSSParser.PropertyContext ctx) {
		ASTNode property = new PropertyName(ctx.getText());
		currentContainer.peek().addChild(property);
	}

	@Override
	public void enterValue(ICSSParser.ValueContext ctx) {
		Expression value;
		if(ctx.COLOR() != null){
			value = new ColorLiteral(ctx.getText());
		} else if(ctx.PIXELSIZE() != null) {
			value = new PixelLiteral(ctx.getText());
		} else if(ctx.PERCENTAGE() != null) {
			value = new PercentageLiteral(ctx.getText());
		} else if(ctx.SCALAR() != null){
			value = new ScalarLiteral(ctx.getText());
		} else{
			return;
		}
		currentContainer.peek().addChild(value);
	}

	@Override
	public void enterVariable_assignment(ICSSParser.Variable_assignmentContext ctx) {
		VariableAssignment assignment = new VariableAssignment();
		currentContainer.peek().addChild(assignment);
		currentContainer.push(assignment);
	}

	@Override
	public void exitVariable_assignment(ICSSParser.Variable_assignmentContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterExpression(ICSSParser.ExpressionContext ctx) {
		if(ctx.getChildCount() == 3){
			Operation operation = null;
			if(ctx.getChild(1).getText().charAt(0) == '*'){
				operation = new MultiplyOperation();
			} else if(ctx.getChild(1).getText().charAt(0) == '+'){
				operation = new AddOperation();
			} else if(ctx.getChild(1).getText().charAt(0) == '-'){
				operation = new SubtractOperation();
			}
			currentContainer.peek().addChild(operation);
			currentContainer.push(operation);
		}
	}

	@Override
	public void exitExpression(ICSSParser.ExpressionContext ctx) {
		if(ctx.getChildCount() == 3){
			currentContainer.pop();
		}
	}

	@Override
	public void enterIf_expression(ICSSParser.If_expressionContext ctx) {
		IfClause ifClause = new IfClause();
		currentContainer.peek().addChild(ifClause);
		currentContainer.push(ifClause);
	}

	@Override
	public void exitIf_expression(ICSSParser.If_expressionContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterBool(ICSSParser.BoolContext ctx) {
		currentContainer.peek().addChild(new BoolLiteral(ctx.getChild(0).getText()));
	}

	@Override
	public void enterVariable_reference(ICSSParser.Variable_referenceContext ctx) {
		VariableReference variable = new VariableReference(ctx.getChild(0).getText());
		currentContainer.peek().addChild(variable);
	}

	@Override
	public void enterElse_expression(ICSSParser.Else_expressionContext ctx) {
		ElseClause elseClause = new ElseClause();
		currentContainer.peek().addChild(elseClause);
		currentContainer.push(elseClause);
	}

	@Override
	public void exitElse_expression(ICSSParser.Else_expressionContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterComparison(ICSSParser.ComparisonContext ctx) {
		Comparison comparison = new Comparison();
		char firstChar = ctx.getChild(1).getText().charAt(0);
		if(firstChar=='<'){
			comparison.addComparisonStrategy(new LessStrategy());
		} else if(firstChar=='>'){
			comparison.addComparisonStrategy(new GreaterStrategy());
		} else if(firstChar=='='){
			comparison.addComparisonStrategy(new EqualStrategy());
		}
		if(ctx.getChild(1).getText().length()==2){
			comparison.addComparisonStrategy(new EqualStrategy());
		}
		currentContainer.peek().addChild(comparison);
		currentContainer.push(comparison);
	}

	@Override
	public void exitComparison(ICSSParser.ComparisonContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterNot_expression(ICSSParser.Not_expressionContext ctx) {
		NotOperation operation = new NotOperation();
		currentContainer.peek().addChild(operation);
		currentContainer.push(operation);
	}

	@Override
	public void exitNot_expression(ICSSParser.Not_expressionContext ctx) {
		currentContainer.pop();
	}
}
