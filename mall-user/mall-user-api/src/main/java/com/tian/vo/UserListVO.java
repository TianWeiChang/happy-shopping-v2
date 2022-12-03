package com.tian.vo;

import com.tian.utils.PageUtil;

public class UserListVO extends PageUtil {
    private String loginName;
    public UserListVO(int page, int limit) {
        super(page, limit);
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
