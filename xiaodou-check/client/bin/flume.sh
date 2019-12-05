#!/bin/bash 
##################################################  
#author:zhouhuan 
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  

if [[ `eval ${flume_check}` == 200 ]];then
   echo "flume is running! " 
else
   echo "flume is down! "
   if [[ -n $flume_process_check ]];then
      echo $flume_process_check
      kill -9 $flume_process_check
      sleep 2
   fi
   eval $flume_start
   echo "################### flume restart at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$flume_log_file
   sendMail "${local_ip} flume restart $(date +'%Y-%m-%d %H:%M:%S')" "${local_ip} flume restart at $(date +'%Y-%m-%d %H:%M:%S')"
   sleep 10
   if [[ `eval ${flume_check}` == 200 ]];then
       echo "flume  start success" >>$flume_log_file
   else
       echo "flume  start fail" >>$flume_log_file
       sendMail "${local_ip} flume重启失败，请登录服务器查看" "${local_ip} flume重启失败，请登录服务器查看"  
   fi 
fi

######################end######################### 
