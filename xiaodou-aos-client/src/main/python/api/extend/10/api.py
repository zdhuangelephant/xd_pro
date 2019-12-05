#!/usr/bin/python
# coding:utf8


import os
import json
import base64
import sys
import time
import docker 
import commands
from xdrpc_server.AosServerService import AosServerService
from util.mutil import FileUtils, MachineUtil, LogUtil


def _execute(command):
   try:
    	commandInfo=command["commandInfo"]
   	line =commands.getstatusoutput(commandInfo)
        log =json.dumps(line[1])
    	output =base64.encodestring(log)
    	state = '1'
    	param = ["{\"id\":\"" + command["id"] + "\",\"state\":\"" + state + "\",\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"msg\":\"" + output +"\",\"version\":\"" + command["version"] + "\"}"]
    	AosServerService.route_service("ThriftApiService", "editState", param)  
   except Exception, ex:
        LogUtil.error(ex)
        log=("Error {0}".format(str(ex)))
        output = base64.encodestring(log)
        state = '1'
        param = ["{\"id\":\"" + command["id"] + "\",\"state\":\"" + state + "\",\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"msg\":\"" + output +"\",\"version\":\"" + command["version"] + "\"}"]
        AosServerService.route_service("ThriftApiService", "editState", param)    	  
