package com.tian.enums;

/**
 * @author tianwc 公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年11月27日 16:32
 */
public enum CreditTypeEnum {
    /**
     * 新增积分
     */
    ADD(0, "新增积分"),
    /**
     * 扣减积分
     */
    REDUCE(1, "扣减积分"),
    /**
     * 预扣减
     */
    PRE_REDUCE(2, "预扣减积分");
    private int code;
    private String msg;

    CreditTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
