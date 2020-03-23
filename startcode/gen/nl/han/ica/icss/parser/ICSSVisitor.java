// Generated from D:/Documenten/Github/icss2020/startcode/src/main/antlr4/nl/han/ica/icss/parser\ICSS.g4 by ANTLR 4.8
package nl.han.ica.icss.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ICSSParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ICSSVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRule(ICSSParser.RuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#color_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColor_declaration(ICSSParser.Color_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#size_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSize_declaration(ICSSParser.Size_declarationContext ctx);
}