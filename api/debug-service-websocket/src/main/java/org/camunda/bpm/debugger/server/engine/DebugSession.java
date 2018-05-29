package org.camunda.bpm.debugger.server.engine;

import org.camunda.bpm.debugger.server.engine.task.ITask;
import org.camunda.bpm.debugger.server.netty.websocket.WebsocketServer;
import org.camunda.bpm.debugger.server.protocol.ProtocolDebugEventListener;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DebugSession {
    final static Logger LOGG = Logger.getLogger(WebsocketServer.class.getName());

    private List<ProtocolDebugEventListener> listeners = new LinkedList<ProtocolDebugEventListener>();
    private Execution currentExecution;
    private String id = "5"; //String.valueOf(new Random().nextInt(10));
    private Set<String> breakpoints = new HashSet<String>();
//  private HashMap<BreakPoint, ITask> breakpoints;


    public void startExecution(){
        if(breakpoints != null){
            currentExecution = new Execution(this, breakpoints);
            currentExecution.run();
        }
        else
            LOGG.log(Level.WARNING, "No breakpoints");
    }

    public void registerEventListener(ProtocolDebugEventListener protocolDebugEventListener) {
        listeners.add(protocolDebugEventListener);
    }

    public void close() {
    }

    public Execution getCurrentExecution() {
        return currentExecution;
    }
    public List<ProtocolDebugEventListener> getListeners() {
        return listeners;
    }
    public void setBreakpoints(Set<String> breakpoints) {
        this.breakpoints = breakpoints;
    }
    public String getId() {
        return id;
    }
}
