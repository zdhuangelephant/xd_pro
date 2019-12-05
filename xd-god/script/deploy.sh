#!/bin/bash
##path##
basedir=/home/work/xiaodou
baseop=xd-god
##server_info##
tomcat=tomcat_8098
shutdown_port=7098
http_port=8098
ajp_port=9098
ser_base_r_tmp=${basedir}/${baseop}
ser_base_r="${ser_base_r_tmp//\//\\/}"
##one_apm##
license_key=WQMIVFBaXlYa09eHSlZADVkdDBbcffRcVUlQU1dQH2f851YPGFAHHVBQa51bAQ9IUlVLVwo=

SRCFILE=/home/work/tmp/template

function init_env
{
	if [ ! -f "$SRCFILE.zip" ]; then 
	 echo "can't find $SRCFILE.zip"
	 return $?
	fi
	mkdir -p $basedir
	cd $basedir
	echo mkdir $basedir/$baseop
	mkdir -p $baseop
	if [ ! -d "$basedir/$tomcat" ]; then 
		unzip -n $SRCFILE -d $basedir/$tomcat > /dev/null
		chmod u+wx -R $basedir/$tomcat/*
		sed -i 's/{#SHUTDOWN_PORT#}/'$shutdown_port'/' $basedir/$tomcat/conf/server.xml && sed -i  's/{#HTTP_PORT#}/'$http_port'/' $basedir/$tomcat/conf/server.xml && sed -i 's/{#AJP_PORT#}/'$ajp_port'/' $basedir/$tomcat/conf/server.xml && sed -i 's/{#APP_BASE#}/'$ser_base_r'/' $basedir/$tomcat/conf/server.xml && sed -i 's/{#PROJECT_NAME#}/'$baseop'/' $basedir/$tomcat/conf/server.xml
		sed -i 's/{#LICENSE_KEY#}/'$license_key'/' $basedir/$tomcat/OneAPM/oneapm.properties && sed -i 's/{#APP_NAME#}/'$baseop'/' $basedir/$tomcat/OneAPM/oneapm.properties
	fi
}

function deploy
{
	cd /home/work/xiaodou/$baseop
	/home/work/xiaodou/java/bin/jar xvf /home/work/xiaodou/$baseop/*.war > /dev/null 2>&1
	cp -rf /home/work/xiaodou/$baseop/conf/* /home/work/xiaodou/$baseop/WEB-INF/classes/conf/custom/env
	#rm -rf /home/work/xiaodou/$baseop/*.war
	sh /home/work/xiaodou/$tomcat/bin/stop_tomcat.sh
	sh /home/work/xiaodou/$tomcat/bin/start_tomcat.sh
}

init_env
deploy
