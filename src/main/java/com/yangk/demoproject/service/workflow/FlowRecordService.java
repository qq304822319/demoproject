package com.yangk.demoproject.service.workflow;


import com.yangk.demoproject.dao.workflow.FlowRecordDao;
import com.yangk.demoproject.model.workflow.FlowRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 流程记录表Service
 *
 * @author yangk
 * @date 2020/1/20
 */
@Slf4j
@Service
public class FlowRecordService {

    @Resource
    private FlowRecordDao flowRecordDao;

    /**
     * 根据业务ID查询流程记录
     *
     * @param businessId 业务表id
     * @return java.util.List<com.fmjtkj.vehicle.model.flow.FlowRecord>
     * @author yangk
     * @date 2020/1/20
     */
    public List<FlowRecord> selectFlowRecordsByPid(String businessId) {
        Example example = new Example(FlowRecord.class);
        example.orderBy("approvalTime");
        example.createCriteria().andEqualTo("businessId", businessId);
        return flowRecordDao.selectByExample(example);
    }

    /**
     * 根据业务ID,流程key查询流程记录
     *
     * @param businessId 业务表id
     * @return java.util.List<com.fmjtkj.vehicle.model.flow.FlowRecord>
     * @author yangk
     * @date 2020/1/20
     */
    public List<FlowRecord> selectFlowRecordsByPid(String id, String flowDefKey) {
        Example example = new Example(FlowRecord.class);
        example.orderBy("approvalTime");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("businessId", id);
        criteria.andEqualTo("flowDefKey", flowDefKey);
        return flowRecordDao.selectByExample(example);
    }

    /**
     * 新增流程记录表数据
     *
     * @param flowRecordInfo
     * @return void
     * @author yangk
     * @date 2020/1/20
     */
    public void insertFlowRecord(FlowRecord flowRecordInfo) {
        flowRecordDao.insertSelective(flowRecordInfo);
    }

    /**
     * 修改流程记录表数据
     *
     * @param flowRecordInfo
     * @return void
     * @author yangk
     * @date 2020/1/20
     */
    public void updateFlowRecord(FlowRecord flowRecordInfo) {
        flowRecordDao.updateByPrimaryKeySelective(flowRecordInfo);
    }

    /**
     * 查询当前流程实例
     *
     * @param businessId
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author yangk
     * @date 2020/3/13
     */
    public Map<String, Object> selectActRuTask(String businessId) {
        return flowRecordDao.selectActRuTask(businessId);
    }

    /**
     * 修改当前流程实例
     *
     * @param map
     * @return void
     * @author yangk
     * @date 2020/3/13
     */
    public void updateActRuTask(Map<String, Object> map) {
        flowRecordDao.updateActRuTask(map);
    }
}
