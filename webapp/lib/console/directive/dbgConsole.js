'use strict';

var fs = require('fs');

var ConsoleController = [ '$scope', function($scope) {

  var serverSession = $scope.workbench.serverSession;
  var eventBus = $scope.workbench.eventBus;
  var nextId = 0;

  $scope.processDebugger = $scope.workbench.processDebugger;
  $scope.script = null;
  $scope.executionId = null;
  $scope.commandResults = [];
  $scope.scripts = [];
  $scope.historyCurrent = 0;
  $scope.scriptLanguage = null;


  $scope.evaluate = function() {
    if ('clear' === $scope.script) {
      $scope.commandResults = [];

    } else {

      var cmd = {
        cmdId : $scope.dbgCmdId++,
        language: $scope.scriptLanguage,
        executionId: $scope.executionId,
        script: $scope.script
      };

      $scope.commandResults.unshift(cmd);
      $scope.scripts.push($scope.script);
      serverSession.evaluateScript(cmd);
    }

    $scope.historyCurrent = 0;
    $scope.script = '';
  };


  $scope.historyPrevious = function() {
    var entry = $scope.scripts.length - $scope.historyCurrent -1;
    if(entry >= 0) {
      $scope.script = $scope.scripts[entry];
      $scope.historyCurrent++;
    }
  };


  $scope.historyNext = function() {
    var entry = $scope.historyCurrent - 1;
    if (entry >= 0) {
      $scope.historyCurrent--;

      if (entry === 0) {
        $scope.script = '';
      } else {
        $scope.script = $scope.scripts[$scope.commandResults.length - entry];
      }
    }
  };


  function addCommandResults(data, failed) {
    var cmdId = data.cmdId;
    for(var i = 0; i < $scope.commandResults.length; i++) {
      var result = $scope.commandResults[i];
      if(result.cmdId == cmdId) {
        result.result = data.result;
        result.evaluationFailed = failed;
      }
    }
  }


  $scope.fetchHints = function(partialInput) {
    return serverSession.completeCodePrefix({
      prefix: partialInput,
      executionId: $scope.executionId
    });
  }


  function logError(error) {
    $scope.commandResults.push({
      script : error.errorType,
      result : error.errorMessage,
      evaluationFailed : true
    });
  }


  // register event listeners on the debug session

  serverSession.eventBus.on('script-evaluated', function(data) {
    addCommandResults(data, false);
  });

  serverSession.eventBus.on('script-evaluation-failed', function(data) {
    addCommandResults(data, true);
  });

  serverSession.eventBus.on('server-error', function(data) {
    logError(data);
  });


  eventBus.on('execution-selected', function(data) {
    $scope.executionId = data.id;
  });

  eventBus.on('execution-deselected', function() {
    $scope.executionId = null;
  });

  // init
  $scope.scriptLanguage = 'Javascript';

}];

var directiveTemplate = fs.readFileSync(__dirname + '/console.html', { encoding: 'utf-8' });

module.exports = function() {
  return {
    scope: {
      workbench : '=',
      commandResults : '=',
      dbgCmdId : '='
    },
    controller: ConsoleController,
    template: directiveTemplate,
  };
};

