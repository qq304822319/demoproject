package com.yangk.demoproject.controller.sys;

import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.model.sys.SysAutoCode;
import com.yangk.demoproject.service.sys.SysAutoCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/sysAutoCode")
@Api(tags = "自动编码接口")
public class SysAutoCodeController {
    @Resource
    private SysAutoCodeService sysAutoCodeService;
    @PostMapping("/save")
    @ApiOperation(value = "保存自动编码", notes = "保存自动编码")
    public Response saveSysDataDictionary(@RequestBody SysAutoCode sysAutoCode) {
        sysAutoCodeService.saveSysAutoCode(sysAutoCode);
        return Response.ok();
    }

    @GetMapping("/getAutoCode")
    @ApiOperation(value = "查询列表", notes = "返回列表")
    public Response getAutoCode(@RequestParam String autoCodeKey){
        String autoCode = sysAutoCodeService.getAutoCode(autoCodeKey);
        return Response.returnData(autoCode);
    }
}
