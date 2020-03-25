package com.yangk.demoproject.dto.workflow;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 工作流DTO
 *
 * @author yangk
 * @date 2020/3/24
 */
@Data
public class WorkflowDto {
    @NotNull(message = "模型编号不可为空")
    public String key;

    @NotNull(message = "模型名称不可为空")
    public String name;

    public String description;
}