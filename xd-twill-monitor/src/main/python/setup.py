#!/usr/bin/env python
# coding=utf-8

from util.mutil import FileUtils

main_fonf = FileUtils.get_instance('config.ini')

import os
import urllib, urllib2
import smtplib
import datetime
import threading
import time
from email.mime.text import MIMEText
from twill.commands import *
from util.mutil import LogUtil


class TwillMonitor(threading.Thread):
    def run(self):
        try:
            server_name = main_fonf.get_prop("server", "name")
            target_urls = main_fonf.get_prop("monitor", "urls")
            target_hosts = main_fonf.get_prop("monitor", "hosts")
            html_info = self.monitor_urls(target_urls)
            html_info += self.monitor_hosts(target_hosts)
            if html_info.strip() != '':
                mail_head = '<table border="0" cellspacing="0" cellpadding="0" ' \
                            'style="font-family:"微软雅黑",Helvetica,Arial,sans-serif;font-size:14px " width="100%"> \
                         <tbody> \
                                    <tr> \
                                        <td style="font-family:Helvetica,Arial,sans-serif;font-size:14px;"> \
                                        <table width="100%" border="3" cellpadding="5" cellspacing="0" > \
                                                <tbody> \
                                                    <tr><td>监控地址</td><td>状态</td><td>描述</td></tr>'
                mail_foot = '</tbody> \
                                                    </table> \
                                                   </td> \
                                              </tr> \
                                   </tbody> \
                                </table>'
                mail_info = mail_head + html_info + mail_foot
                self.send_mail(server_name, mail_info)
        except Exception, runEx:
            LogUtil.error(runEx)

    def monitor_hosts(self, target_hosts):
        html_info = ""
        if target_hosts is None or len(target_hosts) == 0:
            return
        for target_host in target_hosts.split(","):
            try:
                result = self.monitor_host(target_host)
                if result:
                    self.alarm("監控IP地址失敗:[%s]" % target_host)
                    html_info += "<tr><td><a href='%s'><span style='color:#F44336;'>%s</span></a></td><td>" \
                                 "<span style='color:#F44336;'>%s</span></td><td><span style='color:#F44336;'>" \
                                 "%s</span></td></tr>" % (target_host, target_host, "Fail", result)
            except Exception, moHsEx:
                LogUtil.error(moHsEx)
                self.alarm("監控IP地址失敗:[%s]" % target_host)
                html_info += "<tr><td><a href='%s'><span style='color:#F44336;'>%s</span></a></td><td>" \
                             "<span style='color:#F44336;'>%s</span></td><td><span style='color:#F44336;'>" \
                             "%s</span></td></tr>" % (target_host, target_host, "Fail", moHsEx.message)
        return html_info

    def monitor_host(self, target_host):
        _result = True
        for index in range(3):
            _result = os.system('ping -c 1 -w 1 %s' % target_host)
            if not _result:
                return _result
        return _result

    def monitor_urls(self, target_urls):
        html_info = ""
        if target_urls is None or len(target_urls) == 0:
            return
        for target_url in target_urls.split(","):
            try:
                result = self.monitor_url(target_url)
                if result is not None :
                    self.alarm("監控Url地址失敗:[%s]" % target_url)
                    html_info += "<tr><td><a href='%s'><span style='color:#F44336;'>%s</span></a></td><td>" \
                                 "<span style='color:#F44336;'>%s</span></td><td><span style='color:#F44336;'>" \
                                 "%s</span></td></tr>" % (target_url, target_url, "Fail", result.message)
            except Exception, moUsEx:
                LogUtil.error(moUsEx)
                self.alarm("監控Url地址失敗:[%s]" % target_url)
                html_info += "<tr><td><a href='%s'><span style='color:#F44336;'>%s</span></a></td><td>" \
                             "<span style='color:#F44336;'>%s</span></td><td><span style='color:#F44336;'>" \
                             "%s</span></td></tr>" % (target_url, target_url, "Fail", moUsEx.message)
        return html_info

    def monitor_url(self, target_url):
        _result = None
        for index in range(3):
            try:
                go(target_url)
                code(200)
                return _result
            except Exception, moUlEx:
                _result = moUlEx
        return _result

    def send_mail(self, server_name, monitor_info):
        sender = main_fonf.get_prop("mail", "sender")
        receiver = main_fonf.get_prop("mail", "receiver")
        time = datetime.datetime.now()
        subject = 'Twill-监控结果-%s-%s' % (server_name, time.strftime('%Y-%m-%d-%H-%M-%S'))
        smtp_server = main_fonf.get_prop("mail", "smtp_server")
        ssl_port = main_fonf.get_prop("mail", "ssl_port")
        username = main_fonf.get_prop("mail", "username")
        password = main_fonf.get_prop("mail", "password")
        msg = MIMEText(monitor_info, 'html', 'utf-8')
        msg['Subject'] = subject
        msg['From'] = sender
        msg['To'] = receiver
        msg['date'] = time.strftime('%a, %d %b %Y %H:%M:%S %z')
        smtp = smtplib.SMTP_SSL(smtp_server, ssl_port)
        smtp.ehlo()
        smtp.login(username, password)
        smtp.sendmail(sender, receiver, msg.as_string())
        smtp.quit()

    def alarm(self, message):
        sendurl = main_fonf.get_prop("alarm", "sendurl")
        eventModule = main_fonf.get_prop("alarm", "eventmodule")
        eventName = main_fonf.get_prop("alarm", "eventname")
        params = urllib.urlencode(
            {'eventModule': eventModule, 'eventName': eventName, 'messageInfo': message, 'mailInfo': message})
        req = urllib2.Request(url=sendurl, data=params)
        urllib2.urlopen(req)

if __name__ == '__main__':
    interval = int(main_fonf.get_prop("server", "interval"))
    while True:
        TwillMonitor().run()
        time.sleep(interval)
