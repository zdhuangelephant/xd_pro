#!/usr/bin/python
# coding:utf8

import os
import sys
import json
import docker
import commands
from api.api_engine import  AosServerService
from util.mutil import FileUtils, MachineUtil, LogUtil

class DockerUtil(object):

    @staticmethod
    def _docker_status(api):
        try:
            c=docker.Client(base_url='unix://var/run/docker.sock',version=api,timeout=10)
            r=c.version()
            return "1"
        except Exception, ex:
            LogUtil.error(ex)   
            return "2"
            
    @staticmethod
    def _docker_restart(mac,api):
        try:
            output=os.popen("/bin/bash " + sys.path[0] + "/docker.sh ").read();
            c=docker.Client(base_url='unix://var/run/docker.sock',version=api,timeout=10)
            r=c.version()
            param = ["{\"mac\":\"" + mac +"\"}"]
            AosServerService.route_service("ThriftApiService", "startAll", param)
            return "1"
        except Exception, ex:
            LogUtil.error(ex)
            return "2" 
               
    @staticmethod
    def _docker_images(api):
        try:
            c=docker.Client(base_url='unix://var/run/docker.sock',version=api,timeout=10)
            r=json.dumps(c.images())
            return r
        except Exception, ex:
            LogUtil.error(ex)
            return "[]"
    
    @staticmethod   
    def _docker_containers():
        try:
            line =commands.getstatusoutput("docker ps -a  | grep -v 'CONTAINER'")
            r=json.dumps(line[1].replace("\n","xiaodoufenge"))   
            return r
        except Exception, ex:
            LogUtil.error(ex)
            return "[]"