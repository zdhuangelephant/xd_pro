######################COMMON######################
email_reciver="zhouhuan@corp.51xiaodou.com,lidehong@corp.51xiaodou.com,zhaodan@corp.51xiaodou.com,huangzedong@corp.51xiaodou.com"
email_smtphost=smtp.ym.163.com
email_sender=monitor-alarm@corp.51xiaodou.com
email_username=monitor-alarm@corp.51xiaodou.com
email_password=fae64b580af7
local_ip=`ifconfig eth0|grep addr:|grep -v 127.0.0.1| awk '{print $2}'|awk -F ':' '{print $2}'`
base_address=/listen/shell
######################END#########################  
