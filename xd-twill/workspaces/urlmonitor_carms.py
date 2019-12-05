#!/usr/bin/python
# Filename: testweb.py

from twill.commands import *
import twill.browser
import traceback
from twill.twill_enhance import *
from twill.entities import *
from twill.alert import *
import httplib
#from hosts import Hosts

#Hotle3 yd, qr, sh, xz, cx WebSite
#try:
#	hostPath = "/etc/hosts"
#	h = Hosts(hostPath)
#	h.set_one("yd.mis.elong.com", "172.21.21.146")
#	h.write(hostPath)
#except Exception as e:
#	print 'Error: %s' % (e,)
#	exit(1)

# alert function
#def alert(host, instance, response):
#	if not response is None:
#		#printDetail(host, instance, response)
#		if(needAlert(response.getexitcode())):
#			realAlert(host, instance, response)
#		else:
#			pass
#	else:
#		print "response==null"
#		pass
#	
## Judge to alert or not
#def needAlert(exitcode):
#	if exitcode > 0:
#		return True
#	else:
#		return False
#
## realAlert
#def realAlert(host, instance, response):
#	eventtype = "hotelmis_car"
#	eventinstance = instance
#	title = host + ":" + response.getexceptiontitle()
#	message = response.getexceptionmsg()
#	value = 0
#	sendAlert(eventtype, eventinstance, title, message, value)	
#
## send alert message
#def sendAlert(eventType, eventInstance, title, message, value):
#	host = "192.168.31.43"
#	port = 9000
#	timeout = 5000
#	httpClient = httplib.HTTPConnection(host, port, timeout)
#	url = "/rest/com.eLong.Hotel.Alert.Services/AlertRecordRESTService/Record?eventTypeName=" + eventType + "&eventInstanceName=" + eventInstance + "&alertTitle=" + title + "&alertMessage=" + message + "&value=" + str(value)
#	try:
#		print "alerturl = " + url
#		httpClient.request('GET', url)
#	except Exception, e:
#		print e
#	finally:
#		httpClient.close()
#	
#
## print the detail
#def printDetail(host, instance, response):
#	print "=======================start============================="
#	print "host = " + host
#	print "instance = " + instance
#	print "response.exitcode = " + str(response.getexitcode())
#	print "response.responsecode = " + str(response.getresponsecode())
#	print "response.exceptiontitle = " + str(response.getexceptiontitle())
#	print "response.exceptionmsg = " + str(response.getexceptionmsg())
#	print "========================end=============================="
#
# main function
if __name__ == "__main__":
	
	#car online serverip list
	hosts = ["192.168.68.34"]
	#monitor the website one by one
	for host in hosts:	
		try:
			clear_extra_headers()
			add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8")
			#MIS Login Page
			mis_response = get("http://eds.elong.com/mis/index2.asp", "MIS Login Page", 200, 1000, ["IsHaveErrStr"])
			mis_instance = "mis_index"
			alert(host,mis_instance, mis_response)
	
			#Login
			formvalue(1, "staff_name_en", "hoteladmin")
			formvalue(1, "password", "nopass.2")
			login_response = post("Login to MIS", 200, 1000)
			login_instance = "mis_login"
			alert(host, login_instance, login_response)
			
			#default.asp
			default_response = get("http://eds.elong.com/mis/default.asp?main=", "MIS Home", 200, 1000, ["mainframeset"])
			default_instance = "default.asp"
			alert(host, default_instance, default_response)
		
			#set_proxy({"http": host})

			#Go to orderItem page
			orderItem_response = get("http://car.mis.elong.com:8080/taxi/orderItem", "OrderItem Page", 200, 5000, ["search"])
			orderItem_instance = "orderItem"
			alert(host, orderItem_instance, orderItem_response)
	
			#Go to orderItem list
			orderItemDetail_response = get("http://car.mis.elong.com:8080/taxi/orderItem?isSearch=true&pageNo=1&pageSize=20", "OrderItem Detail Page", 200, 5000, ["totalCount", "detailPermission"])
			orderItemDetail_instance = "orderItemDetail"
	                alert(host, orderItemDetail_instance, orderItemDetail_response)
	
			#Go to Merchant page	
			merchant_response = get("http://car.mis.elong.com:8080/merchant/queryMerchant", "QueryMerchant Page", 200, 5000, ["ID"])
			merchant_instance = "merchant"
	                alert(host, merchant_instance, merchant_response)
			
			#carapi_sguid
			#carapi_sguid_response = get("http://carapi.elong.com/m/taxi/get_sguid.html", "CarApi guid Page", 200, 5000, ["sguid"])
			#carapi_sguid_instance = "carapi_sguid"
	                #alert(host, carapi_sguid_instance, carapi_sguid_response)

			#print get_browser().get_url()
			#print get_browser().get_html()
			print "finished"
		except Exception as e:
			traceback.print_exc()
			#print "url:" + get_browser().get_url()
			print "exception error is :%s" % e + " at url " + get_browser().get_url()
			#show()
			#print get_browser().get_html()

