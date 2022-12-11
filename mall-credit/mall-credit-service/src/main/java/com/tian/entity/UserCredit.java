package com.tian.entity;

import java.io.Serializable;

public class UserCredit implements Serializable {
    private Integer id;

    private Long userId;

    private Integer credit;

    private Integer preCredit;


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getPreCredit() {
        return preCredit;
    }

    public void setPreCredit(Integer preCredit) {
        this.preCredit = preCredit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", credit=").append(credit);
        sb.append(", preCredit=").append(preCredit);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}