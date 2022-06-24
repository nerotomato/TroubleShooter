package com.nerotomato.activitidemo.test;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Camunda 工作流引擎使用
 */
public class CamundaActivitiProcessEngineTest {

    @Autowired
    RepositoryService repositoryService;
    @Test
    public void deployment() {
        /*ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();*/
        repositoryService.createDeployment()
                .addClasspathResource("bpmn/holiday.bpmn")
                .addClasspathResource("bpmn/holiday.png")
                .name("请假申请流程")
                .deploy();
    }
}
