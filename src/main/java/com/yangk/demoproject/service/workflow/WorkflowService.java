package com.yangk.demoproject.service.workflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.Page;
import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.exception.ProException;
import com.yangk.demoproject.dto.workflow.WorkflowDto;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 工作流Service
 *
 * @author yangk
 * @date dateString
 */
@Slf4j
@Service
public class WorkflowService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 模型列表
     *
     * @param page
     * @param conditions
     * @return java.util.List<org.activiti.engine.repository.Model>
     * @author yangk
     * @date 2020/3/25
     */
    public List<Model> list(Page page, Map<String, Object> conditions) {
        int rowNumber = page.getPageSize();
        int startRow = (page.getPageNum() - 1) * page.getPageSize();

        ModelQuery modelQuery = repositoryService.createModelQuery();
        page.setTotal(modelQuery.count());
        List<Model> list = modelQuery.orderByCreateTime().desc().listPage(startRow, rowNumber);

        return list;
    }

    /**
     * 保存模型
     *
     * @param workflowDto
     * @return java.lang.String
     * @author yangk
     * @date 2020/3/25
     */
    @Transactional(rollbackFor = Exception.class)
    public String save(WorkflowDto workflowDto) {
        String modelId = "";
        List<Model> modelList = repositoryService.createModelQuery().modelKey(workflowDto.getKey()).list();
        if (modelList != null && modelList.size() > 0) {
            throw new ProException(ResponseCode.FLOW_MODEL_KEY_IS_EXIST);
        }

        try {
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);

            Model modelData = repositoryService.newModel();
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, workflowDto.getName());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, workflowDto.getDescription());

            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(workflowDto.getName());
            modelData.setKey(workflowDto.getKey());

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            modelId = modelData.getId();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("模型创建失败！message:{msg}", e.getMessage());
            throw new ProException(ResponseCode.ERROR, e.getMessage());
        }
        return modelId;
    }

    /**
     * 模型部署
     *
     * @param id
     * @return void
     * @author yangk
     * @date 2020/3/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void deploy(String id) throws IOException {
        Model modelData = repositoryService.getModel(id);
        if (modelData == null) {
            throw new ProException(ResponseCode.FLOW_MODEL_NOT_FOUND);
        }
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
        if (bytes == null) {
            throw new ProException(ResponseCode.FLOW_MODEL_DESIGN_IS_NULL);
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            throw new ProException(ResponseCode.FLOW_MODEL_IS_ERROR);
        }

        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";

        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "utf-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);
    }
}