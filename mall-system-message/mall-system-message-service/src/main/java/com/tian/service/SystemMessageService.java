package com.tian.service;

import com.tian.entity.SystemMessage;

/**
 * @author tianwc 公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月10日 20:32
 */
public interface SystemMessageService {
    int insert(SystemMessage record);

    SystemMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SystemMessage record);

    int delete(Long id);
}
