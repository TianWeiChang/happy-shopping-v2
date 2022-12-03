package com.tian.vo;

import com.tian.utils.PageUtil;

public class CategorisListVO extends PageUtil {
    private Integer categoryLevel;
    private Long parentId;

    public CategorisListVO(int page, int limit) {
        super(page, limit);
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
