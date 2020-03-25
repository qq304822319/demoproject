package com.yangk.demoproject.dao.workflow;

import com.yangk.demoproject.dto.workflow.FlowProcinstDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FlowProcinstDao {
    List<FlowProcinstDto> selectProcinst(Map<String, Object> params);
}
