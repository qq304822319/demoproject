package com.yangk.demoproject.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户登录信息
 *
 * @author yangk
 */
@Data
public class LoginDto {
    @NotBlank(message = "用户名不可为空")
    @Size(min = 3, max = 25, message = "用户名长度必须在3-25之间")
    public String username;

    @NotBlank(message = "密码不可为空")
    @Size(min = 3, max = 30, message = "密码长度必须在3-30之间")
    public String password;
}
