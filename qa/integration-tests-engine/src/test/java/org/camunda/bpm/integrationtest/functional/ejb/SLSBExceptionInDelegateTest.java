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
package org.camunda.bpm.integrationtest.functional.ejb;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.camunda.bpm.engine.runtime.Incident;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.integrationtest.functional.ejb.beans.SLSBClientDelegate;
import org.camunda.bpm.integrationtest.functional.ejb.beans.SLSBThrowExceptionDelegate;
import org.camunda.bpm.integrationtest.util.AbstractFoxPlatformIntegrationTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testcase verifying that if an exception is thrown inside an EJB the original
 * exception reaches the caller
 *
 * @author Ronny Bräunlich
 *
 */
@RunWith(Arquillian.class)
public class SLSBExceptionInDelegateTest extends AbstractFoxPlatformIntegrationTest {

  @Deployment
  public static WebArchive processArchive() {
    return initWebArchiveDeployment().addClass(SLSBThrowExceptionDelegate.class).addClass(SLSBClientDelegate.class)
        .addAsResource("org/camunda/bpm/integrationtest/functional/ejb/SLSBExceptionInDelegateTest.testOriginalExceptionFromEjbReachesCaller.bpmn20.xml")
        .addAsResource("org/camunda/bpm/integrationtest/functional/ejb/SLSBExceptionInDelegateTest.callProcess.bpmn20.xml");
  }

  @Test
  public void testOriginalExceptionFromEjbReachesCaller() {
      runtimeService.startProcessInstanceByKey("callProcessWithExceptionFromEjb");
      Job job = managementService.createJobQuery().singleResult();
      managementService.setJobRetries(job.getId(), 1);
      
      waitForJobExecutorToProcessAllJobs();
      
      Incident incident = runtimeService.createIncidentQuery().activityId("servicetask1").singleResult();
      assertThat(incident.getIncidentMessage(), is("error"));
  }

}
