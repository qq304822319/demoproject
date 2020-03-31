package com.yangk.demoproject.common.dto;

import lombok.Data;

/**
 * 上传
 *
 * @author yangk
 * @date 2020/3/31
 */
@Data
public class FileDto {
    /**
     * 地址
     */
    public String url;

    /**
     * 名字
     */
    public String name;

    /**
     * 后缀
     */
    public String suffix;

    /**
     * 图片大小 单位KB
     */
    public Long size;

    /**
     * 图片宽度 单位PX
     */
    public int width;

    /**
     * 图片高度 单位PX
     */
    public int height;
}