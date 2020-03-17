package com.yangk.demoproject.common.dto;

import com.yangk.demoproject.common.constant.ResponseCode;
import lombok.Data;

/**
 * 错误相应
 *
 * @author yangk
 */
@Data
public class ErrorInfo {

    private String code;
    private String msg;

    public ErrorInfo() {
        this.code = ResponseCode.ERROR.getCode();
        this.msg = ResponseCode.ERROR.getDesc();
    }

    public ErrorInfo(ResponseCode code, String msg) {
        this.code = code.getCode();
        this.msg = msg;
    }

    public ErrorInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorInfo error() {
        return new ErrorInfo();
    }

    public static ErrorInfo error(String msg) {
        return new ErrorInfo(ResponseCode.ERROR, msg);
    }

    public static ErrorInfo error(String code, String msg) {
        return new ErrorInfo(code, msg);
    }
}
