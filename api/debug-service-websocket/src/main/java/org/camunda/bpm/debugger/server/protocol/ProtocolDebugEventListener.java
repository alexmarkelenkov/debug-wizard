package org.camunda.bpm.debugger.server.protocol;

import io.netty.channel.Channel;

import org.camunda.bpm.debugger.server.engine.DebugSession;
import org.camunda.bpm.debugger.server.engine.Execution;
import org.camunda.bpm.debugger.server.protocol.dto.SuspendedExecutionData;
import org.camunda.bpm.debugger.server.protocol.evt.ExecutionSuspendedEvt;
import org.camunda.bpm.debugger.server.protocol.evt.ExecutionUnsuspendedEvt;
import org.camunda.bpm.debugger.server.protocol.evt.ExecutionUpdatedEvt;

public class ProtocolDebugEventListener {

  protected Channel channel;
  protected DebugProtocol protocol;

  public ProtocolDebugEventListener(DebugProtocol protocol, Channel channel) {
    this.protocol = protocol;
    this.channel = channel;
  }

  public void onExecutionSuspended(DebugSession debugSession) {
    protocol.fireEvent(channel, new ExecutionSuspendedEvt(new SuspendedExecutionData(debugSession)));
  }

  public void onExecutionUpdated(DebugSession debugSession) {
    protocol.fireEvent(channel, new ExecutionUpdatedEvt(new SuspendedExecutionData(debugSession)));
  }

  public void onExecutionUnsuspended(DebugSession debugSession) {
    protocol.fireEvent(channel, new ExecutionUnsuspendedEvt(new SuspendedExecutionData(debugSession)));
  }





}
