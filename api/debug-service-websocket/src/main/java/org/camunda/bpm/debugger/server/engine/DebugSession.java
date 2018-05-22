package org.camunda.bpm.debugger.server.engine;

import org.camunda.bpm.debugger.server.engine.task.ITask;
import org.camunda.bpm.debugger.server.netty.websocket.WebsocketServer;
import org.camunda.bpm.debugger.server.protocol.ProtocolDebugEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DebugSession {
    final static Logger LOGG = Logger.getLogger(WebsocketServer.class.getName());

    private List<ProtocolDebugEventListener> listeners = new LinkedList<ProtocolDebugEventListener>();
    private HashMap<BreakPoint, ITask> breakpoints;
    private Execution currentExecution;
    private String id = String.valueOf(new Random().nextInt(10));


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
    public void setBreakpoints(HashMap<BreakPoint, ITask> breakpoints) {
        this.breakpoints = breakpoints;
    }

    public String getId() {
        return id;
    }
}
