package com.tian.mq;

public interface RabbitMQConstants {
    String FANOUT_EXCHANGE = "MyExchange";

    String LOGIN_LOG_ROUTING_KEY = "LoginLogKey";
    String ORDER_ROUTING_KEY = "OrderKey";
    String Product_ROUTING_KEY = "ProductKey";
    String CART_ROUTING_KEY  = "CartItemKey";

    String LOGIN_LOG_QUEUE = "LoginLogDirectQueue";
    String ORDER_QUEUE = "OrderDirectQueue";
    String PRODUCT_QUEUE = "ProductDirectQueue";
    String CART_QUEUE = "ProductDirectQueue";

}
