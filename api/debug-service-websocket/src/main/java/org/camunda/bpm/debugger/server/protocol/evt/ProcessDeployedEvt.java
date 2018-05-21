package org.camunda.bpm.debugger.server.protocol.evt;


public class ProcessDeployedEvt extends EventDto<String> {

  public ProcessDeployedEvt(String id) {
    super("process-deployed", id);
  }

}
