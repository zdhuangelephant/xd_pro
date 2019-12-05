#!/bin/bash 
##################################################  
#author:zhouhuan  
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  


zookeeper_status=`eval $zookeeper_cmd`
if [[ $zookeeper_status -eq 1 ]];then
   echo "zookeeper is running"
else 
   echo "zookeeper is down"
   eval ${zookeeper_restart}
   echo "###################zookeeper restart at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$zookeeper_log  
   sendMail  "${local_ip} zookeeper restart at $(date +'%Y-%m-%d %H:%M:%S')"  "${local_ip} zookeeper restart at $(date +'%Y-%m-%d %H:%M:%S')"   
   sleep 10
   zookeeper_status=`eval $zookeeper_cmd`
   if [[ $zookeeper_status -eq 0 ]];then
     sendMail  "${local_ip} zookeeper重启失败，请登录服务器解决"  "${local_ip} zookeeper重启失败，请登录服务器解决"
   fi
fi

######################end######################### 
