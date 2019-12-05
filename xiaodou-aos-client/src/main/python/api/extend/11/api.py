#!/usr/bin/python
# coding:utf8


import os
import json
import base64
import sys
import time
import docker 
from xdrpc_server.AosServerService import AosServerService
from util.mutil import FileUtils, MachineUtil, LogUtil


def _execute(command):
     try:
        config = FileUtils.get_instance('config.ini')
    	containerId=command["serverId"]
    	c=docker.Client(base_url='unix://var/run/docker.sock',version=config.get_prop("docker-config", "api"),timeout=10)
    	c.start(container=containerId)
    	time.sleep(10)
    	log = c.logs(container=containerId)
    	output =base64.encodestring(log)
    	state = '1'
  	param = ["{\"id\":\"" + command["id"] + "\",\"state\":\"" + state + "\",\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"msg\":\"" + "123" + "\"}"]
    	AosServerService.route_service("ThriftApiService", "editState", param)  	
     except Exception, ex:
        LogUtil.error(ex)
        log=("Error {0}".format(str(ex)))
        output = base64.encodestring(log)
        state = '1'
        param = ["{\"id\":\"" + command["id"] + "\",\"state\":\"" + state + "\",\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"msg\":\"" + "error" + "\"}"]
        AosServerService.route_service("ThriftApiService", "editState", param)  
