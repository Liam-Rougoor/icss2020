// Generated from D:/Documenten/Github/icss2020/startcode/src/main/antlr4/nl/han/ica/icss/parser\ICSS.g4 by ANTLR 4.8
package nl.han.ica.icss.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ICSSParser}.
 */
public interface ICSSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#rule}.
	 * @param ctx the parse tree
	 */
	void enterRule(ICSSParser.RuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#rule}.
	 * @param ctx the parse tree
	 */
	void exitRule(ICSSParser.RuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#color_declaration}.
	 * @param ctx the parse tree
	 */
	void enterColor_declaration(ICSSParser.Color_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#color_declaration}.
	 * @param ctx the parse tree
	 */
	void exitColor_declaration(ICSSParser.Color_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#size_declaration}.
	 * @param ctx the parse tree
	 */
	void enterSize_declaration(ICSSParser.Size_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#size_declaration}.
	 * @param ctx the parse tree
	 */
	void exitSize_declaration(ICSSParser.Size_declarationContext ctx);
}