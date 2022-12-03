package com.tian.controller;

import com.tian.constants.Constants;
import com.tian.dto.OrderListDTO;
import com.tian.service.MallOrderService;
import com.tian.service.UserRemindService;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author tianwc
 * @date 2022-11-20
 * <p>
 * 处理 用户的提醒
 */
@Controller
@RequestMapping
public class OrderRemindController {

    @DubboReference(version = "1.0.0", check = false)
    private UserRemindService userRemindService;

    @GetMapping("/remind")
    public String ordersPage(HttpServletRequest request) {
        request.setAttribute("path", "remind");
        return "admin/remind";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/remind/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        OrderListDTO orderListDTO = new OrderListDTO(Integer.parseInt(params.get("page").toString()), Constants.ORDER_SEARCH_PAGE_LIMIT);
        return ResultGenerator.genSuccessResult(userRemindService.findRemindList(orderListDTO));
    }

    /**
     * 批量处理订单提醒
     */
    @PostMapping(value = "/remind/status/{status}")
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("status") int status) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (status != 1) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        List<Long> idList = Arrays.asList(ids);
        if (userRemindService.updateBatchStatus(idList, status)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }
}
