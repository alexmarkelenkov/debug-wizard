package org.camunda.bpm.debugger.server.engine.task;

public class CalcSumTask implements ITask {
    @Override
    public void executeTask() {
        System.out.println("Calc Sum task code");
    }
}
