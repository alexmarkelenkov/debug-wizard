package org.camunda.bpm.debugger.server.netty.websocket;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import org.camunda.bpm.debugger.server.protocol.ProtocolDebugEventListener;
import org.camunda.bpm.debugger.server.engine.DebugSession;

public class ChannelAttributes {
  protected static final AttributeKey<String> PROCESS_DEF_ID_ATTR = AttributeKey.valueOf("processDefinitionId");
  protected static final AttributeKey<ProtocolDebugEventListener> DBG_EVT_LISTENER_ATTR = AttributeKey.valueOf("debugEventListener");
  protected static final AttributeKey<DebugSession> DBG_SESSION_ATTR = AttributeKey.valueOf("debugSession");


  public static String getProcessDefinitionId(Channel channel) {
    return channel.attr(PROCESS_DEF_ID_ATTR).get();
  }

  public static void setProcessDefinitionId(Channel channel, String value) {
    channel.attr(PROCESS_DEF_ID_ATTR).set(value);
  }

  public static ProtocolDebugEventListener getDebugEventListener(Channel channel) {
    return channel.attr(DBG_EVT_LISTENER_ATTR).get();
  }

  public static void setDebugEventListener(Channel channel, ProtocolDebugEventListener debugEventListener) {
    channel.attr(DBG_EVT_LISTENER_ATTR).set(debugEventListener);
  }

  public static DebugSession getDebugSession(Channel channel) {
    return channel.attr(DBG_SESSION_ATTR).get();
  }

  public static void setDebugSession(Channel channel, DebugSession session) {
    channel.attr(DBG_SESSION_ATTR).set(session);
  }

}
