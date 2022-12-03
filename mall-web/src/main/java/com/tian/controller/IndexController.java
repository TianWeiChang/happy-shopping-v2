package com.tian.controller;

import com.tian.configs.RedisUtil;
import com.tian.constants.Constants;
import com.tian.constants.RedisCacheKey;
import com.tian.enums.IndexConfigTypeEnum;
import com.tian.exception.MallException;
import com.tian.service.MallBannerService;
import com.tian.service.MallCartService;
import com.tian.service.MallCategoryService;
import com.tian.service.MallIndexConfigService;
import com.tian.utils.UserSessionUtil;
import com.tian.vo.IndexBannerVO;
import com.tian.vo.IndexCategoryVO;
import com.tian.vo.IndexConfigGoodsVO;
import com.tian.vo.MallUserVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @DubboReference(version = "1.0.0", check = false)
    private MallBannerService mallBannerService;

    @DubboReference(version = "1.0.0", check = false)
    private MallIndexConfigService mallIndexConfigService;

    @DubboReference(version = "1.0.0", check = false)
    private MallCategoryService mallCategoryService;

    @DubboReference(version = "1.0.0", check = false)
    private MallCartService mallCartService;

    @Resource
    private RedisUtil redisUtil;

    @GetMapping({"/index", "", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<IndexCategoryVO> categories = mallCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            MallException.fail("分类数据不完善");
        }
        HttpSession httpSession = request.getSession();
        if (httpSession != null) {
            MallUserVO user = UserSessionUtil.userSession(request.getSession());
            if (user != null) {
                Object count = redisUtil.get(RedisCacheKey.USER_CART_COUNT_KEY + user.getUserId());
                httpSession.setAttribute("cartCount", count == null ? 0 : (int) count);
            }
        }

        List<IndexBannerVO> carousels = mallBannerService.getBannersForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<IndexConfigGoodsVO> hotGoodses = mallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
        List<IndexConfigGoodsVO> newGoodses = mallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
        List<IndexConfigGoodsVO> recommendGoodses = mallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);

        request.setAttribute("categories", categories);//分类数据
        request.setAttribute("carousels", carousels);//轮播图
        request.setAttribute("hotGoodses", hotGoodses);//热销商品
        request.setAttribute("newGoodses", newGoodses);//新品
        request.setAttribute("recommendGoodses", recommendGoodses);//推荐商品
        return "mall/index";
    }
}
