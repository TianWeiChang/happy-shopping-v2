package com.tian.service;

import com.tian.entity.UserPoints;
import com.tian.utils.PageQueryUtil;
import com.tian.utils.PageResult;
import com.tian.utils.PageUtil;

public interface UserPointsService {
    boolean insert(UserPoints record);

    UserPoints selectByUserId(Long userId);

    PageResult findPointsList(PageUtil pageUtil);

    boolean updatePointsByUserId(UserPoints userPoints);
}
