package org.camunda.bpm.debugger.server.engine;

import org.camunda.bpm.debugger.server.engine.task.ITask;
import org.camunda.bpm.debugger.server.protocol.ProtocolDebugEventListener;

import java.util.*;

import java.util.Map.Entry;

public class Execution {
    private DebugSession session;
    private HashMap<BreakPoint, ITask> breakpoints;
    private Iterator<Entry<BreakPoint, ITask>> iterator;
    private Entry<BreakPoint, ITask> currentEntry;
    private BreakPoint currentBreakPoint;





    public Execution(DebugSession session, HashMap<BreakPoint, ITask> breakpoints){
        this.session = session;
        this.breakpoints = breakpoints;
    }

    public void run(){
        iterator = breakpoints.entrySet().iterator();
        currentEntry = iterator.next();
        currentBreakPoint = currentEntry.getKey();
        currentEntry.getValue().executeTask();

        for(ProtocolDebugEventListener listener: session.getListeners()){
            listener.onExecutionSuspended(this.session);
        }
    }

    public void nextStep() {
        for(ProtocolDebugEventListener listener: session.getListeners()){
            listener.onExecutionUnsuspended(this.session);
        }

        if(iterator.hasNext()){
            currentEntry = iterator.next();
            currentBreakPoint = currentEntry.getKey();
            currentEntry.getValue().executeTask();
            for(ProtocolDebugEventListener listener: session.getListeners()){
                listener.onExecutionSuspended(this.session);
            }
        }
    }


    public BreakPoint getCurrentBreakPoint() {
        return currentBreakPoint;
    }
}
