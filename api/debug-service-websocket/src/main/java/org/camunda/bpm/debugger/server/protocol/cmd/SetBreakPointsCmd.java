package org.camunda.bpm.debugger.server.protocol.cmd;

import java.util.*;

import org.camunda.bpm.debugger.server.engine.BreakPoint;
import org.camunda.bpm.debugger.server.engine.Execution;
import org.camunda.bpm.debugger.server.engine.LoadOrderTaskBreakPoint;
import org.camunda.bpm.debugger.server.protocol.dto.BreakPointDto;
import org.camunda.bpm.debugger.server.protocol.dto.SetBreakPointsData;

import org.camunda.bpm.debugger.server.engine.DebugSession;

public class SetBreakPointsCmd extends DebugCommand<SetBreakPointsData> {

  public static String NAME = "set-breakpoints";




  public void execute(DebugCommandContext ctx) {

    DebugSession debugSession = ctx.getDebugSession();
    Execution execution = debugSession.getCurrentExecution();

    LinkedHashMap<BreakPoint, Boolean> breakPoints = new LinkedHashMap<BreakPoint, Boolean>();

    for (BreakPointDto dto : data.getBreakpoints()) {

      if(dto.getElementId() == "loadOrder")
        breakPoints.put(new LoadOrderTaskBreakPoint(dto.getElementId()), false);
      else
        breakPoints.put(new BreakPoint(dto.getElementId()), false);

    }

    execution.setBreakpoints(breakPoints);
  }



}
