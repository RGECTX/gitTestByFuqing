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

 Date: 03/11/2020 14:59:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aux_sjqk
-- ----------------------------
DROP TABLE IF EXISTS `aux_sjqk`;
CREATE TABLE `aux_sjqk`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `instance_id` bigint(20) NOT NULL COMMENT '所属主体Id',
  `idcard` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号',
  `sjrq` int(11) DEFAULT NULL COMMENT '授奖日期',
  `sjlb` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授奖类别,字典AUX_SJLB',
  `sjmc` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授奖名称',
  `sjyy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授奖原因',
  `pzdw` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '批准单位',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `outKey1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '外键1',
  `outKey2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '外键2',
  `bak1` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备用1',
  `bak2` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备用2',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
