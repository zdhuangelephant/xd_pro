#!/bin/bash 
##################################################  
#author:zhouhuan  
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  
######################REDIS#######################
baseDir=`pwd`
source $baseDir/config.sh
source $baseDir/../src/main/python/api/extend/6000/util.sh 
#账号 
userName=$1
#密码
passWord=$2
#应用地址
projectDir=$3
#应用端口号
projectPort=$4
#REDIS检测命令
redis_check=`echo ""|telnet ${local_ip} ${projectPort} 2>/dev/null|grep "\^]"|wc -l`
#REDIS内存控制
max_redis_memory_check="echo 'config get maxmemory' | ${projectDir}/src/redis-cli -a ${passWord}| grep -v 'maxmemory'"
used_redis_memory_check="${projectDir}/src/redis-cli  -a ${passWord} info | grep 'used_memory' | egrep -v 'used_memory_' | sed 's/used_memory://g'"
#日志地址 
redis_log_file=$baseDir/../logs/redis_logs_`date +%F`.log
#REDIS启动命令
redis_start="sh /etc/init.d/redis"

######################END#########################
if [  $redis_check -eq 0 ];then
   echo "redis is down"
   eval $redis_start
   echo "###################redis restart at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$redis_log_file
   sendMail "${local_ip} redis restart at $(date +'%Y-%m-%d %H:%M:%S')"  "${local_ip}redis restart at $(date +'%Y-%m-%d %H:%M:%S')" 
   sleep 10
else
   echo "redis is running"
fi

max_redis_memory=`eval $max_redis_memory_check`
used_redis_memory=`eval $used_redis_memory_check`
if [[ $max_redis_memory -ne 0 ]];then
    percent_redis_memory=`awk 'BEGIN{printf "%d\n",'$used_redis_memory'/'$max_redis_memory'*100}'`
    if [ $percent_redis_memory -ge 80 ];then
        echo "###################redis_memory is ${percent_redis_memory} at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$redis_log_file   
        sendMail "${local_ip}redis内存使用超标，请登录服务器解决" "${local_ip}redis内存使用超标，请登录服务器解决"
    fi
else
    echo "redis status is unknown at $(date +'%Y-%m-%d %H:%M:%S')" >>$redis_log_file
    sendMail "${local_ip} redis状态异常" "${local_ip} redis状态异常，请登录服务器解决"
fi

######################end######################### 
