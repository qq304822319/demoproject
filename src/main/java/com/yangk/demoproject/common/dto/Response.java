package com.yangk.demoproject.common.dto;

import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.model.sys.SysUser;
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
    private Object data;

    public Response() {
        this.code = ResponseCode.OK.getCode();
        this.msg = ResponseCode.OK.getDesc();
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(Object data) {
        this.code = ResponseCode.OK.getCode();
        this.msg = ResponseCode.OK.getDesc();
        this.data = data;
    }

    public Response(ResponseCode responseCode, Object data) {
        this.code = responseCode.getCode();
        this.msg = responseCode.getDesc();
        this.data = data;
    }

    public String toString() {
        return "Response [code=" + this.code + ", msg=" + this.msg + ", data=" + this.data;
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

}