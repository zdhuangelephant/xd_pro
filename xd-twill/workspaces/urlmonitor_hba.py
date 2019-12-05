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
HBA_LOGIN_PAGE="http://hba.elong.com"
HBA_USERNAME="610721067"
HBA_PASSWORD="610721067"
HBA_ORDER_COMMON_PAGE="http://hba.elong.com/Common/OrderCommonBusiness/OrderSearch"
HBA_ORDERSEARCH_RESULT_PAGE="http://hba.elong.com/Common/OrderCommonBusiness/OrderSearchResult?orderNo=&cellPhone=&txtInDateStart=&txtInDateEnd=&txtOutDateStart=&txtOutDateEnd=&guestName=&contactorName=&hotelName=&CID=&selectOrderType=A&roomCount=&roomCountPerNight=&OrderFrom=undefined&Page=1&modifyUser=&txtModifyStartDate=&txtModifyEndDate=&firstOperator=&txtFirstStartDate=&txtFirstEndDate="
HBA_ORDERSEARCH_DETAIL_PAGE="http://hba.elong.com/booking/Modify/Index?ReserNo=92366376"
HBA_ORDER_BOOKING_PAGE="http://hba.elong.com/booking/search/index"
HBA_ORDER_BOOKING_JSON_PAGE="http://hba.elong.com/booking/search/SearchProduct/?searchParam=%7B%22CardNo%22%3A%228020097239%22%2C%22Ishba%22%3A%22false%22%2C%22NOneedCheckCardPasswordRigth%22%3A%22False%22%2C%22SortingMethod%22%3A%22%22%2C%22SortingDirection%22%3A%22%22%2C%22ShowAll%22%3Afalse%2C%22ArriveDate%22%3A%222014-04-16%22%2C%22LeaveDate%22%3A%222014-04-17%22%2C%22CityId%22%3A%22%22%2C%22HotelStart%22%3A%22%22%2C%22Hoteltype%22%3A%22a%22%2C%22HotelName%22%3A%22%u5317%u4EAC%u996D%u5E97%22%2C%22PoiType%22%3A%221%22%2C%22PoiCode%22%3A%22%22%2C%22Keywords%22%3A%22%22%2C%22OrderFrom%22%3A%221%22%2C%22Distant%22%3A%223000%22%2C%22OldOrderID%22%3A%22%22%7D"
HBA_ORDER_BOOKING_DETAIL_PAGE="http://hba.elong.com/booking/booking/Detail?CardNo=8020097239&HotelID=50101002&ArriveDate=2014-04-16&LeaveDate=2014-04-17&CustomerLevel=2&BookingChannel=2&SellChannel=2&ProxyID=BJDL-8213&sortBy=ByDefaultAndWeiFang_Asc&OrderID=1&reqfrom=&origincardno=&RecogniseCardNo=&mobile=&CTI_PhoneNo=&ByPhoneNum=&CPID=&card_agent_name=&card_agent=&card_agent_default=&card_agent_type=&card_agent_city_id=&Pub_UUIData=&xmlCustomers=&CTI_PhoneNo_Ask_id=&isBDCard=&come_from_hba=0&PromotionChannelCode=&IsHotelCost=-1&guid=57833caa-5b11-4969-8e2d-1ce548669d4d&memberAgentType=&preserno=&OutResNo=&greylist=&InitAgencySettlementType=false&indexInPage=1&IsBuyedRooms=0&cityid=&lat=39.909251&lot=116.410599&D=0&star=5&c=rt_1&comment=85&liwu=0&isdl=0&AuthName=HotelDetail&OldOrderID=&seeType=0&OriginalCardNo=08afaef1879c54e71ac3ab6bf8b635d2&OriginalAT=776d76aa8d6678ac09eea19dcf1a028d&OriginalLT=2b41fa3d27cea210f6be3c46c4f507ec"

if __name__ == "__main__":
	hosts = ['CHC-HHbaNew1.ab.elong.com', 'CHD-HHbaNew1.ab.elong.com', 'CHC-HHbaNew2.ab.elong.com', 'DHB-HHbaNew1.ab.elong.com', 'DHA-HHbaNew1.ab.elong.com', 'DHE-HHbaNew1.ab.elong.com', 'DHE-HHbaNew2.ab.elong.com']
	#hosts = ['CHC-HHbaNew1.ab.elong.com', 'CHD-HHbaNew1.ab.elong.com', 'CHC-HHbaNew2.ab.elong.com', 'CHD-HHbaNew2.ab.elong.com', 'DHB-HHbaNew1.ab.elong.com', 'DHA-HHbaNew1.ab.elong.com', 'DHE-HHbaNew1.ab.elong.com', 'DHE-HHbaNew2.ab.elong.com']
	alerttype="urlmonitor_hba"
	productline="hba"
	#hosts = ['CHD-HHbaNew1.ab.elong.com']
	for host in hosts:
		try:
			clear_extra_headers()
			add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9*/*;q=0.8")
			set_proxy({"http":host})		
			
			#HBA Logout
			#hba_logout_response = get(HBA_LOGOUT_PAGE, "HBA Logout page",200,5000,[""])
			#hba_logout_instance = "hba_logout"
			#alert(host, hba_logout_instance, hba_logout_response)
			
			#HBA Login Page
			hba_response = get(HBA_LOGIN_PAGE, "HBA Login Page", 200, 5000, [""])
			hba_instance = "hba_index";
			alert(productline,host,alerttype,hba_instance,hba_response)
			
			#Login
			formvalue(1,"userid",HBA_USERNAME)
			formvalue(1,"password",HBA_PASSWORD)
			formvalue(1,"logtype","1")
			login_response = post("Login to HBA", 200, 5000)
			login_instance = "hab_login"
			alert(productline,host,alerttype,login_instance,login_response)
			
			#defaultorder page
			default_response = get(HBA_ORDER_COMMON_PAGE, "HBA order master page", 200, 10000, ["hidisNeedPass"])
			default_instance = "order_commonbusiness"
			alert(productline,host,alerttype,default_instance,default_response)
			
			#OrderSearchResult Page
			ordersearchresult_response = get(HBA_ORDERSEARCH_RESULT_PAGE, "HBA order result page", 200, 10000, ["SearchResultTable"])
			ordersearchresult_instance = "ordersearchresult"
			alert(productline,host,alerttype,ordersearchresult_instance,ordersearchresult_response)
			
			#OrderSearchDetailResult Page
			ordersearchdetail_response = get(HBA_ORDERSEARCH_DETAIL_PAGE, "HBA order detail page", 200, 10000, ["tabSpecial"])
			ordersearchdetail_instance = "ordersearchdetail"
			alert(productline,host,alerttype,ordersearchdetail_instance,ordersearchdetail_response)
		
			#Booking Page
			booking_page=get(HBA_ORDER_BOOKING_PAGE, "HBA Booking master page", 200, 10000, ["searchhotelbtn"])
			booking_instance="bookingpage"
			alert(productline,host,alerttype,booking_instance,booking_page)
			
			#Booking Json Page
			booking_json_page=get(HBA_ORDER_BOOKING_JSON_PAGE, "HBA Booking search page", 200, 10000, ["BJDL-"])
			booking_json_instance="bookingjsonpage"
			alert(productline,host,alerttype,booking_json_instance,booking_json_page)
			
			#Booking detail Page
			booking_detail_page=get(HBA_ORDER_BOOKING_DETAIL_PAGE, "HBA Booking detail page", 200, 10000, ["hotelroomtag"])
			booking_detail_instance="bookingdetailpage"
			alert(productline,host,alerttype,booking_detail_instance,booking_detail_page)
								
			#HBA Logout
			hba_logout_response = get(HBA_LOGOUT_PAGE, "HBA Logout page",200,5000,[""])
			hba_logout_instance = "hba_logout"
			#alert(host, hba_logout_instance, hba_logout_response)

			print "finished..."
			
		except Exception as e:
			traceback.print_exc()
			print "exception error is:%s" %e + " at url " + get_browser().get_url()
			
