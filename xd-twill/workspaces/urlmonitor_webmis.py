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

Mis_EDS_PAGE="http://eds.elong.com/mis/index2.asp"
Mis_USERNAME="zhidm"
Mis_PASSWORD="nopass.0515"
Mis_Main_PAGE="http://eds.elong.com/mis/default.asp?main="
Mis_SCREEN_PAGE="http://eds.elong.com/Mis.Net/Presentation.Mis.Hotel2/Booking/DiscernCallin.aspx?phoneNo=15801082374&byphonenum=&Pub_UUIData=&otherAgentID=&CPID=&submit=%20%20%b2%e9%20%20%bf%b4"
Mis_PROMOTION_INFO_PAGE="http://eds.elong.com/Mis.Net/Presentation.Mis.Hotel2/Booking/DiscernCallinForAjax.aspx?funtype=GetPromotion&byphonenum=&Pub_UUIData="
Mis_SERVICE_NAME_PAGE="http://eds.elong.com/Mis.Net/Presentation.Mis.Hotel2/Booking/DiscernCallinForAjax.aspx?funtype=GetServiceDescription&phoneno=15801082374&byphonenum=&CPID=&Pub_UUIData="
Mis_MEMBER_INDENTIFY_PAGE="http://eds.elong.com/Mis.Net/Presentation.Mis.Hotel2/Booking/DiscernCallinForAjax.aspx?t=1402395515290&funtype=Identify&phoneno=15801082374&card_no=&iscallinBinding=0&isalwaysnocheck=&byTelephone="
Mis_HISTORY_PHONE_PAGE="http://eds.elong.com/Mis.Net/Presentation.Mis.Hotel2/Booking/GetCardNoSibel.aspx?funtype=getsibel&card_no=190000000005708511"
Mis_AIR_DATA_PAGE="http://eds.elong.com/Mis.Net/Presentation.Mis.Hotel2/Booking/DiscernCallinForAjax.aspx?funtype=getairorder&card_no=190000000005708511"

if __name__ == "__main__":
	#hosts = ['BHB-HWebMis1.ab.elong.com','BHB-HWebMis2.ab.elong.com','BHA-HWebMis1.ab.elong.com','CHC-HWebMis1.ab.elong.com','CHC-HWebMis2.ab.elong.com','CHD-HWebMis1.ab.elong.com','CHD-HWebMis2.ab.elong.com','CHD-HWebMis3.ab.elong.com','CHE-HWebMis1.ab.elong.com','CHE-HWebMis2.ab.elong.com']
	alerttype="urlmonitor_webmis"
	productline="webmis"
	hosts = ['CHD-HWebMis3.ab.elong.com']
	for host in hosts:
		try:
			clear_extra_headers()
			add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9*/*;q=0.8")
			set_proxy({"http":host})
			
			#Mis eds Page
			eds_response = get(Mis_EDS_PAGE, "Mis eds Page", 200, 5000, [""])
			eds_instance = "eds_page";
			alert(productline,host,alerttype,eds_instance,eds_response)
			
			#Login
			formvalue(1,"staff_name_en",Mis_USERNAME)
			formvalue(1,"password",Mis_PASSWORD)
			login_response = post("Login to Mis", 200, 5000)
			login_instance = "Mis_login"
			alert(productline,host,alerttype,login_instance,login_response)
			
			#Mis Start Page
			eds_main_response = get(Mis_Main_PAGE, "Mis start Page", 200, 5000, [""])
			eds_main_instance = "mis_start";
			alert(productline,host,alerttype,eds_main_instance,eds_main_response)
			
			#screen page
			screen_response = get(Mis_SCREEN_PAGE, "Mis Open Screen", 200, 10000, ["15801082374"])
			screen_instance = "open screen"
			alert(productline,host,alerttype,screen_instance,screen_response)
			
			#promotion Page
			promotion_response = get(Mis_PROMOTION_INFO_PAGE, "Mis Promotion Page", 200, 10000, [""])
			promotion_instance = "promotion_info"
			alert(productline,host,alerttype,promotion_instance,promotion_response)
			
			#Service Name Page
			service_name_response = get(Mis_SERVICE_NAME_PAGE, "Mis Service Name Page", 200, 10000, ["PromotionDetail"])
			service_name_instance = "service_name"
			alert(productline,host,alerttype,service_name_instance,service_name_response)
			
			#Member Identification In Page
			member_response=get(Mis_MEMBER_INDENTIFY_PAGE, "Mis Member Identification page", 200, 10000, ["8511"])
			member_instance="member_identification"
			alert(productline,host,alerttype,member_instance,member_response)
			
			#Call History Page
			call_his_response=get(Mis_HISTORY_PHONE_PAGE, "Mis Call History page", 200, 10000, [""])
			call_his_instance="call_history"
			alert(productline,host,alerttype,call_his_instance,call_his_response)
			
			#air order page
			air_order_response = get(Mis_AIR_DATA_PAGE, "Mis Air Order Page",200,5000,[""])
			air_order_instance = "air_order"
			alert(productline,host,alerttype,air_order_instance,air_order_response)

			print "finished..."
		except Exception as e:
			traceback.print_exc()
			print "exception error is:%s" %e + " at url " + get_browser().get_url()
			
