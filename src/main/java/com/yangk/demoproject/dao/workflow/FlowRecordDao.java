package com.yangk.demoproject.dao.workflow;


import com.yangk.demoproject.common.mapper.TkMybatisMapper;
import com.yangk.demoproject.model.workflow.FlowRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 流程记录表Dao
 *
 * @author yangk
 * @date 2020/1/20
 */
public interface FlowRecordDao extends TkMybatisMapper<FlowRecord> {

    /**
     * 查询当前流程实例
     *
     * @param businessId
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author yangk
     * @date 2020/3/13
     */
    Map<String, Object> selectActRuTask(@Param("businessId") String businessId);

    /**
     * 修改当前流程实例
     *
     * @param map
     * @return void
     * @author yangk
     * @date 2020/3/13
     */
    void updateActRuTask(Map<String, Object> map);
}
