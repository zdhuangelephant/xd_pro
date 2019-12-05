drop table if exists xd_cront_monitor_api_log;

/*==============================================================*/
/* Table: xd_cront_monitor_api_log                              */
/*==============================================================*/
create table xd_cront_monitor_api_log
(
   id                   varchar(40) not null comment '主键ID',
   api_id               varchar(40) not null comment '监控项ID',
   result               varchar(10) not null comment '执行结果',
   message              varchar(2000) comment '错误信息',
   create_time          timestamp not null default '0000-00-00 00:00:00' comment '执行时间',
   _timestamp           timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '时间戳',
   primary key (id)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table xd_cront_monitor_api_log comment '定时监控任务执行记录表';
