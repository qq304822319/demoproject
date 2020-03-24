package com.yangk.demoproject.common.utils;

import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.common.exception.ProException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel工具类
 *
 * @author yangk
 * @date 2020/3/20
 */
public class ExcelUtils {

    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel

    /**
     * 根据文件后缀，自适应上传文件的版本
     * 返回对应的操作对象
     *
     * @param inStr
     * @param fileName 文件名
     * @return org.apache.poi.ss.usermodel.Workbook
     * @author yangk
     * @date 2020/3/23
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);  //2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr);  //2007+
        } else {
            throw new ProException(ResponseCode.EXCEL_NOT_TYPE);
        }
        return wb;
    }

    /*****************************Excel导入*****************************/

    /**
     * 导出Excel.xls---POI---Workbook
     *
     * @param sheetName sheet页名称
     * @param title     Excel表头数组
     * @param values    Excel行数据
     * @return org.apache.poi.hssf.usermodel.Workbook
     * Workbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls
     * @author yangk
     * @date 2020/3/20
     */
    public static Workbook getWorkbook(String fileName, String sheetName, String[] title,
                                       String[][] values) {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            workbook = new HSSFWorkbook();  //2003-
        } else if (excel2007U.equals(fileType)) {
            workbook = new XSSFWorkbook();  //2007+
        } else {
            throw new ProException(ResponseCode.EXCEL_NOT_TYPE);
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);// 默认列宽

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        Row row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式
        Font font1 = workbook.createFont();// 创建一个字体对象
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);// 粗体显示
        style.setFont(font1);// 设置style1的字体

        //声明列对象
        Cell cell = null;

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

    /*****************************Excel导入*****************************/

    /**
     * 导入Excel所有Sheet页内容
     *
     * @param inputStream
     * @param fileName
     * @return java.util.List<java.util.List < java.lang.Object>>
     * List<List<Object>>[所有sheet的行[行内容]]
     * @author yangk
     * @date 2020/3/23
     */
    public static List<List<Object>> importExcelToList(InputStream inputStream,
                                                       String fileName) throws Exception {
        List<List<Object>> list = null;
        //创建Excel工作薄
        Workbook work = getWorkbook(inputStream, fileName);
        if (null == work) {
            throw new ProException(ResponseCode.EXCEL_IS_NULL);
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        list = new ArrayList<List<Object>>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            //遍历当前sheet中的所有行
            //包涵头部，所以要小于等于最后一列数,这里也可以在初始值加上头部行数，以便跳过头部
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                //读取一行
                row = sheet.getRow(j);
                //去掉空行和表头
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                //遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    if (null != cell) {
                        li.add(getCellValue(cell));
                    } else {
                        li.add("");
                    }
                }
                list.add(li);
            }
        }
        return list;
    }

    /**
     * 按Sheet页导入Excel
     *
     * @param inputStream
     * @param fileName
     * @return java.util.Map<java.lang.String, java.util.List < java.util.List < java.lang.Object>>>
     * String : sheet页名称 List<List<Object> : [行[行内容]]
     * @author yangk
     * @date 2020/3/24
     */
    public static Map<String, List<List<Object>>> importExcelToMap(InputStream inputStream,
                                                                   String fileName) throws Exception {
        Map<String, List<List<Object>>> list = null;
        //创建Excel工作薄
        Workbook work = getWorkbook(inputStream, fileName);
        if (null == work) {
            throw new ProException(ResponseCode.EXCEL_IS_NULL);
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        list = new HashMap<String, List<List<Object>>>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            List<List<Object>> l0 = new ArrayList<List<Object>>();
            //遍历当前sheet中的所有行
            //包涵头部，所以要小于等于最后一列数,这里也可以在初始值加上头部行数，以便跳过头部
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                //读取一行
                row = sheet.getRow(j);
                //去掉空行和表头
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                //遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    if (null != cell) {
                        li.add(getCellValue(cell));
                    } else {
                        li.add("");
                    }
                }
                l0.add(li);
            }
            list.put(sheet.getSheetName(), l0);
        }
        return list;
    }

    /**
     * 对表格中数值进行格式化
     *
     * @param cell
     * @return java.lang.Object
     * @author yangk
     * @date 2020/3/23
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化字符类型的数字
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            /*case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;*/
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil
                        .isCellDateFormatted(cell)) {
                    SimpleDateFormat fmt = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    value = fmt.format(cell.getDateCellValue());
                } else {
                    Double d = cell.getNumericCellValue();
                    value = d.toString();
                    // 解决1234.0 去掉后面的.0
                    if (null != value
                            && !"".equals(value.toString().trim())) {
                        String[] item = value.toString().split("[.]");
                        if (1 < item.length && "0".equals(item[1])) {
                            value = item[0];
                        }
                    }
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_ERROR:
                value = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = cell.getStringCellValue();
                break;
            default:
                value = cell.getStringCellValue();
        }
        return value;
    }

}