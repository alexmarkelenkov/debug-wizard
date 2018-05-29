package org.camunda.bpm.debugger.server.protocol.cmd;

import org.camunda.bpm.debugger.server.engine.Execution;
import org.camunda.bpm.debugger.server.protocol.dto.DeployProcessData;
import org.camunda.bpm.debugger.server.protocol.evt.ProcessDeployedEvt;

import org.camunda.bpm.debugger.server.engine.DebugSession;


public class DeployProcessCmd extends DebugCommand<DeployProcessData> {

  public final static String NAME = "deploy-process";


  public void execute(DebugCommandContext ctx) {
    final DebugSession debugSession = ctx.getDebugSession();
    ctx.fireEvent(new ProcessDeployedEvt(debugSession.getId()));
  }

}
