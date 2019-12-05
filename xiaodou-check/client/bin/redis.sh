#!/bin/bash 
##################################################  
#author:zhouhuan  
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  

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
