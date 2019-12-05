#!/usr/bin/python

from twill.commands import *
import twill.browser
from twill.twill_enhance import *
from twill.alert import *
from twill.entities import *

car_server_list = ["CHD-TGiftCardApi1.ab.elong.com:8090","bhi-tgiftcardapi1.ab.elong.com:8090","dhe-tgiftcardapi1.ab.elong.com:8090","dhf-tgiftcardapi1.ab.elong.com:8090","bhi-tgiftcardjob1.ab.elong.com:8090","dhf-tgiftcardjob1.ab.elong.com:8090"]
#car_server_list = ["CHD-TGiftCardApi1.ab.elong.com:8090"]

alerttype="urlmonitor_elongorder"
productline="elongorder"

try:
	print "===Start==="
	
	for host in car_server_list :
		
		try :
			
			print "begin to check the availability of " + host
		
			#add response json
			clear_extra_headers()
			add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9*/*;q=0.8")
			
			#Set giftcard proxy
			set_proxy({"http": host})

			#go to client comments page
			client_orderItem_page_response = get("http://giftcardapi.elong.com:8080/web/card/orderItem?elongId=1&pageIndex=1&pageSize=10", "orderItem", 200, 5000, ["totalCount","list"])
			alert(productline, host, alerttype, "orderItem", client_orderItem_page_response)
		except Exception, ex:
			print "failed : " + str(ex) + " \r\n"
		finally:
			#reset browser
			reset_browser

	print "===Finished==="

except Exception, e2:
	print str(e2) + " at url " + get_browser().get_url()
	#show()
