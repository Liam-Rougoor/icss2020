package nl.han.ica.icss.parser;

import java.util.Stack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
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
		Expression value = null;
		if(ctx.COLOR() != null){
			value = new ColorLiteral(ctx.getText());
		} else if(ctx.PIXELSIZE() != null) {
			value = new PixelLiteral(ctx.getText());
		} else if(ctx.PERCENTAGE() != null) {
			value = new PercentageLiteral(ctx.getText());
		} else if(ctx.TRUE() != null || ctx.FALSE() != null){
			value = new BoolLiteral(ctx.getText());
		} else if(ctx.CAPITAL_IDENT() != null){
			value = new VariableReference(ctx.getText());
		} else if(ctx.SCALAR() != null){
			value = new ScalarLiteral(ctx.getText());
		}
		currentContainer.peek().addChild(value);
	}

	@Override
	public void enterVariable_assignment(ICSSParser.Variable_assignmentContext ctx) {
		VariableAssignment assignment = new VariableAssignment();
		VariableReference reference = new VariableReference(ctx.getChild(0).getText());
		assignment.addChild(reference);
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
			// TODO Strings mogelijk refactoren in G4 bestand (naar operator o.i.d.)
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
}
