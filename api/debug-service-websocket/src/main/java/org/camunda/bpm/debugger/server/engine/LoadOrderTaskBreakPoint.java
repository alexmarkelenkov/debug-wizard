package org.camunda.bpm.debugger.server.engine;

public class LoadOrderTaskBreakPoint extends TaskBreakPoint {

    public LoadOrderTaskBreakPoint(String elementId){
        super(elementId);
    }

    @Override
    public void executeTask() {
        System.out.println("Load Order task code");
    }
}
