package org.camunda.bpm.debugger.server.engine;

import org.camunda.bpm.debugger.server.engine.task.ITask;
import org.camunda.bpm.debugger.server.protocol.ProtocolDebugEventListener;

import java.util.*;

import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Execution {
    private DebugSession session;
    private Set<String> breakpoints;
    private BlockingQueue<String> queue;
    private String id = "";
    private Map<String, Object> variables = new HashMap<String, Object>();
//    private HashMap<BreakPoint, ITask> breakpoints;
//    private Iterator<Entry<BreakPoint, ITask>> iterator;
//    private Entry<BreakPoint, ITask> currentEntry;
//    private BreakPoint currentBreakPoint;



    public Execution(DebugSession session, Set<String> breakpoints){
        this.session = session;
        this.breakpoints = breakpoints;
        this.queue = new ArrayBlockingQueue<String>(1, true);
    }


    public void run(){
        step("StartEvent_1");
        System.out.println("StartEvent_1");
        variables.put("Variable_1", "Value_1");
        step("loadOrder");
        System.out.println("loadOrder");
        variables.put("Variable_2", "Value_2");
        step("calcSum");
        System.out.println("calcSum");
        variables.put("Variable_3", "Value_3");
        step("ScriptTask_3");
        System.out.println("ScriptTask_3");
        step("EndEvent_1");
        System.out.println("EndEvent_1");
    }

    public void step(String bpmnId){
        if(breakpoints.contains(bpmnId)){
            id = bpmnId;
            for(ProtocolDebugEventListener listener: session.getListeners()){
                listener.onExecutionSuspended(this.session);
            }
            try {
                queue.take();
                for(ProtocolDebugEventListener listener: session.getListeners()){
                    listener.onExecutionUnsuspended(this.session);
                }
            } catch (InterruptedException ex){
                System.out.println("Interrupted");
            }
        }
    }


    public void nextStep(){

        try{
            queue.put("step");
        } catch (InterruptedException ex){
            System.out.println("Interrupted");
        }
    }

    public String getId() {
        return id;
    }
    public Map<String, Object> getVariables() {
        return variables;
    }


    //    public void run(){
//        iterator = breakpoints.entrySet().iterator();
//        currentEntry = iterator.next();
//        currentBreakPoint = currentEntry.getKey();
//        currentEntry.getValue().executeTask();
//
//        for(ProtocolDebugEventListener listener: session.getListeners()){
//            listener.onExecutionSuspended(this.session);
//        }
//    }

//    public void nextStep() {
//        for(ProtocolDebugEventListener listener: session.getListeners()){
//            listener.onExecutionUnsuspended(this.session);
//        }
//
//        if(iterator.hasNext()){
//            currentEntry = iterator.next();
//            currentBreakPoint = currentEntry.getKey();
//            currentEntry.getValue().executeTask();
//            for(ProtocolDebugEventListener listener: session.getListeners()){
//                listener.onExecutionSuspended(this.session);
//            }
//        }
//    }
//
//
//    public BreakPoint getCurrentBreakPoint() {
//        return currentBreakPoint;
//    }

}
