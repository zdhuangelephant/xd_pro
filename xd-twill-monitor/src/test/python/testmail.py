#!/usr/bin/env python3
#
# coding: utf-8

import smtplib
from email.mime.text import MIMEText
from email.header import Header
sender = 'zhaodan@corp.51xiaodou.com'
receiver = 'xd-server@corp.51xiaodou.com'
subject = 'python email test'
username = 'zhaodan@corp.51xiaodou.com'
password = 'zhaodan'

msg = MIMEText('你好','text','utf-8')#中文需参数‘utf-8'，单字节字符不需要
msg['Subject'] = Header(subject, 'utf-8')

smtp = smtplib.SMTP()
smtp.connect('123.58.177.199')
#smtp.ehlo()
#smtp.starttls()
#smtp.ehlo()
#smtp.set_debuglevel(1)
smtp.login(username, password)
smtp.sendmail(sender, receiver, msg.as_string())
smtp.quit()