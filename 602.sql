/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : 602

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 27/10/2025 21:07:59
*/

USE `602`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for achievement
-- ----------------------------
DROP TABLE IF EXISTS `achievement`;
CREATE TABLE `achievement`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '介绍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '成果' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of achievement
-- ----------------------------
INSERT INTO `achievement` VALUES (1, 'LNG动力船胎智能安保系统及其云平台', '实验室与中山市蓝水科技能源有限公司共同研发了LNG动力船舶智能安保系统及其云平台，该产品为LNG动力船舶发动机系统提供安保控制，同时可将本地的发动机运行数据通过移动互联网上传云平台，实现PC端和移动端的远程监控，目前该系统已通过投入使用，并获得40条LNG动力渔船改造工程订单，此外该系统参加了2019年广东高校科技成果展，并入围广东高校高质量科技成果库。');
INSERT INTO `achievement` VALUES (2, '金马MES系统', '实验室与中山市金马科技娱乐设备股份有限公司共同研发了金马MES系统，该系统于参与了十二五规划。该产品数据源为该公司已投入使用的ERP系统内数据，MES系统获取ERP系统数据后生成对应设备BOM工艺树，通过BOM树进行基础数据管理、物料管理、工单调配、车间计划、信息汇总及数据采集，数据采集通过物联网使用各类传感器对生产工艺单内所需数据进行采集，最后将MES系统数据上传至云平台实现双系统数据互通，目前该系统已通过投入使用。');
INSERT INTO `achievement` VALUES (3, '智能垃圾箱', '基于物联网技术开发的智能垃圾分类处理系统，用户可使用多种方式登录并投入垃圾，系统会自动对垃圾进行分拣，并为用户计算积分。积分可定期兑换礼物，引导用户养成良好的垃圾分类习惯。区域内所有垃圾处理系统均联网井统一管理，由中控系统对整体垃圾清运流程进行控制，确保整体运营的安全高效。');
INSERT INTO `achievement` VALUES (4, '大学导航', '本项目由本单位与中山职业技术学院合作开发完成，并已上线使用。该项目采集了大量的高考相关历史数据，并对数据进行清洗整合。结合考生的个性化需求，该系统使用大数据算法，为考生志愿填报提供智能化推荐，协助高考考生找到合适的高校和专业，目前已在河南、甘肃、山东、黑龙江、上海等省级行政区投入使用。');
INSERT INTO `achievement` VALUES (5, '建筑工程试件追踪及资料协同管理系统', '该系统由本单位与中山市石岐区广建同欣软件商行共同研发。系统基于Web平台，对建筑工程建设的全流程进行跟踪，对建筑工地各种物料和流程进行电子化表达和管理，有效提高了工地管理的信息化程度，目前已应用于东莞、江门、佛山等多个建筑工程，受到业主的好评。');
INSERT INTO `achievement` VALUES (6, '建筑业协会专家抽取管理系统', '该系统由本单位与中山市石岐区广建同欣软件商行共同研发。系统基于Web平台，解决了建筑业协会专家库及对应专家库抽取的问题，用于管理协会成员优创申报及移动评审，现已应用于中山市建筑业协会。');

-- ----------------------------
-- Table structure for activities
-- ----------------------------
DROP TABLE IF EXISTS `activities`;
CREATE TABLE `activities`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `photo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activities
-- ----------------------------
INSERT INTO `activities` VALUES (1, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/activities/2b5d12d5-9279-4360-89e3-2d75d533ebf0.jpg');
INSERT INTO `activities` VALUES (2, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/activities/4c4f01fe-61c8-4266-b354-aa2dbc8d2286.jpg');
INSERT INTO `activities` VALUES (3, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/activities/0429c199-2aa4-4ca9-af36-7decc33eed0f.jpg');
INSERT INTO `activities` VALUES (4, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/activities/e48227af-4621-49e9-bd10-417a83ccc0c4.jpg');
INSERT INTO `activities` VALUES (5, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/activities/df9b301f-7ade-4b21-9950-ee303d6a1c9c.jpg');
INSERT INTO `activities` VALUES (6, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/activities/723bf9eb-c1e3-404f-863c-f4be004ae27c.jpg');
INSERT INTO `activities` VALUES (7, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/activities/39bf522e-dd52-4b53-ae9e-5f207a5bcb68.jpg');
INSERT INTO `activities` VALUES (10, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/activities/af565321-c8ee-4b16-a699-ec049fcc90fa.jpg');
INSERT INTO `activities` VALUES (11, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/activities/885115d1-a02e-44a1-b16c-8c5d12d65c55.jpg');

-- ----------------------------
-- Table structure for contact_information
-- ----------------------------
DROP TABLE IF EXISTS `contact_information`;
CREATE TABLE `contact_information`  (
  `id` int(11) NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `zip_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮编',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contact_information
-- ----------------------------
INSERT INTO `contact_information` VALUES (1, '12345678900', '1111@163.com', '中山市石岐区电子科技大学中山学院厚德楼A602', '111');

-- ----------------------------
-- Table structure for environment
-- ----------------------------
DROP TABLE IF EXISTS `environment`;
CREATE TABLE `environment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `photo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of environment
-- ----------------------------
INSERT INTO `environment` VALUES (1, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/environment/ff53329a-17a2-4b67-b897-1d4ae7df1f26.jpg');
INSERT INTO `environment` VALUES (2, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/environment/9716080b-9f9d-4018-ab8d-1c9eaa08341e.jpg');
INSERT INTO `environment` VALUES (3, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/environment/d5507253-cdd9-4b73-bd55-9dff3a80a94c.jpg');
INSERT INTO `environment` VALUES (4, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/environment/c1bfeea4-f71d-4378-be69-b48feceb686a.jpg');
INSERT INTO `environment` VALUES (5, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/environment/021a1810-c3da-470f-adc7-63a5abbbdf68.jpg');
INSERT INTO `environment` VALUES (6, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/environment/273f6fb8-1273-4e11-96f1-18151497e467.jpg');

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `photo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO `food` VALUES (2, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/food/9a6acaae-ec11-4056-ae83-398a44e41caa.jpg');
INSERT INTO `food` VALUES (3, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/food/30ffe709-e7cf-410d-bd68-a7f96326096a.jpg');
INSERT INTO `food` VALUES (4, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/food/436915e1-7400-4adf-be4f-ed5ebf86b243.jpg');
INSERT INTO `food` VALUES (5, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/food/98f82561-c140-413f-a2b0-0663f60dd3f1.jpg');
INSERT INTO `food` VALUES (6, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/food/52f81d79-d6d7-4da4-88ef-808a201ab2a5.jpg');
INSERT INTO `food` VALUES (13, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/food/233effe7-e206-4a8c-806f-56cf38665ba8.jpg');
INSERT INTO `food` VALUES (14, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/food/bdff11be-fac3-44b1-8c89-c41e40b4970a.jpg');
INSERT INTO `food` VALUES (15, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/food/d321819a-d4a1-4782-8b6c-850dec080baa.jpg');

-- ----------------------------
-- Table structure for img_achievement
-- ----------------------------
DROP TABLE IF EXISTS `img_achievement`;
CREATE TABLE `img_achievement`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `achievement_id` int(11) NULL DEFAULT NULL COMMENT '成果id',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '成果展示图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of img_achievement
-- ----------------------------
INSERT INTO `img_achievement` VALUES (2, 1, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show1.jpg');
INSERT INTO `img_achievement` VALUES (3, 1, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show11.jpg');
INSERT INTO `img_achievement` VALUES (4, 2, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show2.jpg');
INSERT INTO `img_achievement` VALUES (5, 2, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show22.jpg');
INSERT INTO `img_achievement` VALUES (6, 3, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show3.jpg');
INSERT INTO `img_achievement` VALUES (7, 3, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show33.jpg');
INSERT INTO `img_achievement` VALUES (8, 4, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show4.jpg');
INSERT INTO `img_achievement` VALUES (9, 4, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show44.jpg');
INSERT INTO `img_achievement` VALUES (10, 5, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show5.jpg');
INSERT INTO `img_achievement` VALUES (11, 5, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show55.jpg');
INSERT INTO `img_achievement` VALUES (12, 6, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show6.jpg');
INSERT INTO `img_achievement` VALUES (13, 6, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\achievement\\result_show66.jpg');

-- ----------------------------
-- Table structure for img_news
-- ----------------------------
DROP TABLE IF EXISTS `img_news`;
CREATE TABLE `img_news`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `news_id` int(11) NULL DEFAULT NULL COMMENT '新闻id',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 157 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻配图' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of img_news
-- ----------------------------
INSERT INTO `img_news` VALUES (123, 109, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/news/6ccbc280-eebd-4a90-932f-e0e66fd9ee65.jpg');
INSERT INTO `img_news` VALUES (124, 109, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/news/fd537505-9032-4163-b847-bfc86199ec6e.jpg');
INSERT INTO `img_news` VALUES (125, 109, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/news/22edf693-a5a1-4617-992b-a4840a908483.jpg');
INSERT INTO `img_news` VALUES (129, 111, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/news/03f437ca-daec-4274-a96f-f2967da73854.jpg');
INSERT INTO `img_news` VALUES (130, 111, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/news/47812bb4-b19d-4e41-8815-f910cc935c32.png');

-- ----------------------------
-- Table structure for img_project
-- ----------------------------
DROP TABLE IF EXISTS `img_project`;
CREATE TABLE `img_project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of img_project
-- ----------------------------
INSERT INTO `img_project` VALUES (1, 1, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item01_1.png');
INSERT INTO `img_project` VALUES (2, 1, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item01_2.png');
INSERT INTO `img_project` VALUES (3, 2, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item02_1.png');
INSERT INTO `img_project` VALUES (4, 2, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item02_2.png');
INSERT INTO `img_project` VALUES (5, 3, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item03_1.png');
INSERT INTO `img_project` VALUES (6, 3, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item03_3.png');
INSERT INTO `img_project` VALUES (7, 4, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item04_1.png');
INSERT INTO `img_project` VALUES (8, 4, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item04_2.png');
INSERT INTO `img_project` VALUES (9, 5, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item05_1.png');
INSERT INTO `img_project` VALUES (10, 5, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item05_2.png');
INSERT INTO `img_project` VALUES (11, 6, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item06_1.png');
INSERT INTO `img_project` VALUES (12, 6, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item06_2.png');

-- ----------------------------
-- Table structure for lbt
-- ----------------------------
DROP TABLE IF EXISTS `lbt`;
CREATE TABLE `lbt`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `photo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL COMMENT '轮播图种类',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lbt
-- ----------------------------
INSERT INTO `lbt` VALUES (1, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/58c0d96e-d0a2-4daf-a035-104ba40715ce.jpg', 1);
INSERT INTO `lbt` VALUES (2, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/f08e75b1-569f-46f9-96d9-7345abab9073.jpg', 1);
INSERT INTO `lbt` VALUES (3, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/3243e7d9-663b-495c-b048-fa8b71d86319.png', 1);
INSERT INTO `lbt` VALUES (4, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/771d55cb-291a-4815-9348-a09b94e84edc.png', 1);
INSERT INTO `lbt` VALUES (6, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/702eda22-fe6c-4898-ac37-1e271051420d.png', 1);
INSERT INTO `lbt` VALUES (7, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/38583457-4a0a-46de-8af7-217100d41f9b.jpg', 2);
INSERT INTO `lbt` VALUES (8, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/5706d9fc-2d4a-4818-acab-f932830d1737.jpg', 2);
INSERT INTO `lbt` VALUES (9, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/d5002abd-b37c-4d14-a07d-9afb3ac597ad.jpg', 2);
INSERT INTO `lbt` VALUES (10, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/2abbe765-7160-4170-b142-2a741d7940f8.jpg', 2);
INSERT INTO `lbt` VALUES (11, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/9207ad8b-ad6c-4e29-8d4a-6188abe63b4a.jpg', 2);
INSERT INTO `lbt` VALUES (12, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/lbt/0bea6928-ad17-48bc-893f-2aa01521d6e8.jpg', 2);

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新闻标题',
  `descr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新闻简介',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新闻分类',
  `contect` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '新闻内容',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配图',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (1, '安控智慧环境科技（中山）有限公司与广东云星信息科技有限公司到我校实验室参观交流', 'null', '实验室新闻', 'null', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/news/505825997cover.png', '2024-05-30 17:18:14');
INSERT INTO `news` VALUES (2, '23级学生第一次笔试考核', 'null', '3', 'null', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/news/1994547146cover.png', '2024-06-02 12:00:00');
INSERT INTO `news` VALUES (3, '23级学生第一次后端指导与交流', 'null', '3', 'null', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/news/-28954415cover.png', '2024-06-02 12:00:00');

-- ----------------------------
-- Table structure for news_category
-- ----------------------------
DROP TABLE IF EXISTS `news_category`;
CREATE TABLE `news_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '启用状态（0禁用 1启用）',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news_category
-- ----------------------------
INSERT INTO `news_category` VALUES (1, '测试', '1', 0);
INSERT INTO `news_category` VALUES (2, '获奖新闻', '1', 1);
INSERT INTO `news_category` VALUES (3, '实验室新闻', '1', 1);

-- ----------------------------
-- Table structure for prize
-- ----------------------------
DROP TABLE IF EXISTS `prize`;
CREATE TABLE `prize`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '奖项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prize
-- ----------------------------
INSERT INTO `prize` VALUES (1, '孟庆元同志在2017年度工作成绩显著荣获考核优秀奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\1.jpg');
INSERT INTO `prize` VALUES (2, '孟庆元同志在2017年度工作成绩显著荣获科研优秀奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\2.jpg');
INSERT INTO `prize` VALUES (3, '孟庆元同志在2017年度工作成绩显著荣获突出贡献奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\3.jpg');
INSERT INTO `prize` VALUES (4, '孟庆元同志在2017年度工作成绩显著荣获卓越教学奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\4.jpg');
INSERT INTO `prize` VALUES (5, '由郭金源、余梓宇、曾宪楠、林子扬与周顺同学组成的团队的项目\"基于工业4.0的数字化工厂解决方案\"在2019广东省\"高校杯\"软件作品设计大赛（本科组）荣获三等奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\5.jpg');
INSERT INTO `prize` VALUES (6, '由郭金源、余梓宇、曾宪楠、林子扬与周顺同学组成的团队的项目\"基于工业4.0的数字化工厂解决方案\"在2019\"华资杯\"广东省大学生计算机作品赛暨泛珠三角+大学生计算机作品赛广东选拔赛（本科组）荣获三等奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\6.jpg');
INSERT INTO `prize` VALUES (7, '周伟帆与冯楷在2023年\"典阅杯\"全国大学生人工智能算法设计与应用大赛中荣获二等奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\7.jpg');
INSERT INTO `prize` VALUES (8, '由冯楷、周伟帆、林慧敏、赖嘉杰与李棕泽组成的团队的作品在第十三届全国大学生电子商务\"创新、创意及创业\"挑战赛中荣获校级赛三等奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\8.jpg');
INSERT INTO `prize` VALUES (10, '由曾宪楠、李承明、欧鸿玲、张晓桐、郑家铭、何炳涛、周顺、郭金源、金梓宇与林子扬组成的团队的项目\"迈斯智慧工厂——基于工业4.0的数字化工厂解决方案\"在第五届中国\"互联网+\"大学生创新创业大赛校内赛（高校主赛道）中荣获创意组金奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\10.jpg');
INSERT INTO `prize` VALUES (11, '黄家浩、郭金源与何家浩的创新训练项目\"基于工业4.0的数字化工厂解决方案\"在2021年度\"大学生创新创业训练计划\"中获得校级立项', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\11.jpg');
INSERT INTO `prize` VALUES (12, '由曾宪楠、周顺、李承明、吴绮敏、颜烈集与高晓婷组成的团队的项目\"医链云踪——医疗废物信息化溯源管控系统\"在中国国际大学生创新大赛（2024）校内院赛荣获银奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\12.jpg');
INSERT INTO `prize` VALUES (13, '何炳涛在中山市石岐区广建同欣软件商行与广东省腾跃建筑工程有限公司合作开发的《腾跃质量管理系统》项目中负责软件开发，表现突出', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\13.jpg');
INSERT INTO `prize` VALUES (14, '李承明在中山市石岐区广建同欣软件商行与广东省腾跃建筑工程有限公司合作开发的《腾跃质量管理系统》项目中担任技术总监，表现突出', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\14.jpg');
INSERT INTO `prize` VALUES (15, '吴思帆在中山市石岐区广建同欣软件商行与广东省腾跃建筑工程有限公司合作开发的《腾跃质量管理系统》项目中负责软件开发，表现突出', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\15.jpg');
INSERT INTO `prize` VALUES (16, '张洪发在中山市石岐区广建同欣软件商行与江门市建设工程质量监督站合作开发的《江门质监系统》项目中表现突出', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\16.jpg');
INSERT INTO `prize` VALUES (17, '黄喆琪在\"莲峰杯\"电子信息学院五星党员培育工程之\"走进可爱的后勤人\"第五期后勤岗位体验活动中，表现优秀，荣获参与奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\17.jpg');
INSERT INTO `prize` VALUES (18, '唐毅在2022年8月11日至2022年8月15日参加以\"夯实安全基座，筑牢安全防线\"为主题的\"粤盾-2022\"广东省数字政府网络安全实战攻防演练', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\18.jpg');
INSERT INTO `prize` VALUES (19, '周顺在中山市石岐区广建同欣软件商行与韶关市第一建筑工程有限公司合作开发的《韶关一建系统》项目中表现突出', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\19.jpg');
INSERT INTO `prize` VALUES (20, '由余梓宇、曾宪楠、周顺、郭金源、李承明、林子扬、何炳涛组成的团队的项目\"工业4.0之数字化工厂MES系统\"在第十二届大学生节能减排社会实践与科技竞赛校内赛荣获优秀奖', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\prize\\20.jpg');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '题目',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, '建联检测客户委托及财务结算管理系统', '对于该系统将以集成化和系统化思维方式展开检测内业协同管理。将检测单位收发部、试验室、技术部以及财务部纳入到一个管理体系中，通过设计科学合理的数据链关联，将各工程项目、多类型检测的委托单、收样单编制；试验记录、试验报告单编制以及试验报告单提交、审核、批准、驳回形成完整的数据链闭合。实现以检测企业技术部工作为核心，以指定类型检测过程记录作为管理对象，以委托单形成收样记录和试验报告单，最终实现财务统计报表编制以及数据和报告查询等场景链同步优化作为协同工作目标的集成化工程检测内业协同管理模式。', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item01.png');
INSERT INTO `project` VALUES (2, '东莞市建筑业协会工作系统', '东莞建协系统可以让管理员管理协会的申报项目，并且协同小程序对项目做出评测。', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item02.png');
INSERT INTO `project` VALUES (3, '大学导航', '“大学导航”是一个面对高考应届生填报志愿的指导网站，通过大数据算法选出自己所心仪学校，用户可了解系统模块的功能，掌握智能填报的方法等内容。只要考生在进行卡片激活、用户注册的过程中填写了自己所属省份以后，则点击“志愿设置原则”按钮后，系统在此会给出该省份的志愿设置原则。', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item03.png');
INSERT INTO `project` VALUES (4, '同欣建筑业协会专家抽取管理系统', '中山市建筑业协会是一个对中山市建筑业中各工程项目、各专家的管理系统，包括数据看板、论证专家库、方案论证、评优专家库、优创申报及移动评审、系统管理几大模块。该系统可以让建筑业的项目、专家管理变得更加便捷，更加的集中、规范化。', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item04.png');
INSERT INTO `project` VALUES (5, '江门市防雷检测在线监测系统', '该系统可以对房屋工程、易燃易爆场所、其他建筑物进行登记，进行企业设置和用户管理，登记工程的质量检测，划分危险场所。', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item05.png');
INSERT INTO `project` VALUES (6, '同欣建筑工程试件追踪及资料协同管理系统', '该系统可以管理建筑工程的app端和客户端，对用户的文件进行协同管理。', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\project\\item06.png');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生姓名',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `graduation_year` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '毕业年份',
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `life_motto` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '格言',
  `award_atSchool` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '在校获奖情况',
  `work_experience` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作经验',
  `hobby` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '爱好',
  `say` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '想对师弟师妹说的话',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `introduction` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人简介',
  `photo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生照片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (10, '尹仁杰', '男', '2025年', '13380547656', '桃李不言，下自成蹊', '“粤嵌杯”电子设计大赛三等奖 “莲峰杯”电子设计大赛优秀奖 全国大学生智能车竞赛校内预选赛三等奖 ', '电信学院学生会主席团成员 电信学院2021级年纪级长 21通信班长 23通信B班班导', 'null', '天还很黑，路还很长，至少没有忘，至少在路上，有一份热，就发一份光', '通信工程', 'null', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\student\\image (1).jpeg');
INSERT INTO `student` VALUES (11, '冯楷', '男', '2024年', '107495872@qq.com', '他强任他强，清风拂山岗。\r\n他横任他横，明月照大江。', '太菜了没奖', '大数据协会秘书长', '', '作中庸,子思笔,中不偏,庸不易', '计算机科学与技术', 'null', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/student/eab2d479-1e83-464c-a85d-04c812ce02b2.jpeg');
INSERT INTO `student` VALUES (13, '王康', '男', '2023年', '13822421636', '', '', '', 'null', '', '软件工程', 'null', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images/student/28d35852-3ab9-4a8f-9c29-ac29877c2a5b.jpg');
INSERT INTO `student` VALUES (14, '李棕泽', '男', '2024年', '15018360714', '跟着王康混', '诺贝尔王康奖', '王康司机', NULL, '', '软件工程', NULL, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\student\\微信图片_20240715210922.jpg');
INSERT INTO `student` VALUES (15, '赖嘉杰', '男', '2023年', '18024835254', '理解他人是智慧，理解自己是启迪', '没奖，太菜了', '大数据协会成员', NULL, '愿你们保持学习热情，不断实践，勇往直前！', '计算机科学与技术', NULL, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\student\\微信图片_20240715210922.jpg');
INSERT INTO `student` VALUES (16, '林泽天', '男', '2025年', '13257581817', '能混一天是一天', '蓝桥杯', '全栈', NULL, '加油', '计算机科学与技术', NULL, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\student\\微信图片_20240715210922.jpg');
INSERT INTO `student` VALUES (17, '刘嘉欣', '女', '2025年', '15398829280', '', '', '', NULL, '', '软件工程', NULL, 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\student\\微信图片_20240715210922.jpg');

-- ----------------------------
-- Table structure for student_pre
-- ----------------------------
DROP TABLE IF EXISTS `student_pre`;
CREATE TABLE `student_pre`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生姓名',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `graduation_year` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '毕业年份',
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `life_motto` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '格言',
  `award_atSchool` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '在校获奖情况',
  `work_experience` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作经验',
  `hobby` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '爱好',
  `say` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '想对师弟师妹说的话',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `introduction` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人简介',
  `photo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生照片',
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_pre
-- ----------------------------
INSERT INTO `student_pre` VALUES (1, '冯楷', '男', '2024', '13822421636', '他强任他强，清风拂山岗。 他横任他横，明月照大江。', '蓝桥杯', '无', '踢球', '作中庸,子思笔,中不偏,庸不易', '软件工程', '无', NULL, NULL);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名字',
  `job` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位',
  `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '介绍',
  `honor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '荣誉',
  `direction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '方向',
  `photo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `avatar_color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像背景色',
  `bg_color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '盒子背景色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, '卢晶琦', '讲师  副教授', '无线通信，数据分析', '获得中山科技进步一等奖、二等奖各一次。多次获得中山市优秀教师、吴桂显优秀教师称号', '电子信息学院专任教师，近三年主持或主研五项省级以上教研教改项目,主编规划教材两本,主持主持或主研省市及横向项目十余项\n获得全国高校电子信息青年教师授课竞赛一等奖、学校教学成果一等奖。', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\teacher\\l.png', '#527C5A', '#B0E2AA');
INSERT INTO `teacher` VALUES (2, '孟庆元', '讲师', '1993年6月在北京清华大学自动化系学习，现任电子科技大学中山学院电子信息学院教师， 从事软件设计、单片机、移动通信及计算机通信网等课程的教学工作。近年来主持并完成横 向项目二十多项，科研到帐经费三百多万元', '国家认证系统分析师、网络工程师，多次获得中山市优秀教师称号', '企业信息管理与处理，ERP与MES，图像识别与图像测量，大数据处理与机器学习', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\teacher\\m.png', '#3B5998', '#A8DADC');
INSERT INTO `teacher` VALUES (3, '王桓', '博士 副教授', '基于机器学习技术的数据挖掘', '获中山市科技进步二等奖1项，获2020年度中山市优秀教师荣誉称号', '2009年毕业于湖南大学电气与信息工程学院，获工学博士学位，迄今共\n发表论文20余篇，其中SCI/EI检索10篇。主持并完成中山市社会公益重\n大项目1项，主持广东省教育厅科研项目1项', 'https://602website.oss-cn-shenzhen.aliyuncs.com/images\\teacher\\w.png', '#3A3A3A', '#D8D8D8');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123456');
INSERT INTO `user` VALUES (2, 'ZSA602', '123456');

SET FOREIGN_KEY_CHECKS = 1;
