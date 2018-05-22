package org.camunda.bpm.debugger.server;

import org.camunda.bpm.debugger.server.netty.AbstractNettyServer;
import org.camunda.bpm.debugger.server.netty.websocket.WebsocketServer;
import org.camunda.bpm.debugger.server.protocol.DebugProtocol;
import org.camunda.bpm.debugger.server.protocol.Marshaller;
import org.camunda.bpm.debugger.server.protocol.cmd.DeployProcessCmd;
import org.camunda.bpm.debugger.server.protocol.cmd.ResumeExecutionCmd;
import org.camunda.bpm.debugger.server.protocol.cmd.SetBreakPointsCmd;
import org.camunda.bpm.debugger.server.protocol.cmd.StartProcessCmd;

import com.fasterxml.jackson.databind.ObjectMapper;


public class DebugWebsocketConfiguration {

  protected AbstractNettyServer nettyServer;
  protected DebugProtocol protocol;
  protected int port = 9090;
  protected ObjectMapper objectMapper;
  protected Marshaller marshaller;



  public DebugWebsocket startServer() {
    init();
    return new DebugWebsocket(this);
  }

  protected void init() {
    initOjectMapper();
    initMarshaller();
    initProtocol();
    initNettyServer();
  }

  protected void initMarshaller() {
    if(marshaller == null) {
      marshaller = new Marshaller();
    }
    marshaller.setConfiguration(this);
  }

  protected void initOjectMapper() {
    if(objectMapper == null) {
      objectMapper = new ObjectMapper();
    }
  }

  protected void initProtocol() {
    if(protocol == null) {
      protocol = new DebugProtocol(this);

      // configure the protocol with the default handlers
      protocol.registerCommandHandler(SetBreakPointsCmd.NAME, SetBreakPointsCmd.class);
      protocol.registerCommandHandler(DeployProcessCmd.NAME, DeployProcessCmd.class);
      protocol.registerCommandHandler(StartProcessCmd.NAME, StartProcessCmd.class);
      protocol.registerCommandHandler(ResumeExecutionCmd.NAME, ResumeExecutionCmd.class);
    }
  }

  protected void initNettyServer() {
    if(nettyServer == null) {
      nettyServer = new WebsocketServer(port, this);
    }
  }

  public DebugWebsocketConfiguration protocol(DebugProtocol protocol) {
    this.protocol = protocol;
    return this;
  }

  public DebugWebsocketConfiguration port(int port) {
    this.port = port;
    return this;
  }



  public AbstractNettyServer getNettyServer() {
    return nettyServer;
  }
  public DebugProtocol getProtocol() {
    return protocol;
  }
  public void setProtocol(DebugProtocol protocol) {
    this.protocol = protocol;
  }
  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }
  public Marshaller getMarshaller() {
    return marshaller;
  }

}
