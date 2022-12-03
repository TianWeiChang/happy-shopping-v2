/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-shopping

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-04-14 21:40:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_user_points`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_points`;
CREATE TABLE `tb_user_points` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `points` int DEFAULT NULL COMMENT '积分',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户积分表';

-- ----------------------------
-- Records of tb_user_points
-- ----------------------------
INSERT INTO `tb_user_points` VALUES ('1', '9', '209712', '2022-03-28 10:12:42', '2022-03-28 10:12:42', '18257160372');
INSERT INTO `tb_user_points` VALUES ('2', '11', '0', '2022-03-28 10:12:18', '2022-03-28 10:12:18', '18257160371');
INSERT INTO `tb_user_points` VALUES ('3', '12', '22794', '2022-03-28 10:35:05', '2022-03-28 10:35:05', '18257160370');
