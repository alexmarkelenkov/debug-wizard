package org.camunda.bpm.debugger.server.protocol.cmd;


public abstract class DebugCommand<T> {

  protected String command;

  protected T data;

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public abstract void execute(DebugCommandContext ctx);



}
