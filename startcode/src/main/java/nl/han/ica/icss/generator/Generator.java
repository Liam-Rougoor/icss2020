package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.*;

public class Generator {

	public String generate(AST ast) {
		CSSBuilder builder = new CSSBuilder();
		generate(ast.root, builder);
		builder.apply();
		return builder.toString();
	}

	public void generate(ASTNode node, CSSBuilder builder){
		node.enterCSS(builder);
		for(ASTNode child : node.getChildren()){
			generate(child, builder);
		}
	}
}
