package com.tian.mq;

public interface RabbitMQConstants {
    String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";
    String SEND_CODE_EXCHANGE = "SEND_CODE_EXCHANGE";
    String INIT_USER_CREDIT__EXCHANGE = "INIT_USER_CREDIT_EXCHANGE";

    String LOGIN_LOG_ROUTING_KEY = "LoginLogKey";
    String ORDER_ROUTING_KEY = "OrderKey";
    String Product_ROUTING_KEY = "ProductKey";
    String CART_ROUTING_KEY  = "CartItemKey";
    String SEND_CODE_ROUTING_KEY = "SendCodeKey";
    String INIT_USER_CREDIT_ROUTING_KEY = "InitUserCreditKey";


    String LOGIN_LOG_QUEUE = "LoginLogDirectQueue";
    String ORDER_QUEUE = "OrderDirectQueue";
    String PRODUCT_QUEUE = "ProductDirectQueue";
    String CART_QUEUE = "ProductDirectQueue";
    String INIT_USER_CREDIT_QUEUE = "InitUserCreditDirectQueue";

    String SEND_CODE = "SendCodeQueue";

}
