package com.yangk.demoproject.controller.test;

import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.exception.ProException;
import com.yangk.demoproject.common.utils.ExcelUtils;
import com.yangk.demoproject.common.utils.FileUtils;
import com.yangk.demoproject.model.sys.SysUser;
import com.yangk.demoproject.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "测试接口")
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SysUserService sysUserService;


    @GetMapping("/export")
    @ApiOperation(value = "导出报表", notes = "导出报表")
    public void  export(HttpServletResponse response) throws Exception {

        //获取数据, 需要具体修改
        List<SysUser> list = sysUserService.selectSysUsers(new SysUser());

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
