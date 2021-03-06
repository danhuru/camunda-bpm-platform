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
package org.camunda.bpm.integrationtest.functional.ejb.remote.bean;

import javax.inject.Named;
import javax.naming.InitialContext;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.integrationtest.util.TestConstants;


/**
 * A CDI bean delegating to the remote business
 * interface of a Singleton Bean from a different application.
 *
 * @author Daniel Meyer
 *
 */
@Named
public class RemoteSingletonBeanClientDelegateBean implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    BusinessInterface businessInterface = (BusinessInterface) new InitialContext().lookup("java:global/" +
        TestConstants.getAppName() +
        "service/" +
        "RemoteSingletonBean!org.camunda.bpm.integrationtest.functional.ejb.remote.bean.BusinessInterface");

    execution.setVariable("result", businessInterface.doBusiness());
  }

}
