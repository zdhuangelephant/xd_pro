#
# coding: utf-8

import os

result = os.system('ping -n -t 1 %s' % '172.17.1.123')
if result:
    print "no"
else:
    print "yes"
