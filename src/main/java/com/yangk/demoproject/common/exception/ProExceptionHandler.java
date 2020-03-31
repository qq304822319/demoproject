package com.yangk.demoproject.common.exception;

import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.HostUnauthorizedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.zip.DataFormatException;

/**
 * 全局异常拦截
 *
 * @author yangk
 */
@Slf4j
@Controller
@RestControllerAdvice
public class ProExceptionHandler implements ErrorController {
    private static final String ERROR_PATH = "/error";

    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @Autowired
    public ProExceptionHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * 自定义异常
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {ProException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleException(ProException exception) {
        return Response.error(exception.getCode(), exception.getMsg());
    }

    /**
     * 参数验证异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Response methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String msg = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();
        return Response.error(ResponseCode.INVALID_ARGUMENT.getCode(), msg);
    }

    /**
     * 其他异常处理 包含404
     *
     * @param request
     * @param ex
     * @param req
     * @return
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public Response error(HttpServletRequest request, Exception ex, final WebRequest req) {
        log.error("--------------------------ERROR  START...------------------------------------");
        ex.printStackTrace();
        log.error("--------------------------ERROR  END...------------------------------------");

        if (ex instanceof UnsupportedTokenException) {
            return Response.error("100001", "身份认证失败");
        }

        if (ex instanceof UnknownAccountException) {
            return Response.error("100002", "该用户不存在");
        }

        if (ex instanceof IncorrectCredentialsException) {
            return Response.error("100004", "密码错误");
        }

        if (ex instanceof LockedAccountException) {
            return Response.error("100003", "登录失败，账号被锁定");
        }

        if (ex instanceof DisabledAccountException) {
            return Response.error("100003", "登录失败，账号被禁用");
        }

        if (ex instanceof ExcessiveAttemptsException) {
            return Response.error("100003", "登录次数过多，请过段时间再来");
        }

        if (ex instanceof ConcurrentAccessException) {
            return Response.error("100003", "该账户已在别处登录");
        }

        if (ex instanceof AccountException) {
            return Response.error("100003", "账户异常");
        }

        if (ex instanceof ExpiredCredentialsException) {
            return Response.error("100001", "登陆信息失效");
        }

        if (ex instanceof UnauthorizedException || ex instanceof HostUnauthorizedException) {
            return Response.error("100005", "没有访问权限");
        }

        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return Response.error("100006", "请求方式不正确");
        }

        if (ex instanceof MissingServletRequestParameterException) {
            return Response.error("100007", "参数不正确");
        }

        if (ex instanceof TypeMismatchException) {
            return Response.error("100008", "参数类型不正确");
        }

        if (ex instanceof DataFormatException) {
            return Response.error("100009", "数据格式不正确");
        }

        if (ex instanceof IllegalArgumentException) {
            return Response.error("100010", "非法参数");
        }

        int status = getStatus(request);
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(req, false);
        return Response.error("" + status, String.valueOf(attr.getOrDefault("message", "error")));
    }

    private int getStatus(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status != null) {
            return status;
        }
        return 500;
    }

}