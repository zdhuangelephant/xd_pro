#!/usr/bin/python
# Filename: testweb.py

from twill.commands import *
import twill.browser
import traceback
from twill.twill_enhance import *
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


try:
	#MIS Login Page
	get("http://eds.elong.com/mis/index2.asp", "MIS Login Page", 200, 1000, ["IsHaveErrStr"])

	#Login
	formvalue(1, "staff_name_en", "wangyumeng")
	formvalue(1, "password", "nopass.123")
	post("Login to MIS", 200, 1000, ["sessionurl"])

	#Go to home page
	get("http://eds.elong.com/mis/default.asp?main=", "MIS Home", 200, 1000, ["mainframeset"])

	#Go to login redirect page
	get("http://eds.elong.com/mis/LoginToHotel3.asp?target=12", "Login redirect page", 200, 1000, ["TargetURL"])

	#hotel3 login, go to yd search order page
	post("Login to search order page", 200, 1000, ["btnSearch"])

	#Search order type 'V' list
	set_proxy({"http": "CHF-HWebHtl8.ab.elong.com"})
	
	orderTypeVLink = "http://yd.mis.elong.com/common/OrderCommonBusiness/OrderSearchResult?orderNo=&cellPhone=&txtInDateStart=&txtInDateEnd=&txtOutDateStart=&txtOutDateEnd=&modifyUser=&txtModifyStartDate=&txtModifyEndDate=&guestName=&contactorName=&hotelName=&CID=&firstOperator=&txtFirstStartDate=&txtFirstEndDate=&selectOrderType=V&roomCount=&roomCountPerNight=&payType=&selectSearchFrom=0&Page=1&cardNo=&isChecked=0"
	get(orderTypeVLink, "Order Search List", 200, 1000, ["SearchResultTable"])

	#Open first order detail page
	firstOrderLink = get_browser().find_link("orderhistory").url
	firstOrderLink = firstOrderLink.replace("http://yd.mis.elong.com/Common/OrderCommonBusiness/orderhistory?orderNO=", "http://yd.mis.elong.com/booking/Modify/index?ReserNo=")

	get(firstOrderLink, "Order Detail Page", 200, 1000, ["SaveComplete", "CHF-HWebHtl8"])

	print get_browser().get_url()
	print get_browser().get_html()
	print "finished"
except Exception, e:
	print str(e) + " at url " + get_browser().get_url()
	#show()
#print get_browser().get_html()

