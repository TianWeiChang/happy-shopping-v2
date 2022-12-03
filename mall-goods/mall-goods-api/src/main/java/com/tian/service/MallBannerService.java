package com.tian.service;

import com.tian.entity.Carousel;
import com.tian.utils.PageResult;
import com.tian.utils.PageUtil;
import com.tian.vo.IndexBannerVO;

import java.util.List;

public interface MallBannerService {

    PageResult getBannersPage(PageUtil pageUtil);

    String saveBanner(Carousel carousel);

    String updateBanner(Carousel carousel);

    Carousel getBannerById(Integer id);

    Boolean deleteBannersBatch(Integer[] ids);

    /**
     * 返回固定数量的轮播图对象(首页调用)
     */
    List<IndexBannerVO> getBannersForIndex(int number);
}
