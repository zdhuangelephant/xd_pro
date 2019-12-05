#!/usr/bin/python
# coding:utf8


import os
import json
import base64
import sys
from xdrpc_server.AosServerService import AosServerService
from util.mutil import MachineUtil


def _execute(command):
    
    param = ["{\"serverId\":\"" + command["serverId"] + "\"}"]
    s1 = AosServerService.route_service("ThriftApiService", "getById", param)
    d1 = json.loads(s1)
    output = ""
    for k1 in d1:
        output = os.popen(
       		 "su - " + k1["user"] + " -s /bin/bash " + sys.path[0] + "/api/extend/2/restart.sh " + k1["baseDir"] + ' ' +k1["serverName"] + ' ' + k1["serverId"] + ' ' + k1["warAdress"] + ' ' + command["version"] + ' ' + k1["host"] + ' ' + k1["userName"] + ' ' + k1["passWord"] + ' ' + k1["port"]).read();
        state = '1'
        param = ["{\"id\":\"" + command["id"] + "\",\"state\":\"" + state + "\",\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"msg\":\"" + output +
                "\",\"version\":\"" + command["version"] + "\"}"]
   	AosServerService.route_service("ThriftApiService", "editState", param)  	  
   	output2 = os.popen(
       		 "su - " + k1["user"] + " -s /bin/bash " + sys.path[0] + "/api/extend/2/restart1.sh " + k1["baseDir"] + ' ' + k1["serverName"] + ' ' + k1["serverId"] + ' ' + k1["warAdress"] + ' ' + command["version"] + ' ' + k1["host"] + ' ' + k1["userName"] + ' ' + k1["passWord"] + ' ' + k1["port"]).read();  		 
        param = ["{\"id\":\"" + command["id"] + "\",\"state\":\"" + state + "\",\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"msg\":\"" + output2 +
                "\",\"version\":\"" + command["version"] + "\"}"]
   	AosServerService.route_service("ThriftApiService", "editState", param)



