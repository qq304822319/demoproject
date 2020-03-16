package com.yangk.demoproject.dto;

import lombok.Data;

/**
 * 用户登录信息
 *
 * @author yangk
 */
@Data
public class LoginUserDto {
    private String id;
    private String username;
    private String realName;
    private String userNumber;
}
