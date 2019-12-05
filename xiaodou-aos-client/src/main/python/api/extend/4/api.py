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
    s1 = AosServerService.route_service("ThriftApiService", "getBaseServerById", param);
    d1 = json.loads(s1)
    output = ""
    for k1 in d1:
        output = os.popen("su - " + k1["user"] + " -s /bin/bash " + sys.path[0] + "/api/extend/4/del.sh " + k1["baseDir"] + ' ' +k1["serverName"] + ' ' + k1["baseServerId"] + ' '                  + k1["warAdress"] + ' ' + k1["tomcatPort"]).read();
        state = '1'
        param = ["{\"id\":\"" + command["id"] + "\",\"state\":\"" + state + "\",\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"msg\":\"" + output +
                       "\",\"version\":\"" + "" + "\"}"]
        AosServerService.route_service("ThriftApiService", "editState", param)
   
