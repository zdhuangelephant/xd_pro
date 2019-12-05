#!/usr/bin/python
# Filename: twill_enhance.py

from twill.commands import *
from twill.entities import *
import time
import datetime
import re
from urllib2 import urlopen
import traceback

class HttpStatusError(Exception):
    """
    HttpStatusError
    """
    pass

class KeywordNotFoundError(Exception):
    """
    KeywordNotFoundError
    """
    pass

class WebLoadTimeoutError(Exception):
    """
    WebLoadTimeoutError
    """
    pass

def do_http_action(method, url, data, title, code, interval, keywords):
	try:

		print "request : " + url
		#add_extra_header("Accept","text/plain,text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8")
		#show_extra_headers()
		start = datetime.datetime.now()
		response = httpresponse()
		error_exitcode = 1
		if(method == 'get'):
			go(url)
		elif(method == 'post'):
			submit()
		else:
			postresult = urlopen(url, data)
	
		elapsed = (datetime.datetime.now() - start).seconds
		usedms = elapsed * 1000
	
		actualcode = get_browser().get_code()
		print "==>responsecode=" + str(actualcode)
		if(method == 'postwithoutform'):
			html = postresult.read()
		else:
			html = get_browser().get_html()
		temptitle = re.findall(r'<title>[\s\S]*?</title>',html)
		temptitlelength = len(temptitle)
		if(temptitlelength > 0):
			realtitle = re.findall(r'<title>[\s\S]*?</title>',html)[0].replace('<title>','').replace('</title>','')
		else:
			realtitle = html
		if(actualcode != code):
			#realtitle = re.findall(r'<title>[\s\S]*?</title>',html)[0].replace('<title>','').replace('</title>','')
			response.setexitcode(error_exitcode)
			response.setresponsecode(actualcode)
			response.setexceptiontitle(title + "-HttpStatus=" + str(actualcode) + ".Timespan=" + str(usedms) + "ms." + realtitle)
			#print html
			response.setexceptionmsg(html)
			return response
	
		for keyword in keywords:
			if html.find(keyword) < 0:
				#realtitle = re.findall(r'<title>[\s\S]*?</title>',html)[0].replace('<title>','').replace('</title>','')
				response.setexitcode(error_exitcode)
				response.setresponsecode(actualcode)
				response.setexceptiontitle(title + "Can not find keyword=" + keyword + ".Timespan=" + str(usedms) + "ms." + realtitle)
				response.setexceptionmsg(html)
				return response
	
		if(usedms > interval):
			#realtitle = re.findall(r'<title>[\s\S]*?</title>',html)[0].replace('<title>','').replace('</title>','')
			response.setexitcode(error_exitcode)
			response.setresponsecode(actualcode)
			response.setexceptiontitle(title + "Timespan=" + str(usedms) + "ms>" + str(interval) + "ms." + realtitle)
			response.setexceptionmsg(html)
			return response
	except Exception, ex:
		response.setexitcode(error_exitcode)
                response.setresponsecode(500)
                response.setexceptiontitle(str(ex))
                response.setexceptionmsg(traceback.format_exc())
	return response

def get(url = '', title = '', code = 200, interval = 5000, keywords = [], data=''):
	return do_http_action('get', url, data, title, code, interval, keywords)

def post(title = '', code = 200, interval = 5000, keywords = [], data=''):
	return do_http_action('post', '', '', title, code, interval, keywords)

def postwithoutform(url = '', title = '',code = 200,interval = 5000,keywords = [], data=''):
	return do_http_action('postwithoutform', url, data, title, code, interval, keywords )

