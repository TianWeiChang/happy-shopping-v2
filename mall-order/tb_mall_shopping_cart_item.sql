/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-shopping

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-04-14 21:53:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_mall_shopping_cart_item`
-- ----------------------------
DROP TABLE IF EXISTS `tb_mall_shopping_cart_item`;
CREATE TABLE `tb_mall_shopping_cart_item` (
  `cart_item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物项主键id',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  `goods_id` bigint NOT NULL DEFAULT '0' COMMENT '关联商品id',
  `goods_count` int NOT NULL DEFAULT '1' COMMENT '数量(最大为5)',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
  PRIMARY KEY (`cart_item_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='购物车';

-- ----------------------------
-- Records of tb_mall_shopping_cart_item
-- ----------------------------
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('69', '9', '10162', '1', '1', '2022-03-20 10:02:33', '2022-03-20 10:02:33');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('70', '9', '10170', '1', '1', '2022-03-20 10:02:51', '2022-03-20 10:02:51');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('71', '9', '10893', '1', '1', '2022-03-20 23:17:32', '2022-03-20 23:17:32');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('72', '9', '10779', '1', '1', '2022-03-21 07:27:15', '2022-03-21 07:27:15');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('73', '9', '10742', '1', '1', '2022-03-21 14:06:38', '2022-03-21 14:06:38');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('74', '9', '10159', '1', '1', '2022-03-22 15:34:14', '2022-03-22 15:34:14');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('75', '9', '10700', '1', '1', '2022-03-24 17:14:52', '2022-03-24 17:14:52');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('76', '9', '10779', '1', '1', '2022-03-24 21:18:51', '2022-03-24 21:18:51');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('77', '9', '10779', '1', '1', '2022-03-24 21:30:35', '2022-03-24 21:30:35');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('78', '9', '10779', '1', '1', '2022-03-24 21:47:44', '2022-03-24 21:47:44');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('79', '9', '10779', '4', '1', '2022-03-24 21:50:31', '2022-03-25 10:07:02');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('80', '9', '10779', '3', '1', '2022-03-25 10:11:58', '2022-03-25 10:24:24');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('81', '9', '10159', '3', '1', '2022-03-25 10:24:34', '2022-03-25 22:17:50');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('82', '9', '10700', '1', '1', '2022-03-25 10:27:21', '2022-03-25 10:27:21');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('83', '9', '10283', '1', '1', '2022-03-25 10:28:23', '2022-03-25 10:28:23');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('84', '9', '10320', '1', '1', '2022-03-25 22:46:51', '2022-03-25 22:46:51');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('85', '9', '10254', '1', '1', '2022-03-25 22:54:26', '2022-03-25 22:54:26');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('86', '9', '10693', '1', '1', '2022-03-27 22:45:55', '2022-03-27 22:45:55');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('87', '9', '10693', '1', '1', '2022-03-27 22:47:00', '2022-03-27 22:47:00');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('88', '9', '10779', '3', '1', '2022-03-28 10:43:19', '2022-03-30 09:43:25');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('89', '12', '10283', '1', '1', '2022-03-28 17:43:06', '2022-03-28 17:43:06');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('90', '9', '10159', '1', '1', '2022-04-01 16:18:56', '2022-04-01 16:18:56');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('91', '9', '10320', '1', '1', '2022-04-01 16:19:11', '2022-04-01 16:19:11');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('92', '9', '10113', '1', '1', '2022-04-02 10:30:35', '2022-04-02 10:30:35');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('93', '9', '10320', '2', '1', '2022-04-02 12:00:24', '2022-04-08 17:42:53');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('94', '12', '10779', '4', '1', '2022-04-08 14:52:48', '2022-04-09 11:20:09');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('95', '9', '10237', '1', '1', '2022-04-08 17:43:24', '2022-04-08 17:43:24');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('96', '9', '10779', '4', '1', '2022-04-09 10:35:53', '2022-04-09 11:18:07');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('97', '12', '10779', '1', '1', '2022-04-09 21:38:44', '2022-04-09 21:38:44');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('98', '12', '10779', '1', '1', '2022-04-09 21:43:40', '2022-04-09 21:43:40');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('99', '12', '10779', '1', '1', '2022-04-09 21:56:25', '2022-04-09 21:56:25');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('100', '12', '10779', '1', '1', '2022-04-09 22:02:54', '2022-04-09 22:02:54');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('101', '12', '10779', '1', '1', '2022-04-09 22:04:06', '2022-04-09 22:04:06');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('102', '9', '10779', '1', '1', '2022-04-10 16:30:22', '2022-04-10 16:30:22');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('103', '9', '10779', '1', '1', '2022-04-10 16:32:42', '2022-04-10 16:32:42');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('104', '9', '10269', '1', '1', '2022-04-10 18:28:14', '2022-04-10 18:28:14');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('105', '9', '10269', '1', '1', '2022-04-10 18:29:52', '2022-04-10 18:29:52');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('106', '9', '10320', '1', '1', '2022-04-10 18:32:11', '2022-04-10 18:32:11');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('107', '9', '10283', '1', '1', '2022-04-10 18:32:31', '2022-04-10 18:32:31');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('108', '9', '10269', '1', '1', '2022-04-11 10:34:41', '2022-04-11 10:34:41');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('109', '9', '10283', '2', '1', '2022-04-11 10:37:53', '2022-04-11 10:37:57');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('110', '9', '10779', '2', '1', '2022-04-11 10:42:07', '2022-04-11 10:42:10');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('111', '9', '10269', '1', '1', '2022-04-11 10:42:58', '2022-04-11 10:42:58');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('112', '9', '10269', '1', '1', '2022-04-11 10:44:26', '2022-04-11 10:44:26');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('113', '9', '10320', '1', '1', '2022-04-11 10:45:36', '2022-04-11 10:45:36');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('114', '9', '10895', '2', '1', '2022-04-11 10:47:01', '2022-04-11 10:47:07');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('115', '9', '10283', '1', '1', '2022-04-11 10:47:30', '2022-04-11 10:47:30');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('116', '9', '10320', '1', '1', '2022-04-11 10:49:33', '2022-04-11 10:49:33');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('117', '9', '10283', '1', '1', '2022-04-11 11:00:54', '2022-04-11 11:00:54');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('118', '9', '10283', '1', '1', '2022-04-11 11:01:37', '2022-04-11 11:01:37');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('119', '9', '10283', '1', '1', '2022-04-11 11:02:11', '2022-04-11 11:02:11');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('120', '9', '10895', '1', '1', '2022-04-11 11:03:57', '2022-04-11 11:03:57');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('121', '9', '10895', '1', '1', '2022-04-11 11:04:34', '2022-04-11 11:04:34');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('122', '9', '10283', '1', '1', '2022-04-11 11:06:07', '2022-04-11 11:06:07');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('123', '9', '10283', '1', '1', '2022-04-11 11:10:00', '2022-04-11 11:10:00');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('124', '9', '10283', '1', '1', '2022-04-11 11:13:27', '2022-04-11 11:13:27');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('125', '9', '10283', '1', '1', '2022-04-11 11:14:34', '2022-04-11 11:14:34');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('126', '9', '10283', '1', '1', '2022-04-11 11:24:46', '2022-04-11 11:24:46');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('127', '9', '10895', '1', '1', '2022-04-11 11:28:04', '2022-04-11 11:28:04');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('128', '9', '10283', '1', '1', '2022-04-11 11:28:20', '2022-04-11 11:28:20');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('129', '9', '10159', '1', '0', '2022-04-11 11:36:39', '2022-04-11 11:36:39');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('130', '9', '10895', '4', '0', '2022-04-14 08:44:34', '2022-04-14 08:48:29');
INSERT INTO `tb_mall_shopping_cart_item` VALUES ('131', '9', '10895', '1', '0', '2022-04-14 08:44:34', '2022-04-14 08:44:34');
