package com.tian.dto;

import java.io.Serializable;

public class CartItemDTO implements Serializable {
    private String retMsg;
    private int flag;

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
