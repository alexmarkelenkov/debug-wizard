'use strict';

var BEFORE_ACTIVITY = 'BEFORE_ACTIVITY';
var AFTER_ACTIVITY = 'AFTER_ACTIVITY';
var BREAKPOINT_NAMES = {};
BREAKPOINT_NAMES[BEFORE_ACTIVITY] = 'Before';
BREAKPOINT_NAMES[AFTER_ACTIVITY] = 'After';



var Breakpoint = (function() {


  function Breakpoint(elementId) {
    this.elementId = elementId;
    this.isActive = true;
  }


  Breakpoint.prototype.toString = function() {
    return this.elementId;
  };


  Breakpoint.prototype.asDto = function() {
    return {
      "elementId" : this.elementId
    };
  };

  return Breakpoint;

})();



var BreakpointManager = (function() {

  function BreakpointManager(workbench) {
    this.serverSession = workbench.serverSession;
    this.workbench = workbench;
    this.breakpoints = [];
  }


  BreakpointManager.prototype.updateBreakpoints = function() {
    if(this.serverSession.isOpen()) {
      var breakpointDtos = [];
      for (var i = 0; i < this.breakpoints.length; i++) {
        var bp = this.breakpoints[i];
        if(bp.isActive) {
          breakpointDtos.push(bp.asDto());
        }
      }
      this.serverSession.setBreakpoints(breakpointDtos);
    }
  };

  BreakpointManager.prototype.toggleBreakpointType = function(bp) {

    this.workbench.eventBus.fireEvent('breakpoint-removed', bp);


    // update breakpoints on server
    this.updateBreakpoints();

    // fire event
    this.workbench.eventBus.fireEvent('breakpoint-added', bp);
  };


  BreakpointManager.prototype.toggleBreakpointActive = function(bp) {
    var eventName;
    if(bp.isActive) {
      eventName = 'breakpoint-added';
    } else {
      eventName = 'breakpoint-removed';
    }

    // update breakpoints on server
    this.updateBreakpoints();

    // fire event
    this.workbench.eventBus.fireEvent(eventName, bp);
  };


  BreakpointManager.prototype.clear= function() {

    this.breakpoints = [];
    this.updateBreakpoints();

  };


  BreakpointManager.prototype.toggleBreakpoint = function(elementId) {
    var removeIdx = -1;
    for(var i=0; i < this.breakpoints.length; i++) {
      var bp = this.breakpoints[i];
      if(bp.elementId === elementId) {
        removeIdx = i;
      }
    }

    var eventName, changedBreakpoint;

    if(removeIdx >= 0) {
      // remove existing breakpoint
      eventName = "breakpoint-removed";
      changedBreakpoint = this.breakpoints[removeIdx];
      this.breakpoints.splice(removeIdx, 1);

    } else {
      // add new breakpoint
      eventName = "breakpoint-added";
      changedBreakpoint = new Breakpoint(elementId);
      this.breakpoints.push(changedBreakpoint);

    }

    this.updateBreakpoints();

    // fire event
    this.workbench.eventBus.fireEvent(eventName, changedBreakpoint);
  };


  BreakpointManager.prototype.toggleBreakpointBefore = function(elementId) {
    this.toggleBreakpoint(elementId);
  }; 


  BreakpointManager.prototype.getBreakpoints = function() {
    return this.breakpoints;
  };

  return BreakpointManager;

})();

module.exports = BreakpointManager;
