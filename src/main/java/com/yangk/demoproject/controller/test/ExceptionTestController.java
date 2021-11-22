package com.yangk.demoproject.controller.test;

import com.yangk.demoproject.common.exception.SysResponseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangk
 * @date 2021/11/22
 */
@Slf4j
@RestController
@Api(tags = "Exception测试接口")
@RequestMapping("/test")
public class ExceptionTestController {

    @GetMapping("/exception")
    @ApiOperation(value = "报错测试接口", notes = "测试系统错误")
    public String exception(@RequestParam("code") String code) throws SysResponseException {

        if (code.equals("Y")) {
            throw new SysResponseException("测试系统错误", "001");
        }

        return "succeed";
    }

    @ExceptionHandler(value = {SysResponseException.class})
    @ResponseStatus(HttpStatus.OK)
    public String handleSysResponseException(final SysResponseException e){
        log.info("return SysResponseException : {}", e.getErrorMessage());
        return "return error SysResponseException";
    }
}
