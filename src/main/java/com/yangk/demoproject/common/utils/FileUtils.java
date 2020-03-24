package com.yangk.demoproject.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具类
 *
 * @author yangk
 * @date 2020/3/20
 */
@Slf4j
public class FileUtils {

    private final int BUFFER_SIZE = 2 * 1024;
    //单位缩进字符串
    private static String SPACE = "    ";

    /**
     * 判断目标文件夹是否存在,不存在生成文件夹
     *
     * @param filePath 目标文件夹路径
     * @return void
     * @author yangk
     * @date 2020/3/21
     */
    public static void createFile(String filePath) {
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    /**
     * 发送响应流方法
     *
     * @param response 相应
     * @param fileName 文件名
     * @return void
     * @author yangk
     * @date 2020/3/20
     */
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            String downloadFileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 格式化JSON字符串
     *
     * @param json json格式字符串
     * @return java.lang.String
     * @author yangk
     * @date 2020/3/21
     */
    public static String formatJson(String json) {
        StringBuffer result = new StringBuffer();
        int length = json.length();
        int number = 0;
        char key = 0;
        //遍历输入字符串。
        for (int i = 0; i < length; i++) {
            //1、获取当前字符。
            key = json.charAt(i);

            //2、如果当前字符是前方括号、前花括号做如下处理：
            if ((key == '[') || (key == '{')) {
                //(1) 如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
                if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
                    result.append('\n');
                    result.append(indent(number));
                }
                //(2) 打印：当前字符。
                result.append(key);
                //(3) 前方括号、前花括号，的后面必须换行。打印：换行。
                result.append('\n');
                //(4) 每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
                number++;
                result.append(indent(number));
                //(5) 进行下一次循环。
                continue;
            }
            //3、如果当前字符是后方括号、后花括号做如下处理：
            if ((key == ']') || (key == '}')) {
                //(1) 后方括号、后花括号，的前面必须换行。打印：换行。
                result.append('\n');
                //(2) 每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
                number--;
                result.append(indent(number));
                //(3) 打印：当前字符。
                result.append(key);
                //(4) 如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
                if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }
                //(5) 继续下一次循环。
                continue;
            }
            //4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
            if ((key == ',')) {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }
            //5、打印：当前字符。
            result.append(key);
        }
        return result.toString();
    }

    /***********************************private****************************************/


    /**
     * 返回指定次数的缩进字符串。每一次缩进SPACE。
     *
     * @param number 缩进次数。
     * @return 指定缩进次数的字符串。
     */
    private static String indent(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(SPACE);
        }
        return result.toString();
    }


    /**
     * 压缩方法
     *
     * @param sourceFile       源文件
     * @param name             压缩后的名称
     * @param KeepDirStructure
     * @throws Exception
     * @author yangk 2018-12-19
     * @editor
     * @editcont
     */
    private void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + File.separator));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + File.separator + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }
}
