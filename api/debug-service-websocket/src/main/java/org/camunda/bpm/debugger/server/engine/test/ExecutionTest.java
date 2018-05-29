package org.camunda.bpm.debugger.server.engine.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

public class ExecutionTest{

    @Test
    void Test()  {
        String destUri = "ws://localhost:9090/debug-session";

        WebSocketClient client = new WebSocketClient();
        SimpleEchoSocket socket = new SimpleEchoSocket();
        try
        {
            client.start();

            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket,echoUri,request);

            //socket.getSession().getRemote().sendStringByFuture("123").get(2, TimeUnit.SECONDS);
            System.out.printf("Connecting to : %s%n",echoUri);

            // wait for closed socket connection.
            socket.awaitClose(2, TimeUnit.SECONDS);

            Iterator<String> it = socket.getResponds().iterator();
            assertEquals("{\"event\":\"process-deployed\",\"data\":\"5\"}", it.next());
            assertEquals("{\"event\":\"execution-suspended\",\"data\":{\"id\":\"5\",\"currentActivityId\":\"loadOrder\",\"variables\":[{\"variableName\":\"Variable_1\",\"variableValue\":\"Value_1\"}]}}", it.next());

        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            try
            {
                client.stop();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


}
