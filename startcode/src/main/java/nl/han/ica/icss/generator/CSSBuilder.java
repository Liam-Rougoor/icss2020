package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.Selector;

public class CSSBuilder {
    private StringBuilder builder;
    private int scope;

    public CSSBuilder(){
        builder = new StringBuilder();
        scope = 0;
    }

    public void append(String value){
        builder.append(value);
    }

    public void append(int value){
        builder.append(value);
    }

    public void append(char value){
        builder.append(value);
    }

    public void append(Object value){
        builder.append(value);
    }

    public void addScope(){
        scope++;
    }

    public void removeScope(){
        scope--;
    }

    public void applyScope(){
        builder.append("  ".repeat(scope));
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
