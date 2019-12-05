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

		#print "request : " + url
		
		start = datetime.datetime.now()
		response = httpresponse()
		error_exitcode = 1
		request_code = 0
		try:
			if(method == 'get'):
				go(url)
			elif(method == 'post'):
				submit()
			else:		
				postresult = urlopen(url, data)
		except Exception, reqEx:
			request_code = 1
			
		elapsed = (datetime.datetime.now() - start).seconds
		usedms = elapsed * 1000
		
		if(request_code == 1):
			response.setexitcode(error_exitcode)
			response.setresponsecode(500)
			response.setexceptiontitle(title + " - HttpStatus=" + str(500) + ". Timespan=" + str(usedms) + "ms. can not to open " + url)
			response.setexceptionmsg(title + " - HttpStatus=" + str(500) + ". Timespan=" + str(usedms) + "ms. can not to open " + url)
			return response
		else:
			pass
		
		actualcode = get_browser().get_code()
		print "==>responsecode=" + str(actualcode)
		if(method == 'postwithoutform'):
			html = postresult.read()
		else:
			html = get_browser().get_html()

		try:
			temptitle = re.findall(r'<title>[\s\S]*?</title>',html)
		except Exception, titleEx:
			temptitle = ""

		temptitlelength = len(temptitle)
		if(temptitlelength > 0):
			realtitle = re.findall(r'<title>[\s\S]*?</title>',html)[0].replace('<title>','').replace('</title>','')
		else:
			realtitle = str(html)

		if(actualcode != code):
			#realtitle = re.findall(r'<title>[\s\S]*?</title>',html)[0].replace('<title>','').replace('</title>','')
			response.setexitcode(error_exitcode)
			response.setresponsecode(actualcode)
			response.setexceptiontitle(title + " - HttpStatus=" + str(actualcode) + ". Timespan=" + str(usedms) + "ms. " + realtitle)
			#print html
			response.setexceptionmsg(html)
			return response
		else:
			pass

		for keyword in keywords:
			if html.find(keyword) < 0:
				#realtitle = re.findall(r'<title>[\s\S]*?</title>',html)[0].replace('<title>','').replace('</title>','')
				response.setexitcode(error_exitcode)
				response.setresponsecode(actualcode)
				response.setexceptiontitle(title + " - Can not find keyword=" + keyword + ". Timespan=" + str(usedms) + "ms. " + realtitle)
				response.setexceptionmsg(html)
				return response

		if(usedms > interval):
			#realtitle = re.findall(r'<title>[\s\S]*?</title>',html)[0].replace('<title>','').replace('</title>','')
			response.setexitcode(error_exitcode)
			response.setresponsecode(actualcode)
			response.setexceptiontitle(title + " - Timeout(" + str(usedms) + "ms>" + str(interval) + "ms).")
			response.setexceptionmsg(html)
			return response
	except Exception,ex:
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

#返回调用GET方法获取请求结果，不做任何报警处理
def request(url=''):
	try:
		request = urllib.urlopen(url)
		response = request.read()
		return response
	except Exception, ex:
		print str(ex)
		return ""
