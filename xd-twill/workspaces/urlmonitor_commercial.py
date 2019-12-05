#!/usr/bin/python

from twill.commands import *
import twill.browser
from twill.twill_enhance import *
from twill.alert import *
from twill.entities import * 
import traceback

alerttype="urlmonitor_templateservice"
productline="templateservice"

try: 
	
	print "===Start==="
	hosts = ['CHH-HTmpMis1.ab.elong.com:8081','CHF-HTmpMis1.ab.elong.com:8081','CHG-HTmpMis1.ab.elong.com:8081','DHE-HTmpMis1.ab.elong.com:8081','DHF-HTmpMis1.ab.elong.com:8081']
	for host in hosts:  
		
		try :			
			print "begin to check the availability of " + host
		
			clear_extra_headers()
			add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9*/*;q=0.8")
			
			#Set ebooking.elong.com proxy
			set_proxy({"http": host})
			
			templateservice_response = get("http://template.mis.elong.com:8081/f", "test_templateservice", 200, 5000, [""])
			alert(productline, host, alerttype, "test_templateservice", templateservice_response)
			print "success !!!"	
		except Exception, ex:
			print "failed : " + str(ex) + " \r\n"
			exstr=traceback.format_exc()
			print exstr
		finally:
			#reset browser
			reset_browser

	print "===Finished==="

except Exception, e2:
	print str(e2) + " at url " + get_browser().get_url()
	#show()
