#!/usr/bin/python

from twill.commands import *
import twill.browser
from twill.twill_enhance import *
from twill.alert import *
from twill.entities import *

ebk_server_list = ["BHA-HWebEbk1.ab.elong.com","BHB-HWebEbk1.ab.elong.com",]

alerttype="urlmonitor_ebk"
productline="ebk"

try:
	print "===Start==="
	
	for host in ebk_server_list :
		
		try :
			
			print "begin to check the availability of " + host
		
			#Set ebooking.elong.com proxy
			set_proxy({"http": host})

			#ebooking Login Page
			login_page_response = get("http://ebooking.elong.com/ebookingnew/newlogin.mvc/login", "Open Login Page", 200, 5000, ["hotel_user","password"])
			alert(productline, host, alerttype, "Open Login Page", login_page_response)

			#Login
			formvalue(1, "hotel_user", "monitor2")
			formvalue(1, "password", "js020508")
			formvalue(1, "valicode", "1111")
			post("Login", 500, 5000, "")

			#go to client comments page
			client_comments_page_response = get("http://ebooking.elong.com/EbookingNew/NewHotelComment.mvc/HotelComment", "View Client Comments", 200, 5000, ["user_info"])
			alert(productline, ebk_server_name, alerttype, "View Client Comments", client_comments_page_response)
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
