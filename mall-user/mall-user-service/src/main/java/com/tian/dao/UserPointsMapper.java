package com.tian.dao;

import com.tian.entity.UserPoints;
import com.tian.utils.PageQueryUtil;
import com.tian.utils.PageUtil;

import java.util.List;

public interface UserPointsMapper {
    int insert(UserPoints record);

    UserPoints selectByUserId(Long userId);

    List<UserPoints> findPointsList(PageUtil pageUtil);

    int updatePointsByUserId(UserPoints userPoints);

    int findCount();
}