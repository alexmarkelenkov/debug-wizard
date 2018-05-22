'use strict';

var ServerSession = (function() {

  function ServerSession(wsConnection, eventBus) {
    this.wsConnection = wsConnection;
    this.eventBus = eventBus;
    // register as message listener on the connection:
    wsConnection.onMessage(function(msg) {
      handleMessage(msg, eventBus);
    });
  }

  ServerSession.prototype.promise = function(evtName) {
    // buffered event
    var bufferedEvent = null;
    var callback = null;

    var listener = function(data) {
      if(!!callback) {
        callback(data);
      } else {
        bufferedEvent = data;
      }
    };

    this.eventBus.on(evtName, listener, true);

    return {
      success : function(callb) {
        if(!!bufferedEvent) {
          callb(bufferedEvent);
        } else {
          callback = callb;
        }
      }
    };

  };


  ServerSession.prototype.isOpen = function() {
    return this.wsConnection.isOpen();
  };

  // supported commands /////////////////////////

  ServerSession.prototype.setBreakpoints = function(breakpoints) {

    var cmd = {
      "command" : "set-breakpoints",
      "data" : {
        "breakpoints" : breakpoints
      }
    };

    execute(cmd, this.wsConnection);
  };

  //
  // ServerSession.prototype.evaluateScript = function(scriptInfo) {
  //
  //   var cmd = {
  //     "command" : "evaluate-script",
  //     "data" : scriptInfo
  //   };
  //
  //   execute(cmd, this.wsConnection);
  // };
  //
  //
  // ServerSession.prototype.getScript = function(scriptInfo) {
  //   var cmd = {
  //     "command" : "get-script",
  //     "data" : scriptInfo
  //   };
  //
  //   execute(cmd, this.wsConnection);
  // };
  //
  //
  // ServerSession.prototype.updateScript = function(scriptInfo) {
  //   var cmd = {
  //     "command" : "update-script",
  //     "data" : scriptInfo
  //   };
  //
  //   execute(cmd, this.wsConnection);
  // };


  ServerSession.prototype.deployProcess = function(resourceData) {

    var cmd = {
      "command" : "deploy-process",
      "data" : resourceData
    };

    execute(cmd, this.wsConnection);

    return this.promise("process-deployed");
  };


  ServerSession.prototype.startProcess = function(startProcessData) {

    var cmd = {
      "command" : "start-process",
      "data" : startProcessData
    };

    execute(cmd, this.wsConnection);
  };


  ServerSession.prototype.resumeExecution = function(id) {

    var cmd = {
      "command" : "resume-execution",
      "data" : id
    };

    execute(cmd, this.wsConnection);
  };



  ServerSession.prototype.stepExecution = function(id) {

    var cmd = {
      "command" : "step-execution",
      "data" : id
    };

    execute(cmd, this.wsConnection);
  };


  ServerSession.prototype.listProcessDefinitions = function() {
    var cmd = {
      "command" : "list-process-definitions",
      "data": {}
    };

    execute(cmd, this.wsConnection);

    return this.promise("process-definitions-list");
  };


  ServerSession.prototype.getProcessDefinitionXml = function(processDefId) {
    var cmd = {
      "command" : "get-process-definition-xml",
      "data": processDefId
    };

    execute(cmd, this.wsConnection);

    return this.promise("process-definition-xml");
  };

  ServerSession.prototype.completeCodePrefix = function(completionRequest) {
    var cmd = {
      "command" : "code-completion",
      "data": completionRequest
    };

    execute(cmd, this.wsConnection);

    return this.promise("code-completion-hints");
  };

  // private static helpers /////////////////////


  function execute(cmd, connection) {
    var payload = marshall(cmd);

    // send the payload through the websocket
    connection.send(payload);
  }


  function unmarshall(string) {
    return JSON.parse(string);
  }

  function marshall(object) {
    return JSON.stringify(object);
  }


  function handleMessage(evt, eventBus) {

    // unmarshall the event
    var eventObject = unmarshall(evt.data);

    // fire event on bus
    eventBus.fireEvent(eventObject.event, eventObject.data);
  }

  return ServerSession;
})();

module.exports = ServerSession;
