package com.tian.controller;

import com.tian.service.MallUserService;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import com.tian.vo.UserListVO;
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
 * 用户管理
 */
@Controller
@RequestMapping
public class MallUserController {

    @DubboReference(version = "1.0.0", check = false)
    private MallUserService mallUserService;

    @GetMapping("/users")
    public String usersPage(HttpServletRequest request) {
        request.setAttribute("path", "users");
        return "admin/mall_user";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        UserListVO userListVO = new UserListVO(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("limit").toString()));
        if (params.get("loginName") != null) {
            userListVO.setLoginName(params.get("loginName").toString());
        }
        return ResultGenerator.genSuccessResult(mallUserService.getNewBeeMallUsersPage(userListVO));
    }

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     */
    @RequestMapping(value = "/users/lock/{lockStatus}", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids, @PathVariable int lockStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (lockStatus != 0 && lockStatus != 1) {
            return ResultGenerator.genFailResult("操作非法！");
        }
        if (mallUserService.lockUsers(ids, lockStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("禁用失败");
        }
    }
}