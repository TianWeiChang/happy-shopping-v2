package com.tian.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年11月27日 17:36
 */
@Data
public class CreditDetailDto  implements Serializable {

    private Integer ruleId;

    private Integer type;

    private Integer number;

    private String orderNo;

    private Date createTime;
}
