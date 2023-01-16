package com.tian.util;

import com.tian.dto.UserMessageAddDto;
import com.tian.message.UserMessageAddMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月16日 17:35
 * <p>
 * 实体参数转换类
 */
@Mapper
public interface UserMessageConvert {
    UserMessageConvert INSTANCE = Mappers.getMapper(UserMessageConvert.class);

    UserMessageAddDto convert(UserMessageAddMessage userMessage);
}
