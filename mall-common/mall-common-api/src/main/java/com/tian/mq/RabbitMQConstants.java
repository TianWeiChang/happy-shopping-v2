package com.tian.mq;

public interface RabbitMQConstants {
    String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";
    String SEND_CODE_EXCHANGE = "SEND_CODE_EXCHANGE";

    String LOGIN_LOG_ROUTING_KEY = "LoginLogKey";
    String ORDER_ROUTING_KEY = "OrderKey";
    String Product_ROUTING_KEY = "ProductKey";
    String CART_ROUTING_KEY  = "CartItemKey";
    String SEND_CODE_ROUTING_KEY = "SendCodeKey";


    String LOGIN_LOG_QUEUE = "LoginLogDirectQueue";
    String ORDER_QUEUE = "OrderDirectQueue";
    String PRODUCT_QUEUE = "ProductDirectQueue";
    String CART_QUEUE = "ProductDirectQueue";

    String SEND_CODE = "SendCodeQueue";

}
