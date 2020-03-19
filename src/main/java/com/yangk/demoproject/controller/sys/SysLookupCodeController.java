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
import java.util.Map;

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

    @GetMapping("/getLookupCodeList")
    @ApiOperation(value = "获取通用代码集合", notes = "获取通用代码集合")
    public Response selectSysLookupCodeByType(@RequestParam String lookupType) {
        List<Map<String, Object>> maps = sysLookupCodeService.selectSysLookupCodeByType(lookupType);
        return Response.returnData(maps);
    }

    @PostMapping("/saves")
    @ApiOperation(value = "保存通用代码", notes = "保存通用代码")
    public Response saveSysLookupCodes(@RequestBody List<SysLookupCode> sysLookupCodes,
                                       @LoginUser LoginUserDto loginUserDto) {
        sysLookupCodeService.saveSysLookupCodes(sysLookupCodes, loginUserDto);
        return Response.ok();
    }
}
