/*
 Navicat Premium Data Transfer

 Source Server         : taishang_dbServer
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : taishang_dbServer:3306
 Source Schema         : auxiliary_fuqing

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 04/11/2020 15:23:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aux_dwkq
-- ----------------------------
DROP TABLE IF EXISTS `aux_dwkq`;
CREATE TABLE `aux_dwkq`  (
  `dwkqId` bigint(20) NOT NULL COMMENT '主键',
  `orgId` bigint(20) DEFAULT NULL COMMENT '工作单位ID',
  `orgName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单位名称',
  `nd` int(11) DEFAULT NULL COMMENT '年度',
  `yd` int(11) DEFAULT NULL COMMENT '月度',
  `createBy` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间,1970年以来毫秒数',
  `outKey1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '外键1',
  `outKey2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '外键2',
  `bak1` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备用1',
  `bak2` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备用2',
  PRIMARY KEY (`dwkqId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
