/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : happy-shopping

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2022-04-14 21:58:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_mall_goods_category`
-- ----------------------------
DROP TABLE IF EXISTS `tb_mall_goods_category`;
CREATE TABLE `tb_mall_goods_category` (
  `category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `category_level` tinyint NOT NULL DEFAULT '0' COMMENT '分类级别(1-一级分类 2-二级分类 3-三级分类)',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父分类id',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `category_rank` int NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int NOT NULL DEFAULT '0' COMMENT '创建者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` int DEFAULT '0' COMMENT '修改者id',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='物品分类表';

-- ----------------------------
-- Records of tb_mall_goods_category
-- ----------------------------
INSERT INTO `tb_mall_goods_category` VALUES ('15', '1', '0', '家电 数码 手机', '100', '0', '2019-09-11 18:45:40', '0', '2019-09-11 18:45:40', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('16', '1', '0', '女装 男装 穿搭', '99', '0', '2019-09-11 18:46:07', '0', '2019-09-11 18:46:07', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('17', '2', '15', '家电', '10', '0', '2019-09-11 18:46:32', '0', '2019-09-11 18:46:32', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('18', '2', '15', '数码', '9', '0', '2019-09-11 18:46:43', '0', '2019-09-11 18:46:43', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('19', '2', '15', '手机', '8', '0', '2019-09-11 18:46:52', '0', '2019-09-11 18:46:52', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('20', '3', '17', '生活电器', '0', '0', '2019-09-11 18:47:38', '0', '2019-09-11 18:47:38', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('21', '3', '17', '厨房电器', '0', '0', '2019-09-11 18:47:49', '0', '2019-09-11 18:47:49', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('22', '3', '17', '扫地机器人', '0', '0', '2019-09-11 18:47:58', '0', '2019-09-11 18:47:58', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('23', '3', '17', '吸尘器', '0', '0', '2019-09-11 18:48:06', '0', '2019-09-11 18:48:06', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('24', '3', '17', '取暖器', '0', '0', '2019-09-11 18:48:12', '0', '2019-09-11 18:48:12', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('25', '3', '17', '豆浆机', '0', '0', '2019-09-11 18:48:26', '0', '2019-09-11 18:48:26', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('26', '3', '17', '暖风机', '0', '0', '2019-09-11 18:48:40', '0', '2019-09-11 18:48:40', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('27', '3', '17', '加湿器', '0', '0', '2019-09-11 18:48:50', '0', '2019-09-11 18:48:50', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('28', '3', '17', '蓝牙音箱', '0', '0', '2019-09-11 18:48:57', '0', '2019-09-11 18:48:57', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('29', '3', '17', '烤箱', '0', '0', '2019-09-11 18:49:09', '0', '2019-09-11 18:49:09', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('30', '3', '17', '卷发器', '0', '0', '2019-09-11 18:49:19', '0', '2019-09-11 18:49:19', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('31', '3', '17', '空气净化器', '0', '0', '2019-09-11 18:49:30', '0', '2019-09-11 18:49:30', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('32', '3', '18', '游戏主机', '0', '0', '2019-09-11 18:49:50', '0', '2019-09-11 18:49:50', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('33', '3', '18', '数码精选', '0', '0', '2019-09-11 18:49:55', '0', '2019-09-11 18:49:55', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('34', '3', '18', '平板电脑', '0', '0', '2019-09-11 18:50:08', '0', '2019-09-11 18:50:08', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('35', '3', '18', '苹果 Apple', '0', '0', '2019-09-11 18:50:24', '0', '2019-09-11 18:50:24', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('36', '3', '18', '电脑主机', '0', '0', '2019-09-11 18:50:36', '0', '2019-09-11 18:50:36', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('37', '3', '18', '数码相机', '0', '0', '2019-09-11 18:50:57', '0', '2019-09-11 18:50:57', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('38', '3', '18', '电玩动漫', '0', '0', '2019-09-11 18:52:15', '0', '2019-09-11 18:52:15', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('39', '3', '18', '单反相机', '0', '0', '2019-09-11 18:52:26', '0', '2019-09-11 18:52:26', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('40', '3', '18', '键盘鼠标', '0', '0', '2019-09-11 18:52:46', '0', '2019-09-11 18:52:46', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('41', '3', '18', '无人机', '0', '0', '2019-09-11 18:53:01', '0', '2019-09-11 18:53:01', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('42', '3', '18', '二手电脑', '0', '0', '2019-09-11 18:53:08', '0', '2019-09-11 18:53:08', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('43', '3', '18', '二手手机', '0', '0', '2019-09-11 18:53:14', '0', '2019-09-11 18:53:14', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('44', '3', '19', 'iPhone 11', '89', '0', '2019-09-11 18:53:49', '0', '2019-09-11 18:54:38', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('45', '3', '19', '荣耀手机', '99', '0', '2019-09-11 18:53:59', '0', '2019-09-18 13:40:59', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('46', '3', '19', '华为手机', '98', '0', '2019-09-11 18:54:20', '0', '2019-09-18 13:40:51', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('47', '3', '19', 'iPhone', '88', '0', '2019-09-11 18:54:49', '0', '2019-09-18 13:40:32', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('48', '3', '19', '华为 Mate 20', '79', '0', '2019-09-11 18:55:03', '0', '2019-09-11 18:55:13', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('49', '3', '19', '华为 P30', '97', '0', '2019-09-11 18:55:22', '0', '2019-09-11 18:55:22', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('50', '3', '19', '华为 P30 Pro', '0', '1', '2019-09-11 18:55:32', '0', '2019-09-11 18:55:32', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('51', '3', '19', '小米手机', '0', '0', '2019-09-11 18:55:52', '0', '2019-09-11 18:55:52', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('52', '3', '19', '红米', '0', '0', '2019-09-11 18:55:58', '0', '2019-09-11 18:55:58', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('53', '3', '19', 'OPPO', '0', '0', '2019-09-11 18:56:06', '0', '2019-09-11 18:56:06', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('54', '3', '19', '一加', '0', '0', '2019-09-11 18:56:12', '0', '2019-09-11 18:56:12', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('55', '3', '19', '小米 MIX', '0', '0', '2019-09-11 18:56:37', '0', '2019-09-11 18:56:37', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('56', '3', '19', 'Reno', '0', '0', '2019-09-11 18:56:49', '0', '2019-09-11 18:56:49', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('57', '3', '19', 'vivo', '0', '0', '2019-09-11 18:57:01', '0', '2019-09-11 18:57:01', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('58', '3', '19', '手机以旧换新', '0', '0', '2019-09-11 18:57:09', '0', '2019-09-11 18:57:09', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('59', '1', '0', '运动 户外 乐器', '97', '0', '2019-09-12 00:08:46', '0', '2019-09-12 00:08:46', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('60', '1', '0', '游戏 动漫 影视', '96', '0', '2019-09-12 00:09:00', '0', '2019-09-12 00:09:00', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('61', '1', '0', '家具 家饰 家纺', '98', '0', '2019-09-12 00:09:27', '0', '2019-09-12 00:09:27', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('62', '1', '0', '美妆 清洁 宠物', '94', '0', '2019-09-12 00:09:51', '0', '2019-09-17 18:22:34', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('63', '1', '0', '工具 装修 建材', '93', '0', '2019-09-12 00:10:07', '0', '2019-09-12 00:10:07', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('64', '1', '0', '珠宝 金饰 眼镜', '92', '0', '2019-09-12 00:10:35', '0', '2019-09-12 00:16:30', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('65', '1', '0', '玩具 孕产 用品', '0', '0', '2019-09-12 00:11:17', '0', '2019-09-12 00:11:17', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('66', '1', '0', '鞋靴 箱包 配件', '91', '0', '2019-09-12 00:11:30', '0', '2019-09-12 00:11:30', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('67', '2', '16', '女装', '10', '0', '2019-09-12 00:15:19', '0', '2019-09-12 00:15:19', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('68', '2', '16', '男装', '9', '0', '2019-09-12 00:15:28', '0', '2019-09-12 00:15:28', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('69', '2', '16', '穿搭', '8', '0', '2019-09-12 00:15:35', '0', '2019-09-12 00:15:35', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('70', '2', '61', '家具', '10', '0', '2019-09-12 00:20:22', '0', '2019-09-12 00:20:22', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('71', '2', '61', '家饰', '9', '0', '2019-09-12 00:20:29', '0', '2019-09-12 00:20:29', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('72', '2', '61', '家纺', '8', '0', '2019-09-12 00:20:35', '0', '2019-09-12 00:20:35', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('73', '2', '59', '运动', '10', '0', '2019-09-12 00:20:49', '0', '2019-09-12 00:20:49', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('74', '2', '59', '户外', '9', '0', '2019-09-12 00:20:58', '0', '2019-09-12 00:20:58', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('75', '2', '59', '乐器', '8', '0', '2019-09-12 00:21:05', '0', '2019-09-12 00:21:05', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('76', '3', '67', '外套', '10', '0', '2019-09-12 00:21:55', '0', '2019-09-12 00:21:55', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('77', '3', '70', '沙发', '10', '0', '2019-09-12 00:22:21', '0', '2019-09-12 00:22:21', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('78', '3', '73', '跑鞋', '10', '0', '2019-09-12 00:22:42', '0', '2019-09-12 00:22:42', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('79', '2', '60', '游戏', '10', '0', '2019-09-12 00:23:13', '0', '2019-09-12 00:23:13', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('80', '2', '60', '动漫', '9', '0', '2019-09-12 00:23:21', '0', '2019-09-12 00:23:21', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('81', '2', '60', '影视', '8', '0', '2019-09-12 00:23:27', '0', '2019-09-12 00:23:27', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('82', '3', '79', 'LOL', '10', '0', '2019-09-12 00:23:44', '0', '2019-09-12 00:23:44', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('83', '2', '62', '美妆', '10', '0', '2019-09-12 00:23:58', '0', '2019-09-17 18:22:44', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('84', '2', '62', '宠物', '9', '0', '2019-09-12 00:24:07', '0', '2019-09-12 00:24:07', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('85', '2', '62', '清洁', '8', '0', '2019-09-12 00:24:15', '0', '2019-09-17 18:22:51', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('86', '3', '83', '口红', '10', '0', '2019-09-12 00:24:38', '0', '2019-09-17 18:23:08', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('87', '2', '63', '工具', '10', '0', '2019-09-12 00:24:56', '0', '2019-09-12 00:24:56', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('88', '2', '63', '装修', '9', '0', '2019-09-12 00:25:05', '0', '2019-09-12 00:25:05', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('89', '2', '63', '建材', '8', '0', '2019-09-12 00:25:12', '0', '2019-09-12 00:25:12', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('90', '3', '87', '转换器', '10', '0', '2019-09-12 00:25:45', '0', '2019-09-12 00:25:45', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('91', '2', '64', '珠宝', '10', '0', '2019-09-12 00:26:10', '0', '2019-09-12 00:26:10', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('92', '2', '64', '金饰', '9', '0', '2019-09-12 00:26:18', '0', '2019-09-12 00:26:18', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('93', '2', '64', '眼镜', '8', '0', '2019-09-12 00:26:25', '0', '2019-09-12 00:26:25', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('94', '3', '91', '钻石', '10', '0', '2019-09-12 00:26:40', '0', '2019-09-12 00:26:40', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('95', '2', '66', '鞋靴', '10', '0', '2019-09-12 00:27:09', '0', '2019-09-12 00:27:09', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('96', '2', '66', '箱包', '9', '0', '2019-09-12 00:27:17', '0', '2019-09-12 00:27:17', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('97', '2', '66', '配件', '8', '0', '2019-09-12 00:27:23', '0', '2019-09-12 00:27:23', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('98', '3', '95', '休闲鞋', '10', '0', '2019-09-12 00:27:48', '0', '2019-09-12 00:27:48', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('99', '3', '83', '气垫', '0', '0', '2019-09-17 18:24:23', '0', '2019-09-17 18:24:23', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('100', '3', '83', '美白', '0', '0', '2019-09-17 18:24:36', '0', '2019-09-17 18:24:36', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('101', '3', '83', '隔离霜', '0', '0', '2019-09-17 18:27:04', '0', '2019-09-17 18:27:04', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('102', '3', '83', '粉底', '0', '0', '2019-09-17 18:27:19', '0', '2019-09-17 18:27:19', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('103', '3', '83', '腮红', '0', '0', '2019-09-17 18:27:24', '0', '2019-09-17 18:27:24', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('104', '3', '83', '睫毛膏', '0', '0', '2019-09-17 18:27:47', '0', '2019-09-17 18:27:47', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('105', '3', '83', '香水', '0', '0', '2019-09-17 18:28:16', '0', '2019-09-17 18:28:16', '0');
INSERT INTO `tb_mall_goods_category` VALUES ('106', '3', '83', '面膜', '0', '0', '2019-09-17 18:28:21', '0', '2019-09-17 18:28:21', '0');
