package com.yangk.demoproject.common.utils;

import com.yangk.demoproject.common.constant.ResponseCode;
import com.yangk.demoproject.model.sys.SysUser;
import lombok.Data;

/**
 * API数据格式
 *
 * @author yangk
 * @date 2020/3/11
 */
@Data
public class Response {

    private String code;
    private String msg;
    private int pageNumber;   //页数
    private int pageSize;     //每页大小
    private int totalCount;   //当前页码
    private SysUser sysUser;  //用户信息

    private Object data;

    public Response(){
        this.code = ResponseCode.OK.getCode();
        this.msg = ResponseCode.OK.getDesc();
    }

    public Response(Object data){
        this.code = ResponseCode.OK.getCode();
        this.msg = ResponseCode.OK.getDesc();
        this.data = data;
    }

    public Response(Object data, int pageNumber, int pageSize, int totalCount, SysUser sysUser) {
        this.code = ResponseCode.OK.getCode();
        this.msg = ResponseCode.OK.getDesc();
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.sysUser = sysUser;
    }

    public String toString() {
        return "Response [code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + ", totalCount=" + this.totalCount + ", pageNumber=" + this.pageNumber + ", pageSize=" + this.pageSize + "]";
    }

    public static Response ok() {
        return new Response(ResponseCode.OK);
    }

    public static Response returnData(Object data) {
        return new Response(data);
    }
}
