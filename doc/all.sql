drop table if exists `goods`;
create table `goods` (
    `id` bigint(20) not null comment 'id',
    `name` varchar(50) not null default '' comment '名称',
    `pid` bigint not null comment '分类id',
    `desc` varchar(200) not null default '' comment '描述',
    `cover` varchar(200) not null default '' comment '封面',
    `view_count` bigint(20) not null default 0 comment '浏览数',
    `follow_count` bigint(20) not null default 0 comment '关注数',
    `status` tinyint(1) not null default '0' comment '0:N,1:U,2:D,3:R',
    `create_time` bigint(20) not null default 0 comment '创建时间',
    `update_time` bigint(20) not null default 0 comment '更新时间',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='物品表'

insert into `goods`(id, name, pid) values(1, '九号电动B80', 0);
insert into `goods`(id, name, pid) values(2, '西数WDBlack P10', 0);



