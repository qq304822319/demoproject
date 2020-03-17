package com.yangk.demoproject.controller;

import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.exception.ProException;
import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.dto.LoginDto;
import com.yangk.demoproject.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author yangk
 * @date 2020/3/9
 */
@Slf4j
@RestController
@Api(tags = "登录接口")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("//login")
    @ApiOperation(value = "用户登录", notes = "登录")
    public Response getVehicleAccidentInfos(@RequestBody @Valid LoginDto loginDto,
                                          HttpServletRequest request) throws Exception{

        throw new ProException(ResponseCode.USERNAME_OR_PASSWORD_INVALID);

   /*     //生成当前登录人的token
        UsernamePasswordToken token = new UsernamePasswordToken(loginDto.getUsername(), loginDto.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            //根据登录的用户名/员工号获取用户信息
            SysUser sysUser = sysUserService.findByUserName(loginDto.getUsername());
            return Response.loginInfo(subject.getSession().getId(), sysUser);
        } catch (UnknownAccountException uae) {
            log.debug("对用户[{}]进行登录验证..验证未通过,未知账户", loginDto.getUsername());
            token.clear();
            throw new ProException(ResponseCode.USER_NOT_FOUND);
        } catch (IncorrectCredentialsException ice) {
            log.debug("对用户[{}]进行登录验证..验证未通过,错误的凭证", loginDto.getUsername());
            token.clear();
            throw new ProException(ResponseCode.USERNAME_OR_PASSWORD_INVALID);
        } catch (DisabledAccountException dae) {
            log.debug("对用户[{}]进行登录验证..验证未通过,账户已禁用", loginDto.getUsername());
            token.clear();
            throw new ProException(ResponseCode.USER_ACCOUNT_IS_BAN);
        } catch (ExcessiveAttemptsException eae) {
            log.debug("对用户[{}]进行登录验证..验证未通过,错误次数过多", loginDto.getUsername());
            token.clear();
            throw new ProException(ResponseCode.USERNAME_OR_PASSWORD_TOW_ERROR);
        } catch (AuthenticationException ae) {
            log.debug("对用户[{}]进行登录验证..验证未通过,堆栈轨迹如下", loginDto.getUsername());
            token.clear();
            throw new ProException(ResponseCode.USERNAME_OR_PASSWORD_INVALID);
        }*/
    }

    @GetMapping("/logout")
    @ApiOperation(value = "注销", notes = "注销当前登录人")
    public Response logout() {
        SecurityUtils.getSubject().logout();
        return Response.ok();
    }
}
