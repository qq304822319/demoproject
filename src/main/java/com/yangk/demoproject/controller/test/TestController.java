package com.yangk.demoproject.controller.test;

import com.alibaba.fastjson.JSON;
import com.yangk.demoproject.common.utils.ExcelUtils;
import com.yangk.demoproject.common.utils.FileUtils;
import com.yangk.demoproject.model.sys.SysUser;
import com.yangk.demoproject.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "测试接口")
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/export")
    @ApiOperation(value = "导出Excel", notes = "导出Excel")
    public void exportExcel(HttpServletResponse response) throws Exception {
        //获取数据, 需要具体修改
        List<SysUser> list = sysUserService.selectSysUsers(new SysUser());
        //excel标题
        String[] title = {"员工姓名", "登录名", "工号"};
        //excel文件名
        String fileName = "员工表.xlsx";
        //sheet名
        String sheetName = "员工信息";
        //定义整个数据载体
        String[][] content = new String[list.size()][title.length];
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
        Workbook wb = ExcelUtils.getWorkbook(fileName, sheetName, title, content);
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

    @GetMapping("/import")
    @ApiOperation(value = "导入Excel", notes = "导入Excel")
    public void importExcel() throws Exception {
        String filepath = "D:" + File.separator + "导入测试.xls";
        FileInputStream inputStream = new FileInputStream(new File(filepath));
        Map<String, List<List<Object>>> map = ExcelUtils.importExcelToMap(inputStream, filepath);
        String s = JSON.toJSONString(map);
        System.out.print(s);
    }
}
