/*
Navicat MySQL Data Transfer
Source Server : wsk
Source Server Version : 50640
Source Host : localhost:3306
Source Database : c2c
Target Server Type : MYSQL
Target Server Version : 50640
File Encoding : 65001
Date: 2018-10-17 09:41:51
*/
DROP DATABASE IF EXISTS `c2c`;
CREATE DATABASE `c2c`;

USE `c2c`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for FirstClass
-- ----------------------------
DROP TABLE IF EXISTS `FirstClass`;
CREATE TABLE FirstClass (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(50) NOT NULL,
`modified` datetime DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FirstClass
-- ----------------------------
INSERT INTO FirstClass VALUES ('1', '数码电器', '2020-10-20 00:00:00');
INSERT INTO FirstClass VALUES ('2', '个人护理', '2020-10-20 00:00:00');
INSERT INTO FirstClass VALUES ('3', '生活家居', '2020-10-20 00:00:00');
INSERT INTO FirstClass VALUES ('4', '服饰箱包', '2020-10-20 00:00:00');
INSERT INTO FirstClass VALUES ('5', '教育', '2020-10-20 00:00:00');
INSERT INTO FirstClass VALUES ('6', '食品医药', '2020-10-20 00:00:00');
INSERT INTO FirstClass VALUES ('7', '其他', '2020-10-20 00:00:00');

-- ----------------------------
-- Table structure for SecondClass
-- ----------------------------
DROP TABLE IF EXISTS `SecondClass`;
CREATE TABLE `SecondClass` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(50) NOT NULL,
`modified` datetime DEFAULT NULL,
`aid` int(11) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of SecondClass
-- ----------------------------
INSERT INTO `SecondClass` VALUES ('1', '手机', null, '1');
INSERT INTO `SecondClass` VALUES ('2', '电脑', null, '1');
INSERT INTO `SecondClass` VALUES ('3', '摄影摄像', null, '1');
INSERT INTO `SecondClass` VALUES ('4', '电器', null, '1');
INSERT INTO `SecondClass` VALUES ('5', '其他', null, '1');
INSERT INTO `SecondClass` VALUES ('6', '护肤品', null, '2');
INSERT INTO `SecondClass` VALUES ('7', '彩妆', null, '2');
INSERT INTO `SecondClass` VALUES ('8', '清洁卫生', null, '2');
INSERT INTO `SecondClass` VALUES ('9', '其他', null, '2');
INSERT INTO `SecondClass` VALUES ('10', '床上用品', null, '3');
INSERT INTO `SecondClass` VALUES ('11', '花卉绿植', null, '3');
INSERT INTO `SecondClass` VALUES ('12', '布艺家饰', null, '3');
INSERT INTO `SecondClass` VALUES ('13', '收纳清洁', null, '3');
INSERT INTO `SecondClass` VALUES ('14', '其他', null, '3');
INSERT INTO `SecondClass` VALUES ('15', '男装', null, '4');
INSERT INTO `SecondClass` VALUES ('16', '女装', null, '4');
INSERT INTO `SecondClass` VALUES ('17', '配饰', null, '4');
INSERT INTO `SecondClass` VALUES ('18', '箱包', null, '4');
INSERT INTO `SecondClass` VALUES ('19', '其他', null, '4');
INSERT INTO `SecondClass` VALUES ('20', '图书', null, '5');
INSERT INTO `SecondClass` VALUES ('21', '文具', null, '5');
INSERT INTO `SecondClass` VALUES ('22', '艺术', null, '5');
INSERT INTO `SecondClass` VALUES ('23', '其他', null, '5');
INSERT INTO `SecondClass` VALUES ('24', '食品', null, '6');
INSERT INTO `SecondClass` VALUES ('25', '保健品', null, '6');
INSERT INTO `SecondClass` VALUES ('26', '医药用品', null, '6');
INSERT INTO `SecondClass` VALUES ('27', '其他', null, '6');
INSERT INTO `SecondClass` VALUES ('28', '其他', null, '7');

-- ----------------------------
-- Table structure for ShoppingCart
-- ----------------------------
DROP TABLE IF EXISTS `ShoppingCart`;
CREATE TABLE `ShoppingCart` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`modified` datetime DEFAULT NULL,
`sid` int(11) NOT NULL,
`uid` int(11) NOT NULL,
`quantity` int(11) NOT NULL,
`display` int(11) NOT NULL DEFAULT '1',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ShopMessage
-- ----------------------------
DROP TABLE IF EXISTS `ShopMessage`;
CREATE TABLE `ShopMessage` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`modified` datetime DEFAULT NULL,
`sid` int(11) NOT NULL,
`context` varchar(255) NOT NULL,
`display` int(11) NOT NULL DEFAULT '1',
`uid` int(11) NOT NULL DEFAULT '1',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for AllSales
-- ----------------------------
DROP TABLE IF EXISTS `AllSales`;
CREATE TABLE `AllSales` (

`uid` int(11) NOT NULL,
`image` varchar(255) DEFAULT NULL,
`thumbnails` varchar(255) DEFAULT NULL,
`id` int(11) NOT NULL AUTO_INCREMENT,
`modified` datetime DEFAULT NULL,
`name` varchar(50) NOT NULL,
`level` int(11) NOT NULL,
`remark` varchar(255) NOT NULL,
`price` decimal(10,2) NOT NULL,
`sort` int(11) NOT NULL,
`display` int(11) NOT NULL DEFAULT '1',
`quantity` int(11) NOT NULL,
`transaction` int(11) NOT NULL DEFAULT '1',
`sales` int(11) DEFAULT '0',
PRIMARY KEY (`id`),
KEY `index_uid` (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1098 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ThirdClass
-- ----------------------------
DROP TABLE IF EXISTS `ThirdClass`;
CREATE TABLE `ThirdClass` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(50) NOT NULL,
`modified` datetime DEFAULT NULL,
`cid` int(11) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ThirdClass
-- ----------------------------
INSERT INTO `ThirdClass` VALUES ('1', '整机', null, '1');
INSERT INTO `ThirdClass` VALUES ('2', '保护膜', null, '1');
INSERT INTO `ThirdClass` VALUES ('3', '手机壳', null, '1');
INSERT INTO `ThirdClass` VALUES ('4', '其他', null, '1');
INSERT INTO `ThirdClass` VALUES ('5', '笔记本', null, '2');
INSERT INTO `ThirdClass` VALUES ('6', '台式机', null, '2');
INSERT INTO `ThirdClass` VALUES ('7', '平板', null, '2');
INSERT INTO `ThirdClass` VALUES ('8', '鼠标键盘', null, '2');
INSERT INTO `ThirdClass` VALUES ('9', '显示器', null, '2');
INSERT INTO `ThirdClass` VALUES ('10', '主机', null, '2');
INSERT INTO `ThirdClass` VALUES ('11', '网络产品', null, '2');
INSERT INTO `ThirdClass` VALUES ('12', '其他', null, '2');
INSERT INTO `ThirdClass` VALUES ('13', '相机', null, '3');
INSERT INTO `ThirdClass` VALUES ('14', '镜头', null, '3');
INSERT INTO `ThirdClass` VALUES ('15', '摄影器材', null, '3');
INSERT INTO `ThirdClass` VALUES ('16', '其他', null, '3');
INSERT INTO `ThirdClass` VALUES ('17', '洗衣机', null, '4');
INSERT INTO `ThirdClass` VALUES ('18', '饮水机', null, '4');
INSERT INTO `ThirdClass` VALUES ('19', '电吹风', null, '4');
INSERT INTO `ThirdClass` VALUES ('20', '风扇', null, '4');
INSERT INTO `ThirdClass` VALUES ('21', '空调', null, '4');
INSERT INTO `ThirdClass` VALUES ('22', '其他', null, '4');
INSERT INTO `ThirdClass` VALUES ('23', '耳机', null, '5');
INSERT INTO `ThirdClass` VALUES ('24', '收音机', null, '5');
INSERT INTO `ThirdClass` VALUES ('25', '无人机', null, '5');
INSERT INTO `ThirdClass` VALUES ('26', '其他', null, '5');
INSERT INTO `ThirdClass` VALUES ('27', '补水', null, '6');
INSERT INTO `ThirdClass` VALUES ('28', '保湿', null, '6');
INSERT INTO `ThirdClass` VALUES ('29', '祛痘去角质', null, '6');
INSERT INTO `ThirdClass` VALUES ('30', '其他', null, '6');
INSERT INTO `ThirdClass` VALUES ('31', '美容', null, '7');
INSERT INTO `ThirdClass` VALUES ('32', '美发', null, '7');
INSERT INTO `ThirdClass` VALUES ('33', '美甲', null, '7');
INSERT INTO `ThirdClass` VALUES ('34', '其他', null, '7');
INSERT INTO `ThirdClass` VALUES ('35', '洗发护发', null, '8');
INSERT INTO `ThirdClass` VALUES ('36', '沐浴用品', null, '8');
INSERT INTO `ThirdClass` VALUES ('37', '口腔清洁', null, '8');
INSERT INTO `ThirdClass` VALUES ('38', '其他', null, '8');
INSERT INTO `ThirdClass` VALUES ('39', '其他', null, '9');
INSERT INTO `ThirdClass` VALUES ('40', '蚊帐', null, '10');
INSERT INTO `ThirdClass` VALUES ('41', '眼罩耳塞', null, '10');
INSERT INTO `ThirdClass` VALUES ('42', '床头灯', null, '10');
INSERT INTO `ThirdClass` VALUES ('43', '其他', null, '10');
INSERT INTO `ThirdClass` VALUES ('44', '盆栽盆景', null, '11');
INSERT INTO `ThirdClass` VALUES ('45', '多肉植物', null, '11');
INSERT INTO `ThirdClass` VALUES ('46', '水培植物', null, '11');
INSERT INTO `ThirdClass` VALUES ('47', '园艺工具', null, '11');
INSERT INTO `ThirdClass` VALUES ('48', '其他', null, '11');
INSERT INTO `ThirdClass` VALUES ('49', '窗帘', null, '12');
INSERT INTO `ThirdClass` VALUES ('50', '靠垫坐垫', null, '12');
INSERT INTO `ThirdClass` VALUES ('51', '抱枕', null, '12');
INSERT INTO `ThirdClass` VALUES ('52', '墙纸', null, '12');
INSERT INTO `ThirdClass` VALUES ('53', '摆件装饰', null, '12');
INSERT INTO `ThirdClass` VALUES ('54', '闹钟', null, '12');
INSERT INTO `ThirdClass` VALUES ('55', '其他', null, '12');
INSERT INTO `ThirdClass` VALUES ('56', '衣架', null, '13');
INSERT INTO `ThirdClass` VALUES ('57', '收纳盒&收纳袋', null, '13');
INSERT INTO `ThirdClass` VALUES ('58', '垃圾桶&垃圾袋', null, '13');
INSERT INTO `ThirdClass` VALUES ('59', '防尘', null, '13');
INSERT INTO `ThirdClass` VALUES ('60', '扫帚拖把', null, '13');
INSERT INTO `ThirdClass` VALUES ('61', '除湿防潮', null, '13');
INSERT INTO `ThirdClass` VALUES ('62', '除菌防虫', null, '13');
INSERT INTO `ThirdClass` VALUES ('63', '其他', null, '13');
INSERT INTO `ThirdClass` VALUES ('64', '其他', null, '14');
INSERT INTO `ThirdClass` VALUES ('65', '上装', null, '15');
INSERT INTO `ThirdClass` VALUES ('66', '下装', null, '15');
INSERT INTO `ThirdClass` VALUES ('67', '鞋帽', null, '15');
INSERT INTO `ThirdClass` VALUES ('68', '假发', null, '15');
INSERT INTO `ThirdClass` VALUES ('69', '其他', null, '15');
INSERT INTO `ThirdClass` VALUES ('70', '上装', null, '16');
INSERT INTO `ThirdClass` VALUES ('71', '下装', null, '16');
INSERT INTO `ThirdClass` VALUES ('72', '鞋帽', null, '16');
INSERT INTO `ThirdClass` VALUES ('73', '假发', null, '16');
INSERT INTO `ThirdClass` VALUES ('74', '其他', null, '16');
INSERT INTO `ThirdClass` VALUES ('75', '手表', null, '17');
INSERT INTO `ThirdClass` VALUES ('76', '耳环', null, '17');
INSERT INTO `ThirdClass` VALUES ('77', '手链项链', null, '17');
INSERT INTO `ThirdClass` VALUES ('78', '其他', null, '17');
INSERT INTO `ThirdClass` VALUES ('79', '男包', null, '18');
INSERT INTO `ThirdClass` VALUES ('80', '女包', null, '18');
INSERT INTO `ThirdClass` VALUES ('81', '背包', null, '18');
INSERT INTO `ThirdClass` VALUES ('82', '手提', null, '18');
INSERT INTO `ThirdClass` VALUES ('83', '行李箱', null, '18');
INSERT INTO `ThirdClass` VALUES ('84', '其他', null, '18');
INSERT INTO `ThirdClass` VALUES ('85', '其他', null, '19');
INSERT INTO `ThirdClass` VALUES ('86', '教材教辅', null, '20');
INSERT INTO `ThirdClass` VALUES ('87', '考试资料', null, '20');
INSERT INTO `ThirdClass` VALUES ('88', '畅销文学', null, '20');
INSERT INTO `ThirdClass` VALUES ('89', '期刊杂志', null, '20');
INSERT INTO `ThirdClass` VALUES ('90', '其他', null, '20');
INSERT INTO `ThirdClass` VALUES ('91', '笔', null, '21');
INSERT INTO `ThirdClass` VALUES ('92', '墨', null, '21');
INSERT INTO `ThirdClass` VALUES ('93', '纸', null, '21');
INSERT INTO `ThirdClass` VALUES ('94', '其他', null, '21');
INSERT INTO `ThirdClass` VALUES ('95', '乐器', null, '22');
INSERT INTO `ThirdClass` VALUES ('96', '绘画工具', null, '22');
INSERT INTO `ThirdClass` VALUES ('97', '其他', null, '22');
INSERT INTO `ThirdClass` VALUES ('98', '其他', null, '23');
INSERT INTO `ThirdClass` VALUES ('99', '零食', null, '24');
INSERT INTO `ThirdClass` VALUES ('100', '茶酒', null, '24');
INSERT INTO `ThirdClass` VALUES ('101', '乳品冲饮', null, '24');
INSERT INTO `ThirdClass` VALUES ('102', '水果', null, '24');
INSERT INTO `ThirdClass` VALUES ('103', '其他', null, '24');
INSERT INTO `ThirdClass` VALUES ('104', '维生素', null, '25');
INSERT INTO `ThirdClass` VALUES ('105', '安神助眠', null, '25');
INSERT INTO `ThirdClass` VALUES ('106', '缓解疲劳', null, '25');
INSERT INTO `ThirdClass` VALUES ('107', '提神醒脑', null, '25');
INSERT INTO `ThirdClass` VALUES ('108', '其他', null, '25');
INSERT INTO `ThirdClass` VALUES ('109', '伤口包扎', null, '26');
INSERT INTO `ThirdClass` VALUES ('110', '常备药物', null, '26');
INSERT INTO `ThirdClass` VALUES ('111', '其他', null, '26');
INSERT INTO `ThirdClass` VALUES ('112', '其他', null, '27');
INSERT INTO `ThirdClass` VALUES ('113', '其他', null, '28');

-- ----------------------------
-- Table structure for UserInformation
-- ----------------------------
DROP TABLE IF EXISTS `UserInformation`;
CREATE TABLE `UserInformation` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`modified` datetime DEFAULT NULL,
`username` varchar(50) NOT NULL,
`phone` char(11) NOT NULL,
`realname` varchar(50) DEFAULT NULL,
`clazz` varchar(50) DEFAULT NULL,
`sno` char(12) DEFAULT NULL,
`dormitory` varchar(50) DEFAULT NULL,
`gender` char(2) DEFAULT NULL,
`createtime` datetime DEFAULT NULL,
`avatar` varchar(200) DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `index_id` (`id`) USING BTREE,
KEY `selectByPhone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for UserPassword
-- ----------------------------
DROP TABLE IF EXISTS `UserPassword`;
CREATE TABLE `UserPassword` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`modified` datetime DEFAULT NULL,
`password` varchar(24) NOT NULL,
`uid` int(11) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for UserRelease
-- ----------------------------
DROP TABLE IF EXISTS `UserRelease`;
CREATE TABLE `UserRelease` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`modified` datetime DEFAULT NULL,
`display` int(11) NOT NULL DEFAULT '1',
`uid` int(11) NOT NULL,
`sid` int(11) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for UserWant
-- ----------------------------
DROP TABLE IF EXISTS `UserWant`;
CREATE TABLE `UserWant` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`modified` datetime DEFAULT NULL,
`display` int(11) NOT NULL DEFAULT '1',
`name` varchar(50) NOT NULL,
`sort` int(100) NOT NULL,
`quantity` int(11) NOT NULL,
`price` decimal(10,2) NOT NULL,
`remark` varchar(255) DEFAULT NULL,
`uid` int(11) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `Order`;
CREATE TABLE `OrderTable` (
`order_id` int(11) NOT NULL AUTO_INCREMENT,
`modified` datetime DEFAULT NULL,
`seller_id` int(11) NOT NULL,
`purchaser_id` int(11) NOT NULL,
`sales_id` int(11) NOT NULL,
`sales_name` varchar(50) NOT NULL,
`quantity` int(11) NOT NULL,
`price` decimal(10,2) NOT NULL,
`purchaser_name` varchar(50) NOT NULL,
`address` varchar(255) NOT NULL,
`contact_info` varchar(255) NOT NULL,
`state` tinyint(1) NOT NULL DEFAULT '0',
PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8;
