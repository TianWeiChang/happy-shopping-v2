package com.tian.service;

import com.tian.dto.SystemMessageDto;
import com.tian.utils.Result;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月10日 20:32
 */
public interface SystemMessageDubboService {

    /**
     * 通过ID查询站内信模板
     *
     * @param id 站内信表主键
     * @return 站内信模板
     */
    Result querySystemMessageById(Long id);

    /**
     * 新增站内信模板
     *
     * @param systemMessageDto 站内信模板
     * @return 是否添加成功
     */
    Result add(SystemMessageDto systemMessageDto);

    /**
     * 修改站内信模板
     *
     * @param systemMessageDto 站内信模板
     * @return 是否修改成功
     */
    Result update(SystemMessageDto systemMessageDto);

    /**
     * 删除站内信模板
     *
     * @param systemMessageDto 站内信模板
     * @return 是否删除成功
     */
    Result delete(SystemMessageDto systemMessageDto);
}
