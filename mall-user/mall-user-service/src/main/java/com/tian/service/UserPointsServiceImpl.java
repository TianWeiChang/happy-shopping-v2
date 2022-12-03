package com.tian.service;

import com.tian.dao.UserPointsMapper;
import com.tian.entity.UserPoints;
import com.tian.entity.UserPointsVO;
import com.tian.utils.BeanUtil;
import com.tian.utils.PageResult;
import com.tian.utils.PageUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

@DubboService(version = "1.0.0")
public class UserPointsServiceImpl implements UserPointsService {

    @Resource
    private UserPointsMapper userPointsMapper;

    @Override
    public boolean insert(UserPoints record) {
        return userPointsMapper.insert(record) > 0;
    }

    @Override
    public UserPoints selectByUserId(Long userId) {
        return userPointsMapper.selectByUserId(userId);
    }

    @Override
    public PageResult findPointsList(PageUtil pageUtil) {
        List<UserPoints> userPoints = userPointsMapper.findPointsList(pageUtil);
        List<UserPointsVO> userPointsVOS = BeanUtil.copyList(userPoints, UserPointsVO.class);
        int total = userPointsMapper.findCount();
        return new PageResult(userPointsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
    }

    @Override
    public boolean updatePointsByUserId(UserPoints userPoints) {
        return userPointsMapper.updatePointsByUserId(userPoints) > 0;
    }
}
