drop table if exists `goods`;
create table `goods` (
    `id` bigint(20) not null comment 'id',
    `name` varchar(50) not null default '' comment '名称',
    `pid` bigint not null comment '分类id',
    `description` varchar(200) not null default '' comment '描述',
    `cover` varchar(200) not null default '' comment '封面',
    `view_count` bigint(20) not null default 0 comment '浏览数',
    `follow_count` bigint(20) not null default 0 comment '关注数',
    `create_time` bigint(20) not null default 0 comment '创建时间',
    `update_time` bigint(20) not null default 0 comment '更新时间',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='物品表';

insert into `goods`(id, name, pid, description) values(1, '闪迪至尊极速™ 移动固态硬盘 V2', 0, '这款大容量移动固态硬盘提供快速的NVMe™固态性能，具有1050MB/秒2的读取速度和1000MB/秒2的写入速度');
insert into `goods`(id, name, pid, description) values(2, 'WD_BLACK P10 Game Drive', 0, 'WD_BLACK™ P10 Game Drive 配备高达 5TB 的存储空间，可以保存多达125个游戏');
insert into `goods`(id, name, pid, description) values(3, 'Mac Mini', 0, 'M1芯片,配备两个超高速的雷雳/USB4端口,两个USB-A端口,HDMI2.0,Wi-Fi6及千兆以太网');
insert into `goods`(id, name, pid, description) values(4, 'Xiaomi Watch Color 2', 0, '轻量化设计,表体重量36.3g,内置高端双频 GNSS 芯片,支持GPS北斗,GLONASS,Galileo四大全球卫星定位系统同时定位');





