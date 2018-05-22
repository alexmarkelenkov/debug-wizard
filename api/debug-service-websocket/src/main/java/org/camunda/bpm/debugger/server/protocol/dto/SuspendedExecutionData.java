package org.camunda.bpm.debugger.server.protocol.dto;

import org.camunda.bpm.debugger.server.engine.DebugSession;
import org.camunda.bpm.debugger.server.engine.Execution;

import java.util.ArrayList;
import java.util.List;


public class SuspendedExecutionData {

  protected String id;

//  protected String activityInstanceId;

  protected String currentActivityId;

//  protected String breakPointType;
//
//  protected String operationType;
//
//  protected String currentTransitionId;

  //protected List<String> variables;

  public SuspendedExecutionData(DebugSession debugSession) {
    id = debugSession.getId();
    System.out.println(id);
//    System.out.println("data 1");
//    activityInstanceId = suspendedExecution.getCurrentBreakPoint().getElementId();
//    System.out.println("data 2");
    currentActivityId = debugSession.getCurrentExecution().getCurrentBreakPoint().getElementId();
    System.out.println(currentActivityId);
//    System.out.println("data 3");
//    operationType = "before";
//    System.out.println("data 2");
//    currentTransitionId = "null";
//    System.out.println("data 2");
//    breakPointType = "beforetype";
//    System.out.println("data 2");
//    //variables = new ArrayList<String>();
//    System.out.println("data 3");
//    variables = new ArrayList<VariableInstanceDto>();
//    VariableMap variables = suspendedExecution.getVariablesTyped();
//    for (String name : variables.keySet()) {
//      this.variables.add(new VariableInstanceDto(name, variables.getValueTyped(name)));
//    }


  }


  public String getId() {
    return id;
  }


  public String getCurrentActivityId() {
    return currentActivityId;
  }


}
