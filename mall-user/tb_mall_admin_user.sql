/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-shopping

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-04-14 21:40:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_mall_admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_mall_admin_user`;
CREATE TABLE `tb_mall_admin_user` (
  `admin_user_id` int NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint DEFAULT '0' COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='管理员表';

-- ----------------------------
-- Records of tb_mall_admin_user
-- ----------------------------
INSERT INTO `tb_mall_admin_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'tian', '0');
INSERT INTO `tb_mall_admin_user` VALUES ('2', 'admin1', 'e10adc3949ba59abbe56e057f20f883e', '快购1号', '0');
INSERT INTO `tb_mall_admin_user` VALUES ('3', 'admin2', 'e10adc3949ba59abbe56e057f20f883e', '快购2号', '0');
