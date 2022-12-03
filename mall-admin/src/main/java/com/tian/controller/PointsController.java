package com.tian.controller;

import com.tian.service.UserPointsService;
import com.tian.utils.PageUtil;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author tianwc
 * @date 2022-11-20
 * <p>
 * 查看用户积分列表
 */
@Controller
@RequestMapping
public class PointsController {

    @DubboReference(version = "1.0.0", check = false)
    private UserPointsService userPointsService;

    @GetMapping("/points")
    public String ordersPage(HttpServletRequest request) {
        request.setAttribute("path", "points");
        return "admin/mall_points";
    }

    /**
     * 用户积分列表
     */
    @RequestMapping(value = "/points/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageUtil pageUtil = new PageUtil(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("limit").toString()));
        return ResultGenerator.genSuccessResult(userPointsService.findPointsList(pageUtil));
    }
}
