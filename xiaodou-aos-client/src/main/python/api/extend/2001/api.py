#!/usr/bin/python
# coding:utf8

from xdrpc_server.AosServerService import AosServerService
from log_monitor.monitor import MonitorManager
from util.mutil import MachineUtil, LogUtil


def _execute(config):
    monitor_state = config['state']
    if int(monitor_state) == 1:
        monitor_info = config['commandInfo']
        monitor_infos = monitor_info.split('#LOGMONITOR#')
        if len(monitor_infos) < 2:
            return
        MonitorManager.start_instance(monitor_infos[0], monitor_infos[1])
    elif int(monitor_state) == -1:
        monitor_info = config['commandInfo']
        monitor_infos = monitor_info.split('#LOGMONITOR#')
        if len(monitor_infos) < 2:
            return
        MonitorManager.stop_instance(monitor_infos[0], monitor_infos[1])
        param = ["{\"mac\":\"" + MachineUtil.get_mac_address() + "\",\"configId\":\"" + config['id'] + "\"}"]
        AosServerService.route_service("ThriftApiService", "removeStartupConfig", param)

