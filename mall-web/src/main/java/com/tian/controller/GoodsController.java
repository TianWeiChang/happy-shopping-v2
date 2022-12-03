package com.tian.controller;

import com.tian.constants.Constants;
import com.tian.entity.GoodsInfo;
import com.tian.enums.ServiceResultEnum;
import com.tian.exception.MallException;
import com.tian.service.MallCategoryService;
import com.tian.service.MallGoodsService;
import com.tian.utils.BeanUtil;
import com.tian.utils.PageQueryUtil;
import com.tian.vo.GoodsDetailVO;
import com.tian.vo.SearchGoodsListVO;
import com.tian.vo.SearchPageCategoryVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class GoodsController {

    @DubboReference(version = "1.0.0",check = false)
    private MallGoodsService mallGoodsService;
    @DubboReference(version = "1.0.0")
    private MallCategoryService mallCategoryService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        params.putIfAbsent("page", 1);
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        SearchGoodsListVO searchGoodsListVO= new SearchGoodsListVO(Integer.parseInt(params.get("page").toString()), Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = mallCategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
                searchGoodsListVO.setGoodsCategoryId(categoryId);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
            searchGoodsListVO.setOrderBy(params.get("orderBy").toString());
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        searchGoodsListVO.setKeyword(keyword);
        //搜索上架状态下的商品
        searchGoodsListVO.setGoodsSellStatus(Constants.SELL_STATUS_UP);
        //封装商品数据

        request.setAttribute("pageResult", mallGoodsService.searchNewBeeMallGoods(searchGoodsListVO));
        return "mall/search";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            MallException.fail("参数异常");
        }
        GoodsInfo goods = mallGoodsService.getGoodsById(goodsId);
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            MallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        request.setAttribute("goodsDetail", goodsDetailVO);
        return "mall/detail";
    }

}
