package com.yangk.demoproject.controller.sys;

import com.yangk.demoproject.annotation.LoginUser;
import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.dto.LoginUserDto;
import com.yangk.demoproject.model.sys.SysLookupCode;
import com.yangk.demoproject.service.sys.SysLookupCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangk
 * @date 2020/3/18
 */
@Slf4j
@RestController
@RequestMapping("/sysLookupCode")
@Api(tags = "通用代码接口")
public class SysLookupCodeController {
    @Autowired
    private SysLookupCodeService sysLookupCodeService;

    @GetMapping("/saveSysLookupCodeByPid")
    @ApiOperation(value = "保存数据字典", notes = "保存数据字典")
    public Response saveSysLookupCodeByPid(@RequestBody String pid,
                                           @LoginUser LoginUserDto loginUserDto) {
        SysLookupCode sysLookupCode = new SysLookupCode();
        sysLookupCode.setSysDataDictionaryId(pid);
        sysLookupCodeService.selectSysLookupCodes(sysLookupCode);
        return Response.ok();
    }

    @PostMapping("/saves")
    @ApiOperation(value = "保存数据字典", notes = "保存数据字典")
    public Response saveSysLookupCodes(@RequestBody List<SysLookupCode> sysLookupCodes,
                                       @LoginUser LoginUserDto loginUserDto) {
        sysLookupCodeService.saveSysLookupCodes(sysLookupCodes, loginUserDto);
        return Response.ok();
    }
}
