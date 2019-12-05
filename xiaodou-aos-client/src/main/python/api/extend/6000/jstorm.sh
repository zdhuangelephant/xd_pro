#!/bin/bash 
##################################################  
#author:zhouhuan 
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
##################################################  
######################JSTORM######################
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
jstorm_log_file= $baseDir/../logs/jstorm_logs_`date +%F`.log
#JSTORM进程号检测
jstorm_nimbus_process="ps x | grep com.alibaba.jstorm.daemon.nimbus.NimbusServer | grep -v grep | grep -v $0  | awk '{print $1}'"
jstorm_tepology_process="ps x | grep com.xiaodou.logmonitor.MainTopology | grep -v grep | grep -v $0  | awk '{print $1}'"
#JSTORM启动命令
jstorm_nimbus_start="sh ${projectDir}/bin/start.sh restart"
jstorm_topology_start="nohup ${projectDir}/bin/jstorm jar /home/work/xiaodou/xd-jstorm2b/xd-jstorm2b.jar com.xiaodou.logmonitor.MainTopology > /home/work/xiaodou/xd-jstorm2b/myout.file 2>&1 &"
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
