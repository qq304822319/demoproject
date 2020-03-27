package com.yangk.demoproject.model.sys;

import com.yangk.demoproject.common.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_auto_code")
@org.hibernate.annotations.Table(appliesTo = "sys_auto_code", comment = "自动编码")
public class SysAutoCode extends BaseModel {

    @NotBlank(message = "自动编码key不能为空")
    @ApiModelProperty(value = "自动编码key")
    @Column(name = "auto_code_key", columnDefinition = "varchar(255) CHARACTER SET " +
            "utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '自动编码key'")
    private String autoCodeKey;

    @Size(min = 1, max = 5, message = "编码前缀长度在1位到5位之间")
    @ApiModelProperty(value = "编码前缀")
    @Column(name = "prefix", columnDefinition = "varchar(255) CHARACTER SET " +
            "utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编码前缀'")
    private String prefix;

    @ApiModelProperty(value = "日期格式化规则,可为空")
    @Column(name = "date_format", columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE " +
            "utf8_general_ci NULL DEFAULT NULL COMMENT '日期格式化规则,可为空'")
    private String dateFormat;

    @Max(value = 6, message = "最大6位")
    @Min(value= 2 ,message= "最小2位" )
    @ApiModelProperty(value = "序号位数(默认4位)")
    @Column(name = "serial_number_digit", columnDefinition = "int(11) NOT NULL DEFAULT 4 COMMENT " +
            "'序号位数(默认4位)'")
    private Integer serialNumberDigit;

    @ApiModelProperty(value = "当前日期")
    @Column(name = "current_date_str", columnDefinition = "varchar(255) CHARACTER SET utf8 " +
            "COLLATE utf8_general_ci NOT NULL DEFAULT '19901003' COMMENT '当前日期'")
    private String currentDateStr;

    @ApiModelProperty(value = "当前序号")
    @Column(name = "current_serial_number", columnDefinition = "int(11) NOT NULL DEFAULT 0 " +
            "COMMENT '当前序号'")
    private Integer currentSerialNumber;

    @ApiModelProperty(value = "备注")
    @Column(name = "remark", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注'")
    private String remark;

    @ApiModelProperty(value = "状态")
    @Column(name = "status", columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci " +
            "NOT NULL DEFAULT '1' COMMENT '状态 通用代码:SYS_VALIDITY'")
    private String status;
}
