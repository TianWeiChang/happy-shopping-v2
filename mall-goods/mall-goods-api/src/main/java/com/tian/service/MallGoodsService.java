package com.tian.service;


import com.tian.entity.GoodsInfo;
import com.tian.utils.PageResult;
import com.tian.vo.GoodsListVO;
import com.tian.vo.SearchGoodsListVO;
import com.tian.vo.StockNumDTO;

import java.util.List;

public interface MallGoodsService {
    /**
     * 后台分页查询
     */
    PageResult getMallGoodsPage(GoodsListVO goodsListVO);

    /**
     * 添加商品
     */
    String saveMallGoods(GoodsInfo goods);

    /**
     * 批量新增商品数据
     */
    void batchSaveMallGoods(List<GoodsInfo> goodsInfoList);

    /**
     * 修改商品信息
     */
    String updateMallGoods(GoodsInfo goods);

    /**
     * 获取商品详情
     */
    GoodsInfo getGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     */
    Boolean batchUpdateSellStatus(Long[] ids, int sellStatus);

    List<GoodsInfo> getGoodsListByIds(List<Long> ids);

    /**
     * 商品搜索
     */
    PageResult searchNewBeeMallGoods(SearchGoodsListVO searchGoodsListVO);

    int updateStockNum(List<StockNumDTO> stockNumDTOS);
}
