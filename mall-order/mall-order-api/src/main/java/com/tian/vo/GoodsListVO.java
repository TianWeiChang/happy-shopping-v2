package com.tian.vo;

import com.tian.utils.PageUtil;

import java.util.Date;

public class GoodsListVO extends PageUtil {

    private String goodsName;
    private Integer goodsSellStatus;
    private Date startTime;
    private Date endTime;
    public GoodsListVO(int page, int limit) {
        super(page, limit);
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsSellStatus() {
        return goodsSellStatus;
    }

    public void setGoodsSellStatus(Integer goodsSellStatus) {
        this.goodsSellStatus = goodsSellStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
