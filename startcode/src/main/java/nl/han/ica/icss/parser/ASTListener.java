package nl.han.ica.icss.parser;

import java.util.Stack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
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
	public void enterEveryRule(ParserRuleContext ctx) {
		super.enterEveryRule(ctx);
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		super.exitEveryRule(ctx);
	}

	@Override
	public void visitTerminal(TerminalNode node) {
		super.visitTerminal(node);
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		super.visitErrorNode(node);
	}
}
