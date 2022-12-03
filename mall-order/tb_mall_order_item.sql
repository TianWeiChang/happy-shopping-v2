/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-shopping

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-04-14 21:54:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_mall_order_item`
-- ----------------------------
DROP TABLE IF EXISTS `tb_mall_order_item`;
CREATE TABLE `tb_mall_order_item` (
  `order_item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单关联购物项主键id',
  `order_id` bigint NOT NULL DEFAULT '0' COMMENT '订单主键id',
  `goods_id` bigint NOT NULL DEFAULT '0' COMMENT '关联商品id',
  `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单时商品的名称(订单快照)',
  `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单时商品的主图(订单快照)',
  `selling_price` int NOT NULL DEFAULT '1' COMMENT '下单时商品的价格(订单快照)',
  `goods_count` int NOT NULL DEFAULT '1' COMMENT '数量(订单快照)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单详情表';

-- ----------------------------
-- Records of tb_mall_order_item
-- ----------------------------
INSERT INTO `tb_mall_order_item` VALUES ('1', '1', '10180', 'Apple AirPods 配充电盒', 'http://tianwc.oss-cn-beijing.aliyuncs.com/dev/20220411/1649648554660.jpg', '1246', '2', '2019-09-18 22:53:07');
INSERT INTO `tb_mall_order_item` VALUES ('2', '2', '10147', 'MUJI 羽毛 靠垫', '/goods-img/0f701215-b782-40c7-8bbd-97b51be56461.jpg', '65', '1', '2019-09-18 22:55:20');
INSERT INTO `tb_mall_order_item` VALUES ('3', '2', '10158', '无印良品 女式粗棉线条纹长袖T恤', 'http://localhost:28089/goods-img/5488564b-8335-4b0c-a5a4-52f3f03ee728.jpg', '70', '1', '2019-09-18 22:55:20');
INSERT INTO `tb_mall_order_item` VALUES ('4', '3', '10742', '华为 HUAWEI P30 Pro', '/goods-img/dda1d575-cdac-4eb4-a118-3834490166f7.jpg', '5488', '1', '2019-09-19 23:56:40');
INSERT INTO `tb_mall_order_item` VALUES ('5', '3', '10320', 'Apple iPhone 11 Pro', '/goods-img/0025ad55-e260-4a00-be79-fa5b8c5ac0de.jpg', '9999', '1', '2019-09-19 23:56:40');
INSERT INTO `tb_mall_order_item` VALUES ('6', '4', '10254', 'Apple 2019款 MacBook Air 13.3', '/goods-img/7810bc9d-236f-4386-a0ef-45a831b49bf2.jpg', '8499', '1', '2019-09-19 23:58:18');
INSERT INTO `tb_mall_order_item` VALUES ('7', '5', '10104', '无印良品 MUJI 修正带', '/goods-img/98ce17e1-890e-4eaf-856a-7fce8ffebc4c.jpg', '15', '1', '2019-09-20 00:08:04');
INSERT INTO `tb_mall_order_item` VALUES ('8', '5', '10110', '无印良品 MUJI 基础润肤乳霜', '/goods-img/30036561-a150-4ea7-9106-29bbea278909.jpg', '100', '1', '2019-09-20 00:08:04');
INSERT INTO `tb_mall_order_item` VALUES ('9', '6', '10895', 'HUAWEI Mate 30 4000万超感光徕卡影像', '/goods-img/mate30-3.png', '3999', '2', '2019-09-22 22:57:15');
INSERT INTO `tb_mall_order_item` VALUES ('10', '7', '10895', 'HUAWEI Mate 30 4000万超感光徕卡影像', '/goods-img/mate30-3.png', '3999', '1', '2019-09-22 23:01:44');
INSERT INTO `tb_mall_order_item` VALUES ('11', '7', '10320', 'Apple iPhone 11 Pro', '/goods-img/0025ad55-e260-4a00-be79-fa5b8c5ac0de.jpg', '9999', '1', '2019-09-22 23:01:44');
INSERT INTO `tb_mall_order_item` VALUES ('12', '8', '10180', 'Apple AirPods 配充电盒', '/goods-img/64768a8d-0664-4b29-88c9-2626578ffbd1.jpg', '1246', '1', '2019-09-22 23:02:45');
INSERT INTO `tb_mall_order_item` VALUES ('13', '9', '10237', 'MAC 雾面丝绒哑光子弹头口红', 'http://localhost:28089/goods-img/1930d79b-88bd-4c5c-8510-0697c9ad2578.jpg', '165', '4', '2019-09-22 23:03:52');
INSERT INTO `tb_mall_order_item` VALUES ('14', '9', '10254', 'Apple 2019款 MacBook Air 13.3', '/goods-img/7810bc9d-236f-4386-a0ef-45a831b49bf2.jpg', '8499', '2', '2019-09-22 23:03:52');
INSERT INTO `tb_mall_order_item` VALUES ('15', '9', '10195', '索尼 WH-1000XM3 头戴式耳机', 'http://localhost:28089/goods-img/0dc503b2-90a2-4971-9723-c085a1844b76.jpg', '2599', '1', '2019-09-22 23:03:52');
INSERT INTO `tb_mall_order_item` VALUES ('16', '9', '10894', 'HUAWEI Mate 30 Pro', '/goods-img/mate30p3.png', '5399', '1', '2019-09-22 23:03:52');
INSERT INTO `tb_mall_order_item` VALUES ('17', '10', '10894', 'HUAWEI Mate 30 Pro', '/goods-img/mate30p3.png', '5399', '3', '2019-09-22 23:08:06');
INSERT INTO `tb_mall_order_item` VALUES ('18', '11', '10279', 'Apple iPhone 11 (A2223)', '/goods-img/a0d09f94-9c46-4ee1-aaef-dfd132e7543e.jpg', '5999', '1', '2019-09-23 14:43:27');
INSERT INTO `tb_mall_order_item` VALUES ('19', '12', '10279', 'Apple iPhone 11 (A2223)', '/goods-img/a0d09f94-9c46-4ee1-aaef-dfd132e7543e.jpg', '5999', '1', '2019-09-23 14:57:26');
INSERT INTO `tb_mall_order_item` VALUES ('20', '12', '10159', 'Apple AirPods 配充电盒', '/goods-img/53c9f268-7cd4-4fac-909c-2dc066625655.jpg', '1246', '1', '2019-09-23 14:57:26');
INSERT INTO `tb_mall_order_item` VALUES ('21', '13', '10742', '华为 HUAWEI P30 Pro', '/goods-img/dda1d575-cdac-4eb4-a118-3834490166f7.jpg', '5488', '1', '2019-09-23 15:08:46');
INSERT INTO `tb_mall_order_item` VALUES ('22', '14', '10158', '无印良品 女式粗棉线条纹长袖T恤', '/goods-img/5488564b-8335-4b0c-a5a4-52f3f03ee728.jpg', '70', '1', '2019-09-23 16:59:24');
INSERT INTO `tb_mall_order_item` VALUES ('23', '14', '10704', '华为 HUAWEI P30 超感光徕卡三摄麒麟980AI...', '/goods-img/b9e6d770-06dd-40f4-9ae5-31103cec6e5f.jpg', '3988', '1', '2019-09-23 16:59:24');
INSERT INTO `tb_mall_order_item` VALUES ('24', '14', '10739', '华为 HUAWEI P30 Pro', '/goods-img/65c8e729-aeca-4780-977b-4d0d39d4aa2e.jpg', '4988', '1', '2019-09-23 16:59:24');
INSERT INTO `tb_mall_order_item` VALUES ('25', '15', '10147', 'MUJI 羽毛 靠垫', '/goods-img/0f701215-b782-40c7-8bbd-97b51be56461.jpg', '65', '1', '2019-09-23 17:05:34');
INSERT INTO `tb_mall_order_item` VALUES ('26', '16', '10742', '华为 HUAWEI P30 Pro', '/goods-img/dda1d575-cdac-4eb4-a118-3834490166f7.jpg', '5488', '1', '2019-09-23 17:10:03');
INSERT INTO `tb_mall_order_item` VALUES ('27', '16', '10159', 'Apple AirPods 配充电盒', '/goods-img/53c9f268-7cd4-4fac-909c-2dc066625655.jpg', '1246', '1', '2019-09-23 17:10:03');
INSERT INTO `tb_mall_order_item` VALUES ('28', '16', '10254', 'Apple 2019款 MacBook Air 13.3', '/goods-img/7810bc9d-236f-4386-a0ef-45a831b49bf2.jpg', '8499', '1', '2019-09-23 17:10:03');
INSERT INTO `tb_mall_order_item` VALUES ('29', '17', '10180', 'Apple AirPods 配充电盒', '/goods-img/64768a8d-0664-4b29-88c9-2626578ffbd1.jpg', '1246', '1', '2019-09-26 14:09:56');
INSERT INTO `tb_mall_order_item` VALUES ('30', '18', '10779', '华为 HUAWEI Mate 20', '/goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2019-09-30 08:38:26');
INSERT INTO `tb_mall_order_item` VALUES ('31', '19', '10742', '华为 HUAWEI P30 Pro', '/goods-img/dda1d575-cdac-4eb4-a118-3834490166f7.jpg', '5488', '1', '2019-10-05 20:20:10');
INSERT INTO `tb_mall_order_item` VALUES ('32', '19', '10154', '无印良品 MUJI 塑料浴室座椅', '/goods-img/15395057-94e9-4545-a8ee-8aee025f40c5.jpg', '85', '1', '2019-10-05 20:20:10');
INSERT INTO `tb_mall_order_item` VALUES ('33', '19', '10159', 'Apple AirPods 配充电盒', '/goods-img/53c9f268-7cd4-4fac-909c-2dc066625655.jpg', '1246', '1', '2019-10-05 20:20:10');
INSERT INTO `tb_mall_order_item` VALUES ('34', '20', '10895', 'HUAWEI Mate 30 4000万超感光徕卡影像', '/goods-img/mate30-3.png', '3999', '1', '2019-10-05 22:12:47');
INSERT INTO `tb_mall_order_item` VALUES ('35', '21', '10162', '诺基亚（NOKIA）BH-705 银白色 5.0真无线蓝...', '/goods-img/5e0d089b-fa91-410d-8ff2-9534eb6f627f.jpg', '499', '1', '2022-03-20 10:03:21');
INSERT INTO `tb_mall_order_item` VALUES ('36', '21', '10170', '索尼（SONY）WI-1000X Hi-Res颈挂式 入...', '/goods-img/1631a30b-287c-41da-bbbe-1a9b1b8d1552.jpg', '1499', '1', '2022-03-20 10:03:21');
INSERT INTO `tb_mall_order_item` VALUES ('37', '22', '10893', 'HUAWEI Mate 30 Pro 双4000万徕卡电...', '/goods-img/mate30p2.png', '5399', '1', '2022-03-24 17:10:39');
INSERT INTO `tb_mall_order_item` VALUES ('38', '22', '10779', '华为 HUAWEI Mate 20', '/goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-03-24 17:10:39');
INSERT INTO `tb_mall_order_item` VALUES ('39', '22', '10742', '华为 HUAWEI P30 Pro', '/goods-img/dda1d575-cdac-4eb4-a118-3834490166f7.jpg', '5488', '1', '2022-03-24 17:10:39');
INSERT INTO `tb_mall_order_item` VALUES ('40', '22', '10159', 'Apple AirPods 配充电盒', '/goods-img/53c9f268-7cd4-4fac-909c-2dc066625655.jpg', '1246', '1', '2022-03-24 17:10:39');
INSERT INTO `tb_mall_order_item` VALUES ('41', '23', '10700', '荣耀8X 千元屏霸 91%屏占比 2000万AI双摄', '/goods-img/6a160b96-9b4a-4844-b335-feb31b1f5d8c.jpg', '999', '1', '2022-03-24 17:15:03');
INSERT INTO `tb_mall_order_item` VALUES ('42', '24', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '3', '2022-03-25 22:32:49');
INSERT INTO `tb_mall_order_item` VALUES ('43', '24', '10159', 'Apple AirPods 配充电盒', 'http://localhost:9009//goods-img/53c9f268-7cd4-4fac-909c-2dc066625655.jpg', '1246', '3', '2022-03-25 22:32:49');
INSERT INTO `tb_mall_order_item` VALUES ('44', '24', '10700', '荣耀8X 千元屏霸 91%屏占比 2000万AI双摄', 'http://localhost:9009//goods-img/6a160b96-9b4a-4844-b335-feb31b1f5d8c.jpg', '999', '1', '2022-03-25 22:32:49');
INSERT INTO `tb_mall_order_item` VALUES ('45', '24', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-03-25 22:32:49');
INSERT INTO `tb_mall_order_item` VALUES ('46', '25', '10320', 'Apple iPhone 11 Pro', 'http://localhost:9009//goods-img/0025ad55-e260-4a00-be79-fa5b8c5ac0de.jpg', '9999', '1', '2022-03-25 22:47:01');
INSERT INTO `tb_mall_order_item` VALUES ('47', '26', '10254', 'Apple 2019款 MacBook Air 13.3', 'http://localhost:9009//goods-img/7810bc9d-236f-4386-a0ef-45a831b49bf2.jpg', '8499', '1', '2022-03-25 22:54:31');
INSERT INTO `tb_mall_order_item` VALUES ('48', '27', '10693', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4...', 'http://localhost:9009//goods-img/f8ab28c3-8e04-49a0-ba05-2e6a3ae7211f.jpg', '999', '1', '2022-03-27 22:46:08');
INSERT INTO `tb_mall_order_item` VALUES ('49', '28', '10693', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4...', 'http://localhost:9009//goods-img/f8ab28c3-8e04-49a0-ba05-2e6a3ae7211f.jpg', '999', '1', '2022-03-27 22:47:09');
INSERT INTO `tb_mall_order_item` VALUES ('50', '29', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-03-28 17:43:52');
INSERT INTO `tb_mall_order_item` VALUES ('51', '30', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '3', '2022-04-01 16:19:17');
INSERT INTO `tb_mall_order_item` VALUES ('52', '30', '10159', 'Apple AirPods 配充电盒', 'http://localhost:9009//goods-img/53c9f268-7cd4-4fac-909c-2dc066625655.jpg', '1246', '1', '2022-04-01 16:19:17');
INSERT INTO `tb_mall_order_item` VALUES ('53', '30', '10320', 'Apple iPhone 11 Pro', 'http://localhost:9009//goods-img/0025ad55-e260-4a00-be79-fa5b8c5ac0de.jpg', '9999', '1', '2022-04-01 16:19:17');
INSERT INTO `tb_mall_order_item` VALUES ('54', '31', '10113', '无印良品 MUJI 小型超声波香薰机', 'http://localhost:9009//goods-img/9608b59d-cbca-4b70-9f05-226fde41c51c.jpg', '250', '1', '2022-04-09 11:18:15');
INSERT INTO `tb_mall_order_item` VALUES ('55', '31', '10320', 'Apple iPhone 11 Pro', 'http://localhost:9009//goods-img/0025ad55-e260-4a00-be79-fa5b8c5ac0de.jpg', '9999', '2', '2022-04-09 11:18:15');
INSERT INTO `tb_mall_order_item` VALUES ('56', '31', '10237', 'MAC 雾面丝绒哑光子弹头口红', 'http://localhost:9009//goods-img/1930d79b-88bd-4c5c-8510-0697c9ad2578.jpg', '165', '1', '2022-04-09 11:18:15');
INSERT INTO `tb_mall_order_item` VALUES ('57', '31', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '4', '2022-04-09 11:18:15');
INSERT INTO `tb_mall_order_item` VALUES ('58', '32', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '4', '2022-04-09 20:58:15');
INSERT INTO `tb_mall_order_item` VALUES ('59', '33', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-09 21:38:49');
INSERT INTO `tb_mall_order_item` VALUES ('60', '34', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-09 21:47:44');
INSERT INTO `tb_mall_order_item` VALUES ('61', '35', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-09 21:56:31');
INSERT INTO `tb_mall_order_item` VALUES ('62', '36', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-09 21:56:32');
INSERT INTO `tb_mall_order_item` VALUES ('63', '37', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-09 21:56:34');
INSERT INTO `tb_mall_order_item` VALUES ('64', '38', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-09 22:03:01');
INSERT INTO `tb_mall_order_item` VALUES ('65', '39', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-09 22:03:31');
INSERT INTO `tb_mall_order_item` VALUES ('66', '40', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-09 22:03:31');
INSERT INTO `tb_mall_order_item` VALUES ('67', '41', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-09 22:04:10');
INSERT INTO `tb_mall_order_item` VALUES ('68', '42', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-10 16:30:29');
INSERT INTO `tb_mall_order_item` VALUES ('69', '43', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '1', '2022-04-10 16:32:49');
INSERT INTO `tb_mall_order_item` VALUES ('70', '44', '10269', 'Apple 2019新品 Macbook Pro 13....', 'http://localhost:9009//goods-img/a2afdb6c-69a7-4081-bd09-62174f9f5624.jpg', '12999', '1', '2022-04-10 18:28:20');
INSERT INTO `tb_mall_order_item` VALUES ('71', '45', '10269', 'Apple 2019新品 Macbook Pro 13....', 'http://localhost:9009//goods-img/a2afdb6c-69a7-4081-bd09-62174f9f5624.jpg', '12999', '1', '2022-04-10 18:29:57');
INSERT INTO `tb_mall_order_item` VALUES ('72', '46', '10320', 'Apple iPhone 11 Pro', 'http://localhost:9009//goods-img/0025ad55-e260-4a00-be79-fa5b8c5ac0de.jpg', '9999', '1', '2022-04-10 18:32:14');
INSERT INTO `tb_mall_order_item` VALUES ('73', '47', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-10 18:32:34');
INSERT INTO `tb_mall_order_item` VALUES ('74', '48', '10269', 'Apple 2019新品 Macbook Pro 13....', 'http://localhost:9009//goods-img/a2afdb6c-69a7-4081-bd09-62174f9f5624.jpg', '12999', '1', '2022-04-11 10:34:48');
INSERT INTO `tb_mall_order_item` VALUES ('75', '49', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '2', '2022-04-11 10:41:09');
INSERT INTO `tb_mall_order_item` VALUES ('76', '50', '10779', '华为 HUAWEI Mate 20', 'http://localhost:9009//goods-img/08f9a912-f049-4cf8-a839-115fc6582398.jpg', '3199', '2', '2022-04-11 10:42:14');
INSERT INTO `tb_mall_order_item` VALUES ('77', '51', '10269', 'Apple 2019新品 Macbook Pro 13....', 'http://localhost:9009//goods-img/a2afdb6c-69a7-4081-bd09-62174f9f5624.jpg', '12999', '1', '2022-04-11 10:43:54');
INSERT INTO `tb_mall_order_item` VALUES ('78', '52', '10269', 'Apple 2019新品 Macbook Pro 13....', 'http://localhost:9009//goods-img/a2afdb6c-69a7-4081-bd09-62174f9f5624.jpg', '12999', '1', '2022-04-11 10:44:02');
INSERT INTO `tb_mall_order_item` VALUES ('79', '53', '10269', 'Apple 2019新品 Macbook Pro 13....', 'http://localhost:9009//goods-img/a2afdb6c-69a7-4081-bd09-62174f9f5624.jpg', '12999', '1', '2022-04-11 10:44:03');
INSERT INTO `tb_mall_order_item` VALUES ('80', '54', '10269', 'Apple 2019新品 Macbook Pro 13....', 'http://localhost:9009//goods-img/a2afdb6c-69a7-4081-bd09-62174f9f5624.jpg', '12999', '1', '2022-04-11 10:44:41');
INSERT INTO `tb_mall_order_item` VALUES ('81', '55', '10269', 'Apple 2019新品 Macbook Pro 13....', 'http://localhost:9009//goods-img/a2afdb6c-69a7-4081-bd09-62174f9f5624.jpg', '12999', '1', '2022-04-11 10:44:42');
INSERT INTO `tb_mall_order_item` VALUES ('82', '56', '10269', 'Apple 2019新品 Macbook Pro 13....', 'http://localhost:9009//goods-img/a2afdb6c-69a7-4081-bd09-62174f9f5624.jpg', '12999', '1', '2022-04-11 10:44:43');
INSERT INTO `tb_mall_order_item` VALUES ('83', '57', '10320', 'Apple iPhone 11 Pro', 'http://localhost:9009//goods-img/0025ad55-e260-4a00-be79-fa5b8c5ac0de.jpg', '9999', '1', '2022-04-11 10:45:40');
INSERT INTO `tb_mall_order_item` VALUES ('84', '58', '10895', 'HUAWEI Mate 30 4000万超感光徕卡影像', 'http://localhost:9009//goods-img/mate30-3.png', '3999', '2', '2022-04-11 10:47:12');
INSERT INTO `tb_mall_order_item` VALUES ('85', '59', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 10:47:33');
INSERT INTO `tb_mall_order_item` VALUES ('86', '60', '10320', 'Apple iPhone 11 Pro', 'http://tianwc.oss-cn-beijing.aliyuncs.com/dev/20220411/1649648693000.jpg', '9999', '1', '2022-04-11 10:50:30');
INSERT INTO `tb_mall_order_item` VALUES ('87', '61', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:01:03');
INSERT INTO `tb_mall_order_item` VALUES ('88', '62', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:01:43');
INSERT INTO `tb_mall_order_item` VALUES ('89', '63', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:01:47');
INSERT INTO `tb_mall_order_item` VALUES ('90', '64', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:01:49');
INSERT INTO `tb_mall_order_item` VALUES ('91', '65', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:02:15');
INSERT INTO `tb_mall_order_item` VALUES ('92', '66', '10895', 'HUAWEI Mate 30 4000万超感光徕卡影像', 'http://localhost:9009//goods-img/mate30-3.png', '3999', '1', '2022-04-11 11:04:00');
INSERT INTO `tb_mall_order_item` VALUES ('93', '67', '10895', 'HUAWEI Mate 30 4000万超感光徕卡影像', 'http://localhost:9009//goods-img/mate30-3.png', '3999', '1', '2022-04-11 11:04:02');
INSERT INTO `tb_mall_order_item` VALUES ('94', '68', '10895', 'HUAWEI Mate 30 4000万超感光徕卡影像', 'http://localhost:9009//goods-img/mate30-3.png', '3999', '1', '2022-04-11 11:04:04');
INSERT INTO `tb_mall_order_item` VALUES ('95', '69', '10895', 'HUAWEI Mate 30 4000万超感光徕卡影像', 'http://localhost:9009//goods-img/mate30-3.png', '3999', '1', '2022-04-11 11:04:38');
INSERT INTO `tb_mall_order_item` VALUES ('96', '70', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:06:12');
INSERT INTO `tb_mall_order_item` VALUES ('97', '71', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:11:30');
INSERT INTO `tb_mall_order_item` VALUES ('98', '72', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:13:37');
INSERT INTO `tb_mall_order_item` VALUES ('99', '73', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:14:37');
INSERT INTO `tb_mall_order_item` VALUES ('100', '74', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:24:49');
INSERT INTO `tb_mall_order_item` VALUES ('101', '75', '10895', 'HUAWEI Mate 30 4000万超感光徕卡影像', 'http://localhost:9009//goods-img/mate30-3.png', '3999', '1', '2022-04-11 11:28:08');
INSERT INTO `tb_mall_order_item` VALUES ('102', '76', '10283', 'Apple iPhone 11 (A2223)', 'http://localhost:9009//goods-img/075a188a-9045-45f0-9c67-1e42e0552aa2.jpg', '6799', '1', '2022-04-11 11:28:27');
