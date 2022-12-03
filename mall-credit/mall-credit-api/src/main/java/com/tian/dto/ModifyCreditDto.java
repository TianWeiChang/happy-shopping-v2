package com.tian.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年11月27日 15:57
 */
@Data
public class ModifyCreditDto  implements Serializable {
    private Long userId;

    private Integer number;

    private String orderNo;
}
