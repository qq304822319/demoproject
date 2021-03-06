package com.yangk.demoproject.controller.sys;

import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.model.sys.SysDataDictionary;
import com.yangk.demoproject.service.sys.SysDataDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sysDataDictionary")
@Api(tags = "数据字典接口")
public class SysDataDictionaryController {
    @Autowired
    private SysDataDictionaryService sysDataDictionaryService;

    @PostMapping("/save")
    @ApiOperation(value = "保存数据字典", notes = "保存数据字典")
    public Response saveSysDataDictionary(@RequestBody SysDataDictionary sysDataDictionary) {
        sysDataDictionaryService.saveSysDataDictionary(sysDataDictionary);
        return Response.ok();
    }

    @PostMapping("/getSysDataDictionarys")
    @ApiOperation(value = "查询数据字典", notes = "查询数据字典")
    public Response getSysDataDictionarys(@RequestBody SysDataDictionary sysDataDictionary) {
        List<SysDataDictionary> list = sysDataDictionaryService.selectSysDataDictionarys(sysDataDictionary);
        return Response.returnData(list);
    }

}
