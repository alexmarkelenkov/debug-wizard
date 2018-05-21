package org.camunda.bpm.debugger.server;

public class StandaloneDebugWebsocketBootstrap {

  public static void main(String[] args) {


    DebugWebsocket debugWebsocket = null;
    try {

      // configure & start the server
      debugWebsocket = new DebugWebsocketConfiguration()
        .port(9090)
        .startServer();

      // block
      debugWebsocket.waitForShutdown();

    } finally {
      if(debugWebsocket != null) {
        debugWebsocket.shutdown();
      }
    }

  }

}
