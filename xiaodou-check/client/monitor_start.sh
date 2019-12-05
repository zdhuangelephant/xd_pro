#!/bin/bash
base_address=/listen/shell
#配置
source $base_address/initEnv.sh
#公共方法
source $base_address/utils/util.sh
#调用
source $base_address/bin/mysql.sh 
source $base_address/bin/redis.sh 
source $base_address/bin/mongodb.sh 
source $base_address/bin/flume.sh
source $base_address/bin/rabbitmq.sh
source $base_address/bin/zookeeper.sh
source $base_address/bin/jstorm.sh