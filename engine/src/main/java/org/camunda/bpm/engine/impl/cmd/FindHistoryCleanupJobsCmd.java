/*
 * Copyright © 2012 - 2018 camunda services GmbH and various authors (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.impl.cmd;

import java.io.Serializable;
import java.util.List;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.jobexecutor.historycleanup.HistoryCleanupJobHandler;
import org.camunda.bpm.engine.runtime.Job;

/**
 * @author Svetlana Dorokhova
 */
public class FindHistoryCleanupJobsCmd implements Command<List<Job>>, Serializable {

  private static final long serialVersionUID = 1L;

  @Override
  public List<Job> execute(CommandContext commandContext) {
    return commandContext.getJobManager().findJobsByHandlerType(HistoryCleanupJobHandler.TYPE);
  }

}
