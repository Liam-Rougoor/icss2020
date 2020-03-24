package nl.han.ica.icss.checker;

import java.util.HashMap;
import java.util.LinkedList;

public class VariableStore<T> {
    private LinkedList<HashMap<String, T>> variableTypes;

    public VariableStore() {
        variableTypes = new LinkedList<>();
    }

    public void addScopeLevel(){
        variableTypes.addFirst(new HashMap<>());
    }

    public void removeScopeLevel(){
        variableTypes.removeFirst();
    }

    public void storeVariable(String variable, T type){
        variableTypes.getFirst().put(variable, type);
    }

    public T getVariableType(String variable){
        for(HashMap<String, T> map : variableTypes){
            if(map.containsKey(variable)){
                return map.get(variable);
            }
        }
        return null;
    }

    public boolean isDefined(String variable){
        for(HashMap<String, T> variables : variableTypes){
            if(variables.containsKey(variable)){
                return true;
            }
        }
        return false;
    }
}
