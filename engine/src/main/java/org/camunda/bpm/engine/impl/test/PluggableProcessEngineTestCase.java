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
package org.camunda.bpm.engine.impl.test;

import java.io.FileNotFoundException;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;


/** Base class for the process engine test cases.
 *
 * The main reason not to use our own test support classes is that we need to
 * run our test suite with various configurations, e.g. with and without spring,
 * standalone or on a server etc.  Those requirements create some complications
 * so we think it's best to use a separate base class.  That way it is much easier
 * for us to maintain our own codebase and at the same time provide stability
 * on the test support classes that we offer as part of our api (in org.camunda.bpm.engine.test).
 *
 * @author Tom Baeyens
 * @author Joram Barrez
 */
public class PluggableProcessEngineTestCase extends AbstractProcessEngineTestCase {

  protected static ProcessEngine cachedProcessEngine;

  protected void initializeProcessEngine() {
    processEngine = getOrInitializeCachedProcessEngine();
  }

  private static ProcessEngine getOrInitializeCachedProcessEngine() {
    if (cachedProcessEngine == null) {
      try {
        cachedProcessEngine = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("camunda.cfg.xml")
                .buildProcessEngine();
      } catch (RuntimeException ex) {
        if (ex.getCause() != null && ex.getCause() instanceof FileNotFoundException) {
          cachedProcessEngine = ProcessEngineConfiguration
              .createProcessEngineConfigurationFromResource("activiti.cfg.xml")
              .buildProcessEngine();
        } else {
          throw ex;
        }
      }
    }
    return cachedProcessEngine;
  }

  public static ProcessEngine getProcessEngine() {
    return getOrInitializeCachedProcessEngine();
  }


}