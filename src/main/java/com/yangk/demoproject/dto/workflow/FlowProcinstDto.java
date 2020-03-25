package com.yangk.demoproject.dto.workflow;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * 流程实例Dto
 */
@Data
public class FlowProcinstDto {
    /**
     * 流程实例ID
     */
    private String id;

    /**
     * 已部署的流程定义ID 对应表：ACT_RE_PROCDEF
     */
    private String procDefId;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 流程KEY
     */
    private String flowKey;

    /**
     * 流程版本
     */
    private String version;

    /**
     * 业务表ID
     */
    private String businessKey;

    /**
     * 实例开始时间
     */
    private Date startTime;

    /**
     * 实例结束时间
     */
    private Date endTime;

    /**
     * 时长
     */
    private BigInteger duration;

    /**
     * 删除理由
     */
    private String deleteReason;

    /**
     * 发起人
     */
    private String startUserId;

    /**
     * 发起人真实姓名
     */
    private String startUserRealName;

    /**
     * 发起人
     */
    private String startUserName;

}
