/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.mayer;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.RuntimeConfiguration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;
import org.apache.struts2.config.StrutsXmlConfigurationProvider;
import org.apache.struts2.junit.StrutsSpringTestCase;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class ConfigTest extends StrutsSpringTestCase {
    @Override
    protected void setupBeforeInitDispatcher() throws Exception {
        applicationContext = new AnnotationConfigApplicationContext("de.mayer.penandpaperdmhelper");
        super.setupBeforeInitDispatcher();
    }

    private static ConfigTest configTest;

    public static ConfigTest getInstance() throws Exception {
        if (configTest == null){
            configTest = new ConfigTest();
            configTest.setUp();
        }
        return configTest;
    }

    public void assertSuccess(String result) {
        assertEquals("Expected a success result!", ActionSupport.SUCCESS, result);
    }

    public void assertInput(String result) {
        assertEquals("Expected an input result!", ActionSupport.INPUT, result);
    }

    public Map<String, List<String>> assertFieldErrors(ActionSupport action) {
        assertTrue(action.hasFieldErrors());
        return action.getFieldErrors();
    }

    public void assertFieldError(Map<String, List<String>> fieldErrors, String fieldName, String errorMessage) {
        List<String> errors = fieldErrors.get(fieldName);
        assertNotNull("Expected errors for " + fieldName, errors);
        assertFalse("Expected errors for " + fieldName, errors.isEmpty());
        // TODO: Should be a loop
        assertEquals(errorMessage, errors.get(0));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        XmlConfigurationProvider c = new StrutsXmlConfigurationProvider("struts.xml");
        configurationManager.addContainerProvider(c);
        configurationManager.reload();
    }

    public ActionConfig assertActionHasClass(String namespace, String actionName, String expectedClassName) {
        RuntimeConfiguration configuration = configurationManager.getConfiguration().getRuntimeConfiguration();
        ActionConfig config = configuration.getActionConfig(namespace, actionName);
        assertNotNull("Mssing action", config);
        assertEquals("Wrong class name: [" + config.getClassName() + "]", expectedClassName, config.getClassName());
        return config;
    }

    public ActionConfig assertActionHasClass(String actionName, String expectedClassName) {
        return assertActionHasClass("", actionName, expectedClassName);
    }

    public void assertResult(ActionConfig config, String resultName, String resultValue) {
        Map<String, ResultConfig> results = config.getResults();
        ResultConfig result = results.get(resultName);
        Map<String, String> params = result.getParams();
        String value = params.get("actionName");
        if (value == null)
            value = params.get("location");
        assertEquals("Wrong result value: [" + value + "]", resultValue, value);
    }

    public void testAppStarts() throws Exception {
        getInstance();
        assertNotNull(configurationManager);
    }

}
