package com.yangk.demoproject.controller.common;


import com.alibaba.fastjson.JSON;
import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.dto.FileDto;
import com.yangk.demoproject.common.dto.Response;
import com.yangk.demoproject.common.exception.ProException;
import com.yangk.demoproject.common.utils.ExcelUtils;
import com.yangk.demoproject.common.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/upload")
@Api(tags = "上传相关接口")
public class UploadController {

    @PostMapping("/uploadImage")
    @ApiOperation(value = "单个图片上传", notes = "单个图片上传")
    public Response single(@RequestParam String path, @RequestParam MultipartFile file,
                           HttpServletRequest request) {
        if (StringUtils.isEmpty(path)) {
            throw new ProException(ResponseCode.FILES_PATH_NOT_FOUND);
        }

        try {
            FileDto fileDto = FileUtils.uploadImage(path, file);
            return Response.returnData(fileDto);
        } catch (Exception e) {
            log.error("上传失败 " + e.getMessage());
            return Response.error(e.getMessage());
        }
    }

    @PostMapping("/uploadImageList")
    @ApiOperation(value = "多个图片上传", notes = "多个图片上传")
    public Response multiple(@RequestParam String path, @RequestParam MultipartFile[] files,
                             HttpServletRequest request) {
        if (StringUtils.isEmpty(path)) {
            throw new ProException(ResponseCode.FILES_PATH_NOT_FOUND);
        }

        List<FileDto> fileList = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                FileDto fileDto = FileUtils.uploadImage(path, file);
                fileList.add(fileDto);
            }
            return Response.returnData(fileList);
        } catch (Exception e) {
            log.error("上传失败 " + e.getMessage());
            return Response.error(e.getMessage());
        }
    }

    @PostMapping("/uploadExcel")
    @ApiOperation(value = "导入Excel", notes = "导入Excel")
    public void uploadExcel(@RequestParam MultipartFile multipartFile,
                            HttpServletRequest request) throws Exception {
        //指定缓存文件夹
        String path =
                ResourceUtils.getURL("classpath:").getPath() + "static/upload/excal/";
        File mkdirFile = new File(path);
        if (!mkdirFile.exists()) {
            mkdirFile.mkdirs();
        }

        //获取后缀
        String suffix = FileUtils.getSuffix(multipartFile.getOriginalFilename());
        //指定唯一文件名
        String Name = FileUtils.createFileName(suffix);

        path += Name;
        File excelFile = new File(path);

        multipartFile.transferTo(excelFile);
        FileInputStream inputStream = new FileInputStream(excelFile);
        Map<String, List<List<Object>>> map = ExcelUtils.importExcelToMap(inputStream, path);
        String s = JSON.toJSONString(map);

        /**
         * 业务
         */
        System.out.print(s);

        //删除缓存文件
        excelFile.delete();
    }
}
