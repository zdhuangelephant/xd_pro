#!/bin/bash 
##################################################  
#author:zhouhuan 
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  
######################MONGO#######################
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
mongo_log_file= $baseDir/../logs/mongo_logs_`date +%F`.log
#MONGO状态监测命令
mongo_check="curl -i -m 10 -o /dev/null -s -w %{http_code} http://${local_ip}:${projectPort}/"
#MONGO进程号检测
mongo_process_check=`ps x | grep ${projectDir}/bin/mongod | grep -v grep | grep -v $0  | awk '{print $1}'`
#MONGO启动命令
mongo_start="${projectDir}/bin/mongod -dbpath ${projectDir}/data/   -logpath  ${projectDir}/log/mongodb.log --logappend &"

######################END#########################
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
