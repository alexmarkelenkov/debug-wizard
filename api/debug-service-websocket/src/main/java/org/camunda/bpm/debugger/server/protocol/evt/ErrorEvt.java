package org.camunda.bpm.debugger.server.protocol.evt;

import org.camunda.bpm.debugger.server.protocol.dto.ErrorData;

public class ErrorEvt extends EventDto<ErrorData> {

  protected final static String NAME = "server-error";

  public ErrorEvt(ErrorData data) {
    super(NAME, data);
  }

}
