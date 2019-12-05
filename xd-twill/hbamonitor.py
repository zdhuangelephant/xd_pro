#! /usr/bin/python
# Filename:hbamonitor.py

from twill.commands import *
import twill.browser
import traceback
from twill.twill_enhance import *
from twill.entities import *
from twill.alert import *
import httplib

# main function

HBA_LOGOUT_PAGE="http://hba.elong.com/Initiate/LogOut"
HBA_LOGIN_PAGE="http://hba.elong.com/Initiate/Login"
HBA_USERNAME="henan114"
HBA_PASSWORD="henan"
HBA_ORDER_COMMON_PAGE="http://hba.elong.com/Common/OrderCommonBusiness/OrderSearch"
HBA_ORDERSEARCH_RESULT_PAGE="http://hba.elong.com/Common/OrderCommonBusiness/OrderSearchResult?orderNo=&cellPhone=&txtInDateStart=&txtInDateEnd=&txtOutDateStart=&txtOutDateEnd=&guestName=&contactorName=&hotelName=&CID=&selectOrderType=V&roomCount=&roomCountPerNight=&OrderFrom=undefined&Page=1&modifyUser=&txtModifyStartDate=&txtModifyEndDate=&firstOperator=&txtFirstStartDate=&txtFirstEndDate="

if __name__ == "__main__":
	#hosts = ['172.21.13.81', '172.21.14.63', '172.21.14.63', '172.21.14.63', '10.35.37.159', '10.35.37.159', '10.35.37.159', '10.35.37.159']
	alerttype="urlmonitor_hba"
	productline="hba"
	hosts = ['172.21.13.81']
	for host in hosts:
		try:
			clear_extra_headers()
			add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9*/*;q=0.8")
			
			#HBA Logout
			hba_logout_response = get(HBA_LOGOUT_PAGE, "HBA Logout page",200,5000,[""])
			hba_logout_instance = "hba_logout"
			#alert(host, hba_logout_instance, hba_logout_response)
			
			#HBA Login Page
			hba_response = get(HBA_LOGIN_PAGE, "HBA Login Page", 20, 5000, [""])
			hba_instance = "hba_index";
			alert(productline,host,alerttype,hba_instance,hba_response)
			
			##Login
			#formvalue(1,"userid",HBA_USERNAME)
			#formvalue(1,"password",HBA_PASSWORD)
			#login_response = post("Login to HBA",200,5000)
			#login_instance = "hab_login"
			#alert(productline,host,alerttype,login_instance,login_response)
			#
			##default.asp
			#default_response = get(HBA_ORDER_COMMON_PAGE, 200, 1000, ["order_commonbusiness"])
			#default_instance="order_commonbusiness"
			#alert(productline,host,alerttype,default_instance,default_response)
			#
			##OrderSearchResult Page
			#ordersearchresult_response=get(HBA_ORDERSEARCH_RESULT_PAGE, 200, 10000, ["searchresult"])
			#ordersearchresult_instance="ordersearchresult"
			#alert(productline,host,alerttype,ordersearchresult_instance,ordersearchresult_response)
		
		except Exception as e:
			traceback.print_exc()
			print "exception error is:%s" %e + " at url " + get_browser().get_url()
			
