package com.tian.service;


import com.tian.entity.IndexConfig;
import com.tian.utils.PageResult;
import com.tian.vo.ConfigsListVO;
import com.tian.vo.IndexConfigGoodsVO;

import java.util.List;

public interface MallIndexConfigService {
    /**
     * 后台分页
     *
     */
    PageResult getConfigsPage(ConfigsListVO configsListVO);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    List<IndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number);

    Boolean deleteBatch(Long[] ids);
}
