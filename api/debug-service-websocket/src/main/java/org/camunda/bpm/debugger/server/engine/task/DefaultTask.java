package org.camunda.bpm.debugger.server.engine.task;

import org.camunda.bpm.debugger.server.engine.task.ITask;

public class DefaultTask implements ITask {
    @Override
    public void executeTask() {

        System.out.println("Default task code");

    }
}
