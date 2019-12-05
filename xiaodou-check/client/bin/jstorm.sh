#!/bin/bash 
##################################################  
#author:zhouhuan 
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  

if [[  -n `eval $jstorm_nimbus_start` && -n `eval $jstorm_tepology_process` ]];then
   echo "jstorm is running"
   exit 0
else
   echo "jstorm is down"
   if [[ -z `eval $jstorm_nimbus_process` ]];then
        echo "jstorm_nimbus restart"
        eval ${jstorm_nimbus_start}
   fi
   if [[ -z `eval $jstorm_tepology_process` ]];then
        echo "jstorm_tepology restart"
        eval ${jstorm_topology_start}
   fi
   echo "################### jstorm restart at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$jstorm_log_file
   sendMail "${local_ip} jstorm restart at $(date +'%Y-%m-%d %H:%M:%S')" "${local_ip} jstorm restart at $(date +'%Y-%m-%d %H:%M:%S')"
   echo "jstorm is restart" >>$jstorm_log_file
   if [[ -n `eval $jstorm_nimbus_start` && -n `eval $jstorm_tepology_process` ]];then
       echo "jstorm  start success"  >> $jstorm_log_file
   else
       echo "jstorm  start fail"  >> $jstorm_log_file
       sendMail "${local_ip} jstorm重启失败，请登录服务器查看" "${local_ip} jstorm重启失败，请登录服务器查看"  
   fi 
fi

######################END########################## 
