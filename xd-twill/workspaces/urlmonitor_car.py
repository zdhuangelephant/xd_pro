#!/usr/bin/python

from twill.commands import *
import twill.browser
from twill.twill_enhance import *
from twill.alert import *
from twill.entities import *

car_server_list = ["dhe-tcarapi2.ab.elong.com:8090","BHI-TCarApi1.ab.elong.com:8090","CHD-TCarApi2.ab.elong.com:8090","dhf-tcarapi2.ab.elong.com:8090","dhf-tcarjob1.ab.elong.com:8090"]

#car_server_list = ["CHD-TCarApi2.ab.elong.com:8090"]

alerttype="urlmonitor_elongorder"
productline="elongorder"

try:
	print "===Start==="
	
	for host in car_server_list :
		
		try :
			
			print "begin to check the availability of " + host

			clear_extra_headers()
                        add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9*/*;q=0.8")	
			#Set ebooking.elong.com proxy
			set_proxy({"http": host})

			#go to client create order page
			client_createOrder_page_response = get("http://rentcarapi.elong.com/m/render/order/create_order.html", "create_order", 200, 5000, ["retcode"])
			alert(productline, host, alerttype, "create_order", client_createOrder_page_response)
			
			#go to client get order liset page
			#client_orderList_page_response = get("rentcarapi.elong.com/m/render/order/get_order_list.html?uid=zhaodan&productType=0201&orderStatus=1", "order_list", 200, 5000, ["pageNo"])
			#alert(productline, host, alerttype, "order_list", client_orderList_page_response)
						
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
