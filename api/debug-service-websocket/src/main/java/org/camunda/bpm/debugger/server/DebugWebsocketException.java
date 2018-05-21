package org.camunda.bpm.debugger.server;


public class DebugWebsocketException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DebugWebsocketException(String message, Throwable cause) {
    super(message, cause);
  }

}
