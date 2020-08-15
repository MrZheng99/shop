/*
Navicat MySQL Data Transfer

Source Server         : my
Source Server Version : 50730
Source Host           : 120.26.184.107:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-08-15 11:13:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `uid` int(11) NOT NULL,
  `status` char(1) NOT NULL,
  `flag` char(1) NOT NULL,
  PRIMARY KEY (`aid`),
  KEY `fk_address_uid` (`uid`),
  CONSTRAINT `fk_address_uid` FOREIGN KEY (`uid`) REFERENCES `tb_userinfo` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_address
-- ----------------------------
INSERT INTO `tb_address` VALUES ('106', '北京市市辖区东城区', '108', '1', '0');
INSERT INTO `tb_address` VALUES ('108', '天津市市辖区河西区河西街道', '108', '1', '1');
INSERT INTO `tb_address` VALUES ('109', '北京市市辖区东城区', '181', '1', '1');
INSERT INTO `tb_address` VALUES ('110', '河北省秦皇岛市北戴河区学校附近', '108', '1', '0');

-- ----------------------------
-- Table structure for tb_admininfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_admininfo`;
CREATE TABLE `tb_admininfo` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `status` char(1) NOT NULL,
  `type` char(1) NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_admininfo
-- ----------------------------
INSERT INTO `tb_admininfo` VALUES ('101', 'e10adc3949ba59abbe56e057f20f883e', 'adm', '18789868985', '1', '0');
INSERT INTO `tb_admininfo` VALUES ('103', 'e10adc3949ba59abbe56e057f20f883e', 'su', '18499998886', '1', '1');
INSERT INTO `tb_admininfo` VALUES ('104', 'c8837b23ff8aaa8a2dde915473ce0991', 'adm1', '18789868986', '0', '0');

-- ----------------------------
-- Table structure for tb_goodsassession
-- ----------------------------
DROP TABLE IF EXISTS `tb_goodsassession`;
CREATE TABLE `tb_goodsassession` (
  `asid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `gid` int(11) NOT NULL,
  `oid` int(11) NOT NULL,
  `assessiondetails` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`asid`),
  KEY `tb_goodsassession_uid` (`uid`),
  KEY `tb_goodsassession_gid` (`gid`),
  KEY `fk_goodsassession_oid` (`oid`),
  CONSTRAINT `fk_goodsassession_gid` FOREIGN KEY (`gid`) REFERENCES `tb_goodsinfo` (`gid`),
  CONSTRAINT `fk_goodsassession_oid` FOREIGN KEY (`oid`) REFERENCES `tb_orderinfo` (`oid`),
  CONSTRAINT `fk_goodsassession_uid` FOREIGN KEY (`uid`) REFERENCES `tb_userinfo` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_goodsassession
-- ----------------------------
INSERT INTO `tb_goodsassession` VALUES ('108', '108', '103', '169', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈hkudhifhkfnuihkufh,kfs', '2020-08-01 09:57:20', '1');
INSERT INTO `tb_goodsassession` VALUES ('109', '108', '138', '169', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈hkudhifhkfnuihkufh,kfs', '2020-08-01 09:57:20', '1');
INSERT INTO `tb_goodsassession` VALUES ('110', '108', '2316127', '169', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈hkudhifhkfnuihkufh,kfs', '2020-08-01 09:57:20', '1');
INSERT INTO `tb_goodsassession` VALUES ('111', '108', '102', '172', '好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊', '2020-08-09 21:15:18', '1');
INSERT INTO `tb_goodsassession` VALUES ('112', '108', '102', '616', '好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊', '2020-08-09 21:16:58', '1');
INSERT INTO `tb_goodsassession` VALUES ('113', '108', '101', '616', '好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊好啊', '2020-08-09 21:16:58', '1');

-- ----------------------------
-- Table structure for tb_goodsinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_goodsinfo`;
CREATE TABLE `tb_goodsinfo` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `goodsname` varchar(255) NOT NULL,
  `goodsprice` float(10,2) NOT NULL,
  `store` int(11) NOT NULL,
  `status` char(1) NOT NULL,
  `goodsdescription` text NOT NULL,
  `goodstype` int(11) NOT NULL,
  `images` tinytext NOT NULL,
  `hot` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`gid`),
  KEY `fk_goodsinfo_goodstype` (`goodstype`),
  CONSTRAINT `fk_goodsinfo_goodstype` FOREIGN KEY (`goodstype`) REFERENCES `tb_goodstype` (`gtid`)
) ENGINE=InnoDB AUTO_INCREMENT=2316131 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_goodsinfo
-- ----------------------------
INSERT INTO `tb_goodsinfo` VALUES ('101', '时尚短袖女装上衣1', '205.00', '44', '1', '限量版1', '101', 'images/goods/clo_1.jpg,images/goods/clo_21.jpg,images/goods/clo_22.jpg,images/goods/clo_23.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('102', '时尚短袖女装上衣2', '300.00', '44', '1', '限量版', '101', 'images/goods/clo_2.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('103', '时尚短袖女装上衣3', '200.00', '49', '1', '限量版', '101', 'images/goods/clo_3.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('104', '时尚短袖女装上衣4', '300.00', '50', '1', '限量版', '101', 'images/goods/clo_4.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('105', '时尚短袖女装上衣5', '200.00', '50', '1', '限量版', '101', 'images/goods/clo_5.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('106', '时尚短袖女装上衣6', '400.00', '50', '1', '限量版', '101', 'images/goods/clo_6.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('107', '时尚短袖男装上衣1', '100.00', '49', '1', '限量版', '102', 'images/goods/clo_7.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('108', '时尚短袖男装上衣2', '200.00', '46', '1', '限量版', '102', 'images/goods/clo_8.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('109', '时尚短袖男装上衣3', '100.00', '12', '1', '限量版', '102', 'images/goods/clo_9.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('110', '时尚短袖男装上衣4', '300.00', '49', '1', '限量版', '102', 'images/goods/clo_10.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('111', '时尚夏季女鞋1', '100.00', '50', '1', '限量版', '103', 'images/goods/shoes_1.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('112', '时尚夏季女鞋2', '200.00', '49', '1', '限量版', '103', 'images/goods/shoes_2.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('113', '时尚夏季女鞋3', '400.00', '14', '1', '限量版', '103', 'images/goods/shoes_3.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('114', '时尚夏季女鞋4', '400.00', '50', '1', '限量版', '103', 'images/goods/shoes_4.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('115', '时尚夏季女鞋5', '400.00', '50', '1', '限量版', '103', 'images/goods/shoes_5.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('116', '商务男士皮鞋1', '500.00', '50', '1', '限量版', '104', 'images/goods/shoes_6.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('117', '商务男士皮鞋2', '500.00', '50', '1', '限量版', '104', 'images/goods/shoes_7.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('118', '商务男士皮鞋3', '500.00', '49', '1', '限量版', '104', 'images/goods/shoes_8.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('119', '商务男士皮鞋4', '500.00', '50', '1', '限量版', '104', 'images/goods/shoes_9.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('120', '商务男士皮鞋5', '500.00', '50', '1', '限量版', '104', 'images/goods/shoes_10.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('126', '时尚短袖女装上衣7', '800.00', '50', '1', '限量版', '101', 'images/goods/clo_11.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('127', '时尚短袖女装上衣8', '700.00', '45', '1', '限量版', '101', 'images/goods/clo_12.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('128', '时尚短袖女装上衣9', '600.00', '50', '1', '限量版', '101', 'images/goods/clo_13.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('129', '时尚短袖女装上衣10', '500.00', '50', '1', '限量版', '101', 'images/goods/clo_14.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('130', '时尚短袖男装上衣5', '300.00', '50', '1', '限量版', '102', 'images/goods/clo_15.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('131', '时尚短袖男装上衣6', '300.00', '50', '1', '限量版', '102', 'images/goods/clo_16.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('132', '时尚短袖男装上衣7', '300.00', '50', '1', '限量版', '102', 'images/goods/clo_17.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('133', '时尚短袖男装上衣8', '300.00', '50', '1', '限量版', '102', 'images/goods/clo_18.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('134', '时尚短袖男装上衣9', '300.00', '50', '1', '限量版', '102', 'images/goods/clo_19.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('135', '时尚短袖男装上衣10', '300.00', '50', '1', '限量版', '102', 'images/goods/clo_20.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('136', '时尚夏季女鞋6', '700.00', '50', '1', '限量版', '103', 'images/goods/shoes_11.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('137', '时尚夏季女鞋7', '300.00', '50', '1', '限量版', '103', 'images/goods/shoes_12.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('138', '时尚夏季女鞋8', '200.00', '49', '1', '限量版', '103', 'images/goods/shoes_13.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('139', '时尚夏季女鞋9', '400.00', '50', '1', '限量版', '103', 'images/goods/shoes_14.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('140', '时尚夏季女鞋10', '800.00', '47', '1', '限量版', '103', 'images/goods/shoes_15.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('141', '商务男士皮鞋6', '500.00', '50', '1', '限量版', '104', 'images/goods/shoes_16.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('142', '商务男士皮鞋7', '500.00', '50', '1', '限量版', '104', 'images/goods/shoes_17.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('143', '商务男士皮鞋8', '500.00', '50', '1', '限量版', '104', 'images/goods/shoes_18.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('144', '商务男士皮鞋9', '500.00', '50', '1', '限量版', '104', 'images/goods/shoes_19.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('145', '商务男士皮鞋10', '500.00', '50', '0', '限量版', '104', 'images/goods/shoes_20.jpg', '1');
INSERT INTO `tb_goodsinfo` VALUES ('2316126', '夏季短袖1', '105.00', '51', '1', '<p style=\"text-align:center\"><img alt=\"\" src=\"..\\..\\images\\goods\\_1597453700505.jpg\" style=\"height:460px; width:500px\" /></p>\r\n\r\n<p style=\"text-align:center\"> </p>\r\n\r\n<p style=\"text-align:center\">夏季短袖好啊</p>\r\n', '102', 'images/goods/_1597453714375.jpg,images/goods/_1597455848636.jpg,images/goods/_1597455994743.jpg', '0');
INSERT INTO `tb_goodsinfo` VALUES ('2316127', '美女短袖', '200.00', '47', '0', '<p style=\"text-align:center\"><img alt=\"\" src=\"..\\..\\images\\goods\\_1597453623832.jpg\" style=\"height:500px; width:500px\" /></p>\r\n\r\n<p style=\"text-align:center\">好衣服呀</p>\r\n\r\n<p style=\"text-align:center\"> </p>\r\n\r\n<p style=\"text-align:center\"> </p>\r\n', '101', 'images/goods/_1597453652087.jpg', '1');

-- ----------------------------
-- Table structure for tb_goodstype
-- ----------------------------
DROP TABLE IF EXISTS `tb_goodstype`;
CREATE TABLE `tb_goodstype` (
  `gtid` int(11) NOT NULL AUTO_INCREMENT,
  `typename` varchar(255) NOT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`gtid`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_goodstype
-- ----------------------------
INSERT INTO `tb_goodstype` VALUES ('101', '女装', '1');
INSERT INTO `tb_goodstype` VALUES ('102', '男装', '1');
INSERT INTO `tb_goodstype` VALUES ('103', '女鞋', '1');
INSERT INTO `tb_goodstype` VALUES ('104', '男鞋', '1');

-- ----------------------------
-- Table structure for tb_logisticsinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_logisticsinfo`;
CREATE TABLE `tb_logisticsinfo` (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `oid` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `details` varchar(255) NOT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_logisticsinfo
-- ----------------------------

-- ----------------------------
-- Table structure for tb_orderdetails
-- ----------------------------
DROP TABLE IF EXISTS `tb_orderdetails`;
CREATE TABLE `tb_orderdetails` (
  `odid` int(11) NOT NULL AUTO_INCREMENT,
  `price` float(8,2) NOT NULL,
  `number` int(11) NOT NULL,
  `gid` int(11) NOT NULL,
  `oid` int(11) NOT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`odid`),
  KEY `fk_orderdetails_oid` (`oid`),
  KEY `fk_orderdetails_gid` (`gid`),
  CONSTRAINT `fk_orderdetails_gid` FOREIGN KEY (`gid`) REFERENCES `tb_goodsinfo` (`gid`),
  CONSTRAINT `fk_orderdetails_oid` FOREIGN KEY (`oid`) REFERENCES `tb_orderinfo` (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_orderdetails
-- ----------------------------
INSERT INTO `tb_orderdetails` VALUES ('183', '200.00', '1', '103', '169', '1');
INSERT INTO `tb_orderdetails` VALUES ('184', '200.00', '1', '138', '169', '1');
INSERT INTO `tb_orderdetails` VALUES ('185', '200.00', '2', '2316127', '169', '1');
INSERT INTO `tb_orderdetails` VALUES ('191', '300.00', '1', '102', '172', '1');
INSERT INTO `tb_orderdetails` VALUES ('192', '300.00', '1', '102', '179', '1');
INSERT INTO `tb_orderdetails` VALUES ('196', '300.00', '1', '102', '616', '1');
INSERT INTO `tb_orderdetails` VALUES ('197', '200.00', '1', '101', '616', '1');
INSERT INTO `tb_orderdetails` VALUES ('198', '700.00', '1', '127', '322184322', '1');
INSERT INTO `tb_orderdetails` VALUES ('199', '300.00', '1', '102', '221927770', '1');
INSERT INTO `tb_orderdetails` VALUES ('200', '700.00', '1', '127', '9457161', '1');
INSERT INTO `tb_orderdetails` VALUES ('203', '205.00', '1', '101', '151136232', '1');
INSERT INTO `tb_orderdetails` VALUES ('204', '200.00', '1', '108', '151136232', '1');
INSERT INTO `tb_orderdetails` VALUES ('205', '205.00', '1', '101', '194413274', '1');
INSERT INTO `tb_orderdetails` VALUES ('206', '205.00', '1', '101', '194413460', '1');
INSERT INTO `tb_orderdetails` VALUES ('207', '205.00', '1', '101', '194719305', '1');
INSERT INTO `tb_orderdetails` VALUES ('208', '205.00', '1', '101', '194719469', '1');
INSERT INTO `tb_orderdetails` VALUES ('209', '300.00', '1', '102', '19537482', '1');
INSERT INTO `tb_orderdetails` VALUES ('210', '100.00', '1', '107', '164955922', '1');
INSERT INTO `tb_orderdetails` VALUES ('211', '300.00', '3', '102', '20519248', '1');
INSERT INTO `tb_orderdetails` VALUES ('212', '205.00', '2', '101', '20519248', '1');
INSERT INTO `tb_orderdetails` VALUES ('213', '700.00', '1', '127', '20519248', '1');
INSERT INTO `tb_orderdetails` VALUES ('214', '100.00', '1', '107', '20519248', '1');
INSERT INTO `tb_orderdetails` VALUES ('215', '200.00', '2', '108', '20519248', '1');
INSERT INTO `tb_orderdetails` VALUES ('216', '100.00', '3', '109', '20519248', '1');
INSERT INTO `tb_orderdetails` VALUES ('217', '400.00', '1', '113', '211240768', '1');
INSERT INTO `tb_orderdetails` VALUES ('218', '200.00', '1', '112', '211240768', '1');
INSERT INTO `tb_orderdetails` VALUES ('219', '500.00', '1', '118', '211240768', '1');
INSERT INTO `tb_orderdetails` VALUES ('220', '200.00', '1', '108', '211240768', '1');

-- ----------------------------
-- Table structure for tb_orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_orderinfo`;
CREATE TABLE `tb_orderinfo` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `aid` int(11) DEFAULT NULL,
  `date` datetime NOT NULL,
  `orderprogress` varchar(12) NOT NULL,
  `amount` varchar(10) NOT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `fk_orderinfo_uid` (`uid`),
  KEY `fk_orderinfo_aid` (`aid`),
  CONSTRAINT `fk_orderinfo_aid` FOREIGN KEY (`aid`) REFERENCES `tb_address` (`aid`),
  CONSTRAINT `fk_orderinfo_uid` FOREIGN KEY (`uid`) REFERENCES `tb_userinfo` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=322184323 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_orderinfo
-- ----------------------------
INSERT INTO `tb_orderinfo` VALUES ('169', '108', '108', '2020-07-24 10:44:36', '已付款', '800.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('172', '108', '106', '2020-07-26 15:44:32', '已付款', '300.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('179', '108', '108', '2020-07-26 15:53:26', '已付款', '300.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('616', '108', '108', '2020-07-29 22:15:34', '已付款', '500.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('9457161', '108', '108', '2020-07-30 09:04:57', '已收货', '700.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('19537482', '108', null, '2020-08-09 19:53:08', '未付款', '300.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('20519248', '108', '108', '2020-08-10 20:51:09', '已付款', '2810.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('151136232', '108', '108', '2020-08-09 15:11:37', '已发货', '405.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('164955922', '108', null, '2020-08-10 16:49:59', '未付款', '100.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('194413274', '108', null, '2020-08-09 19:44:14', '未付款', '205.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('194413460', '108', null, '2020-08-09 19:44:14', '未付款', '205.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('194719305', '108', null, '2020-08-09 19:47:19', '未付款', '205.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('194719469', '108', null, '2020-08-09 19:47:20', '未付款', '205.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('211240768', '108', '108', '2020-08-10 21:12:41', '已付款', '1300.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('221927770', '108', '108', '2020-07-29 22:19:28', '未付款', '300.0', '1');
INSERT INTO `tb_orderinfo` VALUES ('322184322', '108', '108', '2020-07-29 22:18:04', '未付款', '700.0', '1');

-- ----------------------------
-- Table structure for tb_shoppingcart
-- ----------------------------
DROP TABLE IF EXISTS `tb_shoppingcart`;
CREATE TABLE `tb_shoppingcart` (
  `scid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `gid` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`scid`),
  KEY `fk_shoppingcart_uid` (`uid`),
  KEY `fk_shoppingcart_gid` (`gid`),
  CONSTRAINT `fk_shoppingcart_gid` FOREIGN KEY (`gid`) REFERENCES `tb_goodsinfo` (`gid`),
  CONSTRAINT `fk_shoppingcart_uid` FOREIGN KEY (`uid`) REFERENCES `tb_userinfo` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_shoppingcart
-- ----------------------------
INSERT INTO `tb_shoppingcart` VALUES ('101', '179', '101', '1', '1');

-- ----------------------------
-- Table structure for tb_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_userinfo`;
CREATE TABLE `tb_userinfo` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `sex` char(1) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_userinfo
-- ----------------------------
INSERT INTO `tb_userinfo` VALUES ('108', 'user', '刘强', '123456@163.com', '女', 'e10adc3949ba59abbe56e057f20f883e', '13656568585', '1');
INSERT INTO `tb_userinfo` VALUES ('109', 'c', '真实姓名', 'zhengjym@163.com', '男', '242bccba550a3ed42e1239f8399fd7a6', '123456', '1');
INSERT INTO `tb_userinfo` VALUES ('110', 'f', null, 'zhengjym@163.com', null, '353af271dc02bf6be90240c3619568e6', '10012244', '1');
INSERT INTO `tb_userinfo` VALUES ('177', 'm', null, '395581770@qq.com', null, 'e10adc3949ba59abbe56e057f20f883e', null, '1');
INSERT INTO `tb_userinfo` VALUES ('178', 'n', null, '395581770@qq.com', null, 'e10adc3949ba59abbe56e057f20f883e', null, '1');
INSERT INTO `tb_userinfo` VALUES ('179', 'navy', null, '541155580@qq.com', null, 'c8837b23ff8aaa8a2dde915473ce0991', null, '1');
INSERT INTO `tb_userinfo` VALUES ('180', '王梦杰', null, '1737965693@qq.com', null, 'e10adc3949ba59abbe56e057f20f883e', null, '0');
INSERT INTO `tb_userinfo` VALUES ('181', '杰哥', null, '2874764212@qq.com', null, 'e10adc3949ba59abbe56e057f20f883e', null, '0');
