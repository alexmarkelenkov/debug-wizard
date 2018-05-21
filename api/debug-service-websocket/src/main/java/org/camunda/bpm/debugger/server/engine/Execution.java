package org.camunda.bpm.debugger.server.engine;

import org.camunda.bpm.debugger.server.protocol.ProtocolDebugEventListener;
import org.camunda.bpm.debugger.server.protocol.dto.SuspendedExecutionData;

import java.util.*;

import java.util.Map.Entry;

public class Execution {

    private DebugSession session;

    private LinkedHashMap<BreakPoint, Boolean> breakpoints;

    private Iterator<Entry<BreakPoint, Boolean>> iterator;

    private BreakPoint currentBreakPoint = null;

    private String name;

    private String data;

    private String id = String.valueOf(new Random().nextInt(10));



    public Execution(DebugSession session){
        this.session = session;
    }


    public void run(){
//        while (true){
//            for(Entry<BreakPoint, Boolean> entry: breakpoints.entrySet()){
//                if(entry.getValue() && entry.getKey() instanceof TaskBreakPoint)
//                    ((TaskBreakPoint) entry.getKey()).executeTask();
//            }
//        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setBreakpoints(LinkedHashMap<BreakPoint, Boolean> breakpoints) {
        this.breakpoints = breakpoints;
        iterator = breakpoints.entrySet().iterator();
    }

    public void nextStep() {
        for(ProtocolDebugEventListener listener: session.getListeners()){
            listener.onExecutionUnsuspended(this);
        }
        if(iterator.hasNext()){
            Entry<BreakPoint, Boolean> entry = iterator.next();
            entry.setValue(true);
            currentBreakPoint = entry.getKey();
            for(ProtocolDebugEventListener listener: session.getListeners()){
                listener.onExecutionSuspended(this);
            }
        }

    }

    public BreakPoint getCurrentBreakPoint() {
        return currentBreakPoint;
    }
}
