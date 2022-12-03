package com.tian.constants;

public enum ResultCode {
    LOGIN_SUCCESS(0, "登录成功"),
    LOGIN_FAILED(1, "登录失败"),
    LOGIN_FAILED_LOCK(2, "用户已被锁");
    private int errorCode;
    private String errorMsg;

    ResultCode(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
