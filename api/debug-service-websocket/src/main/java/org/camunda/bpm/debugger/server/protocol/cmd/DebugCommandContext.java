package org.camunda.bpm.debugger.server.protocol.cmd;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.camunda.bpm.debugger.server.netty.websocket.ChannelAttributes;
import org.camunda.bpm.debugger.server.protocol.DebugProtocol;
import org.camunda.bpm.debugger.server.protocol.evt.EventDto;

import org.camunda.bpm.debugger.server.engine.DebugSession;


import io.netty.channel.Channel;


public class DebugCommandContext {

  protected Channel channel;
  protected DebugProtocol protocol;

  public DebugCommandContext(Channel channel, DebugProtocol protocol) {
    this.channel = channel;
    this.protocol = protocol;
  }

  protected String getProcessDefinitionId() {
    return ChannelAttributes.getProcessDefinitionId(channel);
  }

  public void fireEvent(EventDto<?> event) {
    protocol.fireEvent(channel, event);
  }

  protected DebugSession getDebugSession() {
    return ChannelAttributes.getDebugSession(channel);
  }

  public <V> Future<V> executeAsync(Callable<V> callable) {
    FutureTask<V> task = new FutureTask<V>(callable);
    Thread thread = new Thread(task);
    thread.start();
    return task;
  }

}
