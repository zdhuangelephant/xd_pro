#!/usr/bin/python
# coding:utf8

import json
from xdrpc_server.RouteService import Client
from xdrpc_server.ttypes import *
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
from util.mutil import FileUtils, LogUtil


class ApiUtil(object):
    @staticmethod
    def execute_api(module_name, param):
        func_name = "_execute"
        module = __import__('api.extend.' + module_name + '.api', fromlist=True)
        if hasattr(module, func_name):
            getattr(module, func_name)(param)
        return



class _Transport(object):
    def __init__(self, thrift_host, thrift_port, timeout=None, unix_socket=None):
        self.thrift_host = thrift_host
        self.thrift_port = thrift_port
        self.timeout = timeout
        self.unix_socket = unix_socket

        self._socket = TSocket.TSocket(self.thrift_host, self.thrift_port, self.unix_socket)
        self._transport_factory = TTransport.TFramedTransportFactory()
        self._transport = self._transport_factory.getTransport(self._socket)

    def connect(self):
        try:
            if self.timeout:
                self._socket.setTimeout(self.timeout)
            if not self.is_open():
                self._transport = self._transport_factory.getTransport(self._socket)
                self._transport.open()
        except Exception, e:
            LogUtil.error(e)
            self.close()

    def is_open(self):
        return self._transport.isOpen()

    def get_transport(self):
        return self._transport

    def close(self):
        self._transport.close()


class AosServer(object):
    def __init__(self, thrift_host, thrift_port, timeout=None, unix_socket=None):
        self._transObj = _Transport(thrift_host, thrift_port, timeout=timeout, unix_socket=unix_socket)
        self._protocol = TCompactProtocol.TCompactProtocol(trans=self._transObj.get_transport())
        self.client = Client(iprot=self._protocol, oprot=self._protocol)
        self._transObj.connect()

    def route_service(self, service_name, func_name, param):
        try:
            return self.client.routeService(service_name, func_name, param)
        except Exception, e:
            LogUtil.error(e)
        finally:
            self._transObj.connect()

    def close(self):
        self._transObj.close()


class AosServerService(object):
    config = FileUtils.get_instance("config.ini")

    @staticmethod
    def route_service(service, method, param):
        try:
            aos_client = AosServer(AosServerService.config.get_prop("aos-config", "server_ip"),
                                   int(AosServerService.config.get_prop("aos-config", "server_port")))
            result = aos_client.route_service(service, method, json.dumps(param))
            if result is None or result.model is None:
                return None
            LogUtil.in_out(service, method, param, result.model)
            return result.model
        except Exception, ex:
            LogUtil.error(ex)
        finally:
            aos_client.close()
