#!/bin/bash 
##################################################  
#author:zhouhuan 
#email:zhouhuan@corp.51xiaodou.com
##################################################  
# crontab  -e 
# */5 * * * * source /etc/profile && sh /listen/*.sh  
################################################## 
#接收者  
email_reciver="zhouhuan@corp.51xiaodou.com"  
#smtp服务器地址  
email_smtphost=smtp.ym.163.com  
#发送者邮箱  
email_sender=zhouhuan@corp.51xiaodou.com 
#邮箱用户名  
email_username=zhouhuan@corp.51xiaodou.com  
#邮箱密码  
email_password=zhouhuan123  
#服务器ip  
local_ip=`ifconfig|grep Bcast|awk -F: '{print $2}'|awk -F " " '{print $1}'|head -1`  
#主题  
email_title="服务器${local_ip}DOCKER状态"  
#日志地址 
LOG_FILE=/xiaodou-aos-client/logs/dockerlogs_`date +%F`.log  
#DOCKER运行状态监测命令
DOCKER_STATUS="service docker status| grep -a  'running'| wc -l"
source /etc/profile
echo "###################log at $(date +'%d-%m-%Y %H:%M:%S') ######################" >>$LOG_FILE
if  [[ `eval ${DOCKER_STATUS}` == 0 ]] ; then 
    #如果docker服务不是运行中，重启,等待后发送启动日志
    echo "docker restart" >>$LOG_FILE
    service docker restart 
    sleep 5
    #发送docker重启的邮件和记录日志
    sendEmail -f ${email_sender} -t ${email_reciver} -s ${email_smtphost} -u ${email_title} -xu ${email_username} -xp ${email_password} -m $(service docker status)   
else 
    echo "docker is running" >>$LOG_FILE
fi
if  [[ `eval ${DOCKER_STATUS}` == 0 ]] ; then 
    #如果docker服务不是运行中，直接发送错误邮件到用户
    sendEmail -f ${email_sender} -t ${email_reciver} -s ${email_smtphost} -u "服务器${local_ip}docker严重问题无法恢复" -xu ${email_username} -xp ${email_password} -m "服务器${local_ip}docker出现严重问题，系统无法自动恢复，请登录服务器查看并解决"   
fi
