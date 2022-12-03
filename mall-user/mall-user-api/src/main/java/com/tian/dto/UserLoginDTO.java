package com.tian.dto;

import com.tian.vo.MallUserVO;

import java.io.Serializable;

public class UserLoginDTO implements Serializable {
    //0:成功,1：失败，2：账户被锁
    private int errorCode;
    private Long userId;
    private String errorMsg;
    private MallUserVO mallUserVO;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public MallUserVO getMallUserVO() {
        return mallUserVO;
    }

    public void setMallUserVO(MallUserVO mallUserVO) {
        this.mallUserVO = mallUserVO;
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "errorCode=" + errorCode +
                ", userId=" + userId +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
