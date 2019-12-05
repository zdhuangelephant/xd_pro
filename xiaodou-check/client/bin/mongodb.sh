#!/bin/bash 
##################################################  
#author:zhouhuan 
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  

if [[ `eval ${mongo_check}` == 200 ]];then
   echo "mongodb is running! " 
else
   echo "mongodb is down! "
   if [[ -n $mongo_process_check ]];then
      echo $mongo_process_check
      kill -9 $mongo_process_check
      sleep 2
   fi
   eval $mongo_start
   echo "################### mongodb restart at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$mongo_log_file
   sendMail "${local_ip} mongodb restart at $(date +'%Y-%m-%d %H:%M:%S')" "${local_ip} mongodb restart at $(date +'%Y-%m-%d %H:%M:%S')"
   echo "mongodb is restart" >>$mongo_log_file
   sleep 40
   if [[ `eval ${mongo_check}` == 200 ]];then
       echo "mongodb  start success" >>$mongo_log_file
   else
       echo "mongodb  start fail" >>$mongo_log_file
	   echo "################### mongodb restart fail at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$mongo_log_file
       sendMail "${local_ip} mongodb重启失败，请登录服务器查看" "${local_ip} mongodb重启失败，请登录服务器查看"   
   fi 
fi

######################end######################### 
