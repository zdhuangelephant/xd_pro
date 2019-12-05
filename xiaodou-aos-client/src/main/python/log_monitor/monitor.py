# -*- coding: utf-8 -*-

import re
import time
import thread
from util.mutil import Watcher, Encrypt
from flume_sender import LogMonitor


class MonitorManager(object):
    _monitor_manager = dict()

    def __init__(self):
        pass

    @staticmethod
    def start_instance(log_path, regex_str):
        key = Encrypt.md5("%s:%s" % (log_path, regex_str))
        if not MonitorManager._monitor_manager.has_key(key):
            monitor = Monitor(log_path, regex_str)
            MonitorManager._monitor_manager[key] = monitor
            monitor.start()

    @staticmethod
    def stop_instance(log_path, regex_str):
        key = Encrypt.md5("%s:%s" % (log_path, regex_str))
        if MonitorManager._monitor_manager.has_key(key):
            MonitorManager._monitor_manager.get(key).stop()
            del MonitorManager._monitor_manager[key]


class Monitor(object):

    def __init__(self, log_path, regex_str):
        self.state = None
        self.flag = True
        self.log_path = log_path
        self.regex = ur"^" + regex_str

    def start(self):
        if self.state is None:
            self.state = "started"
            self.flag = True
            thread.start_new_thread(self.run, ())

    def run(self):
        while self.flag:
            Watcher(file_dir=self.log_path, time_skip=30, sleep_time=10, function=self.process).watch_dir()
            time.sleep(10)
        thread.exit_thread()

    def stop(self):
        self.flag = False
        self.state = None

    def process(self, file_name, file_path):
        if self.regex is not None and file_name is not None:
            if re.match(self.regex, file_name):
                Monitor.send_log(file_path)
                return True
        return False

    @staticmethod
    def send_log(file_path):
        log_file = open(file_path)
        while 1:
            lines = log_file.readlines(1000)
            if not lines:
                break
            LogMonitor.send_log_batch(lines)
