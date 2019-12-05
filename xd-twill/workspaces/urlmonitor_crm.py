#!/usr/bin/python

from twill.commands import *
import twill.browser
from twill.twill_enhance import *
from twill.alert import *
from twill.entities import * 
import traceback

alerttype="urlmonitor_crm"
productline="crm_psg"

try: 
	
	print "===Start==="
	apihosts = ['CHF-OApiCrm1.ab.elong.com:8088','CHG-OApiCrm1.ab.elong.com:8088','DHC-OApiCrm1.ab.elong.com:8088','DHD-OApiCrm1.ab.elong.com:8088']
	#apihosts = ['DHC-OApiCrm1.ab.elong.com:8088','DHD-OApiCrm1.ab.elong.com:8088']
	#apihosts = ['CHF-OApiCrm1.ab.elong.com:8081']
	for host in apihosts:  
		try :			
			print "begin to check the availability of " + host		
			clear_extra_headers()
			add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9*/*;q=0.8")			
			#Set ebooking.elong.com proxy
			set_proxy({"http": host})
			
			hotelinfo_response = get("http://oapicrm.elong.com/mobile/api/hotelInfoList?userId=100&pageSize=1", "hotelInfoList", 200, 5000, [""])
			alert(productline, host, alerttype, "hotelInfoList", hotelinfo_response)
			
			signinghotel_response = get("http://oapicrm.elong.com/mobile/api/signingHotels?userId=100&pageSize=1", "signingHotels", 200, 5000, [""])
			alert(productline, host, alerttype, "signingHotels", signinghotel_response)
			
			visittype_response = get("http://oapicrm.elong.com/mobile/api/visitType", "visitType", 200, 5000, [""])
			alert(productline, host, alerttype, "visitType", visittype_response)
			print "success !!!"	
		except Exception, ex:
			print "failed : " + str(ex) + " \r\n"
			exstr=traceback.format_exc()
			print exstr
		finally:
			#reset browser
			reset_browser
	
	crm_username='crmadmin'
	crm_password='NOPASS.2'
	webhosts = ['CHH-OWebCrm1.ab.elong.com:8088','CHH-OWebCrm2.ab.elong.com:8088','DHE-OWebCrm1.ab.elong.com:8088','DHE-OWebCrm2.ab.elong.com:8088']
	#webhosts = ['DHE-OWebCrm1.ab.elong.com:8088','DHE-OWebCrm2.ab.elong.com:8088']
	#webhosts = ['DHE-OWebCrm1.ab.elong.com:8088','DHE-OWebCrm2.ab.elong.com:8088']
	for host in webhosts:  
		try :			
			print "begin to check the availability of " + host		
			clear_extra_headers()
			add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9*/*;q=0.8")			
			#Set ebooking.elong.com proxy
			set_proxy({"http": host})
			
			home_response = get("http://p.psgcrm.elong.com/system/home", "home", 200, 5000, [""])
			alert(productline, host, alerttype, "home", home_response)
			
			#Login
			logindata=urllib.urlencode({'username':crm_username, 'password':crm_password})
                        login_response = postwithoutform("http://p.psgcrm.elong.com/system/home","Login to crm", 200, 5000,[""],logindata)
			alert(productline,host,alerttype,"login to home",login_response)
			
			list_response = get("http://p.psgcrm.elong.com/system/crmHmRel/list", "list", 200, 5000, [""])
			alert(productline, host, alerttype, "list", list_response)
			
			signingHotels_response = get("http://p.psgcrm.elong.com/system/crmHmRel/signingHotels", "signingHotels", 200, 5000, [""])
			alert(productline, host, alerttype, "signingHotels", signingHotels_response)
			
			visitRecord_response = get("http://p.psgcrm.elong.com/system/visitRecord/searchHotelVisitRecord", "searchHotelVisitRecord", 200, 5000, [""])
			alert(productline, host, alerttype, "searchHotelVisitRecord", visitRecord_response)
			
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
