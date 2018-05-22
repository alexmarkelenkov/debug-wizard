package org.camunda.bpm.debugger.server.protocol.evt;


public class EventDto<T> {
  protected String event;
  protected T data;


  public EventDto(String eventName, T data) {
    this.event = eventName;
    this.data = data;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
