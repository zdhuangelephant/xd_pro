#!/bin/bash 
##################################################  
#author:zhouhuan 
#email:zhouhuan@corp.51xiaodou.com
##################################################

$mysql_check -e "select version();" >/dev/null 2>&1  
if [ $? -eq 0 ]   
then 
echo "mysql is running! " 
else 
echo "mysql is down! "
echo "###################mysql is restart at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$mysql_log_file 
eval ${mysql_start} >$mysql_log_file
sleep 5;  
$mysql_check -e "select version();" >/dev/null 2>&1  
if [ $? -ne 0 ]  
then   
for num in `seq 10`#
do  
killall mysqld>/dev/null 2>&1   
[ $? -ne 0 ] && break;  
sleep 2  
done  
echo "###################mysql is restart at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$mysql_log_file
eval ${mysql_start} >>$mysql_log_file   
fi  
$mysql_check -e "select version();" >/dev/null 2>&1 && Status="started" || Status="unknown"  
echo "################### mysql restart is $Status at $(date +'%Y-%m-%d %H:%M:%S') ######################" >>$mysql_log_file
sendMail "${local_ip} mysql restart status is $Status" "${local_ip} mysql restart status is $Status"
fi  


######################end######################### 
