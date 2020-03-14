package com.yangk.demoproject.model.sys;

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

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_user")
@org.hibernate.annotations.Table(appliesTo = "sys_user",comment="系统用户表")
public class SysUser extends BusinessModel {

    @ApiModelProperty(value = "用户名")
    @Column(name = "username", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NOT NULL COMMENT '用户名'")
    private String username;

    @ApiModelProperty(value = "用户真实姓名")
    @Column(name = "real_name", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NOT NULL COMMENT '用户真实姓名'")
    private String realName;

    @ApiModelProperty(value = "性别")
    @Column(name = "sex", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NOT NULL COMMENT '性别'")
    private String sex;

    @ApiModelProperty(value = "身份证号")
    @Column(name = "id_card_no", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NOT NULL COMMENT '身份证号'")
    private String idCardNo;

}
