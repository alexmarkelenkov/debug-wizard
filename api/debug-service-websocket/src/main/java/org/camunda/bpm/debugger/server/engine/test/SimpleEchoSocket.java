package org.camunda.bpm.debugger.server.engine.test;

import java.util.LinkedList;
import java.util.concurrent.*;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


@WebSocket(maxTextMessageSize = 64 * 1024)
public class SimpleEchoSocket
{
    private LinkedList<String> responds = new LinkedList<String>();
    private final CountDownLatch closeLatch;
    @SuppressWarnings("unused")
    private Session session;

    public SimpleEchoSocket()
    {
        this.closeLatch = new CountDownLatch(1);
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException
    {
        return this.closeLatch.await(duration,unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        System.out.printf("Connection closed: %d - %s%n",statusCode,reason);
        this.session = null;
        this.closeLatch.countDown(); // trigger latch
    }

    @OnWebSocketConnect
    public void onConnect(Session session)
    {
        System.out.printf("Got connect: %s%n",session);
        this.session = session;
        try
        {
            Future<Void> fut;
            fut = session.getRemote().sendStringByFuture("{\"command\":\"set-breakpoints\",\"data\":{\"breakpoints\":[]}}");
            fut.get(1,TimeUnit.SECONDS); // wait for send to complete.

            fut = session.getRemote().sendStringByFuture("{\"command\":\"deploy-process\",\"data\":{\"resourceName\":\"process.bpmn\",\"resourceData\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?>\\n<definitions xmlns:bpmn2=\\\"http://www.omg.org/spec/BPMN/20100524/MODEL\\\" xmlns:bpmndi=\\\"http://www.omg.org/spec/BPMN/20100524/DI\\\" xmlns:dc=\\\"http://www.omg.org/spec/DD/20100524/DC\\\" xmlns:di=\\\"http://www.omg.org/spec/DD/20100524/DI\\\" xmlns:tns=\\\"http://activiti.org/bpmn\\\" xmlns:xsd=\\\"http://www.w3.org/2001/XMLSchema\\\" xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns:yaoqiang=\\\"http://bpmn.sourceforge.net\\\" exporter=\\\"camunda modeler\\\" exporterVersion=\\\"2.5.0\\\" expressionLanguage=\\\"http://www.w3.org/1999/XPath\\\" id=\\\"_UMTjANlIEeO126z8O3IuJg\\\" name=\\\"\\\" targetNamespace=\\\"http://activiti.org/bpmn\\\" typeLanguage=\\\"http://www.w3.org/2001/XMLSchema\\\" xsi:schemaLocation=\\\"http://www.omg.org/spec/BPMN/20100524/MODEL http://bpmn.sourceforge.net/schemas/BPMN20.xsd\\\">\\n  <bpmn2:process id=\\\"Process_1\\\" isClosed=\\\"false\\\" isExecutable=\\\"true\\\" processType=\\\"None\\\">\\n    <bpmn2:extensionElements>\\n      <yaoqiang:description/>\\n      <yaoqiang:pageFormat height=\\\"841.8897637795276\\\" imageableHeight=\\\"831.8897637795276\\\" imageableWidth=\\\"588.1102362204724\\\" imageableX=\\\"5.0\\\" imageableY=\\\"5.0\\\" orientation=\\\"0\\\" width=\\\"598.1102362204724\\\"/>\\n      <yaoqiang:page background=\\\"#FFFFFF\\\" horizontalCount=\\\"1\\\" verticalCount=\\\"1\\\"/>\\n    </bpmn2:extensionElements>\\n    <bpmn2:startEvent id=\\\"StartEvent_1\\\" isInterrupting=\\\"true\\\" parallelMultiple=\\\"false\\\">\\n      <bpmn2:outgoing>SequenceFlow_1</bpmn2:outgoing>\\n      <bpmn2:outputSet/>\\n    </bpmn2:startEvent>\\n    <bpmn2:sequenceFlow id=\\\"SequenceFlow_1\\\" sourceRef=\\\"StartEvent_1\\\" targetRef=\\\"loadOrder\\\"/>\\n    <bpmn2:scriptTask completionQuantity=\\\"1\\\" id=\\\"loadOrder\\\" isForCompensation=\\\"false\\\" name=\\\"Load order\\\" scriptFormat=\\\"Javascript\\\" startQuantity=\\\"1\\\">\\n      <bpmn2:incoming>SequenceFlow_1</bpmn2:incoming>\\n      <bpmn2:outgoing>SequenceFlow_2</bpmn2:outgoing>\\n      <bpmn2:script><![CDATA[print(\\\"LOAD ORDER\\\");\\nexecution.setVariable(\\\"data\\\", \\\"ORDER_DATA\\\");]]></bpmn2:script>\\n    </bpmn2:scriptTask>\\n    <bpmn2:scriptTask completionQuantity=\\\"1\\\" id=\\\"calcSum\\\" isForCompensation=\\\"false\\\" name=\\\"Calculate sum\\\" scriptFormat=\\\"Javascript\\\" startQuantity=\\\"1\\\">\\n      <bpmn2:incoming>SequenceFlow_2</bpmn2:incoming>\\n      <bpmn2:outgoing>SequenceFlow_8</bpmn2:outgoing>\\n      <bpmn2:script><![CDATA[execution.setVariable(\\\"sum\\\", 100);]]></bpmn2:script>\\n    </bpmn2:scriptTask>\\n    <bpmn2:sequenceFlow id=\\\"SequenceFlow_2\\\" sourceRef=\\\"loadOrder\\\" targetRef=\\\"calcSum\\\"/>\\n    <bpmn2:sequenceFlow id=\\\"SequenceFlow_8\\\" sourceRef=\\\"calcSum\\\" targetRef=\\\"ScriptTask_3\\\"/>\\n    <bpmn2:scriptTask completionQuantity=\\\"1\\\" id=\\\"ScriptTask_3\\\" isForCompensation=\\\"false\\\" name=\\\"Handle order\\\" scriptFormat=\\\"Javascript\\\" startQuantity=\\\"1\\\">\\n      <bpmn2:incoming>SequenceFlow_8</bpmn2:incoming>\\n      <bpmn2:outgoing>SequenceFlow_5</bpmn2:outgoing>\\n      <bpmn2:script><![CDATA[print(\\\"ORDER HANDLED \\\" + execution.getVariable(\\\"data\\\"));]]></bpmn2:script>\\n    </bpmn2:scriptTask>\\n    <bpmn2:sequenceFlow id=\\\"SequenceFlow_5\\\" sourceRef=\\\"ScriptTask_3\\\" targetRef=\\\"EndEvent_1\\\"/>\\n    <bpmn2:endEvent id=\\\"EndEvent_1\\\">\\n      <bpmn2:incoming>SequenceFlow_5</bpmn2:incoming>\\n      <bpmn2:inputSet/>\\n    </bpmn2:endEvent>\\n  </bpmn2:process>\\n  <bpmndi:BPMNDiagram id=\\\"Yaoqiang_Diagram-Process_1\\\" name=\\\"Untitled Diagram\\\" resolution=\\\"96.0\\\">\\n    <bpmndi:BPMNPlane bpmnElement=\\\"Process_1\\\">\\n      <bpmndi:BPMNShape bpmnElement=\\\"StartEvent_1\\\" id=\\\"Yaoqiang-StartEvent_1\\\">\\n        <dc:Bounds height=\\\"32.0\\\" width=\\\"32.0\\\" x=\\\"150.0\\\" y=\\\"216.0\\\"/>\\n        <bpmndi:BPMNLabel>\\n          <dc:Bounds height=\\\"18.8\\\" width=\\\"6.0\\\" x=\\\"165.0\\\" y=\\\"257.08\\\"/>\\n        </bpmndi:BPMNLabel>\\n      </bpmndi:BPMNShape>\\n      <bpmndi:BPMNShape bpmnElement=\\\"loadOrder\\\" id=\\\"Yaoqiang-loadOrder\\\">\\n        <dc:Bounds height=\\\"80.0\\\" width=\\\"100.0\\\" x=\\\"276.0\\\" y=\\\"194.0\\\"/>\\n        <bpmndi:BPMNLabel>\\n          <dc:Bounds height=\\\"18.8\\\" width=\\\"68.0\\\" x=\\\"292.0\\\" y=\\\"226.6\\\"/>\\n        </bpmndi:BPMNLabel>\\n      </bpmndi:BPMNShape>\\n      <bpmndi:BPMNShape bpmnElement=\\\"calcSum\\\" id=\\\"Yaoqiang-calcSum\\\">\\n        <dc:Bounds height=\\\"80.0\\\" width=\\\"100.0\\\" x=\\\"426.0\\\" y=\\\"194.0\\\"/>\\n        <bpmndi:BPMNLabel>\\n          <dc:Bounds height=\\\"18.8\\\" width=\\\"87.0\\\" x=\\\"432.5\\\" y=\\\"226.6\\\"/>\\n        </bpmndi:BPMNLabel>\\n      </bpmndi:BPMNShape>\\n      <bpmndi:BPMNShape bpmnElement=\\\"ScriptTask_3\\\" id=\\\"Yaoqiang-ScriptTask_3\\\">\\n        <dc:Bounds height=\\\"80.0\\\" width=\\\"100.0\\\" x=\\\"576.0\\\" y=\\\"194.0\\\"/>\\n        <bpmndi:BPMNLabel>\\n          <dc:Bounds height=\\\"18.8\\\" width=\\\"80.0\\\" x=\\\"586.0\\\" y=\\\"226.6\\\"/>\\n        </bpmndi:BPMNLabel>\\n      </bpmndi:BPMNShape>\\n      <bpmndi:BPMNShape bpmnElement=\\\"EndEvent_1\\\" id=\\\"Yaoqiang-EndEvent_1\\\">\\n        <dc:Bounds height=\\\"32.0\\\" width=\\\"32.0\\\" x=\\\"773.0\\\" y=\\\"216.0\\\"/>\\n        <bpmndi:BPMNLabel>\\n          <dc:Bounds height=\\\"18.8\\\" width=\\\"6.0\\\" x=\\\"788.0\\\" y=\\\"257.08\\\"/>\\n        </bpmndi:BPMNLabel>\\n      </bpmndi:BPMNShape>\\n      <bpmndi:BPMNEdge bpmnElement=\\\"SequenceFlow_8\\\" id=\\\"Yaoqiang-SequenceFlow_8\\\">\\n        <di:waypoint x=\\\"526.0\\\" y=\\\"234.0\\\"/>\\n        <di:waypoint x=\\\"576.0\\\" y=\\\"234.0\\\"/>\\n        <bpmndi:BPMNLabel>\\n          <dc:Bounds height=\\\"18.8\\\" width=\\\"6.0\\\" x=\\\"548.0\\\" y=\\\"224.6\\\"/>\\n        </bpmndi:BPMNLabel>\\n      </bpmndi:BPMNEdge>\\n      <bpmndi:BPMNEdge bpmnElement=\\\"SequenceFlow_5\\\" id=\\\"Yaoqiang-SequenceFlow_5\\\">\\n        <di:waypoint x=\\\"676.0\\\" y=\\\"234.0\\\"/>\\n        <di:waypoint x=\\\"773.0078144082805\\\" y=\\\"232.0\\\"/>\\n        <bpmndi:BPMNLabel>\\n          <dc:Bounds height=\\\"18.8\\\" width=\\\"6.0\\\" x=\\\"721.5\\\" y=\\\"223.59\\\"/>\\n        </bpmndi:BPMNLabel>\\n      </bpmndi:BPMNEdge>\\n      <bpmndi:BPMNEdge bpmnElement=\\\"SequenceFlow_2\\\" id=\\\"Yaoqiang-SequenceFlow_2\\\">\\n        <di:waypoint x=\\\"376.0\\\" y=\\\"234.0\\\"/>\\n        <di:waypoint x=\\\"426.0\\\" y=\\\"234.0\\\"/>\\n        <bpmndi:BPMNLabel>\\n          <dc:Bounds height=\\\"18.8\\\" width=\\\"6.0\\\" x=\\\"398.0\\\" y=\\\"224.6\\\"/>\\n        </bpmndi:BPMNLabel>\\n      </bpmndi:BPMNEdge>\\n      <bpmndi:BPMNEdge bpmnElement=\\\"SequenceFlow_1\\\" id=\\\"Yaoqiang-SequenceFlow_1\\\">\\n        <di:waypoint x=\\\"181.99218559171948\\\" y=\\\"232.0\\\"/>\\n        <di:waypoint x=\\\"276.0\\\" y=\\\"234.0\\\"/>\\n        <bpmndi:BPMNLabel>\\n          <dc:Bounds height=\\\"18.8\\\" width=\\\"6.0\\\" x=\\\"226.0\\\" y=\\\"223.59\\\"/>\\n        </bpmndi:BPMNLabel>\\n      </bpmndi:BPMNEdge>\\n    </bpmndi:BPMNPlane>\\n  </bpmndi:BPMNDiagram>\\n</definitions>\\n\"}}");
            fut.get(1,TimeUnit.SECONDS); // wait for send to complete.

            fut = session.getRemote().sendStringByFuture("{\"command\":\"set-breakpoints\",\"data\":{\"breakpoints\":[{\"elementId\":\"loadOrder\"},{\"elementId\":\"calcSum\"}]}}");
            fut.get(1,TimeUnit.SECONDS); // wait for send to complete.

            fut = session.getRemote().sendStringByFuture("{\"command\":\"start-process\",\"data\":{\"processDefinitionId\":\"5\"}}");
            fut.get(1,TimeUnit.SECONDS); // wait for send to complete.


            //session.close(StatusCode.NORMAL,"I'm done");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg)
    {
        System.out.printf("Got msg: %s%n",msg);
        responds.add(msg);
    }

    public LinkedList<String> getResponds() {
        return responds;
    }
}