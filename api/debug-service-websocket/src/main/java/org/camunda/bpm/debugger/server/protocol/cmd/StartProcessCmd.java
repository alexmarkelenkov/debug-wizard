package org.camunda.bpm.debugger.server.protocol.cmd;

import java.util.concurrent.Callable;

import org.camunda.bpm.debugger.server.engine.DebugSession;
import org.camunda.bpm.debugger.server.engine.Execution;
import org.camunda.bpm.debugger.server.protocol.dto.StartProcessData;



public class StartProcessCmd extends DebugCommand<StartProcessData> {
  public final static String NAME = "start-process";


  public void execute(DebugCommandContext ctx) {
    final DebugSession debugSession = ctx.getDebugSession();
    ctx.executeAsync(new Callable<Void>() {

      public Void call() throws Exception {
        debugSession.startExecution();
        return null;
      }

    });
  }
}
