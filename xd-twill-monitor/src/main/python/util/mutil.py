#!/usr/bin/python
# coding:utf8

import os
import md5
import sys
import uuid
import json
import time
import logging
import traceback
import ConfigParser
import thread

class Encrypt(object):

    @staticmethod
    def md5(string):
        m1 = md5.new()
        m1.update(string)
        return m1.hexdigest()


class MachineUtil(object):

    @staticmethod
    def get_mac_address():
        mac = uuid.UUID(int=uuid.getnode()).hex[-12:].upper()
        return ":".join([mac[e:e + 2] for e in range(0, 11, 2)])


class FileUtils(object):
    _file_util_map = dict()

    @staticmethod
    def get_instance(file_name):
        if file_name in FileUtils._file_util_map:
            return FileUtils._file_util_map.get(file_name)
        else:
            file_util = FileUtil(file_name)
            FileUtils._file_util_map[file_name] = file_util
            return file_util


class FileUtil(object):

    def __init__(self, file_name):
        self._config = ConfigParser.ConfigParser()
        self._config.read(os.path.join(sys.path[0], "../resources/conf/"+file_name))

    def get_prop(self, prefix, key):
        return self._config.get(prefix, key)


class Watcher(object):

    def __init__(self, file_dir, time_skip, sleep_time, function):
        self.file_dir = file_dir
        self.time_skip = time_skip
        self.current_time = time.time()
        self.process_func = function
        self.sleep_time = sleep_time

    def monitor_file(self, base_dir, file_name):
        file_path = os.path.join(base_dir, file_name)
        if not os.path.isfile(file_path):
            return
        m_file_time = os.path.getmtime(file_path)
        if self.time_skip + m_file_time < self.current_time:
            if self.process_func is not None:
                if self.process_func(file_name, file_path):
                    os.remove(file_path)
            else:
                os.remove(file_path)

    def watch_dir(self):
        if not os.path.exists(self.file_dir):
            return
        file_list = os.listdir(self.file_dir)
        for file_name in file_list:
            self.monitor_file(self.file_dir, file_name)

    def run(self):
        while True:
            self.watch_dir()
            time.sleep(self.sleep_time)


class LogUtil(object):

    logUtil = None
    thread.start_new_thread(Watcher(file_dir="../logs/", time_skip=3*24*60*60, sleep_time=3600, function=None).run, ())

    def __init__(self):
        formatter = logging.Formatter("%(asctime)s - %(name)s - %(levelname)s - %(message)s")
        self.log_suffix = time.strftime('%Y-%m-%d', time.localtime(time.time()))
        cmd = logging.StreamHandler()
        cmd.setFormatter(formatter)
        self.debug_logger = logging.getLogger("debug")
        self.debug_logger.setLevel(logging.DEBUG)
        self.debug_logger.addHandler(cmd)
        # info_logger
        self.info_logger = logging.getLogger("info")
        self.info_logger.setLevel(logging.INFO)
        self.info_logger.addHandler(cmd)
        info_handler = logging.FileHandler("../logs/info_%s.log" % self.log_suffix)
        info_handler.setLevel(logging.INFO)
        info_handler.setFormatter(formatter)
        self.info_logger.addHandler(info_handler)
        # in_out_logger
        self.in_out_logger = logging.getLogger("inout")
        self.in_out_logger.setLevel(logging.INFO)
        self.in_out_logger.addHandler(cmd)
        in_out_handler = logging.FileHandler("../logs/in_out_%s.log" % self.log_suffix)
        in_out_handler.setLevel(logging.INFO)
        in_out_handler.setFormatter(formatter)
        self.in_out_logger.addHandler(in_out_handler)
        # error_logger
        self.error_logger = logging.getLogger("error")
        self.error_logger.setLevel(logging.ERROR)
        self.error_logger.addHandler(cmd)
        error_handler = logging.FileHandler("../logs/error_%s.log" % self.log_suffix)
        error_handler.setLevel(logging.INFO)
        error_handler.setFormatter(formatter)
        self.error_logger.addHandler(error_handler)
        self.error_file = "../logs/error_%s.log" % self.log_suffix

    def release(self):
        for i in self.info_logger.handlers:
            self.info_logger.removeFilter(i)
            i.flush()
            i.close()
        logging.Logger.manager.loggerDict.pop("info")
        self.info_logger.manager = None
        self.info_logger.handlers = []
        self.info_logger.disabled
        for i in self.in_out_logger.handlers:
            self.in_out_logger.removeFilter(i)
            i.flush()
            i.close()
        logging.Logger.manager.loggerDict.pop("inout")
        self.in_out_logger.manager = None
        self.in_out_logger.handlers = []
        self.in_out_logger.disabled
        for i in self.error_logger.handlers:
            self.error_logger.removeFilter(i)
            i.flush()
            i.close()
        logging.Logger.manager.loggerDict.pop("error")
        self.error_logger.manager = None
        self.error_logger.handlers = []
        self.error_logger.disabled

    def is_not_2day(self):
        return self.log_suffix != time.strftime('%Y-%m-%d', time.localtime(time.time()))

    @staticmethod
    def get_instance():
        if LogUtil.logUtil is None:
            LogUtil.logUtil = LogUtil()
        elif LogUtil.logUtil.is_not_2day():
            LogUtil.logUtil.release()
            LogUtil.logUtil = LogUtil()
        return LogUtil.logUtil

    @staticmethod
    def debug(info):
        LogUtil.get_instance().debug_logger.debug(info)

    @staticmethod
    def info(info):
        LogUtil.get_instance().info_logger.info(info)

    @staticmethod
    def error(error):
        logutil = LogUtil.get_instance()
        logutil.error_logger.error(error.message)
        traceback.print_exc(file=open(logutil.error_file, 'w+'))

    @staticmethod
    def in_out(service, func, param, result):
        LogUtil.get_instance().in_out_logger.info(json.dumps(InOutEntity(service, func, param, result).__dict__))

class InOutEntity(object):
    def __init__(self, service, func, param, result):
        self.service = service
        self.func = func
        self.param = param
        self.result = result



