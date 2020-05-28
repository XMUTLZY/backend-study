/*
 Navicat MySQL Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50515
 Source Host           : localhost:3306
 Source Schema         : backend-study

 Target Server Type    : MySQL
 Target Server Version : 50515
 File Encoding         : 65001

 Date: 28/05/2020 12:46:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码 md5(加密)',
  `role` tinyint(4) NULL DEFAULT 1 COMMENT '管理员角色 1：普通管理员 2：超级管理员',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(4) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '林哈', 'e10adc3949ba59abbe56e057f20f883e', 1, '2020-05-28 02:01:16', '2020-05-28 12:23:33', 1);
INSERT INTO `admin` VALUES (2, '校长', 'e10adc3949ba59abbe56e057f20f883e', 2, '2020-05-28 01:59:13', '2020-05-27 19:58:04', 1);
INSERT INTO `admin` VALUES (49, '林一', '202cb962ac59075b964b07152d234b70', 1, '2020-05-28 02:24:46', '2020-05-28 02:24:46', 1);
INSERT INTO `admin` VALUES (54, '林哈哈', 'e10adc3949ba59abbe56e057f20f883e', 1, '2020-05-28 12:23:44', '2020-05-28 12:23:49', 0);

-- ----------------------------
-- Table structure for admin_privilege
-- ----------------------------
DROP TABLE IF EXISTS `admin_privilege`;
CREATE TABLE `admin_privilege`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin_privilege
-- ----------------------------
INSERT INTO `admin_privilege` VALUES (1, '普通管理员');
INSERT INTO `admin_privilege` VALUES (2, '超级管理员');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (55);
INSERT INTO `hibernate_sequence` VALUES (55);
INSERT INTO `hibernate_sequence` VALUES (55);
INSERT INTO `hibernate_sequence` VALUES (55);
INSERT INTO `hibernate_sequence` VALUES (55);
INSERT INTO `hibernate_sequence` VALUES (55);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `admin_id` int(11) NULL DEFAULT NULL COMMENT '上这门课的老师',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '0:删除 1：正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, '数学', 1, 1);
INSERT INTO `project` VALUES (2, '语文', 1, 1);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '题目名称',
  `answer_a` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项A',
  `answer_b` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项B',
  `answer_c` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项C',
  `answer_d` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项D',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '0:删除 1：正常',
  `correct_answer` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '正确答案(A、B、C、D)',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '课题id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, '11+12=？', '20', '21', '22', '23', 1, '23', 1);
INSERT INTO `question` VALUES (2, '20+40=？', '58', '59', '60', '61', 1, '60', 1);
INSERT INTO `question` VALUES (3, '李白号什么？', '青莲居士', '太白', '诗鬼', '诗仙', 0, '诗仙', 2);
INSERT INTO `question` VALUES (4, '1+1=?', '1', '2', '3', '4', 1, '2', 1);
INSERT INTO `question` VALUES (5, '1+1=?', '1', '2', '3', '4', 1, '2', 1);
INSERT INTO `question` VALUES (6, '1', '312312', '3123', '1231', '3123', 0, '1231231', 2);
INSERT INTO `question` VALUES (50, '10+20=?', '19', '20', '30', '39', 0, '30', 1);
INSERT INTO `question` VALUES (52, '1+2=?', '1', '25', '3', '4', 0, '3', 1);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生邮箱',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5加密后的密码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '0:删除 1：正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '李一', '1241@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '2020-05-28 00:35:44', 1);
INSERT INTO `student` VALUES (2, '林一', '1231@qq.com', 'e10adc3949ba59abbe56e057f20f883e', '2020-05-28 00:35:45', 1);
INSERT INTO `student` VALUES (18, '', '09921@qq.com', '202cb962ac59075b964b07152d234b70', '2020-05-28 01:08:38', 1);
INSERT INTO `student` VALUES (47, 'jake', '12@qq.com', 'd7b1cd787be1d89dad4e2b6267568ca6', '2020-05-28 01:32:55', 1);
INSERT INTO `student` VALUES (48, '林已', '12@qq.com', 'd7b1cd787be1d89dad4e2b6267568ca6', '2020-05-28 01:34:46', 1);
INSERT INTO `student` VALUES (51, '林已', '12@qq.com', 'd7b1cd787be1d89dad4e2b6267568ca6', '2020-05-28 12:19:22', 1);
INSERT INTO `student` VALUES (53, '林已', '12@qq.com', 'd7b1cd787be1d89dad4e2b6267568ca6', '2020-05-28 12:22:41', 1);

-- ----------------------------
-- Table structure for student_answer
-- ----------------------------
DROP TABLE IF EXISTS `student_answer`;
CREATE TABLE `student_answer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '学生id',
  `question_id` int(11) NULL DEFAULT NULL COMMENT '题目id',
  `choose_answer` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生选择的答案',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '作答时间',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '0:删除 1：正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student_answer
-- ----------------------------
INSERT INTO `student_answer` VALUES (1, 1, 1, '20', '2020-05-28 11:48:49', 1);
INSERT INTO `student_answer` VALUES (2, 1, 2, '69', '2020-05-28 11:48:52', 1);
INSERT INTO `student_answer` VALUES (3, 2, 1, '23', '2020-05-28 11:48:54', 1);

SET FOREIGN_KEY_CHECKS = 1;
