/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-shopping

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-04-14 21:58:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_mall_carousel`
-- ----------------------------
DROP TABLE IF EXISTS `tb_mall_carousel`;
CREATE TABLE `tb_mall_carousel` (
  `carousel_id` int NOT NULL AUTO_INCREMENT COMMENT '首页轮播图主键id',
  `carousel_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '轮播图',
  `redirect_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '''##''' COMMENT '点击后的跳转地址(默认不跳转)',
  `carousel_rank` int NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int NOT NULL DEFAULT '0' COMMENT '创建者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` int NOT NULL DEFAULT '0' COMMENT '修改者id',
  PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='首页轮播商品表';

-- ----------------------------
-- Records of tb_mall_carousel
-- ----------------------------
INSERT INTO `tb_mall_carousel` VALUES ('2', 'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner1.png', 'https://juejin.im/book/5da2f9d4f265da5b81794d48/section/5da2f9d6f265da5b794f2189', '13', '0', '2019-11-29 00:00:00', '0', '2019-11-29 00:00:00', '0');
INSERT INTO `tb_mall_carousel` VALUES ('5', 'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner2.png', 'https://juejin.im/book/5da2f9d4f265da5b81794d48/section/5da2f9d6f265da5b794f2189', '0', '0', '2019-11-29 00:00:00', '0', '2019-11-29 00:00:00', '0');
INSERT INTO `tb_mall_carousel` VALUES ('8', 'http://tianwc.oss-cn-beijing.aliyuncs.com/dev/20220328/1648435859997.png', 'http://tianwc.oss-cn-beijing.aliyuncs.com/dev/20220328/1648435859997.png', '0', '1', '2022-03-28 10:51:02', '0', '2022-03-28 10:54:26', '0');
INSERT INTO `tb_mall_carousel` VALUES ('9', 'http://tianwc.oss-cn-beijing.aliyuncs.com/dev/20220328/1648436214441.png', '##', '0', '1', '2022-03-28 10:57:15', '0', '2022-03-28 10:57:48', '0');
INSERT INTO `tb_mall_carousel` VALUES ('10', 'http://tianwc.oss-cn-beijing.aliyuncs.com/dev/20220328/1648436287375.png', '##', '0', '0', '2022-03-28 10:58:13', '0', '2022-03-28 10:58:13', '0');
