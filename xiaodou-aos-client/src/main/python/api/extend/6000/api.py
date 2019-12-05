#!/usr/bin/python
# coding:utf8


import os
import sys
import json
import base64
from xdrpc_server.AosServerService import AosServerService
from util.mutil import FileUtils, MachineUtil, LogUtil

def _execute(command):
    LogUtil.info("程序检测中")
    mac = MachineUtil.get_mac_address()
    param = ["{\"mac\":\"" + mac + "\"}"]
    s1 = AosServerService.route_service("ThriftApiService", "getMonitoringData", param);
    d1 = json.loads(s1)
    for k1 in d1:
	    if k1["serverType"] == "5001":
                os.popen("sh " + sys.path[0] + "/api/extend/6000/mysql.sh " + k1["serverUserName"] + ' ' +k1["serverPassword"] + ' ' + k1["projectDir"] + ' ' + k1["projectPort"] ).read();
	    if k1["serverType"] == "5002":
                os.popen("su - " + k1["user"] + " -s /bin/bash " + sys.path[0] + "/api/extend/6000/redis.sh " + k1["serverUserName"] + ' ' +k1["serverPassword"] + ' ' + k1["projectDir"] + ' ' + k1["projectPort"] ).read();
	    if k1["serverType"] == "5003":
                os.popen("su - " + k1["user"] + " -s /bin/bash " + sys.path[0] + "/api/extend/6000/mongodb.sh " + k1["serverUserName"] + ' ' +k1["serverPassword"] + ' ' + k1["projectDir"] + ' '   + k1["projectPort"] ).read();
	    if k1["serverType"] == "5004":
                os.popen("su - " + k1["user"] + " -s /bin/bash " + sys.path[0] + "/api/extend/6000/flume.sh " + k1["serverUserName"] + ' ' +k1["serverPassword"] + ' ' + k1["projectDir"] + ' '   + k1["projectPort"] ).read();
            if k1["serverType"] == "5005":
		LogUtil.info("检测rabbitMQ")
                os.popen("sh " + sys.path[0] + "/api/extend/6000/rabbitmq.sh " + k1["serverUserName"] + ' ' +k1["serverPassword"] + ' ' + k1["projectDir"] + ' '  + k1["projectPort"] ).read();
	    if k1["serverType"] == "5006":
                os.popen("su - " + k1["user"] + " -s /bin/bash " + sys.path[0] + "/api/extend/6000/zookeeper.sh " + k1["serverUserName"] + ' ' +k1["serverPassword"] + ' ' + k1["projectDir"] + ' '  + k1["projectPort"] ).read();
	    if k1["serverType"] == "5007":
                os.popen("su - " + k1["user"] + " -s /bin/bash " + sys.path[0] + "/api/extend/6000/jstorm.sh " + k1["serverUserName"] + ' ' +k1["serverPassword"] + ' ' + k1["projectDir"] + ' ' + k1["projectPort"] ).read();
        
