#!/usr/bin/python

from twill.commands import *
import twill.browser
from twill.twill_enhance import *
from twill.alert import *
from twill.entities import * 
import traceback

alerttype="urlmonitor_ivr"
productline="ivr"

try: 
	
	print "===Start==="
	hosts = ['BHB-HApiIVR1.ab.elong.com:8080','BHI-HApiIVR1.ab.elong.com:8080','DHE-HApiIVR1.ab.elong.com:8080','DHF-HApiIVR1.ab.elong.com:8080']
	for host in hosts:  
		
		try :			
			print "begin to check the availability of " + host
		
			clear_extra_headers()
			add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9*/*;q=0.8")
			
			#Set ebooking.elong.com proxy
			set_proxy({"http": host})
			
			ivr_response = get("http://ivr.mis.elong.com:8080/getorderbycardno?cardno=4922601", "test_ivr", 200, 5000, ["status"])
			alert(productline, host, alerttype, "test_ivr", ivr_response)
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
