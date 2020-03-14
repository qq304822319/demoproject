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

    @Column(name = "create_time", columnDefinition = "datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @Transient
    @ApiModelProperty(value = "创建人名称")
    private String createByName;

    @Column(name = "update_time", columnDefinition = "timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间' ")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @Column(name = "update_by")
    @ApiModelProperty(value = "修改人")
    private String updateBy;
    @Transient
    @ApiModelProperty(value = "修改人名称")
    private String updateByName;

}
