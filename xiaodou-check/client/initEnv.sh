#!/bin/bash
##################################################  
#author:zhouhuan  
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/check.sh  
##################################################  



######################COMMON######################
email_reciver="zhouhuan@corp.51xiaodou.com,lidehong@corp.51xiaodou.com,zhaodan@corp.51xiaodou.com,huangzedong@corp.51xiaodou.com"
email_smtphost=smtp.ym.163.com
email_sender=monitor-alarm@corp.51xiaodou.com
email_username=monitor-alarm@corp.51xiaodou.com
email_password=fae64b580af7
local_ip=`ifconfig eth0|grep addr:|grep -v 127.0.0.1| awk '{print $2}'|awk -F ':' '{print $2}'`

######################END#########################  



######################MYSQL#######################
 
#MYSQL账号 
mysql_user=xddev  
#MYSQL密码
mysql_pass=xddev@xiaodou 
#日志地址 
mysql_log_file=$base_address/logs/mysql_logs_`date +%F`.log  
#MYSQL状态监测命令
mysql_check="mysql -u$mysql_user -p$mysql_pass" 
#MYSQL启动命令
mysql_start="service mysqld restart"


######################END#########################
 
 
######################REDIS#######################

#REDIS检测命令
redis_check=`echo ""|telnet ${local_ip} 6379 2>/dev/null|grep "\^]"|wc -l`
#REDIS内存控制
max_redis_memory_check="echo 'config get maxmemory' | /lib/redis/redis-2.8.9/src/redis-cli -a redis@51xiaodou| grep -v 'maxmemory'"
used_redis_memory_check="/lib/redis/redis-2.8.9/src/redis-cli  -a redis@51xiaodou info | grep 'used_memory' | egrep -v 'used_memory_' | sed 's/used_memory://g'"
#日志地址 
redis_log_file=$base_address/logs/redis_logs_`date +%F`.log
#REDIS启动命令
redis_start="sh /etc/init.d/redis"

######################END#########################


######################MONGO#######################

#日志地址 
mongo_log_file=$base_address/logs/mongo_logs_`date +%F`.log
#MONGO状态监测命令
mongo_check="curl -i -m 10 -o /dev/null -s -w %{http_code} http://119.61.66.56:27017/"
#MONGO进程号检测
mongo_process_check=`ps x | grep /home/mongodb/mongodb/bin/mongod | grep -v grep | grep -v $0  | awk '{print $1}'`
#MONGO启动命令
mongo_start="sh /etc/init.d/mongo"

######################END#########################


######################FLUME#######################

#日志地址 
flume_log_file=$base_address/logs/flume_logs_`date +%F`.log
#FLUME状态监测命令
flume_check="curl -i -m 10 -o /dev/null -s -w %{http_code} http://${local_ip}:5871/metrics"
#FLUME进程号检测
flume_process_check=`ps x | grep /usr/local/flume | grep -v grep | grep -v $0  | awk '{print $1}'`
#FLUME启动命令
flume_start="/etc/init.d/flume"

######################END#########################

######################RABBITMQ#######################

#RABBITMQ账号 
rabbitmq_user=guest  
#RABBITMQ密码
rabbitmq_pass=guest 
#RABBITMQ检测命令
rabbitmq_cmd="curl -i -u ${rabbitmq_user}:${rabbitmq_pass} -m 10 -o /dev/null -s -w %{http_code} http://${local_ip}:15672/api/vhosts"
#RABBITMQ的环境变量  
export RABBITMQPATH=/usr/lib/rabbitmq/bin  
#日志地址 
rabbitmq_log_file=$base_address/logs/rabbitmq_logs_`date +%F`.log  
#堵塞最大数量  
maxNum=1000 
#RABBITMQ启动命令
rabbitmq_start="service rabbitmq-server restart"

######################END#########################


######################ZOOKEEPER#######################

#ZOOKEEPER检测命令
zookeeper_cmd='echo ""|telnet ${local_ip} 2181 2>/dev/null|grep "\^]"|wc -l'
#日志地址 
zookeeper_log=$base_address/logs/zookeeper_logs_`date +%F`.log
#ZOOKEEPER重启命令
zookeeper_restart="sh /home/zkjs/zookeeper-3.4.6/bin/zkServer.sh restart"


######################END#########################


######################JSTORM######################

#日志地址 
jstorm_log_file=$base_address/logs/jstorm_logs_`date +%F`.log
#JSTORM进程号检测
jstorm_nimbus_process="ps x | grep com.alibaba.jstorm.daemon.nimbus.NimbusServer | grep -v grep | grep -v $0  | awk '{print $1}'"
jstorm_tepology_process="ps x | grep com.xiaodou.logmonitor.MainTopology | grep -v grep | grep -v $0  | awk '{print $1}'"
#JSTORM启动命令
jstorm_nimbus_start="sh /home/zkjs/jstorm-2.1.1/bin/start.sh restart"
jstorm_topology_start="nohup /home/zkjs/jstorm-2.1.1/bin/jstorm jar /home/work/xiaodou/xd-jstorm2b/xd-jstorm2b.jar com.xiaodou.logmonitor.MainTopology > /home/work/xiaodou/xd-jstorm2b/myout.file 2>&1 &"

######################END#########################
