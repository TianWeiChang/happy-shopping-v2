/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-shopping

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-04-14 21:53:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_order_remind`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_remind`;
CREATE TABLE `tb_order_remind` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `order_id` bigint DEFAULT NULL COMMENT '订单号',
  `status` int DEFAULT '0' COMMENT '鎻愰啋鐘舵€侊紝0:鏈鐞?1:宸插鐞?',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id_idx` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='用户订单提醒表';

-- ----------------------------
-- Records of tb_order_remind
-- ----------------------------
INSERT INTO `tb_order_remind` VALUES ('6', '12', '33', '1', '2022-04-09 21:43:11', '2022-04-09 21:43:11', '18257160370');
INSERT INTO `tb_order_remind` VALUES ('7', '9', '42', '1', '2022-04-10 16:33:24', '2022-04-10 16:33:24', '18257160372');
INSERT INTO `tb_order_remind` VALUES ('9', '9', '44', '1', '2022-04-10 18:29:37', '2022-04-10 18:29:37', '18257160372');
INSERT INTO `tb_order_remind` VALUES ('10', '9', '45', '1', '2022-04-10 18:30:04', '2022-04-10 18:30:04', '18257160372');
INSERT INTO `tb_order_remind` VALUES ('11', '9', '46', '1', '2022-04-10 18:32:22', '2022-04-10 18:32:22', '18257160372');
INSERT INTO `tb_order_remind` VALUES ('12', '9', '47', '0', '2022-04-10 18:32:42', '2022-04-10 18:32:42', '18257160372');
INSERT INTO `tb_order_remind` VALUES ('13', '9', '48', '0', '2022-04-11 10:36:26', '2022-04-11 10:36:26', '18257160372');
INSERT INTO `tb_order_remind` VALUES ('14', '9', '50', '0', '2022-04-11 10:42:23', '2022-04-11 10:42:23', '18257160372');
INSERT INTO `tb_order_remind` VALUES ('15', '9', '76', '0', '2022-04-11 11:28:36', '2022-04-11 11:28:36', '18257160372');
