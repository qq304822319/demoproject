package com.yangk.demoproject.annotation.aspect;

import cn.hutool.core.util.StrUtil;
import com.yangk.demoproject.annotation.LoginUser;
import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.exception.ProException;
import com.yangk.demoproject.common.utils.BeanCopyUtils;
import com.yangk.demoproject.config.shiro.ShiroConfig;
import com.yangk.demoproject.dto.LoginUserDto;
import com.yangk.demoproject.model.sys.SysUser;
import com.yangk.demoproject.service.sys.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Order(2)
@Component
public class LoginUserAspect implements HandlerMethodArgumentResolver {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 判断是否支持要转换的参数类型
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * 当支持后进行相应的转换 做业务操作
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest request,
                                  WebDataBinderFactory webDataBinderFactory) {
        //从请求头中获参数信息
        String token = request.getHeader(ShiroConfig.TOKEN);
        if (StrUtil.isEmptyIfStr(token)) {
            throw new ProException(ResponseCode.NOT_LOGIN);
        }

        String username = (String) SecurityUtils.getSubject().getPrincipal();
        if (StrUtil.isEmptyIfStr(username)) {
            throw new ProException(ResponseCode.NOT_LOGIN);
        }

        SysUser sysUser = sysUserService.findByUserName(username);
        if (sysUser == null || sysUser.getId() == null) {
            throw new ProException(ResponseCode.NOT_LOGIN);
        }

        LoginUserDto loginUserDto = BeanCopyUtils.copyProperties(sysUser, LoginUserDto.class);
        if (loginUserDto == null || loginUserDto.getId() == null) {
            throw new ProException(ResponseCode.NOT_LOGIN);
        }

        return loginUserDto;
    }
}