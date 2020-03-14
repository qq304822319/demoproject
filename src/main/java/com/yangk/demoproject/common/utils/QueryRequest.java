package com.yangk.demoproject.common.utils;

import lombok.Data;

/**
 * Post请求格式化
 *
 * @author yangk
 * @date 2020/3/11
 */
@Data
public class QueryRequest<T> {
    private int pageNumber;   //页数
    private int pageSize;     //每页大小
    private String sort;      //排序字段
    private String order;     //排序规则
    private T data;           //查询参数
}
