package org.camunda.bpm.debugger.server.protocol.dto;

import java.util.List;

public class SetBreakPointsData {
  protected List<BreakPointDto> breakpoints;

  public List<BreakPointDto> getBreakpoints() {
    return breakpoints;
  }

}
