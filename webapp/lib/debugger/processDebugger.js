'use strict';

var BreakpointManager = require('./breakpointManager'),
    ExecutionManager = require('./executionManager');

var ProcessDebugger = (function() {

  // static helper functions /////////////

  function diagramChanged(processDebugger, processDefinitionId) {
    if(processDebugger.processDefinitionId !== processDefinitionId) {
      processDebugger.processDefinitionId = processDefinitionId;
      processDebugger.breakpointManager.clear();
      processDebugger.executionManager.resumeAllExecutions();
    }
  }

  function registerListeners(eventBus, processDebugger) {

    eventBus.on('process-deployed', function(evt) {
      eventBus.fireEvent('diagram-changed', evt);
    });

    eventBus.on('diagram-changed', function(evt) {
      diagramChanged(processDebugger, evt);
    });

    eventBus.on('OPEN', function() {
      processDebugger.breakpointManager.updateBreakpoints();
    });

    eventBus.on('CLOSE', function() {
      processDebugger.executionManager.clear();
    });
  }

  // class ///////////////////////////////


  function ProcessDebugger(workbench) {
    this.workbench = workbench;
    this.breakpointManager = new BreakpointManager(workbench);
    this.executionManager = new ExecutionManager(workbench);
    this.processDefinitionId = null;
    this.processInstanceId = null;
    // initialize
    registerListeners(workbench.eventBus, this);
  }



  ProcessDebugger.prototype.deployProcess = function(callback) {
    var self = this;
    this.workbench.diagramProvider.getBpmnXml(function(err, xml) {

      self.workbench.serverSession.deployProcess({
        resourceName: 'process.bpmn',
        resourceData: xml
      }).success(function(data) {
        if(!!callback) {
          callback(data);
        }
      });

    });
  };



  ProcessDebugger.prototype.isSessionOpen = function() {
    return this.workbench.serverSession.isOpen();
  };




  ProcessDebugger.prototype.canDeployProcess = function() {
    return this.isSessionOpen();
  };



  ProcessDebugger.prototype.runMode = function() {
    if(!this.canRun()) {
      return null;

    } else if(this.executionManager.selectedExecution === null) {
      return "Start Process Instance";

    } else {
      return "Resume Execution";
    }
  };



  ProcessDebugger.prototype.runActivity = function(id) {

    if (!this.canRun()) {
      return null;
    }

    var executions = this.executionManager.executions;

    for (var i = 0, execution; !!(execution = executions[i]); i++) {

      if (execution.currentActivityId === id) {
        this.executionManager.resumeExecution(execution);
      }
    }
  };



  ProcessDebugger.prototype.run = function() {
    if(this.processDefinitionId !== null) {
      var selectedExecution = this.executionManager.selectedExecution;
      if(selectedExecution === null) {
        this.workbench.serverSession.startProcess({
          'processDefinitionId': this.processDefinitionId
        });
      } else {
        this.executionManager.resumeExecution(selectedExecution);
      }
    }
  };



  ProcessDebugger.prototype.canRun = function() {
    if(!this.isSessionOpen() || this.processDefinitionId === null) {
      return false;

    } else {
      var suspendedExecutions = this.executionManager.executions;
      if(suspendedExecutions === null || suspendedExecutions.length === 0) {
        return true;
      } else {
        return this.executionManager.selectedExecution !== null;
      }
    }

  };



  ProcessDebugger.prototype.step = function() {
    var execution = this.executionManager.selectedExecution;
    if(!!execution) {
      this.executionManager.stepExecution(execution);
    }
  };

  ProcessDebugger.prototype.canStep = function() {
    return this.executionManager.selectedExecution !== null;
  };

  ProcessDebugger.prototype.stop = function() {
  };

  ProcessDebugger.prototype.canStop = function() {
    return false;
  };

  ProcessDebugger.prototype.doToggleBreakpoint = function(id) {
    var selectedIds = (id && [id]) || this.workbench.diagramProvider.getSelectedElements();

    for(var i = 0; i<selectedIds.length; i++) {
      var elId = selectedIds[i];
      this.breakpointManager.toggleBreakpointBefore(elId, this.processDefinitionId);
    }
  };

  ProcessDebugger.prototype.toggleBreakpoint = function(id) {
    var self = this;
    if(!this.processDefinitionId) {
      this.deployProcess(function(data) {
        self.processDefinitionId = data;
        self.doToggleBreakpoint(id);
      });
    } else {
      self.doToggleBreakpoint(id);
    }
  };

  ProcessDebugger.prototype.canToggleBreakpoint = function() {
    return this.isSessionOpen() &&
      this.processDefinitionId !== null &&
      this.workbench.diagramProvider.getSelectedElements() !== null &&
      this.workbench.diagramProvider.getSelectedElements().length > 0;
  };

  ProcessDebugger.prototype.openSession = function() {
    // (re-)connect
    this.workbench.serverSession.wsConnection.open();
  };

  ProcessDebugger.prototype.canOpenSession = function() {
    return !this.isSessionOpen();
  };

  ProcessDebugger.prototype.closeSession = function() {
    // close connection to server
    this.workbench.serverSession.wsConnection.close();

  };

  ProcessDebugger.prototype.canCloseSession = function() {
    return this.isSessionOpen();
  };

  return ProcessDebugger;

})();

module.exports = ProcessDebugger;
