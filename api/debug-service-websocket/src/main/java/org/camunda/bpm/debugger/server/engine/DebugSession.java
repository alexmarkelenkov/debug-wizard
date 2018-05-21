package org.camunda.bpm.debugger.server.engine;

import org.camunda.bpm.debugger.server.protocol.ProtocolDebugEventListener;

import java.util.LinkedList;
import java.util.List;

public class DebugSession {

    private List<ProtocolDebugEventListener> listeners = new LinkedList<ProtocolDebugEventListener>();


    private Execution currentExecution = new Execution(this);

    public void registerEventListener(ProtocolDebugEventListener protocolDebugEventListener) {
        listeners.add(protocolDebugEventListener);
    }

    public void close() {

    }

    public Execution getCurrentExecution() {
        return currentExecution;
    }

    public void setCurrentExecution(Execution currentExecution) {
        this.currentExecution = currentExecution;
    }

    public List<ProtocolDebugEventListener> getListeners() {
        return listeners;
    }
}
