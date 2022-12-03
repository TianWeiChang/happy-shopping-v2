/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-shopping

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-04-14 21:40:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_mall_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_mall_user`;
CREATE TABLE `tb_mall_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `login_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',
  `password_md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
  `introduce_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '个性签名',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货地址',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '注销标识字段(0-正常 1-已注销)',
  `locked_flag` tinyint NOT NULL DEFAULT '0' COMMENT '锁定标识字段(0-未锁定 1-已锁定)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of tb_mall_user
-- ----------------------------
INSERT INTO `tb_mall_user` VALUES ('1', '勇哥', '13700002703', 'e10adc3949ba59abbe56e057f20f883e', '我不怕千万人阻挡，只怕自己投降', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', '0', '0', '2019-09-22 08:44:57');
INSERT INTO `tb_mall_user` VALUES ('6', '苗哥', '13711113333', 'dda01dc6d334badcd031102be6bee182', '测试用户1', '上海浦东新区XX路XX号 999 137xxxx7797', '0', '0', '2019-08-29 10:51:39');
INSERT INTO `tb_mall_user` VALUES ('7', '西西哥', '13811113333', 'dda01dc6d334badcd031102be6bee182', '测试用户2', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', '0', '0', '2019-08-29 10:55:08');
INSERT INTO `tb_mall_user` VALUES ('8', '小姐姐', '13911113333', 'dda01dc6d334badcd031102be6bee182', '测试用户3', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', '0', '0', '2019-08-29 10:55:16');
INSERT INTO `tb_mall_user` VALUES ('9', '田哥', '18257160372', 'e10adc3949ba59abbe56e057f20f883e', '', '广东省湛江市霞山区2号', '0', '0', '2022-03-20 10:01:55');
INSERT INTO `tb_mall_user` VALUES ('10', '18257150372', '18257150372', 'e10adc3949ba59abbe56e057f20f883e', '', '', '0', '0', '2022-03-28 10:10:36');
INSERT INTO `tb_mall_user` VALUES ('11', '18257160371', '18257160371', 'e10adc3949ba59abbe56e057f20f883e', '', '', '0', '0', '2022-03-28 10:12:18');
INSERT INTO `tb_mall_user` VALUES ('12', '18257160370', '18257160370', 'e10adc3949ba59abbe56e057f20f883e', '', '广东省湛江市霞山区112号', '0', '0', '2022-03-28 10:35:04');
