package com.tian.service.impl;

import com.tian.entity.SystemMessage;
import com.tian.mapper.SystemMessageMapper;
import com.tian.service.SystemMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月10日 20:32
 * <p>
 * 站内信模板
 */
@Service
public class SystemMessageServiceImpl implements SystemMessageService {

    @Resource
    private SystemMessageMapper systemMessageMapper;

    @Override
    public int insert(SystemMessage record) {
        return systemMessageMapper.insert(record);
    }

    @Override
    public SystemMessage selectByPrimaryKey(Long id) {
        return systemMessageMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(SystemMessage record) {
        return systemMessageMapper.updateByPrimaryKey(record);
    }

    @Override
    public int delete(Long id) {
        return systemMessageMapper.deleteByPrimaryKey(id);
    }
}
