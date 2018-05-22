'use strict';

var WsConnection = (function() {


  function WsConnection(serverUrl) {

    this.serverUrl = serverUrl;
    this.listeners = [];
    this.ws = null;

  }

  WsConnection.prototype.open = function() {

    if(this.ws !== null) {
      this.close();
    }

    // open websocket conneciton
    this.ws = new WebSocket(this.serverUrl);

    var listeners = this.listeners;
    var self = this;

    this.ws.onopen = function() {
      handleMessage({data: '{"event" : "OPEN"}'}, listeners);
    };

    this.ws.onclose = function() {
      handleMessage({data: '{"event" : "CLOSE"}'}, listeners);
      self.ws = null;
    };

    this.ws.onmessage = function(msg) {
      handleMessage(msg, listeners);
    };

  };



  WsConnection.prototype.send = function(msg) {
    this.ws.send(msg);
  };



  WsConnection.prototype.close = function() {

    this.ws.close();

  };



  WsConnection.prototype.onMessage = function(listener) {

    this.listeners.unshift(listener);

  };



  WsConnection.prototype.isOpen = function() {
    return this.ws !== null && this.ws.readyState == 1;
  };



  function handleMessage(msg, listeners) {
    for(var i=0; i < listeners.length; i++) {
      listeners[i](msg);
    }
  }

  return WsConnection;

})();

module.exports = WsConnection;
