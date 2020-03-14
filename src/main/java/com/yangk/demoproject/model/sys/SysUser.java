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

    @ApiModelProperty(value = "密码")
    @Column(name = "password", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NOT NULL COMMENT '密码'")
    private String password;

    @ApiModelProperty(value = "用户真实姓名")
    @Column(name = "real_name", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NOT NULL COMMENT '用户真实姓名'")
    private String realName;

    @ApiModelProperty(value = "用户编号")
    @Column(name = "user_number", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NOT NULL COMMENT '用户编号'")
    private String userNumber;

    @ApiModelProperty(value = "状态")
    @Column(name = "status", columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci " +
            "NOT NULL DEFAULT '0' COMMENT '状态 通用代码:SYS_USER_STATUS'")
    private String status;
}
