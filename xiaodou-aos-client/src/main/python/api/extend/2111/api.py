#!/usr/bin/python
# coding:utf8


import os
import sys
import json
import base64
import nginx
from xdrpc_server.AosServerService import AosServerService
from util.mutil import MachineUtil
from xdrpc_server.AosServerService import AosServerService
from util.mutil import FileUtils, MachineUtil, LogUtil
def _execute(command):
	config = FileUtils.get_instance('config.ini')
        nginx_conf_dir = config.get_prop("nginx-config", "nginx_conf_dir")
        nginx_conf_dir_bak = config.get_prop("nginx-config", "nginx_conf_dir_bak")
	os.system('mkdir -p ' + nginx_conf_dir)
	os.system('cp -r ' + nginx_conf_dir + nginx_conf_dir_bak)
	os.system('rm -rf ' + nginx_conf_dir +'*')
	state='0'
	try:
	    for k1 in command:
	    	upstreamName = k1["upstreamName"]
	    	upstreamNode = json.loads(k1["upstreamNode"])
		fileName=nginx_conf_dir+upstreamName+'.conf'
            	os.system('touch '+fileName)    	
	    	c = nginx.Conf() 
	    	LogUtil.info("Node信息:%s" % (upstreamNode))
		t=''
	    	for k2 in range(len(upstreamNode)):
			if(k2==0):
				t='server'+' '+upstreamNode[k2]["ipAdress"]+' '+upstreamNode[k2]["strategy"]+';\n'
			if(0<k2<len(upstreamNode)-1):
				t=t+'     '+'server'+' '+upstreamNode[k2]["ipAdress"]+' '+upstreamNode[k2]["strategy"]+';\n'
        	        if(k2==len(upstreamNode)-1):
        	                t=t+'     '+'server'+' '+upstreamNode[k2]["ipAdress"]+' '+upstreamNode[k2]["strategy"]
      	        ipHash = k1["ipHash"]
   	 	if(ipHash=='1'):
    			u = nginx.Upstream(upstreamName,
    				nginx.Key('',t),
    				nginx.Key('','ip_hash'))
			c.add(u)
	    	if(ipHash=='0'):
	    		u = nginx.Upstream(upstreamName,
	    			nginx.Key('',t))
    	    	        c.add(u)
       	        s = nginx.Server()
		s.add(
	     		nginx.Key('listen',k1["nginxListenPort"]),
	     		nginx.Key('server_name', k1["nginxServerName"]),
	     		nginx.Key('access_log', k1["accessLog"]),
	     		nginx.Location('= /',
	                nginx.Key('proxy_pass', 'http://'+k1["upstreamName"]),
	          	nginx.Key('proxy_redirect', 'off'),
	          	nginx.Key('proxy_set_header', 'Host    $host'),
	          	nginx.Key('proxy_set_header', 'X-Real-IP  $remote_addr'),
	          	nginx.Key('proxy_set_header', 'X-Forwarded-For   $proxy_add_x_forwarded_for')
	     		)
		)
        	c.add(s)
    		nginx.dumpf(c, fileName)
            LogUtil.info("完成Nginx配置")
	    output = os.popen('service nginx restart').read();
 	    state='1'
	    param = ["{\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"state\":\"" + state + "\",\"msg\":\"" + output + "\"}"]
            os.system('rm -rf ' + nginx_conf_dir_bak)
            AosServerService.route_service("ThriftApiService", "nginxLog", param)
        except Exception, ex:
		LogUtil.error(ex)
		os.system('rm -rf ' + nginx_conf_dir)
		os.system('mv ' + nginx_conf_dir_bak + nginx_conf_dir)
		os.system('service nginx restart')
 		output = ex.message;
 		state='2'
	        param = ["{\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"state\":\"" + state + "\",\"msg\":\"" + output + "\"}"]
		AosServerService.route_service("ThriftApiService", "nginxLog", param)
