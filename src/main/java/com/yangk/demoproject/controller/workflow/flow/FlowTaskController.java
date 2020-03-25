package com.yangk.demoproject.controller.workflow.flow;


import com.github.pagehelper.Page;
import com.yangk.demoproject.annotation.LoginUser;
import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.common.exception.ProException;
import com.yangk.demoproject.common.utils.PageUtils;
import com.yangk.demoproject.dto.LoginUserDto;
import com.yangk.demoproject.service.workflow.FlowTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/flow/task")
@Api(tags = "工作流-任务接口")
public class FlowTaskController {

    @Resource
    private TaskService taskService;

    @Resource
    private FlowTaskService flowTaskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ProcessEngineConfigurationImpl processEngineConfiguration;

    @GetMapping("/page")
    @ApiOperation(value = "所有任务列表", notes = "所有任务分页列表接口")
    public Response page(@RequestParam Map<String, Object> conditions) {
        Page page = PageUtils.getPage(conditions);

        int rowNumber = page.getPageSize();
        int startRow = (page.getPageNum() - 1) * page.getPageSize();

        TaskQuery taskQuery = taskService.createTaskQuery();
        page.setTotal(taskQuery.count());
        List<Task> list = taskQuery.orderByTaskCreateTime().desc().listPage(startRow, rowNumber);

        List<Map<String, Object>> taskList = taskHandle(list);

        return Response.returnData(taskList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    @GetMapping("/waitHandlePage")
    @ApiOperation(value = "待办任务列表", notes = "待办任务分页列表接口")
    public Response waitPage(@LoginUser LoginUserDto loginUserDto, @RequestParam Map<String, Object> conditions) {
        Page page = PageUtils.getPage(conditions);

        int rowNumber = page.getPageSize();
        int startRow = (page.getPageNum() - 1) * page.getPageSize();

        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(loginUserDto.getUsername());
        page.setTotal(taskQuery.count());
        List<Task> list = taskQuery.orderByTaskCreateTime().desc().listPage(startRow, rowNumber);

        List<Map<String, Object>> taskList = taskHandle(list);

        return Response.returnData(taskList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    @GetMapping("/alreadyHandlePage")
    @ApiOperation(value = "已办任务列表", notes = "已办任务分页列表接口")
    public Response alreadyHandlePage(@LoginUser LoginUserDto loginUserDto, @RequestParam Map<String, Object> conditions) {
        Page page = PageUtils.getPage(conditions);

        int rowNumber = page.getPageSize();
        int startRow = (page.getPageNum() - 1) * page.getPageSize();

        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(loginUserDto.getUsername());
        page.setTotal(historicTaskInstanceQuery.count());
        List<HistoricTaskInstance> list = historicTaskInstanceQuery.finished().orderByTaskCreateTime().desc().listPage(startRow, rowNumber);

        return Response.returnData(list, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    @ApiIgnore
    @GetMapping("/claim/{taskId}")
    @ApiOperation(value = "任务签收", notes = "任务签收接口")
    public Response claim(@LoginUser LoginUserDto loginUserDto, @PathVariable String taskId) {
        flowTaskService.claim(loginUserDto, taskId);
        return Response.ok();
    }

    @ApiIgnore
    @GetMapping("/delegate")
    @ApiOperation(value = "任务委托", notes = "任务委托接口")
    public Response delegate(@LoginUser LoginUserDto loginUserDto,
                             @RequestBody Map<String, Object> delegate) {

        String userId = null;
        String taskId = null;
        try {
            userId = delegate.get("userId").toString();
            taskId = delegate.get("taskId").toString();
        } catch (Exception e) {
            log.debug("userId/taskId为空!");
        }

        flowTaskService.delegateTask(loginUserDto, taskId, userId);
        return Response.ok();
    }

    @GetMapping("/queryProImg")
    @ApiOperation(value = "生成流程图", notes = "任务生成流程图接口")
    public void queryProImg(String processInstanceId) throws Exception {
        //获取历史流程实例
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        if (processInstance == null) {
            throw new ProException(ResponseCode.ERROR);
        }

        //根据流程定义获取输入流
        InputStream is = repositoryService.getProcessDiagram(processInstance.getProcessDefinitionId());
        BufferedImage bi = ImageIO.read(is);
        File file = new File("demo2.png");
        if (!file.exists()) file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ImageIO.write(bi, "png", fos);
        fos.close();
        is.close();
        System.out.println("图片生成成功");

        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("userId").list();
        for (Task t : tasks) {
            System.out.println(t.getName());
        }
    }

    @ApiIgnore
    @GetMapping("/queryProHighLighted")
    @ApiOperation(value = "生成高亮流程图", notes = "任务生成高亮流程图接口")
    public void queryProHighLighted(String processInstanceId) throws Exception {
        //获取历史流程实例
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();

        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitList);

        for (HistoricActivityInstance tempActivity : highLightedActivitList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }
        //配置字体
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, "宋体", "微软雅黑", "黑体", null, 2.0);
        BufferedImage bi = ImageIO.read(imageStream);
        File file = new File("demo2.png");
        if (!file.exists()) file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ImageIO.write(bi, "png", fos);
        fos.close();
        imageStream.close();
        System.out.println("图片生成成功");
    }

    /**
     * 获取需要高亮的线
     *
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return java.util.List<java.lang.String>
     * @author yangk
     * @date 2020/3/25
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {

        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

    /**
     * 进行中任务数据处理
     *
     * @param list
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author yangk
     * @date 2020/3/25
     */
    private List<Map<String, Object>> taskHandle(List<Task> list) {
        List<Map<String, Object>> taskList = new ArrayList<>();
        for (Task task : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", task.getId());
            map.put("name", task.getName());
            map.put("createTime", task.getCreateTime());

            taskList.add(map);
        }
        return taskList;
    }
}