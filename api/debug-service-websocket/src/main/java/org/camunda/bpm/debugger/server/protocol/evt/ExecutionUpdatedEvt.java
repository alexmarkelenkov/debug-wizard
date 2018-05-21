package org.camunda.bpm.debugger.server.protocol.evt;

import org.camunda.bpm.debugger.server.protocol.dto.SuspendedExecutionData;


public class ExecutionUpdatedEvt extends EventDto<SuspendedExecutionData> {

  public ExecutionUpdatedEvt(SuspendedExecutionData data) {
    super("execution-updated", data);
  }

}
