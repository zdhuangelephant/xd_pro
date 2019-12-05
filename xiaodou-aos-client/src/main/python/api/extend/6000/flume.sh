#!/bin/bash 
##################################################  
#author:zhouhuan 
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  

######################FLUME#######################
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
#日志地址 
flume_log_file=$base_address/logs/flume_logs_`date +%F`.log
#FLUME状态监测命令
flume_check="curl -i -m 10 -o /dev/null -s -w %{http_code} http://${local_ip}:${projectPort}/metrics"
#FLUME进程号检测
flume_process_check=`ps x | grep ${projectDir} | grep -v grep | grep -v $0  | awk '{print $1}'`
#FLUME启动命令
flume_start="${projectDir}/bin/flume-ng agent -c ${projectDir}/conf -f ${projectDir}/conf/flume-conf.conf -n agent -Dflume.root.logger=INFO,console -Dflume.monitoring.type=http -Dflume.monitoring.port=${projectPort} LOGFILE &"

######################END#########################
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
