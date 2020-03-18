package com.yangk.demoproject.model.sys;

import com.yangk.demoproject.common.model.BaseModel;
import com.yangk.demoproject.common.model.BusinessModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_data_dictionary")
@org.hibernate.annotations.Table(appliesTo = "sys_data_dictionary",comment="数据字典")
public class SysDataDictionary extends BaseModel {

    @ApiModelProperty(value = "通用代码")
    @Column(name = "lookup_type", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NOT NULL COMMENT '通用代码'")
    private String lookupType;

    @ApiModelProperty(value = "备注")
    @Column(name = "remark", columnDefinition = "varchar(4000) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注'")
    private String remark;

    @ApiModelProperty(value = "是否可修改")
    @Column(name = "yn_update", columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE " +
            "utf8_general_ci NOT NULL DEFAULT 'Y' COMMENT '是否可修改 通用代码:SYS_YN'")
    private String ynUpdate;

    @ApiModelProperty(value = "状态")
    @Column(name = "status", columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci " +
            "NOT NULL DEFAULT '0' COMMENT '状态 通用代码:SYS_VALIDITY'")
    private String status;
}
