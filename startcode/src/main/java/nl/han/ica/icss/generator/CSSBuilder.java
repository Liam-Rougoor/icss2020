package nl.han.ica.icss.generator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CSSBuilder {
    private StringBuilder builder;
    private int scope;

    private Queue<String> entries;
    private Stack<String> exits;

    public CSSBuilder(){
        builder = new StringBuilder();
        entries = new LinkedList<>();
        exits = new Stack<>();
        scope = 0;
    }

    public void apply(){
        applyEntry();
        applyExit();
    }

    private void applyEntry(){
        while(!entries.isEmpty()){
           builder.append(entries.poll());
        }
    }

    private void applyExit(){
        while(!exits.isEmpty()){
            builder.append(exits.pop());
        }
    }

    public void appendEntry(String value) {
        entries.offer(value);
    }

    public void appendExit(String value) {
        exits.push(value);
    }

    public void addScope(){
        scope++;
    }

    public void removeScope(){
        scope--;
    }

    public void applyScope(){
        entries.offer("  ".repeat(scope));
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
