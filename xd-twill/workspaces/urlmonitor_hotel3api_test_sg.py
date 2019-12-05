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

ServerGroupList=["/ELONG/Hotel/Hotel3_Api_Ebk","/ELONG/Hotel/Hotel3_Api_Mis","/ELONG/Hotel/Hotel3_Api_NB","/ELONG/Hotel/Hotel3_Api_Web","/ELONG/Hotel/Hotel3_Asyn","/ELONG/Hotel/Hotel3_Api_Hba","/ELONG/Hotel/Hotel3_Api_HotelMaster","/ELONG/Hotel/Hotel3_Web"]
UrlHeader="Http://"
BaseUrl=":9000/REST/com.eLong.Hotel.Framework.Core.Services/ServiceHostControlRESTService/"
MemcachedHealthCheck_Url=BaseUrl + "CheckMemcached"
OrderHealthCheck_URL=BaseUrl + "CheckHotelOrder"
ProductHealthCheck_URL=BaseUrl + "CheckHotelProduct"
RabbitMQHealthCheck_Url=BaseUrl + "CheckRabbitMQ"
GetServerListByServerGroup_BaseUrl="Http://dashboard2.mis.elong.com/servermanager/serverlist?serverGroupName="

if __name__ == "__main__":
	alerttype="urlmonitor_hotel3api"
	productline="hotel3"
	for serverGroup in ServerGroupList:
		try:
			# set http header
			clear_extra_headers()
			add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9*/*;q=0.8")
			# get serverlist by servergroup
			print "serverGroup=" + serverGroup
			realSgUrl = GetServerListByServerGroup_BaseUrl + serverGroup
			serverListStr = request(realSgUrl)
			if(serverListStr == ""):
				print "serverListStr = space"
				continue
			else:
				pass
			serverList = serverListStr.split(',')
			for server in serverList:
				print "server=" + server
			print "finished..."
		except Exception as e:
			traceback.print_exc()
			print "exception error is:%s" %e + " at url " + get_browser().get_url()
			
