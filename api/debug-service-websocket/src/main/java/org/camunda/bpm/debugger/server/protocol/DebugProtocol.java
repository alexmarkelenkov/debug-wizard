package org.camunda.bpm.debugger.server.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.camunda.bpm.debugger.server.DebugWebsocketConfiguration;
import org.camunda.bpm.debugger.server.netty.websocket.ChannelAttributes;
import org.camunda.bpm.debugger.server.protocol.cmd.DebugCommand;
import org.camunda.bpm.debugger.server.protocol.cmd.DebugCommandContext;
import org.camunda.bpm.debugger.server.protocol.dto.ErrorData;
import org.camunda.bpm.debugger.server.protocol.evt.ErrorEvt;
import org.camunda.bpm.debugger.server.protocol.evt.EventDto;


import org.camunda.bpm.debugger.server.engine.DebugSession;
import org.camunda.bpm.debugger.server.engine.DebugSessionFactory;


public class DebugProtocol {
  protected static Pattern commandNameMatcher = Pattern.compile(".*\"command\"\\s*:\\s*\"([\\w|-]*)\".*");
  protected static Logger LOGG = Logger.getLogger(DebugWebsocketConfiguration.class.getName());
  protected DebugWebsocketConfiguration debugWebsocketConfiguration;
  protected Map<String, Class<? extends DebugCommand<?>>> commandHandlers = new HashMap<String, Class<? extends DebugCommand<?>>>();


  public DebugProtocol(DebugWebsocketConfiguration debugWebsocketConfiguration) {
    this.debugWebsocketConfiguration = debugWebsocketConfiguration;
  }

  public void registerCommandHandler(String cmdName, Class<? extends DebugCommand<?>> handler) {
    this.commandHandlers.put(cmdName, handler);
  }

  public void executeCommand(Channel ch, String commandPayload) {

    try {
      Matcher matcher = commandNameMatcher.matcher(commandPayload);
      if(!matcher.matches()) {
        LOGG.warning("Command has improper format. No command name found.");
        return;
      }

      // get handler
      String commandName = matcher.group(1);
      Class<? extends DebugCommand<?>> commandHandler = commandHandlers.get(commandName);
      if(commandHandler != null) {
        // unmarshall protocol POJO:
        DebugCommand<?> commandDto = debugWebsocketConfiguration
          .getMarshaller()
          .unmarshal(commandPayload, commandHandler);

          commandDto.execute(new DebugCommandContext(ch, this));

      } else {
        LOGG.warning("Unrecognized command '" + commandName + "'.");
        return;
      }

    } catch (Exception e) {
      fireEvent(ch, new ErrorEvt(new ErrorData(e)));
      LOGG.log(Level.WARNING, "Error while processing payload "+commandPayload, e);
    }
  }

  public void fireEvent(Channel channel, EventDto<?> event) {

    try {
      String marshalledEvent = debugWebsocketConfiguration.getMarshaller()
        .marshal(event);

      System.out.println(marshalledEvent);

      channel.writeAndFlush(new TextWebSocketFrame(marshalledEvent));

    } catch (Exception e) {
      LOGG.log(Level.WARNING, "Error while marshaling event payload ", e);
    }

  }

  public void closeSession(ChannelHandlerContext ctx) {
    DebugSession debugSession = ChannelAttributes.getDebugSession(ctx.channel());
    if(debugSession != null) {
      debugSession.close();
    }
  }

  public void openSession(ChannelHandlerContext ctx, DebugSessionFactory debugSessionFactory) {

    DebugSession session = debugSessionFactory.openSession();

    ProtocolDebugEventListener protocolDebugEventListener = new ProtocolDebugEventListener(this, ctx.channel());
    session.registerEventListener(protocolDebugEventListener);

    ChannelAttributes.setDebugEventListener(ctx.channel(), protocolDebugEventListener);
    ChannelAttributes.setDebugSession(ctx.channel(), session);
  }

}
