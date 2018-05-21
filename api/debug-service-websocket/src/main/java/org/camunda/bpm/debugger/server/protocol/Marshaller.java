package org.camunda.bpm.debugger.server.protocol;

import java.io.StringWriter;

import org.camunda.bpm.debugger.server.DebugWebsocketConfiguration;
import org.camunda.bpm.debugger.server.DebugWebsocketException;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Marshaller {

  protected DebugWebsocketConfiguration configuration;

  public String marshal(Object object) {

    final ObjectMapper objectMapper = configuration.getObjectMapper();

    try {
      StringWriter valueWriter = new StringWriter();
      objectMapper.writeValue(valueWriter, object);

      return valueWriter.toString();

    } catch (Exception e) {
      throw new DebugWebsocketException("Error while marshalling to JSON ", e);
    }

  }

  public <T> T unmarshal(String source, Class<T> type) {

    final ObjectMapper objectMapper = configuration.getObjectMapper();

    try {
      return objectMapper.readValue(source, type);

    } catch (Exception e) {
      throw new DebugWebsocketException("Error while unmarshalling from JSON ", e);
    }

  }



  public void setConfiguration(DebugWebsocketConfiguration configuration) {
    this.configuration = configuration;
  }

}
