package org.camunda.bpm.debugger.server.protocol.dto;

public class VariableInstanceDto {
    protected String variableName;
    protected Object variableValue;

    public VariableInstanceDto(String name, Object value){
        variableName = name;
        variableValue = value;
    }


    public String getVariableName() {
        return variableName;
    }
    public Object getVariableValue() {
        return variableValue;
    }
}
