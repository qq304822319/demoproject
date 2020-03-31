package com.yangk.demoproject.controller.sys;

import com.yangk.demoproject.annotation.LoginUser;
import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.dto.LoginUserDto;
import com.yangk.demoproject.model.sys.SysUser;
import com.yangk.demoproject.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sysUser")
@Api(tags = "用户接口")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/save")
    @ApiOperation(value = "添加/修改用户", notes = "添加/修改用户")
    public Response saveSysDataDictionary(@RequestBody @Valid SysUser sysUser,
                                          @LoginUser LoginUserDto loginUserDto) throws Exception {
        if (StringUtils.isEmpty(sysUser.getId())) {
            sysUserService.insertSysUser(sysUser, loginUserDto);
        } else {
            sysUserService.updateSysUser(sysUser, loginUserDto);
        }
        return Response.ok();
    }

    @PostMapping("/getSysUsers")
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    public Response getSysUsers(@RequestBody SysUser sysUser) {
        List<SysUser> list = sysUserService.selectSysUsers(sysUser);
        return Response.returnData(list);
    }
}
