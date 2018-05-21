package org.camunda.bpm.debugger.server.engine;

public class BreakPoint {

    private String elementId;

    public BreakPoint(String elementId){
        this.elementId = elementId;
    }

    public String getElementId() {
        return elementId;
    }
}
