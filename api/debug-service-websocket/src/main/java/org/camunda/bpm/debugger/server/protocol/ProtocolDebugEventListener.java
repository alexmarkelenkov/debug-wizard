/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.debugger.server.protocol;

import io.netty.channel.Channel;

import org.camunda.bpm.debugger.server.engine.Execution;
import org.camunda.bpm.debugger.server.protocol.dto.ErrorData;
import org.camunda.bpm.debugger.server.protocol.dto.SuspendedExecutionData;
import org.camunda.bpm.debugger.server.protocol.evt.ErrorEvt;
import org.camunda.bpm.debugger.server.protocol.evt.ExecutionSuspendedEvt;
import org.camunda.bpm.debugger.server.protocol.evt.ExecutionUnsuspendedEvt;
import org.camunda.bpm.debugger.server.protocol.evt.ExecutionUpdatedEvt;

public class ProtocolDebugEventListener {

  protected Channel channel;
  protected DebugProtocol protocol;

  public ProtocolDebugEventListener(DebugProtocol protocol, Channel channel) {
    this.protocol = protocol;
    this.channel = channel;
  }

  public void onExecutionSuspended(Execution execution) {
    protocol.fireEvent(channel, new ExecutionSuspendedEvt(new SuspendedExecutionData(execution)));
  }

  public void onExecutionUpdated(Execution execution) {
    protocol.fireEvent(channel, new ExecutionUpdatedEvt(new SuspendedExecutionData(execution)));
  }

  public void onExecutionUnsuspended(Execution execution) {
    protocol.fireEvent(channel, new ExecutionUnsuspendedEvt(new SuspendedExecutionData(execution)));
  }





}
