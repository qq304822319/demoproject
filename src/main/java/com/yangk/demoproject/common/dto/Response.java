package com.yangk.demoproject.common.dto;

import com.yangk.demoproject.common.constant.ResponseCode;
import lombok.Data;

/**
 * 成功响应
 *
 * @author yangk
 */
@Data
public class Response {

    private String code;
    private String msg;
    private int pageNumber;   //页数
    private int pageSize;     //每页大小
    private int totalCount;   //当前页码
    private Object data;

    public Response() {
        this.code = ResponseCode.OK.getCode();
        this.msg = ResponseCode.OK.getDesc();
    }

    public Response(Object data) {
        this.code = ResponseCode.OK.getCode();
        this.msg = ResponseCode.OK.getDesc();
        this.data = data;
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(ResponseCode code, String msg) {
        this.code = code.getCode();
        this.msg = msg;
    }

    public Response(Object data, int pageNumber, int pageSize, int totalCount) {
        this.code = ResponseCode.OK.getCode();
        this.msg = ResponseCode.OK.getDesc();
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public String toString() {
        return "Response [code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + ", totalCount=" + this.totalCount + ", pageNumber=" + this.pageNumber + ", pageSize=" + this.pageSize + "]";
    }

    public static Response ok() {
        return new Response(ResponseCode.OK);
    }

}
