package org.camunda.bpm.debugger.server.engine;

public abstract class TaskBreakPoint extends BreakPoint implements ITask{

    public TaskBreakPoint(String elementId){
        super(elementId);
    }

    public abstract void executeTask();
}
