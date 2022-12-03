package com.tian.vo;

import com.tian.utils.PageUtil;

public class SearchGoodsListVO extends PageUtil {
    private Long goodsCategoryId;
    private Integer goodsSellStatus;
    private String orderBy;
    private String keyword;

    public SearchGoodsListVO(int page, int limit) {
        super(page, limit);
    }

    public Long getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Long goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public Integer getGoodsSellStatus() {
        return goodsSellStatus;
    }

    public void setGoodsSellStatus(Integer goodsSellStatus) {
        this.goodsSellStatus = goodsSellStatus;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
