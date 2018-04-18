/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : test02

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-17 17:53:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(64) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `nickName` varchar(100) NOT NULL COMMENT '用户昵称',
  `email` varchar(100) NOT NULL COMMENT '邮箱，登录唯一判定值',
  `creation_time` timestamp NOT NULL DEFAULT '1970-01-02 00:00:00' COMMENT '账号创建时间',
  `last_login_time` timestamp NULL DEFAULT '1970-01-02 00:00:00' COMMENT '上次登录时间',
  `department_id` bigint(64) unsigned NOT NULL COMMENT '所属部门编号',
  `passWord` varchar(200) NOT NULL COMMENT '密码',
  `state` int(1) NOT NULL COMMENT '0：启用状态；1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_deptId` (`department_id`),
  CONSTRAINT `fk_deptId` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', 'admin', 'admin', 'admin@dfsx.com', '2018-04-13 15:45:27', '2018-04-17 17:29:12', '1', 'e10adc3949ba59abbe56e057f20f883e', '0');
INSERT INTO `customer` VALUES ('2', 'zhou', 'zhou', 'zhou@dfsx.com', '2018-04-16 11:33:52', '2018-04-16 11:33:52', '2', 'e10adc3949ba59abbe56e057f20f883e', '0');
INSERT INTO `customer` VALUES ('3', 'chuan', 'chuan', 'chuan@dfsx.com', '2018-04-16 11:33:55', '2018-04-16 11:33:55', '2', 'e10adc3949ba59abbe56e057f20f883e', '0');
INSERT INTO `customer` VALUES ('4', 'zhang', 'zhang', 'zhang@dfsx.com', '2018-04-16 11:33:55', '2018-04-17 15:02:59', '2', 'e10adc3949ba59abbe56e057f20f883e', '0');
INSERT INTO `customer` VALUES ('5', 'zhao', 'zhao', 'zhao@dfsx.com', '2018-04-16 11:32:48', '1970-01-02 00:00:00', '3', 'e10adc3949ba59abbe56e057f20f883e', '0');
INSERT INTO `customer` VALUES ('6', 'qian', 'qian3', 'qian3@dfsx.com', '2018-04-16 12:39:56', '2018-04-17 14:17:41', '7', 'fcea920f7412b5da7be0cf42b8c93759', '1');
INSERT INTO `customer` VALUES ('7', 'wang', 'wang', 'wang@dfsx.com', '2018-04-17 15:23:35', '1970-01-02 00:00:00', '6', 'e10adc3949ba59abbe56e057f20f883e', '0');
INSERT INTO `customer` VALUES ('8', 'li', 'li', 'li@dfsx.com', '2018-04-17 15:25:06', '1970-01-02 00:00:00', '8', 'e10adc3949ba59abbe56e057f20f883e', '0');
INSERT INTO `customer` VALUES ('9', 'liu', 'liu', 'liu@dfsx.com', '2018-04-17 15:37:39', '1970-01-02 00:00:00', '8', 'e10adc3949ba59abbe56e057f20f883e', '0');

-- ----------------------------
-- Table structure for `customer_role`
-- ----------------------------
DROP TABLE IF EXISTS `customer_role`;
CREATE TABLE `customer_role` (
  `customerId` bigint(64) unsigned NOT NULL,
  `roleId` bigint(64) unsigned NOT NULL,
  PRIMARY KEY (`customerId`,`roleId`),
  KEY `fk_roleId` (`roleId`),
  CONSTRAINT `fk_customerId` FOREIGN KEY (`customerId`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_roleId` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_role
-- ----------------------------
INSERT INTO `customer_role` VALUES ('1', '2');
INSERT INTO `customer_role` VALUES ('1', '3');
INSERT INTO `customer_role` VALUES ('4', '3');
INSERT INTO `customer_role` VALUES ('1', '4');

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` bigint(64) unsigned NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `name` varchar(50) NOT NULL COMMENT '部门名称',
  `parentId` bigint(64) unsigned NOT NULL COMMENT '上级部门编号（无上级为0）',
  `idPath` varchar(650) DEFAULT NULL,
  `namePath` varchar(510) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '部门创建时间',
  `description` varchar(200) DEFAULT NULL,
  `manager` varchar(100) DEFAULT NULL,
  `state` int(1) NOT NULL COMMENT '0：启用状态；1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '东方', '0', '/1/', '/东方/', '2018-04-11 18:19:40', '公司', '', '0');
INSERT INTO `department` VALUES ('2', '开发部', '1', '/1/2/', '/东方/开发部/', '2018-04-13 17:44:51', '开发', '', '0');
INSERT INTO `department` VALUES ('3', '人事部', '1', '/1/3/', '/东方/人事部/', '2018-04-12 10:43:02', '人力资源管理', '', '0');
INSERT INTO `department` VALUES ('6', '财务部', '1', '/1/6/', '/东方/财务部/', '2018-04-12 10:57:36', '资金管理', null, '0');
INSERT INTO `department` VALUES ('7', '测试部', '1', '/1/7/', '/东方/测试部/', '2018-04-12 11:11:09', '项目测试', null, '0');
INSERT INTO `department` VALUES ('8', 'Java组', '2', '/1/2/8/', '/东方/开发部/Java组/', '2018-04-12 15:12:34', 'java后台', null, '0');
INSERT INTO `department` VALUES ('9', '行政二部', '1', '/1/9/', '/东方/行政部/', '2018-04-17 16:08:55', '行政', null, '0');
INSERT INTO `department` VALUES ('10', '后勤', '9', '/1/9/10/', '/东方/行政部/后勤/', '2018-04-17 16:13:14', '后勤事务', '', '0');
INSERT INTO `department` VALUES ('11', '大后勤2', '9', '/1/9/11/', '/东方/行政部/大后勤2/', '2018-04-17 16:17:17', '后勤', '', '1');
INSERT INTO `department` VALUES ('12', '小后勤', '11', '/1/9/11/12/', '/东方/行政部/大后勤2/小后勤/', '2018-04-17 16:17:32', '', '', '1');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(64) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `state` int(1) NOT NULL COMMENT '0：启用状态；1：以删除',
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'bg', '2018-04-16 16:39:53', '0', '后台管理');
INSERT INTO `role` VALUES ('2', 'bg.admin_user', '2018-04-16 17:55:35', '0', '用户管理');
INSERT INTO `role` VALUES ('3', 'bg.admin_role', '2018-04-16 17:55:37', '0', '角色管理');
INSERT INTO `role` VALUES ('4', 'bg.admin_department', '2018-04-16 17:55:40', '0', '部门管理');
INSERT INTO `role` VALUES ('5', 'test1', '2018-04-16 17:31:14', '1', '测试1');
INSERT INTO `role` VALUES ('6', 'test2', '2018-04-17 17:15:35', '1', 'fffffff');
