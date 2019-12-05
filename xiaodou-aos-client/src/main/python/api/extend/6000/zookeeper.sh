#!/bin/bash 
##################################################  
#author:zhouhuan  
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  
######################ZOOKEEPER#######################
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
#ZOOKEEPER检测命令
zookeeper_cmd='echo ""|telnet ${local_ip} ${projectPort} 2>/dev/null|grep "\^]"|wc -l'
#日志地址 
zookeeper_log=$baseDir/../logs/zookeeper_logs_`date +%F`.log
#ZOOKEEPER重启命令
zookeeper_restart="sh ${projectDir}/bin/zkServer.sh restart"


######################END#########################

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
