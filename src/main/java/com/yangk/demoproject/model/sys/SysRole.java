package com.yangk.demoproject.model.sys;

import com.yangk.demoproject.common.model.base.UUIdGenId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_role")
@org.hibernate.annotations.Table(appliesTo = "sys_role", comment = "系统角色表")
public class SysRole {
    @Id
    @KeySql(genId = UUIdGenId.class)
    private String id;

    @ApiModelProperty(value = "角色名")
    @Column(name = "role_name", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NOT NULL COMMENT '角色名'")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    @Column(name = "role_describe", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述'")
    private String roleDescribe;
}
