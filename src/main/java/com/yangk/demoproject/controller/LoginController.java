package com.yangk.demoproject.controller;

import com.yangk.demoproject.common.utils.QueryRequest;
import com.yangk.demoproject.model.sys.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangk
 * @date 2020/3/9
 */
@Slf4j
@RestController
@RequestMapping("/login")
@Api(tags = "登录接口")
public class LoginController {
    @PostMapping("/loginUser")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public String getVehicleAccidentInfos(@RequestBody QueryRequest<SysUser> queryRequest) {
        return "用户:" + queryRequest.getData().getRealName() + " 登陆成功!";
    }
}
