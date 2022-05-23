package com.cssnj.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 报销测试工作流
 * @author panbing
 * @date 2022/5/6 15:08
 */
@Controller
@Api(tags = "报销测试工作流")
@RequestMapping(value = "expense")
public class FlowableTestController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 添加报销
     * @param userId 用户ID
     * @param money 报销金额
     * @param description 描述
     * @return
     */
    @ApiOperation("添加报销")
    @PostMapping(value = "/add")
    @ResponseBody
    public String addExpense(String userId, Integer money, String description){
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", userId);
        map.put("money", money);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return "提交成功，流程ID为："+processInstance.getId();
    }

    /**
     * 获取审批管理列表
     * @param userId
     * @return
     */
    @ApiOperation("获取审批管理列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public String list(String userId){
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        for (Task task:tasks){
            System.out.println(task.toString());
        }
        return tasks.toString();
    }

    /**
     * 批准
     * @param taskId
     * @return
     */
    @ApiOperation("批准")
    @PostMapping(value = "/apply")
    @ResponseBody
    public String apply(String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null){
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        return "process ok!";
    }

    /**
     * 拒绝
     * @param taskId
     * @return
     */
    @ApiOperation("驳回")
    @PostMapping(value = "/reject")
    @ResponseBody
    public String reject(String taskId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete("taskId", map);
        return "reject";
    }

    /**
     * 生成流程图
     * @param httpServletResponse
     * @param processId 任务ID
     */
    @ApiOperation("查看流程图")
    @GetMapping(value = "/processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception{
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        //流程图走完的不显示图
        if(processInstance == null){
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String instanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(instanceId).list();
        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe:executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        ProcessEngineConfiguration engineConfiguration = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engineConfiguration.getProcessDiagramGenerator();
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engineConfiguration.getActivityFontName(),
                engineConfiguration.getLabelFontName(), engineConfiguration.getAnnotationFontName(), engineConfiguration.getClassLoader(), 1.0, true);
        OutputStream outputStream = null;
        byte[] buf = new byte[1024];
        int length = 0;
        try{
            outputStream = httpServletResponse.getOutputStream();
            while ((length = inputStream.read(buf)) != -1){
                outputStream.write(buf, 0, length);
            }
//            BufferedImage image = ImageIO.read(inputStream);
//            ImageIO.write(image, "png", outputStream);
//            outputStream.flush();
        } finally {
            if(inputStream != null){
                inputStream.close();
            }
            if(outputStream != null){
                outputStream.close();
            }
        }

    }

}
