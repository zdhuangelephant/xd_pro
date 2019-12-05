#!/usr/bin/python
# coding:utf8


import os
import sys
import json
import base64
from xdrpc_server.AosServerService import AosServerService
from util.mutil import MachineUtil

def _execute(command):
    param = ["{\"serverId\":\"" + command["serverId"] + "\"}"]
    s1 = AosServerService.route_service("ThriftApiService", "getMiddleServerById", param);
    d1 = json.loads(s1)
    output = ""
    for k1 in d1:
        output = output + os.popen("wget  -O command.sh " +  k1["installCommand"]).read();
        output = output + os.popen("su - " + k1["user"] + " -s /bin/bash " + sys.path[0] + "/../../../bin/command.sh " + k1["serverUserName"] + ' ' +k1["serverPassword"]+ ' ' +k1["projectDir"]+ ' ' +k1["projectPort"]+ ' ' +k1["flumeIp"]+ ' ' +k1["flumeRabbitMqIp"]+ ' ' +k1["flumeRabbitMqQueueName"]+ ' ' +k1["flumeRabbitMqUserName"]+ ' ' +k1["flumeRabbitMqPassword"]+ ' ' +k1["flumeRabbitMqPort"]+ ' ' +k1["flumeRabbitMqExchange"]).read();
        output = output + os.popen("rm -rf command.sh").read();
	output =base64.encodestring(output)
        state = '1'
        param = ["{\"id\":\"" + command["id"] + "\",\"state\":\"" + state + "\",\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"msg\":\"" + output +
                       "\",\"version\":\"" + "" + "\"}"]
        AosServerService.route_service("ThriftApiService", "editState", param)

