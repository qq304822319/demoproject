package com.yangk.demoproject.controller.workflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.common.exception.ProException;
import com.yangk.demoproject.common.utils.PageUtils;
import com.yangk.demoproject.dto.workflow.WorkflowDto;
import com.yangk.demoproject.service.workflow.WorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/workflow")
@Api(tags = "工作流主接口")
public class WorkflowController {

    /*
     * 流程使用说明:
     *      1. 流程模型保存: POST /workflow/save  返回流程 modelId
     *      2. 设计流程图: http://localhost:8083/activiti/modeler.html?modelId=modelId
     *      3. 流程图保存 ------ 设计器保存按钮
     *      4. 部署流程: POST /workflow/deploy/{modelId}
     *
     *
     *
     */

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private RepositoryService repositoryService;

    @PostMapping("/save")
    @ApiOperation(value = "模型保存", notes = "工作流列表接口")
    public Response create(@RequestBody @Valid WorkflowDto workflowDto) {
        HashMap<String, Object> paramMap = new HashMap<>(7);
        String modelId = workflowService.save(workflowDto);
        paramMap.put("modelId", modelId);
        return Response.returnData(paramMap);
    }

    @GetMapping("/page")
    @ApiOperation(value = "模型列表", notes = "模型分页列表接口")
    public Response page(@RequestParam Map<String, Object> conditions) {
        Page page = PageUtils.getPage(conditions);
        List<Model> list = workflowService.list(page, conditions);
        return Response.returnData(list);
    }


    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "模型删除", notes = "模型删除")
    public Response create(@PathVariable String id) {
        repositoryService.deleteModel(id);
        return Response.ok();
    }

    @PostMapping("/deploy/{id}")
    @ApiOperation(value = "模型部署", notes = "模型部署")
    public Response deploy(@PathVariable String id) throws IOException {
        workflowService.deploy(id);
        return Response.ok();
    }

    @GetMapping("/export/{id}")
    @ApiOperation(value = "模型导出", notes = "模型导出")
    public void export(@PathVariable String id, HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(id);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            //获取节点信息
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            //将节点信息转换为xml
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
            OutputStream outputStream = response.getOutputStream();
            IOUtils.copy(in, outputStream);

            String filename = modelData.getName() + ".bpmn20.xml";

            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "utf-8"));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出model的xml文件失败：{}", e.getMessage(), e);
            throw new ProException(ResponseCode.FLOW_IS_NOT_FOUND);
        }
    }
}
