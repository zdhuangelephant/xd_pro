#!/bin/bash
##path##
basedir=/listen/shell
baseop=xiaodou-check
zipfile=`ls|grep zip`

function _deploy
{
	rm -rf /listen/shell 
	unzip `ls|grep zip` -d /listen/ && mv /listen/client  /listen/shell
	mkdir -p /listen/xiaodou-check/conf/
	cd /listen/xiaodou-check/conf
	unzip `ls|grep zip` 
	cp -rf /listen/xiaodou-check/conf/*  /listen/shell/
	cd /listen/shell
	chmod u+x monitor_start.sh
}

function _crontab
{
    sed -i "/monitor_start.sh/d" /var/spool/cron/root  
	crontab -l > /tmp/.root_crontab.bak
	echo "0 */2 * * * source /etc/profile && /listen/shell/monitor_start.sh" >> /tmp/.root_crontab.bak
	crontab /tmp/.root_crontab.bak
	rm -rf /tmp/.root_crontab.bak
	service crond restart
}

_deploy
_crontab
