drop table if exists `message_log{#prefix1#}`;
CREATE TABLE `message_log{#prefix1#}` (
  `message_id` varchar(60) NOT NULL,
  `context_id` varchar(60) NOT NULL,
  `custom_tag` varchar(60) NOT NULL,
  `message_name` varchar(60) NOT NULL,
  `process_result` int(10) NOT NULL,
  `process_server_name` varchar(60) DEFAULT NULL,
  `process_type` int(10) DEFAULT NULL,
  `process_time_span` int(10) DEFAULT NULL,
  `process_log` varchar(3000) CHARACTER SET latin1 DEFAULT NULL,
  `begin_process_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_process_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `platform` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `message_log{#prefix2#}`;
CREATE TABLE `message_log{#prefix2#}` (
  `message_id` varchar(60) NOT NULL,
  `context_id` varchar(60) NOT NULL,
  `custom_tag` varchar(60) NOT NULL,
  `message_name` varchar(60) NOT NULL,
  `process_result` int(10) NOT NULL,
  `process_server_name` varchar(60) DEFAULT NULL,
  `process_type` int(10) DEFAULT NULL,
  `process_time_span` int(10) DEFAULT NULL,
  `process_log` varchar(3000) CHARACTER SET latin1 DEFAULT NULL,
  `begin_process_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_process_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `platform` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `message_log{#prefix3#}`;
CREATE TABLE `message_log{#prefix3#}` (
  `message_id` varchar(60) NOT NULL,
  `context_id` varchar(60) NOT NULL,
  `custom_tag` varchar(60) NOT NULL,
  `message_name` varchar(60) NOT NULL,
  `process_result` int(10) NOT NULL,
  `process_server_name` varchar(60) DEFAULT NULL,
  `process_type` int(10) DEFAULT NULL,
  `process_time_span` int(10) DEFAULT NULL,
  `process_log` varchar(3000) CHARACTER SET latin1 DEFAULT NULL,
  `begin_process_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_process_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `platform` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `message_log{#prefix4#}`;
CREATE TABLE `message_log{#prefix4#}` (
  `message_id` varchar(60) NOT NULL,
  `context_id` varchar(60) NOT NULL,
  `custom_tag` varchar(60) NOT NULL,
  `message_name` varchar(60) NOT NULL,
  `process_result` int(10) NOT NULL,
  `process_server_name` varchar(60) DEFAULT NULL,
  `process_type` int(10) DEFAULT NULL,
  `process_time_span` int(10) DEFAULT NULL,
  `process_log` varchar(3000) CHARACTER SET latin1 DEFAULT NULL,
  `begin_process_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_process_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `platform` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `message_log{#prefix5#}`;
CREATE TABLE `message_log{#prefix5#}` (
  `message_id` varchar(60) NOT NULL,
  `context_id` varchar(60) NOT NULL,
  `custom_tag` varchar(60) NOT NULL,
  `message_name` varchar(60) NOT NULL,
  `process_result` int(10) NOT NULL,
  `process_server_name` varchar(60) DEFAULT NULL,
  `process_type` int(10) DEFAULT NULL,
  `process_time_span` int(10) DEFAULT NULL,
  `process_log` varchar(3000) CHARACTER SET latin1 DEFAULT NULL,
  `begin_process_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_process_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `platform` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
