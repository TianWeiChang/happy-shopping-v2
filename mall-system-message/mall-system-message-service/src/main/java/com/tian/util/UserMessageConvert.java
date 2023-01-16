package com.tian.util;

import com.tian.dto.UserMessageQueryRespDto;
import com.tian.entity.UserMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


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

    List<UserMessageQueryRespDto> convertList(List<UserMessage> userMessageList);

    UserMessageQueryRespDto convert(UserMessage userMessage);
}
