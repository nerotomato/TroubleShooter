package com.nerotomato.activitidemo.test;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.List;
/**
 * Activiti工作流引擎使用
 * */
public class ActivitiProcessEngineTest {
    /**
     * 使用activiti提供的默认方式来创建25张mysql的表
     */
    @Test
    public void createTable() {
        // 执行该方法，会在数据库自动生成activiti所需的25张表
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(defaultProcessEngine);
    }

    /**
     * 部署流程定义 文件上传方式
     * 就是把定义好的bpmn，保存到数据库中
     */
    @Test
    public void deployment() {
        // 创建processEngine
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        // 得到RepositoryService实例
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        // 使用repositoryService进行部署
        /*Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/holiday.bpmn") //添加bpmn资源
                .addClasspathResource("bpmn/holiday.png")  //添加png资源
                .name("请假申请流程")
                .deploy();*/
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/My_leave_process.bpmn") //添加bpmn资源
                .addClasspathResource("bpmn/My_leave_process.png")  //添加png资源
                .name("请假申请流程")
                .deploy();

        // 输出部署信息
        System.out.println("流程部署ID：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }

    /**
     * 删除流程部署定义
     * */
    @Test
    public void deleteDeployment(){
        // 创建processEngine
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        // 得到RepositoryService实例
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        repositoryService.deleteDeployment("7501");
    }

    /**
     * 启动定义好的流程实例
     * 启动流程实例,相当于开启一个流程
     */
    @Test
    public void startProcess() {
        // 创建processEngine
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        // 获取RuntimeService
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        // 启动流程，这个key是定义holiday.bpmn时填写的id，即流程id, bpmn:process id="Process_leave" isExecutable="true">
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my_leave_process");
        // 输出内容
        System.out.println("流程定义id: " + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id: " + processInstance.getId());
        System.out.println("当前活动id: " + processInstance.getActivityId());
    }
    /**
     * 删除历史流程实例
     * */
    @Test
    public void deleteHistoryProcessInstance(){
        // 创建processEngine
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        // 得到RepositoryService实例
        HistoryService historyService = defaultProcessEngine.getHistoryService();
        historyService.deleteHistoricProcessInstance("5001");
    }

    /**
     * 查询待处理的任务
     * */
    @Test
    public void findPersonalTaskList() {
        String assignee = "worker";
        String assignee2 = "manager";
        String assignee3 = "personnel";
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        // 创建TaskService
        TaskService taskService = defaultProcessEngine.getTaskService();
        // 根据流程key和任务负责人查询任务。查询流程实例，有没有需要worker执行的节点
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("my_leave_process")
                //.processDefinitionId("Process_leave:2:7504")
                //.processInstanceId("10001")
                .taskAssignee(assignee3)
                .list();
        for (Task task : list) {
            System.out.println("流程定义id:" + task.getProcessDefinitionId());
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }

    /**
     * 完成任务
     * 当worker的任务执行完毕时，该流程向前推进，到了manager需要完成的节点
     * */
    @Test
    public void completeTask(){
        String assignee = "worker";
        String assignee2 = "manager";
        String assignee3 = "personnel";
        //获取引擎
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //获取taskService
        TaskService taskService = defaultProcessEngine.getTaskService();
        //根据流程key和任务负责人 查询任务
        //返回一个任务对象，查询实例id是20001，执行者是worker的任务
        Task task = taskService.createTaskQuery()
                .processInstanceId("20001")
                .taskAssignee(assignee3)
                .singleResult();
        //完成任务，参数：任务Id
        taskService.complete(task.getId());
        System.out.println("Task id: "+task.getId()+" ,name: "+task.getName()+" has been completed");
    }
}
