'use strict';

var Workbench = (function() {

  function Workbench() {
    this.serverSession = null;
    this.diagramProvider = null;
    this.eventBus = null;
    this.update = null;
    // the current view on the workbench
    this.perspective = 'model';
  }

  return Workbench;

})();

module.exports = Workbench;
