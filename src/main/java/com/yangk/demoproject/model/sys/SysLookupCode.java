package com.yangk.demoproject.model.sys;

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
@Table(name = "sys_lookup_code")
@org.hibernate.annotations.Table(appliesTo = "sys_lookup_code",comment="通用代码转换")
public class SysLookupCode {
    @ApiModelProperty(value = "数据字典id")
    @Column(name = "sys_Data_Dictionary_Id", columnDefinition = "varchar(255) CHARACTER SET " +
            "utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据字典id'")
    private String sysDataDictionaryId;

    @ApiModelProperty(value = "代码")
    @Column(name = "code", columnDefinition = "varchar(255) CHARACTER SET " +
            "utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代码'")
    private String code;

    @ApiModelProperty(value = "含义")
    @Column(name = "value", columnDefinition = "varchar(255) CHARACTER SET " +
            "utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '含义'")
    private String value;

    @ApiModelProperty(value = "序号")
    @Column(name = "order_number", columnDefinition = "int(3) NOT NULL COMMENT '序号'")
    private Integer orderNumber ;
}
