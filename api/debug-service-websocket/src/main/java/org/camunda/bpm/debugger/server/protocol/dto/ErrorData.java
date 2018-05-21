package org.camunda.bpm.debugger.server.protocol.dto;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;


public class ErrorData {

  protected String errorType;

  protected String errorMessage;

  protected String exceptonStrackTrace;

  public ErrorData() {
  }

  public ErrorData(Exception e) {
    errorType = e.getClass().getSimpleName();
    errorMessage = e.getMessage();
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    PrintWriter writer = new PrintWriter(bos);
    e.printStackTrace(writer);
    writer.flush();
    exceptonStrackTrace = bos.toString();
  }


}
