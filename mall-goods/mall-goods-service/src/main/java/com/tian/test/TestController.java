package com.tian.test;

import com.tian.entity.GoodsInfo;
import com.tian.service.MallGoodsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianwc
 * @version 1.0.0
 * @description TODO
 * @createTime 2022年07月30日 18:20
 */
@RestController
public class TestController {

    @DubboReference(version = "1.0.0")
    private MallGoodsService mallGoodsService;

    @RequestMapping("/test")
    public void test() {
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        List<GoodsInfo> listByIds = mallGoodsService.getGoodsListByIds(list);
        System.out.println(listByIds);
    }
}
