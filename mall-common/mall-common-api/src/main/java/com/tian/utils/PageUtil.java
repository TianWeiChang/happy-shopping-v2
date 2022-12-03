package com.tian.utils;

import java.io.Serializable;

public class PageUtil implements Serializable {
    //当前页码
    private int page;
    //每页条数
    private int limit;

    private int start;

    public PageUtil(int page, int limit) {
        this.page = page;
        this.limit = limit;
        this.start = (page - 1) * limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
