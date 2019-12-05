#!/usr/bin/python

from twill.commands import *
import twill.browser
from twill.twill_enhance import *
from twill.alert import *
from twill.entities import *

car_server_list = ["DHE-TCarApi1.ab.elong.com:8090","DHF-TCArApi1.ab.elong.com:8090","CHC-TCarApi1.ab.elong.com:8090","CHD-TCarApi1.ab.elong.com:8090","CHC-TCarJob1.ab.elong.com:8090"]
#car_server_list = ["DHE-TCarApi1.ab.elong.com:8090"]

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
			
			#Set car proxy
			set_proxy({"http": host})

			#go to client get squid
			client_getSquid_page_response = get("http://carapi.elong.com/m/taxi/get_sguid.html", "car_api_sguid", 200, 5000, ["retcode"])
			alert(productline, host, alerttype, "car_api_sguid", client_getSquid_page_response)
			
			#go to client get order list page
			client_queryOrder_page_response = get("http://carapi.elong.com/m/taxi/order/query_order.html?orderId=2014012408O000296390101", "query_order", 200, 5000, ["retcode"])
			alert(productline, host, alerttype, "query_order", client_queryOrder_page_response)
						
		except Exception, ex:
			print "failed : " + str(ex) + " \r\n"
		else:
			print "success\r\n"
		finally:
			#reset browser
			reset_browser

	print "===Finished==="

except Exception, e2:
	print str(e2) + " at url " + get_browser().get_url()
	#show()
