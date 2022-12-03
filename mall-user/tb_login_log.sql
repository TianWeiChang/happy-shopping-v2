/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-shopping

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-04-14 21:40:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_login_log`
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_log`;
CREATE TABLE `tb_login_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='登录日志表';

-- ----------------------------
-- Records of tb_login_log
-- ----------------------------
INSERT INTO `tb_login_log` VALUES ('22', '9', '2022-03-30 10:58:54');
INSERT INTO `tb_login_log` VALUES ('23', '9', '2022-03-30 11:02:26');
INSERT INTO `tb_login_log` VALUES ('24', '9', '2022-03-30 14:39:52');
INSERT INTO `tb_login_log` VALUES ('25', '9', '2022-04-01 16:16:54');
INSERT INTO `tb_login_log` VALUES ('26', '9', '2022-04-01 16:16:55');
INSERT INTO `tb_login_log` VALUES ('27', '9', '2022-04-01 16:16:55');
INSERT INTO `tb_login_log` VALUES ('28', '9', '2022-04-01 16:18:51');
INSERT INTO `tb_login_log` VALUES ('29', '9', '2022-04-02 11:10:15');
INSERT INTO `tb_login_log` VALUES ('30', '9', '2022-04-02 11:10:15');
INSERT INTO `tb_login_log` VALUES ('31', '9', '2022-04-02 11:10:15');
INSERT INTO `tb_login_log` VALUES ('32', '9', '2022-04-02 11:11:11');
INSERT INTO `tb_login_log` VALUES ('33', '9', '2022-04-02 11:13:45');
INSERT INTO `tb_login_log` VALUES ('34', '9', '2022-04-02 11:22:44');
INSERT INTO `tb_login_log` VALUES ('35', '9', '2022-04-02 11:57:44');
INSERT INTO `tb_login_log` VALUES ('36', '9', '2022-04-02 11:58:55');
INSERT INTO `tb_login_log` VALUES ('37', '9', '2022-04-02 11:58:55');
INSERT INTO `tb_login_log` VALUES ('38', '9', '2022-04-02 11:58:55');
INSERT INTO `tb_login_log` VALUES ('39', '9', '2022-04-02 11:58:58');
INSERT INTO `tb_login_log` VALUES ('40', '9', '2022-04-02 11:59:30');
INSERT INTO `tb_login_log` VALUES ('41', '9', '2022-04-02 11:59:32');
INSERT INTO `tb_login_log` VALUES ('42', '9', '2022-04-02 11:59:32');
INSERT INTO `tb_login_log` VALUES ('43', '9', '2022-04-02 11:59:32');
INSERT INTO `tb_login_log` VALUES ('44', '9', '2022-04-02 11:59:37');
INSERT INTO `tb_login_log` VALUES ('45', '9', '2022-04-02 11:59:38');
INSERT INTO `tb_login_log` VALUES ('46', '9', '2022-04-02 11:59:38');
INSERT INTO `tb_login_log` VALUES ('47', '9', '2022-04-02 11:59:38');
INSERT INTO `tb_login_log` VALUES ('48', '9', '2022-04-02 11:59:39');
INSERT INTO `tb_login_log` VALUES ('49', '9', '2022-04-02 12:03:25');
INSERT INTO `tb_login_log` VALUES ('50', '9', '2022-04-09 11:16:45');
INSERT INTO `tb_login_log` VALUES ('51', '9', '2022-04-09 11:16:45');
INSERT INTO `tb_login_log` VALUES ('52', '9', '2022-04-09 11:16:45');
INSERT INTO `tb_login_log` VALUES ('53', '9', '2022-04-09 11:16:45');
INSERT INTO `tb_login_log` VALUES ('54', '9', '2022-04-09 11:16:45');
INSERT INTO `tb_login_log` VALUES ('55', '12', '2022-04-09 11:19:58');
INSERT INTO `tb_login_log` VALUES ('56', '9', '2022-04-11 10:31:22');
INSERT INTO `tb_login_log` VALUES ('57', '9', '2022-04-11 10:31:22');
INSERT INTO `tb_login_log` VALUES ('58', '9', '2022-04-11 10:31:22');
INSERT INTO `tb_login_log` VALUES ('59', '9', '2022-04-11 10:31:23');
INSERT INTO `tb_login_log` VALUES ('60', '9', '2022-04-11 10:31:23');
INSERT INTO `tb_login_log` VALUES ('61', '9', '2022-04-11 10:34:34');
