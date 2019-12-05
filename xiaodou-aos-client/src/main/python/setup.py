#!/usr/bin/env python
# coding=utf-8

import sys, os

sys.path.append(os.path.join(sys.path[0], '../../lib'))
import json
import time
import base64
from util.mutil import FileUtils, MachineUtil, LogUtil
from api.api_engine import ApiUtil, AosServerService

config = FileUtils.get_instance('config.ini')
if config.get_prop("docker-config", "docker") is "1":
    from util.dockerUtil import DockerUtil

class NodeLifeTime(object):
    config = FileUtils.get_instance('config.ini')
    api = config.get_prop("docker-config", "api")
    def _regist_node(self):
        mac = MachineUtil.get_mac_address()
        ip = NodeLifeTime.config.get_prop("aos-config", "client_ip")
        docker=NodeLifeTime.config.get_prop("docker-config","docker")
        docker_status ="0"
        docker_images ="[]"
        docker_containers="[]"
        if docker=="1":
             docker_status = DockerUtil._docker_status(NodeLifeTime.api)
        if docker_status=="2":
             docker_status=DockerUtil._docker_restart(mac,NodeLifeTime.api)
        if docker_status=="1":
             docker_images = base64.encodestring(DockerUtil._docker_images(NodeLifeTime.api))
             docker_containers = base64.encodestring(DockerUtil._docker_containers())
        param = ["{\"mac\":\"" + mac + "\",\"ip\":\"" + ip + "\",\"dockerStatus\":\"" + docker_status + "\",\"dockerImages\":\"" + docker_images + "\",\"containers\":\"" + docker_containers +"\"}"]
        LogUtil.info("注册节点信息:%s" % (json.dumps(param)))
        return AosServerService.route_service("ThriftApiService", "add", param);
        
        
    def _do_action(self, server_response_command):
        if len(server_response_command) == 0:
            return
        for action in server_response_command:
            LogUtil.info("执行指令:%s" % (json.dumps(action)))
            ApiUtil.execute_api(action["commandId"], action)

    def _do_config(self, server_response_config):
        if len(server_response_config) == 0:
            return
        for config in server_response_config:
            LogUtil.info("执行配置项:%s" % (json.dumps(config)))
            ApiUtil.execute_api(config["commandId"], config)
            

    def run(self):
        try:
            server_response = self._regist_node()
            if server_response is None:
                return
            server_responses = server_response.split("#ADDNODE#")
            if len(server_responses) < 2:
                return
            server_response_command = json.loads(server_responses[0])
            server_response_config = json.loads(server_responses[1])
            self._do_config(server_response_config)
            self._do_action(server_response_command)
        except Exception, ex:
            LogUtil.error(ex)
            

    def dispatch(self):
        while True:
            LogUtil.info("开始生命周期")
            self.run()
            LogUtil.info("结束生命周期")
            time.sleep(5)
    

if __name__ == '__main__':
    node_lifetime = NodeLifeTime()
    node_lifetime.dispatch()
