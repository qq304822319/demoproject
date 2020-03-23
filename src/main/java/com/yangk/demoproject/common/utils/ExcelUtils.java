package com.yangk.demoproject.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 * Excel工具类
 *
 * @author yangk
 * @date 2020/3/20
 */
public class ExcelUtils {

    /**
     * 导出Excel.xls---POI---HSSFWorkbook
     *
     * @param sheetName sheet页名称
     * @param title     Excel表头数组
     * @param values    Excel行数据
     * @return org.apache.poi.hssf.usermodel.HSSFWorkbook
     *         HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls
     * @author yangk
     * @date 2020/3/20
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title,
                                               String[][] values) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);// 默认列宽

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFFont font1 = workbook.createFont();// 创建一个字体对象
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        style.setFont(font1);// 设置style1的字体

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return workbook;
    }

    /**
     * 导出Excel.xlsx---POI---XSSFWorkbook
     *
     * @param sheetName sheet页名称
     * @param title     Excel表头数组
     * @param values    Excel行数据
     * @return org.apache.poi.hssf.usermodel.XSSFWorkbook
     *         XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx
     * @author yangk
     * @date 2020/3/20
     */
    public static XSSFWorkbook getXSSFWorkbook(String sheetName, String[] title,
                                               String[][] values) {

        // 第一步，创建一个XSSFWorkbook，对应一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);// 默认列宽

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        XSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        XSSFFont font1 = workbook.createFont();// 创建一个字体对象
        font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        style.setFont(font1);// 设置style1的字体

        //声明列对象
        XSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return workbook;
    }
}

