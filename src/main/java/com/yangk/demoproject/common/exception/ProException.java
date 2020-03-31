package com.yangk.demoproject.common.exception;

import com.yangk.demoproject.common.constant.ResponseCode;

/**
 * 自定义异常
 *
 * @author yangk
 */
public class ProException extends RuntimeException {

    private String code;
    private String msg;

    public ProException(ResponseCode code) {
        this.code = code.getCode();
        this.msg = code.getInfo();
    }

    public ProException(ResponseCode code, String msg) {
        this.code = code.getCode();
        this.msg = msg;
    }

    public ProException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ProException{code=\'" + this.code + '\'' + ", msg=\'" + this.msg + '\'' + '}';
    }

}
