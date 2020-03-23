package com.yangk.demoproject.controller.sys;

import com.yangk.demoproject.annotation.LoginUser;
import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.common.exception.ProException;
import com.yangk.demoproject.common.utils.ExcelUtils;
import com.yangk.demoproject.common.utils.FileUtils;
import com.yangk.demoproject.dto.LoginUserDto;
import com.yangk.demoproject.model.sys.SysUser;
import com.yangk.demoproject.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/sysUser")
@Api(tags = "用户接口")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/save")
    @ApiOperation(value = "添加/修改用户", notes = "添加/修改用户")
    public Response saveSysDataDictionary(@RequestBody SysUser sysUser) throws Exception {
        if (StringUtils.isEmpty(sysUser.getId())) {
            sysUserService.insertSysUser(sysUser);
        } else {
            sysUserService.updateSysUser(sysUser);
        }
        return Response.ok();
    }

    @PostMapping("/getSysUsers")
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    public Response getSysUsers(@RequestBody SysUser sysUser){
        List<SysUser> list = sysUserService.selectSysUsers(sysUser);
        return Response.returnData(list);
    }


    @PostMapping("/export")
    @ApiOperation(value = "导出报表", notes = "导出报表")
    public void  export(@RequestBody SysUser params, HttpServletResponse response) throws Exception {

        //获取数据, 需要具体修改
        List<SysUser> list = sysUserService.selectSysUsers(params);

        //excel标题
        String[] title = {"员工姓名", "登录名", "工号"};

        //excel文件名
        String fileName = "员工表.xlsx";

        //sheet名
        String sheetName = "员工信息";

        //定义整个数据载体
        String [][] content = new String[list.size()][title.length];

        for (int i = 0; i < list.size(); i++) {
            //定义一行
            content[i] = new String[title.length];
            //需要具体修改 ??
            SysUser sysUser = list.get(i);

            //给每行具体赋值，需要具体修改 ??
            content[i][0] = sysUser.getRealName();
            content[i][1] = sysUser.getUsername();
            content[i][2] = sysUser.getUserNumber();
        }

        //Excel文件对象
        Workbook wb = null;

        if(".xlsx".equals(fileName.substring(fileName.indexOf("."), fileName.length()))){
            //创建XSSFWorkbook
            wb = ExcelUtils.getXSSFWorkbook(sheetName, title, content);
        } else if (".xls".equals(fileName.substring(fileName.indexOf("."), fileName.length()))){
            //创建HSSFWorkbook
            wb = ExcelUtils.getHSSFWorkbook(sheetName, title, content);
        } else {
            log.debug("文件类型错误,只能生成.xlsx或者.xls类型的Excel文件");
            throw new ProException(ResponseCode.FILES_NOT_TYPE);
        }

        //响应到客户端
        try {
            FileUtils.setResponseHeader(response, fileName);
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
