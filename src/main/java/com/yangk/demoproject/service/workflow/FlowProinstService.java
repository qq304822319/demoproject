package com.yangk.demoproject.service.workflow;


import com.yangk.demoproject.dao.workflow.FlowProcinstDao;
import com.yangk.demoproject.dto.workflow.FlowProcinstDto;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FlowProinstService {
    @Resource
    FlowProcinstDao flowProcinstDao;

    @Resource
    private RuntimeService runtimeService;

    /**
     * 流程实例列表
     *
     * @param params
     * @return java.util.List<com.yangk.demoproject.dto.workflow.FlowProcinstDto>
     * @author yangk
     * @date 2020/3/25
     */
    public List<FlowProcinstDto> selectProcinst(Map<String, Object> params) {
        return flowProcinstDao.selectProcinst(params);
    }

    /**
     * 删除实例
     *
     * @param id
     * @param deleteReason
     * @return void
     * @author yangk
     * @date 2020/3/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id, String deleteReason) {
        runtimeService.deleteProcessInstance(id, deleteReason);
    }

    /**
     * 挂起实例
     *
     * @param id
     * @return void
     * @author yangk
     * @date 2020/3/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void suspend(String id) {
        runtimeService.suspendProcessInstanceById(id);
    }
}
