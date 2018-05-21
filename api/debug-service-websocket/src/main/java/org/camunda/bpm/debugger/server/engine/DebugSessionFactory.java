package org.camunda.bpm.debugger.server.engine;

import java.util.ArrayList;
import java.util.List;

public class DebugSessionFactory {

    private List<DebugSession> sessions = new ArrayList<DebugSession>();

    public DebugSession openSession() {
        DebugSession debugSession = new DebugSession();
        sessions.add(debugSession);
        return debugSession;
    }
}
