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

class NodeLifeTime(object):
    def monitoring(self):
        while True:
            LogUtil.info("开始定时任务检测生命周期")
            ApiUtil.execute_api("6000",[])
            LogUtil.info("结束定时任务检测生命周期")
            time.sleep(5)


if __name__ == '__main__':
    node_lifetime = NodeLifeTime()
    node_lifetime.monitoring()

