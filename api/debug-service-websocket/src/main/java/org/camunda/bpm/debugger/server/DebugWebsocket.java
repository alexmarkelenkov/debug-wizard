package org.camunda.bpm.debugger.server;

import io.netty.channel.ChannelFuture;


public class DebugWebsocket {

  protected DebugWebsocketConfiguration debugWebsocketConfiguration;

  protected ChannelFuture closeFuture;


  public DebugWebsocket(DebugWebsocketConfiguration debugWebsocketConfiguration) {
    this.debugWebsocketConfiguration = debugWebsocketConfiguration;

    try {
      closeFuture = debugWebsocketConfiguration.getNettyServer().run()
        .sync()
        .channel()
        .closeFuture();

    } catch(Exception e) {
      throw new DebugWebsocketException("Exception while staring server", e);

    }

  }

  public void waitForShutdown() {
    try {
      closeFuture.sync();
    } catch (InterruptedException e) {
      // ignore
    }
  }

  public void shutdown() {
    debugWebsocketConfiguration.getNettyServer().shutdown();
  }

  public DebugWebsocketConfiguration getDebugWebsocketConfiguration() {
    return debugWebsocketConfiguration;
  }
}
