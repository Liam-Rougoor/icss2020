package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.PropertyType;

public class PropertyName extends ASTNode {

    public String name;

    public PropertyName() {
        super();
        name = "undefined";
    }
    public PropertyName(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getNodeLabel() {
        return "Property: (" + name + ")";
    }

    public PropertyType getType(){
        switch(name){
            case "width":
            case "height":
                return PropertyType.SIZE;
            case "color":
            case "background-color":
                return PropertyType.COLOR;
            default:
                return PropertyType.UNDEFINED;
        }
    }
}
