package com.yangk.demoproject.common.exception;

/**
 * 系统通用返回异常
 * @author yangk
 * @date 2021/11/22
 */
public class SysResponseException extends Exception{

    private String errorMessage;
    private String errorCode;

    public SysResponseException(final String errorMessage, final String errorCode){
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
