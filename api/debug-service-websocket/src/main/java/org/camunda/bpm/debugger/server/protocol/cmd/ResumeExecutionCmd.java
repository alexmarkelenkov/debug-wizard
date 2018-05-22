package org.camunda.bpm.debugger.server.protocol.cmd;


public class ResumeExecutionCmd extends DebugCommand<String> {
  public final static String NAME = "resume-execution";


  public void execute(DebugCommandContext ctx) {
    ctx.getDebugSession().getCurrentExecution().nextStep();
  }
}
