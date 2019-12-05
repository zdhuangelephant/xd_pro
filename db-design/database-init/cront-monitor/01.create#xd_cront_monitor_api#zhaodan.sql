drop table if exists xd_cront_monitor_api;

/*==============================================================*/
/* Table: xd_cront_monitor_api                                  */
/*==============================================================*/
create table xd_cront_monitor_api
(
   id                   varchar(40) not null comment '主键ID',
   name                 varchar(300) not null comment '监控项',
   protocol             varchar(10) default 'http' comment '请求协议',
   format               varchar(10) default 'normal' comment '参数格式',
   url                  varchar(300) comment '请求地址',
   method               varchar(6) default 'get' comment '请求方法',
   params               varchar(2000) comment '参数列表',
   time_out             int default 5000 comment '超时时间',
   retry_time           tinyint default 1 comment '重试次数',
   create_time          timestamp default '0000-00-00 00:00:00' comment '创建时间',
   update_time          timestamp default '0000-00-00 00:00:00' comment '更新时间',
   _timestamp           timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '时间戳',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table xd_cront_monitor_api comment '定时监控任务';
