package com.tian.vo;

import com.tian.utils.PageUtil;

public class ConfigsListVO extends PageUtil {

    private Integer configType;

    public ConfigsListVO(int page, int limit) {
        super(page, limit);
    }

    public Integer getConfigType() {
        return configType;
    }

    public void setConfigType(Integer configType) {
        this.configType = configType;
    }
}
