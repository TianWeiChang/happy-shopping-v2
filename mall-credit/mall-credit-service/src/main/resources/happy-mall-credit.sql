/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-mall-credit

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2023-01-17 19:49:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `credit_detail`
-- ----------------------------
DROP TABLE IF EXISTS `credit_detail`;
CREATE TABLE `credit_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `type` int NOT NULL,
  `number` int NOT NULL,
  `order_no` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of credit_detail
-- ----------------------------
INSERT INTO `credit_detail` VALUES ('1', '1', '0', '10', '100001', '2022-11-27 17:51:50');
INSERT INTO `credit_detail` VALUES ('2', '2', '0', '5', 'ADD100001', '2022-11-27 22:41:12');
INSERT INTO `credit_detail` VALUES ('4', '2', '0', '15', 'REDUCE100001', '2022-11-28 10:22:23');

-- ----------------------------
-- Table structure for `user_credit`
-- ----------------------------
DROP TABLE IF EXISTS `user_credit`;
CREATE TABLE `user_credit` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `credit` int NOT NULL DEFAULT '0',
  `pre_credit` int DEFAULT NULL,
  `operate_type` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_indx_uniq` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_credit
-- ----------------------------
INSERT INTO `user_credit` VALUES ('1', '1', '999', null, null);
INSERT INTO `user_credit` VALUES ('2', '2', '0', null, null);
INSERT INTO `user_credit` VALUES ('3', '17', '0', null, null);
INSERT INTO `user_credit` VALUES ('6', '18', '0', null, null);
INSERT INTO `user_credit` VALUES ('9', '19', '0', null, null);
INSERT INTO `user_credit` VALUES ('10', '20', '0', null, null);
INSERT INTO `user_credit` VALUES ('11', '21', '0', null, null);
INSERT INTO `user_credit` VALUES ('12', '22', '0', null, null);
INSERT INTO `user_credit` VALUES ('13', '23', '0', null, null);
