SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS wiki;
CREATE DATABASE wiki DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use wiki;

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `sort_flag` int(11) NULL DEFAULT NULL COMMENT '排序权重',
  `deleted` bigint(20) NULL DEFAULT 0 COMMENT '0:Y,1:D',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sort_flag`(`sort_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

INSERT INTO `category` VALUES (100, 0, '数码', 5, 0);
INSERT INTO `category` VALUES (101, 100, '硬盘', 10, 0);
INSERT INTO `category` VALUES (102, 100, '可穿戴', 15, 0);
INSERT INTO `category` VALUES (200, 0, '玩具', 15, 0);
INSERT INTO `category` VALUES (201, 200, '可动', 25, 0);
INSERT INTO `category` VALUES (202, 200, '扭蛋', 30, 0);
INSERT INTO `category` VALUES (300, 0, '电玩', 35, 0);
INSERT INTO `category` VALUES (301, 300, '游戏', 40, 0);
INSERT INTO `category` VALUES (302, 300, '主机', 45, 0);
INSERT INTO `category` VALUES (303, 300, '掌机', 55, 0);

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `goods_id` bigint(20) NOT NULL COMMENT '物品id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `create_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cover` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `view_count` bigint(20) NOT NULL DEFAULT 0 COMMENT '浏览数',
  `follow_count` bigint(20) NOT NULL DEFAULT 0 COMMENT '关注数',
  `comment_count` bigint(20) NOT NULL DEFAULT 0 COMMENT '评论数',
  `create_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `update_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '更新时间',
  `deleted` bigint(20) NULL DEFAULT 0 COMMENT '0:U,1:D',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '物品表' ROW_FORMAT = Dynamic;

INSERT INTO `goods` VALUES (1, '越南大战3 塔尔马 1/12可动人偶', 201, '这款大容量移动固态硬盘提供快速的NVMe™固态性能，具有1050MB/秒2的读取速度和1000MB/秒2的写入速度具有1050MB/秒2的读取速度和1000MB/秒2的写入速度', '/image/finder.png', 100, 200, 10, 0, 0, 0);
INSERT INTO `goods` VALUES (2, '闪迪至尊极速™ 移动固态硬盘', 101, 'WD_BLACK™ P10 Game Drive 配备高达 5TB 的存储空间，可以保存多达125个游戏', '/image/macbook.png', 0, 0, 0, 0, 0, 0);
INSERT INTO `goods` VALUES (3, 'Apple Mac Mini', 102, 'M1芯片,配备两个超高速的雷雳/USB4端口,两个USB-A端口,HDMI2.0,Wi-Fi6及千兆以太网', '/image/finder.png', 0, 0, 0, 0, 0, 0);
INSERT INTO `goods` VALUES (4, '佳明 fēnix 6S Pro', 102, '腕式光学心率计和Pulse Ox脉搏血氧传感器,内置导航传感器,包括三轴电子罗盘,陀螺仪和气压高度,支持多个全球导航卫星系统(GPS、北斗、GLONASS)', '/image/finder.png', 0, 0, 0, 0, 0, 0);
INSERT INTO `goods` VALUES (5, '佳明 fēnix 6S Pro', 102, 'M1芯片,配备两个超高速的雷雳/USB4端口,两个USB-A端口,HDMI2.0,Wi-Fi6及千兆以太网', '/image/macbook.png', 0, 0, 0, 0, 0, 0);
INSERT INTO `goods` VALUES (6, '佳明 fēnix 6S Pro', 102, 'M1芯片,配备两个超高速的雷雳/USB4端口,两个USB-A端口,HDMI2.0,Wi-Fi6及千兆以太网', '/image/finder.png', 0, 0, 0, 0, 0, 0);
INSERT INTO `goods` VALUES (7, '佳明 fēnix 6S Pro', 102, 'M1芯片,配备两个超高速的雷雳/USB4端口,两个USB-A端口,HDMI2.0,Wi-Fi6及千兆以太网', '/image/finder.png', 0, 0, 0, 0, 0, 0);
INSERT INTO `goods` VALUES (8, '佳明 fēnix 6S Pro', 102, 'M1芯片，配备两个超高速的雷雳/USB4端口，两个USB-A端口,HDMI2.0,Wi-Fi6及千兆以太网', '/image/finder.png', 0, 0, 0, 0, 0, 0);
INSERT INTO `goods` VALUES (655551366013386752, 'HHKB HYBRID TYPE-S', 102, '全键PBT材质键帽', '/image/macbook.png', 0, 0, 0, 0, 0, 0);

DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `comment_id` bigint(20) NOT NULL COMMENT '评论id',
  `rela_id` bigint(20) NOT NULL COMMENT '关联id',
  `type` bigint(20) NOT NULL COMMENT '回复类型(1:评论;2:回复)',
  `from_uid` bigint(20) NOT NULL,
  `to_uid` bigint(20) NOT NULL,
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `create_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '回复表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `login_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录名',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`login_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户基本信息表' ROW_FORMAT = Dynamic;

INSERT INTO `user` VALUES (661009431047176192, '空腹虫管理员', '饺子', 'f71d82e0d93377dc11f7a22eb166f7f7');

SET FOREIGN_KEY_CHECKS = 1;

# update goods g join (select goods_id, count(1) comment_count from comment group by goods_id) c on g.id = c.goods_id set g.comment_count = c.comment_count;
