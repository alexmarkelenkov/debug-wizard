package org.camunda.bpm.debugger.server.protocol.evt;

import org.camunda.bpm.debugger.server.protocol.dto.SuspendedExecutionData;


public class ExecutionUnsuspendedEvt extends EventDto<SuspendedExecutionData> {

  public ExecutionUnsuspendedEvt(SuspendedExecutionData data) {
    super("execution-unsuspended", data);
  }

}
