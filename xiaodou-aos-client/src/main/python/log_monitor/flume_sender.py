# -*- coding: utf-8 -*-

from flume import ThriftSourceProtocol
from flume.ttypes import ThriftFlumeEvent
from thrift.transport import TTransport, TSocket
from thrift.protocol import TCompactProtocol
from util.mutil import FileUtils


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
            print(e)
            self.close()

    def is_open(self):
        return self._transport.isOpen()

    def get_transport(self):
        return self._transport

    def close(self):
        self._transport.close()


class FlumeClient(object):
    def __init__(self, thrift_host, thrift_port, timeout=None, unix_socket=None):
        self._transObj = _Transport(thrift_host, thrift_port, timeout=timeout, unix_socket=unix_socket)
        self._protocol = TCompactProtocol.TCompactProtocol(trans=self._transObj.get_transport())
        self.client = ThriftSourceProtocol.Client(iprot=self._protocol, oprot=self._protocol)
        self._transObj.connect()

    def send(self, event):
        try:
            self.client.append(event)
        except Exception, e:
            print(e)
        finally:
            self._transObj.connect()

    def send_batch(self, events):
        try:
            self.client.appendBatch(events)
        except Exception, e:
            print(e)
        finally:
            self._transObj.connect()

    def close(self):
        self._transObj.close()


class LogMonitor(object):
    config = FileUtils.get_instance("config.ini")

    header = {"messageType": "log"}

    @staticmethod
    def send_log(log_info):
        try:
            flume_client = FlumeClient(LogMonitor.config.get_prop("flume-config", "server_ip"),
                                       int(LogMonitor.config.get_prop("flume-config", "server_port")))
            event = ThriftFlumeEvent(LogMonitor.header, log_info)
            flume_client.send(event)
        except Exception, ex:
            print "%s" % ex.message
        finally:
            flume_client.close()

    @staticmethod
    def send_log_batch(log_info_list):
        try:
            flume_client = FlumeClient(LogMonitor.config.get_prop("flume-config", "server_ip"),
                                       int(LogMonitor.config.get_prop("flume-config", "server_port")))
            event_list = []
            for log_info in log_info_list:
                event_list.append(ThriftFlumeEvent(LogMonitor.header, log_info))
            flume_client.send_batch(event_list)
        except Exception, ex:
            print "%s" % ex.message
        finally:
            flume_client.close()
