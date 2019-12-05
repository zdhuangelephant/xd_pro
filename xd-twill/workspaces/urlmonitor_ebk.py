#!/usr/bin/python

from twill.commands import *
import twill.browser
from twill.twill_enhance import *
from twill.alert import *
from twill.entities import *

ebk_server_list = ["BHA-HWebEbk1.ab.elong.com",
"BHB-HWebEbk1.ab.elong.com",
"BHB-HWebEbk2.ab.elong.com",
"CHC-HWebEbk1.ab.elong.com",
"CHC-HWebEbk2.ab.elong.com",
"CHD-HWebEbk1.ab.elong.com",
"CHD-HWebEbk2.ab.elong.com",
"CHE-HWebEbk1.ab.elong.com",
"CHD-HWebEbk3.ab.elong.com",
"CHG-HWebEbk1.ab.elong.com",
"DHA-HWebEbk1.ab.elong.com",
"DHB-HWebEbk1.ab.elong.com",
"DHC-HWebEbk1.ab.elong.com",
"DHD-HWebEbk1.ab.elong.com",
"DHA-HWebEbk2.ab.elong.com",
"DHB-HWebEbk2.ab.elong.com"]

eb_server_list = ["CHD-HWebEb1.ab.elong.com",
"CHE-HWebEb1.ab.elong.com",
"CHF-HWebEb2.ab.elong.com",
"CHD-HWebEb2.ab.elong.com",
"CHG-HWebEb1.ab.elong.com",
"CHF-HWebEb3.ab.elong.com",
"CHG-HWebEb2.ab.elong.com",
"DHB-HWebEb2.ab.elong.com",
"DHA-HWebEb1.ab.elong.com",
"DHB-HWebEb1.ab.elong.com",
"DHA-HWebEb2.ab.elong.com",
"DHB-HWebEb3.ab.elong.com"]

alerttype="urlmonitor_ebk"
productline="ebk"

try:
	times = max([len(ebk_server_list), len(eb_server_list)])
	
	print "===Start==="
	
	for i in range(times) :
		
		ebk_server_name = ebk_server_list[i%len(ebk_server_list)]
		eb_server_name = eb_server_list[i%len(eb_server_list)]
		
		try :
			
			print "begin to check the availability of " + ebk_server_name + " and " + eb_server_name
		
			#Set ebooking.elong.com proxy
			set_proxy({"http": ebk_server_name})

			#ebooking Login Page
			login_page_response = get("http://ebooking.elong.com/ebookingnew/newlogin.mvc/login", "Open Login Page", 200, 5000, ["hotel_user","password"])
			alert(productline, ebk_server_name, alerttype, "Open Login Page", login_page_response)

			#Login
			formvalue(1, "hotel_user", "monitor2")
			formvalue(1, "password", "js020508")
			formvalue(1, "valicode", "1111")
			post("Login", 500, 5000, "")

			#go to client comments page
			client_comments_page_response = get("http://ebooking.elong.com/EbookingNew/NewHotelComment.mvc/HotelComment", "View Client Comments", 200, 5000, ["user_info"])
			alert(productline, ebk_server_name, alerttype, "View Client Comments", client_comments_page_response)


			#Set eb.elong.com proxy
			set_proxy({"http": eb_server_name})
	
			#Go to dashboard page
			dashboard_page_response = get("http://eb.elong.com/dashboard.aspx", "View Dashboard", 200, 5000, ["OrderCountOfDashBoard"])
			alert(productline, eb_server_name, alerttype, "View Dashboard", dashboard_page_response)

			#Go to confirm reserve list page
			confirm_reserve_list_page_response = get("http://eb.elong.com/orderConfirmList.aspx", "View Confirm Reserve List", 200, 5000, ["startDate","endDate"])
			alert(productline, eb_server_name, alerttype, "View Confirm Reserve List", confirm_reserve_list_page_response)
			
			#Go to maintain room amount page
			maintain_room_amount_page_response = get("http://eb.elong.com/roomNumModify.aspx", "Maintain Room Amount", 200, 5000, ["editNum","edtiState"])
			alert(productline, eb_server_name, alerttype, "Maintain Room Amount", maintain_room_amount_page_response)
			
			#Go to maintain price page
			maintain_price_page_response = get("http://eb.elong.com/housePriceMaintenance.aspx", "Maintain Price", 200, 5000, ["calendar1","calendar2"])
			alert(productline, eb_server_name, alerttype, "Maintain Price", maintain_price_page_response)
			
			#Go to audit reserve page
			audit_reserve_page_response = get("http://eb.elong.com/auditOrderList.aspx", "Audit Reserve", 200, 5000, ["startDate","endDate"])
			alert(productline, eb_server_name, alerttype, "Audit Reserve", audit_reserve_page_response)
			
			
			#Set ebooking.elong.com proxy
			set_proxy({"http": ebk_server_name})
			
			#logout
			logout_response = get("http://ebooking.elong.com/EbookingNew/NewLogin.mvc/Logout", "Logout", 200, 5000, ["hotel_user","password"])
			alert(productline, ebk_server_name, alerttype, "Logout", logout_response)
			
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
