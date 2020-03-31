package com.yangk.demoproject.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 业务模块父类
 *
 * @author yangk
 * @date 2020/3/4
 */
@Data
@MappedSuperclass
public class BusinessModel extends BaseModel {

    @Column(name = "create_time", columnDefinition = "datetime NULL DEFAULT " +
            "CURRENT_TIMESTAMP COMMENT '创建时间'")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @ApiModelProperty(value = "创建人")
    @Column(name = "create_by", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人'")
    private String createBy;
    @Transient
    @ApiModelProperty(value = "创建人名称")
    private String createByName;

    @Column(name = "update_time", columnDefinition = "timestamp NULL DEFAULT " +
            "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间' ")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    @Column(name = "update_by", columnDefinition = "varchar(255) CHARACTER SET utf8mb4 COLLATE " +
            "utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人'")
    private String updateBy;
    @Transient
    @ApiModelProperty(value = "修改人名称")
    private String updateByName;

    @ApiModelProperty(value = "乐观锁")
    @Column(name = "version", columnDefinition = "int(11) NOT NULL DEFAULT 0 COMMENT '版本号'")
    private Integer version;

}
