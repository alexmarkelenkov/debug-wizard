package org.camunda.bpm.debugger.server.protocol.evt;

import org.camunda.bpm.debugger.server.protocol.dto.SuspendedExecutionData;


public class ExecutionSuspendedEvt extends EventDto<SuspendedExecutionData> {

  public ExecutionSuspendedEvt(SuspendedExecutionData data) {
    super("execution-suspended", data);
  }

}
