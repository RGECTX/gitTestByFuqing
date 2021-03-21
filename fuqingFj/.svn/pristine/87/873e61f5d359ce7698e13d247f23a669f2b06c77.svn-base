/*
 Navicat Premium Data Transfer

 Source Server         : auxiliary_dbServer
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : 192.168.0.169:3306
 Source Schema         : auxiliary_fuqing

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 20/11/2020 14:27:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aux_notice
-- ----------------------------
DROP TABLE IF EXISTS `aux_notice`;
CREATE TABLE `aux_notice`  (
  `noticeId` bigint(20) NOT NULL COMMENT '通知ID',
  `appCode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用编码',
  `noticeTitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知标题',
  `noticeText` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '通知内容',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态，1启用 2停用',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `createBy` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `createTime` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `outKey1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键1',
  `outKey2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外键2',
  `bak1` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备用1',
  `bak2` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备用2',
  PRIMARY KEY (`noticeId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知通报' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
