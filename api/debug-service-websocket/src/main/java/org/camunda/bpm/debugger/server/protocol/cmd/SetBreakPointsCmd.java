package org.camunda.bpm.debugger.server.protocol.cmd;

import java.util.*;

import org.camunda.bpm.debugger.server.engine.*;
import org.camunda.bpm.debugger.server.engine.task.*;
import org.camunda.bpm.debugger.server.protocol.dto.BreakPointDto;
import org.camunda.bpm.debugger.server.protocol.dto.SetBreakPointsData;

public class SetBreakPointsCmd extends DebugCommand<SetBreakPointsData> {

  public static String NAME = "set-breakpoints";

  public void execute(DebugCommandContext ctx) {

    DebugSession debugSession = ctx.getDebugSession();

//    LoadOrderTask loadOrderTask = new LoadOrderTask();
//    DefaultTask defaultTask = new DefaultTask();
//    CalcSumTask calcSumTask = new CalcSumTask();
//    ScriptTask_3 scriptTask_3 = new ScriptTask_3();
//
//    HashMap<BreakPoint, ITask> breakPoints = new HashMap<BreakPoint, ITask>();
//
//    for (BreakPointDto dto : data.getBreakpoints()) {
//      if(dto.getElementId().equals("loadOrder"))
//        breakPoints.put(new BreakPoint(dto.getElementId()), loadOrderTask);
//      else if(dto.getElementId().equals("calcSum"))
//        breakPoints.put(new BreakPoint(dto.getElementId()), calcSumTask);
//      else if(dto.getElementId().equals("ScriptTask_3"))
//        breakPoints.put(new BreakPoint(dto.getElementId()), scriptTask_3);
//      else
//        breakPoints.put(new BreakPoint(dto.getElementId()), defaultTask);
//    }

    HashSet<String> breakPoints = new HashSet<String>();

    for(BreakPointDto dto: data.getBreakpoints()){
        breakPoints.add(dto.getElementId());
    }

    debugSession.setBreakpoints(breakPoints);
  }

}
