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

    protected String code;
    protected String info;
    protected Object data;


    public Response() {
        this.code = ResponseCode.OK.getCode();
        this.info = ResponseCode.OK.getInfo();
    }

    public Response(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public Response(Object data) {
        this.code = ResponseCode.OK.getCode();
        this.info = ResponseCode.OK.getInfo();
        this.data = data;
    }

    public Response(ResponseCode responseCode, Object data) {
        this.code = responseCode.getCode();
        this.info = responseCode.getInfo();
        this.data = data;
    }

    public String toString() {
        return "Response [code=" + this.code + ", info=" + this.info + ", data=" + this.data;
    }

    public static Response ok() {
        return new Response(ResponseCode.OK);
    }

    public static Response returnData(Object data) {
        return new Response(data);
    }

    public static Response returnData(ResponseCode responseCode, Object data) {
        return new Response(responseCode, data);
    }

    public static PagaData returnData(Object data, long total, int pageNumber, int pageSize) {
        return new PagaData(data, (int) total, pageNumber, pageSize);
    }


    public static Response error() {
        return new Response(ResponseCode.ERROR);
    }

    public static Response error(String msg) {
        return new Response(ResponseCode.ERROR, msg);
    }

    public static Response error(String code, String msg) {
        return new Response(code, msg);
    }

}

class PagaData extends Response {
    private int totalCount;   //总条数
    private int pageNumber;   //页数
    private int pageSize;     //每页大小

    public PagaData(Object t, int totalCount, int pageNumber, int pageSize) {
        this.code = ResponseCode.OK.getCode();
        this.info = ResponseCode.OK.getInfo();
        this.data = t;
        this.pageNumber = pageNumber;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
    }
}