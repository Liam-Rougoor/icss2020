package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.*;

public class Generator {

	public String generate(AST ast) {
		CSSBuilder builder = new CSSBuilder();
		generate(ast.root, builder, 0);
		return builder.toString();
	}

	public void generate(ASTNode node, CSSBuilder builder, int scope){
		if(node instanceof CSSEntry) {
			((CSSEntry)node).enterCSS(builder);
		}

		for(ASTNode child : node.getChildren()){
			generate(child, builder, scope);
		}

		if(node instanceof CSSExit) {
			((CSSExit)node).exitCSS(builder);
		}

	}
}
