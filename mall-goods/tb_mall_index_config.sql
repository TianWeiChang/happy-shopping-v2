/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-shopping

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-04-14 21:58:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_mall_index_config`
-- ----------------------------
DROP TABLE IF EXISTS `tb_mall_index_config`;
CREATE TABLE `tb_mall_index_config` (
  `config_id` bigint NOT NULL AUTO_INCREMENT COMMENT '首页配置项主键id',
  `config_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '显示字符(配置搜索时不可为空，其他可为空)',
  `config_type` tinyint NOT NULL DEFAULT '0' COMMENT '1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐',
  `goods_id` bigint NOT NULL DEFAULT '0' COMMENT '商品id 默认为0',
  `redirect_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '##' COMMENT '点击后的跳转地址(默认不跳转)',
  `config_rank` int NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int NOT NULL DEFAULT '0' COMMENT '创建者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
  `update_user` int DEFAULT '0' COMMENT '修改者id',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='首页配置表';

-- ----------------------------
-- Records of tb_mall_index_config
-- ----------------------------
INSERT INTO `tb_mall_index_config` VALUES ('1', '热销商品 iPhone XR', '3', '10284', '##', '10', '0', '2019-09-18 17:04:56', '0', '2019-09-18 17:04:56', '0');
INSERT INTO `tb_mall_index_config` VALUES ('2', '热销商品 华为 Mate20', '3', '10779', '##', '100', '0', '2019-09-18 17:05:27', '0', '2019-09-18 17:05:27', '0');
INSERT INTO `tb_mall_index_config` VALUES ('3', '热销商品 荣耀8X', '3', '10700', '##', '300', '0', '2019-09-18 17:08:02', '0', '2019-09-18 17:08:02', '0');
INSERT INTO `tb_mall_index_config` VALUES ('4', '热销商品 Apple AirPods', '3', '10159', '##', '101', '0', '2019-09-18 17:08:56', '0', '2019-09-18 17:08:56', '0');
INSERT INTO `tb_mall_index_config` VALUES ('5', '新品上线 Macbook Pro', '4', '10269', '##', '100', '0', '2019-09-18 17:10:36', '0', '2019-09-18 17:10:36', '0');
INSERT INTO `tb_mall_index_config` VALUES ('6', '新品上线 荣耀 9X Pro', '4', '10755', '##', '100', '0', '2019-09-18 17:11:05', '0', '2019-09-18 17:11:05', '0');
INSERT INTO `tb_mall_index_config` VALUES ('7', '新品上线 iPhone 11', '4', '10283', '##', '102', '0', '2019-09-18 17:11:44', '0', '2019-09-18 17:11:44', '0');
INSERT INTO `tb_mall_index_config` VALUES ('8', '新品上线 iPhone 11 Pro', '4', '10320', '##', '101', '0', '2019-09-18 17:11:58', '0', '2019-09-18 17:11:58', '0');
INSERT INTO `tb_mall_index_config` VALUES ('9', '新品上线 华为无线耳机', '4', '10186', '##', '100', '0', '2019-09-18 17:12:29', '0', '2019-09-18 17:12:29', '0');
INSERT INTO `tb_mall_index_config` VALUES ('10', '纪梵希高定香榭天鹅绒唇膏', '5', '10233', '##', '98', '0', '2019-09-18 17:47:23', '0', '2019-09-18 17:47:23', '0');
INSERT INTO `tb_mall_index_config` VALUES ('11', 'MAC 磨砂系列', '5', '10237', '##', '100', '0', '2019-09-18 17:47:44', '0', '2019-09-18 17:47:44', '0');
INSERT INTO `tb_mall_index_config` VALUES ('12', '索尼 WH-1000XM3', '5', '10195', '##', '102', '0', '2019-09-18 17:48:00', '0', '2019-09-18 17:48:00', '0');
INSERT INTO `tb_mall_index_config` VALUES ('13', 'Apple AirPods', '5', '10180', '##', '101', '0', '2019-09-18 17:49:11', '0', '2019-09-18 17:49:11', '0');
INSERT INTO `tb_mall_index_config` VALUES ('14', '小米 Redmi AirDots', '5', '10160', '##', '100', '0', '2019-09-18 17:49:28', '0', '2019-09-18 17:49:28', '0');
INSERT INTO `tb_mall_index_config` VALUES ('15', '2019 MacBookAir 13', '5', '10254', '##', '100', '0', '2019-09-18 17:50:18', '0', '2019-09-18 17:50:18', '0');
INSERT INTO `tb_mall_index_config` VALUES ('16', '女式粗棉线条纹长袖T恤', '5', '10158', '##', '99', '0', '2019-09-18 17:52:03', '0', '2019-09-18 17:52:03', '0');
INSERT INTO `tb_mall_index_config` VALUES ('17', '塑料浴室座椅', '5', '10154', '##', '100', '0', '2019-09-18 17:52:19', '0', '2019-09-18 17:52:19', '0');
INSERT INTO `tb_mall_index_config` VALUES ('18', '靠垫', '5', '10147', '##', '101', '0', '2019-09-18 17:52:50', '0', '2019-09-18 17:52:50', '0');
INSERT INTO `tb_mall_index_config` VALUES ('19', '小型超声波香薰机', '5', '10113', '##', '100', '0', '2019-09-18 17:54:07', '0', '2019-09-18 17:54:07', '0');
INSERT INTO `tb_mall_index_config` VALUES ('20', '11', '5', '1', '##', '0', '1', '2019-09-19 08:31:11', '0', '2019-09-19 08:31:20', '0');
INSERT INTO `tb_mall_index_config` VALUES ('21', '热销商品 华为 P30', '3', '10742', '##', '200', '0', '2019-09-19 23:23:38', '0', '2019-09-19 23:23:38', '0');
INSERT INTO `tb_mall_index_config` VALUES ('22', '新品上线 华为Mate30 Pro', '4', '10893', '##', '200', '0', '2019-09-19 23:26:05', '0', '2019-09-19 23:26:05', '0');
INSERT INTO `tb_mall_index_config` VALUES ('23', '新品上线 华为 Mate 30', '4', '10895', '##', '199', '0', '2019-09-19 23:26:32', '0', '2019-09-19 23:26:32', '0');
INSERT INTO `tb_mall_index_config` VALUES ('24', '华为 Mate 30 Pro', '5', '10894', '##', '101', '0', '2019-09-19 23:27:00', '0', '2019-09-19 23:27:00', '0');
