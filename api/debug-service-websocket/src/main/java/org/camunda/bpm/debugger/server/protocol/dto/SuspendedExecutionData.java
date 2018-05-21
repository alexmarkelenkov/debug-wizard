package org.camunda.bpm.debugger.server.protocol.dto;

import org.camunda.bpm.debugger.server.engine.Execution;

import java.util.ArrayList;
import java.util.List;


public class SuspendedExecutionData {

  protected String id;

  protected String activityInstanceId;

  protected String currentActivityId;

  protected String breakPointType;

  protected String operationType;

  protected String currentTransitionId;

  protected List<String> variables;

  public SuspendedExecutionData(Execution suspendedExecution) {
    id = suspendedExecution.getId();
    activityInstanceId = suspendedExecution.getCurrentBreakPoint().getElementId();
    currentActivityId = suspendedExecution.getCurrentBreakPoint().getElementId();
    operationType = "before";
    currentTransitionId = "null";
    breakPointType = "beforetype";
    variables = new ArrayList<String>();
//    variables = new ArrayList<VariableInstanceDto>();
//    VariableMap variables = suspendedExecution.getVariablesTyped();
//    for (String name : variables.keySet()) {
//      this.variables.add(new VariableInstanceDto(name, variables.getValueTyped(name)));
//    }
  }


}
