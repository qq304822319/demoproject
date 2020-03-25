package com.yangk.demoproject.model.workflow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yangk.demoproject.common.model.BusinessModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 流程附加信息记录
 *
 * @author yangk
 * @date 2020/1/17
 */
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "flow_record")
@org.hibernate.annotations.Table(appliesTo = "flow_record", comment = "流程记录表")
public class FlowRecord extends BusinessModel {

    @ApiModelProperty(value = "流程定义key")
    @Column(name = "flow_def_key", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程定义key'")
    private String flowDefKey;

    @ApiModelProperty(value = "流程关联业务表表名")
    @Column(name = "business_table", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程关联业务表表名'")
    private String businessTable;

    @ApiModelProperty(value = "流程关联业务表数据ID")
    @Column(name = "business_id", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程关联业务表数据ID'")
    private String businessId;

    @ApiModelProperty(value = "流程执行类型")
    @Column(name = "operator_type", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程执行类型'")
    private String operatorType;

    @ApiModelProperty(value = "当前节点定义ID")
    @Column(name = "task_id", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前节点定义ID'")
    private String taskId;

    @ApiModelProperty(value = "当前节点名称/审批环节")
    @Column(name = "task_name", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前节点名称/审批环节'")
    private String taskName;

    @ApiModelProperty(value = "审批人ID")
    @Column(name = "approver_user_id", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人ID'")
    private String approverUserId;

    @ApiModelProperty(value = "审批人NAME")
    @Column(name = "approver_user_name", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人NAME'")
    private String approverUserName;

    @ApiModelProperty(value = "审批人审批结果")
    @Column(name = "approver_result", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人审批结果'")
    private String approverResult;

    @ApiModelProperty(value = "审批人审批意见")
    @Column(name = "approval_opinion", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人审批意见'")
    private String approverOpinion;

    @ApiModelProperty(value = "审批人审批时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "approval_time", columnDefinition = "datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审批人审批时间'")
    private Date approvalTime;

    @ApiModelProperty(value = "扩展数据")
    @Column(name = "json_data", columnDefinition = "text CHARACTER SET utf8mb4 " +
            "COLLATE utf8mb4_general_ci NULL COMMENT '扩展数据'")
    private String jsonData;

}