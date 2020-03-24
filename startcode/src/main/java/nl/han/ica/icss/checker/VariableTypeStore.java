package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;
import java.util.LinkedList;

public class VariableTypeStore {
    private LinkedList<HashMap<String, ExpressionType>> variableTypes;

    public VariableTypeStore() {
        variableTypes = new LinkedList<>();
    }

    public void addScopeLevel(){
        variableTypes.addFirst(new HashMap<>());
    }

    public void removeScopeLevel(){
        variableTypes.removeFirst();
    }

    public void storeVariable(String variable, ExpressionType variableType){
        variableTypes.getFirst().put(variable, variableType);
    }

    public ExpressionType getVariableType(String variable){
        for(HashMap<String, ExpressionType> map : variableTypes){
            if(map.containsKey(variable)){
                return map.get(variable);
            }
        }
        return ExpressionType.UNDEFINED;
    }

    public boolean isDefined(String variable){
        for(HashMap<String, ExpressionType> variables : variableTypes){
            if(variables.containsKey(variable)){
                return true;
            }
        }
        return false;
    }
}
