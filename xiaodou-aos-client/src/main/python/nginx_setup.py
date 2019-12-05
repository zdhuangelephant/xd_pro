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

class NginxLifeTime(object):
    config = FileUtils.get_instance('config.ini')
    def _nginx_conf(self):
        mac = MachineUtil.get_mac_address()
        param = ["{\"mac\":\"" + mac + "\"}"]
        LogUtil.info("拉取Nginx配置信息:%s" % (json.dumps(param)))
        return AosServerService.route_service("ThriftApiService", "getNginxConf", param);
        
    def _do_action(self, server_response_command):
        LogUtil.info("执行指令:%s" % (server_response_command))
        ApiUtil.execute_api("2111", server_response_command)

    def nginx_run(self):
        try:
            server_response = self._nginx_conf()
            if server_response is None:
                return
            server_responses = server_response.split("#EDIT#")
            if len(server_responses) < 1:
                return
            server_response_nginx = json.loads(server_responses[0])
            self._do_action(server_response_nginx)
        except Exception, ex:
            LogUtil.error(ex)

    def nginx_dispatch(self):
        while True:
            LogUtil.info("开始拉取Nginx配置信息")
            self.nginx_run()
            LogUtil.info("结束Nginx配置")
            time.sleep(1800)


if __name__ == '__main__':
    node_lifetime = NginxLifeTime()
    node_lifetime.nginx_dispatch()
