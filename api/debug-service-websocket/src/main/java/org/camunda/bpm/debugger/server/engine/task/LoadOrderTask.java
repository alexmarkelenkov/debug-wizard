package org.camunda.bpm.debugger.server.engine.task;


import org.camunda.bpm.debugger.server.engine.task.ITask;

public class LoadOrderTask implements ITask {
    @Override
    public void executeTask() {

        System.out.println("Load Order task code");

    }
}
