

如果喜欢，请点个右上角 star 小星星

电商项目，技术栈

 > Spring Boot +Dubbo+Zookeeper+Redis+Mybatis+RabbitMQ+MySQL+OSS

## 功能
- 用户注册
- 用户登录
- 首页商品展示
- 商品详情
- 加入购物车
- 购物车商品列表
- 立即购买
- 支付
- 提醒商家发货
- 订单列表
- 站内信模板定制（添加、修改、查询、删除）
- 站内信发送（站内信模板定制、站内信发送明细）
- 订单评论
- 用户积分（积分增加、兑换、积分账户注销、积分账户初始化等）
- 市场营销


## 项目端口
目前这个电商项目的子项目越来越多（现在已经11个了，后期可能会继续增加），所以端口管理还是蛮重要的。

| 系统名称            | 中文解释         | web端口号 | dubbo 端口号 | 说明                     |
| ------------------- | ---------------- | --------- | ------------ | ------------------------ |
| mall-admin          | 后台管理（商家） | 9001      |              |                          |
| mall-common         | 公共服务         | 9002      | 20881        | 暂时没有实现，后期规划中，目前只是一些共用工具类，没有涉及到具体服务 |
| mall-credit         | 用户积分服务     | 9003      | 20882        |                          |
| mall-goods          | 商品服务         | 9004      | 20883        |                          |
| mall-job            | 定时任务         | 9005      | 20884        |                          |
| mall-marketing      | 营销系统         | 9006      | 20885        |                          |
| mall-mq             | 消息消费         | 9007      | 20886        |                          |
| mall-order          | 订单服务         | 9008      | 20887        |                          |
| mall-system-message | 站内信服务       | 9009      | 20888        |                          |
| mall-user           | 用户服务         | 9010      | 20889        |                          |
| mall-web            | 商城系统         | 9011      | 20889        |                          |


## 项目架构图


## 功能设计

用户注册：[用户注册设计](https://wx.zsxq.com/dweb2/index/columns/48418244881248)

积分服务：[用户积分服务的设计](https://wx.zsxq.com/dweb2/index/columns/48418244881248)









