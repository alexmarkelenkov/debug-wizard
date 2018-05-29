package org.camunda.bpm.debugger.server.protocol.dto;

import org.camunda.bpm.debugger.server.engine.DebugSession;
import org.camunda.bpm.debugger.server.engine.Execution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SuspendedExecutionData {
  protected String id;
  protected String currentActivityId;
  //protected Map<String, Object> vars;
  protected List<VariableInstanceDto> variables;


  public SuspendedExecutionData(DebugSession debugSession) {
    id = debugSession.getId();
    currentActivityId = debugSession.getCurrentExecution().getId();
    variables = new ArrayList<VariableInstanceDto>();
    Map<String, Object> variables = debugSession.getCurrentExecution().getVariables();
    for (Map.Entry<String, Object> entry: variables.entrySet()) {
      this.variables.add(new VariableInstanceDto(entry.getKey(), entry.getValue()));
    }
}

  public List<VariableInstanceDto> getVariables() {
    return variables;
  }
  public String getId() {
    return id;
  }
  public String getCurrentActivityId() {
    return currentActivityId;
  }

}
