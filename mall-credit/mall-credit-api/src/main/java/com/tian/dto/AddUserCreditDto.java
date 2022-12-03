package com.tian.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年11月27日 09:55
 */
@Data
public class AddUserCreditDto implements Serializable {
    private Integer id;

    private Long userId;

    private Integer credit;
}
