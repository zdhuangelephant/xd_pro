drop table if exists `message_body{#prefix1#}`;
CREATE TABLE `message_body{#prefix1#}` (
  `message_id` varchar(60) DEFAULT NULL,
  `context_id` varchar(60) DEFAULT NULL,
  `custom_tag` varchar(60) DEFAULT NULL,
  `message_name` varchar(60) DEFAULT NULL,
  `from_class` varchar(200) DEFAULT NULL,
  `message_data_type` varchar(60) DEFAULT NULL,
  `message_data` varchar(6000) DEFAULT NULL,
  `message_size` int(10) DEFAULT NULL,
  `message_send_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `message_receive_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `send_server_name` varchar(100) DEFAULT NULL,
  `queue_name` varchar(100) DEFAULT NULL,
  `route_key` varchar(60) DEFAULT NULL,
  `process_server_name` varchar(60) DEFAULT NULL,
  `process_status` int(5) DEFAULT NULL,
  `result` int(5) DEFAULT NULL,
  `failed_count` int(5) DEFAULT NULL,
  `priority` int(5) DEFAULT NULL,
  `begin_process_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `end_process_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `platform` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `message_body{#prefix2#}`;
CREATE TABLE `message_body{#prefix2#}` (
  `message_id` varchar(60) DEFAULT NULL,
  `context_id` varchar(60) DEFAULT NULL,
  `custom_tag` varchar(60) DEFAULT NULL,
  `message_name` varchar(60) DEFAULT NULL,
  `from_class` varchar(200) DEFAULT NULL,
  `message_data_type` varchar(60) DEFAULT NULL,
  `message_data` varchar(6000) DEFAULT NULL,
  `message_size` int(10) DEFAULT NULL,
  `message_send_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `message_receive_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `send_server_name` varchar(100) DEFAULT NULL,
  `queue_name` varchar(100) DEFAULT NULL,
  `route_key` varchar(60) DEFAULT NULL,
  `process_server_name` varchar(60) DEFAULT NULL,
  `process_status` int(5) DEFAULT NULL,
  `result` int(5) DEFAULT NULL,
  `failed_count` int(5) DEFAULT NULL,
  `priority` int(5) DEFAULT NULL,
  `begin_process_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `end_process_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `platform` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `message_body{#prefix3#}`;
CREATE TABLE `message_body{#prefix3#}` (
  `message_id` varchar(60) DEFAULT NULL,
  `context_id` varchar(60) DEFAULT NULL,
  `custom_tag` varchar(60) DEFAULT NULL,
  `message_name` varchar(60) DEFAULT NULL,
  `from_class` varchar(200) DEFAULT NULL,
  `message_data_type` varchar(60) DEFAULT NULL,
  `message_data` varchar(6000) DEFAULT NULL,
  `message_size` int(10) DEFAULT NULL,
  `message_send_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `message_receive_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `send_server_name` varchar(100) DEFAULT NULL,
  `queue_name` varchar(100) DEFAULT NULL,
  `route_key` varchar(60) DEFAULT NULL,
  `process_server_name` varchar(60) DEFAULT NULL,
  `process_status` int(5) DEFAULT NULL,
  `result` int(5) DEFAULT NULL,
  `failed_count` int(5) DEFAULT NULL,
  `priority` int(5) DEFAULT NULL,
  `begin_process_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `end_process_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `platform` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `message_body{#prefix4#}`;
CREATE TABLE `message_body{#prefix4#}` (
  `message_id` varchar(60) DEFAULT NULL,
  `context_id` varchar(60) DEFAULT NULL,
  `custom_tag` varchar(60) DEFAULT NULL,
  `message_name` varchar(60) DEFAULT NULL,
  `from_class` varchar(200) DEFAULT NULL,
  `message_data_type` varchar(60) DEFAULT NULL,
  `message_data` varchar(6000) DEFAULT NULL,
  `message_size` int(10) DEFAULT NULL,
  `message_send_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `message_receive_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `send_server_name` varchar(100) DEFAULT NULL,
  `queue_name` varchar(100) DEFAULT NULL,
  `route_key` varchar(60) DEFAULT NULL,
  `process_server_name` varchar(60) DEFAULT NULL,
  `process_status` int(5) DEFAULT NULL,
  `result` int(5) DEFAULT NULL,
  `failed_count` int(5) DEFAULT NULL,
  `priority` int(5) DEFAULT NULL,
  `begin_process_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `end_process_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `platform` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists `message_body{#prefix5#}`;
CREATE TABLE `message_body{#prefix5#}` (
  `message_id` varchar(60) DEFAULT NULL,
  `context_id` varchar(60) DEFAULT NULL,
  `custom_tag` varchar(60) DEFAULT NULL,
  `message_name` varchar(60) DEFAULT NULL,
  `from_class` varchar(200) DEFAULT NULL,
  `message_data_type` varchar(60) DEFAULT NULL,
  `message_data` varchar(6000) DEFAULT NULL,
  `message_size` int(10) DEFAULT NULL,
  `message_send_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `message_receive_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `send_server_name` varchar(100) DEFAULT NULL,
  `queue_name` varchar(100) DEFAULT NULL,
  `route_key` varchar(60) DEFAULT NULL,
  `process_server_name` varchar(60) DEFAULT NULL,
  `process_status` int(5) DEFAULT NULL,
  `result` int(5) DEFAULT NULL,
  `failed_count` int(5) DEFAULT NULL,
  `priority` int(5) DEFAULT NULL,
  `begin_process_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `end_process_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `platform` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;