drop table if exists `goods`;
create table `goods` (
    `id` bigint(20) not null comment 'id',
    `name` varchar(50) not null default '' comment '名称',
    `category_id` bigint not null comment '分类id',
    `description` varchar(256) not null default '' comment '描述',
    `cover` varchar(256) not null default '' comment '封面',
    `view_count` bigint(20) not null default 0 comment '浏览数',
    `follow_count` bigint(20) not null default 0 comment '关注数',
    `comment_count` bigint(20) not null default 0 comment '评论数',
    `create_time` bigint(20) not null default 0 comment '创建时间',
    `update_time` bigint(20) not null default 0 comment '更新时间',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='物品表';

alter table `goods` add column `deleted` bigint(20) default 0 comment '0:U,1:D';
alter table `goods` modify `cover` varchar(256);
alter table `goods` modify `description` varchar(256);

insert into `goods`(id, name, category_id, description) values(1, '闪迪至尊极速™ 移动固态硬盘 V2', 0, '这款大容量移动固态硬盘提供快速的NVMe™固态性能，具有1050MB/秒2的读取速度和1000MB/秒2的写入速度');
insert into `goods`(id, name, category_id, description) values(2, '西部数据 WD_BLACK P10 Game Drive', 0, 'WD_BLACK™ P10 Game Drive 配备高达 5TB 的存储空间，可以保存多达125个游戏');
insert into `goods`(id, name, category_id, description) values(3, 'Apple Mac Mini', 0, 'M1芯片,配备两个超高速的雷雳/USB4端口,两个USB-A端口,HDMI2.0,Wi-Fi6及千兆以太网');
insert into `goods`(id, name, category_id, description) values(4, '佳明 fēnix 6S Pro', 0, '腕式光学心率计和Pulse Ox脉搏血氧传感器,内置导航传感器,包括三轴电子罗盘,陀螺仪和气压高度,支持多个全球导航卫星系统(GPS、北斗、GLONASS)');

drop table if exists `category`;
create table `category` (
    `id` bigint not null comment 'id',
    `parent_id` bigint not null default 0 comment '父id',
    `name` varchar(50) not null comment '名称',
    `sort_flag` int comment '排序权重',
    `deleted` bigint(20) default 0 comment '0:Y,1:D',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='分类表';

insert into `category`(id, parent_id, name, sort_flag) values(100, 0, '数码', 5);
insert into `category`(id, parent_id, name, sort_flag) values(101, 100, '硬盘', 10);
insert into `category`(id, parent_id, name, sort_flag) values(102, 100, '可穿戴', 15);
insert into `category`(id, parent_id, name, sort_flag) values(200, 0, '玩具', 20);
insert into `category`(id, parent_id, name, sort_flag) values(201, 200, '可动', 25);
insert into `category`(id, parent_id, name, sort_flag) values(202, 200, '扭蛋', 30);
insert into `category`(id, parent_id, name, sort_flag) values(300, 0, '电玩', 35);
insert into `category`(id, parent_id, name, sort_flag) values(301, 300, '游戏', 40);
insert into `category`(id, parent_id, name, sort_flag) values(302, 300, '主机', 45);

drop table if exists `user`;
create table `user` (
    `id` bigint not null comment 'id',
    `login_name` varchar(50) not null comment '登录名',
    `name` varchar(50) not null comment '昵称',
    `password` varchar(50) not null comment '密码',
    primary key (`id`),
    unique key `login_name` (`login_name`)
) engine=innodb default charset=utf8mb4 comment='用户基本信息表';

drop table if exists `comment`;
create table `comment` (
    `id` bigint not null comment 'id',
    `goods_id` bigint not null comment '物品id',
    `user_id` bigint not null comment '用户id',
    `content` varchar(1024) not null default '' comment '内容',
    `create_time` bigint(20) not null default 0 comment '创建时间',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='评论表';

drop table if exists `reply`;
create table `reply` (
    `id` bigint not null comment 'id',
    `comment_id` bigint not null comment '评论id',
    `rela_id` bigint not null comment '关联id',
    `type` bigint not null comment '回复类型(1:评论;2:回复)',
    `from_uid` bigint not null,
    `to_uid` bigint not null,
    `content` varchar(1024) not null default '' comment '内容',
    `create_time` bigint(20) not null default 0 comment '创建时间',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='回复表';


update goods g join (select goods_id, count(1) comment_count from comment group by goods_id) c on g.id = c.goods_id set g.comment_count = c.comment_count;
