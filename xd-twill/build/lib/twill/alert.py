
import traceback
from twill.entities import *
import httplib
import urllib

# alert function
def alert(productline, host, alerttype, instance, response):
        if not response is None:
                #printDetail(host, instance, response)
                if(needAlert(response.getexitcode())):
                        realAlert(productline, host, alerttype, instance, response)
                else:
                        pass
        else:
                print "response==null"
                pass

# Judge to alert or not
def needAlert(exitcode):
        if exitcode > 0:
                return True
        else:
                return False

# realAlert
def realAlert(productline, host, alerttype, instance, response):
        #eventtype = "hotelmis_car"
        eventtype = alerttype
        eventinstance = instance
        title = productline + "-" + host + ":" + response.getexceptiontitle()
        message = response.getexceptionmsg()
        value = 0
        sendAlert(eventtype, eventinstance, title, message, value)

# send alert message
def sendAlert(eventType, eventInstance, title, message, value):
        #host = "172.21.13.75"
        host = "192.168.31.12"
        #host = "192.168.0.112"
        port = 9000
        timeout = 5000
        httpClient = httplib.HTTPConnection(host, port, timeout)
	realEventType = urllib.quote(eventType)
	realEventInstance = urllib.quote(eventInstance)
	realTitle=urllib.quote(title)
	realMessage=urllib.quote(message)
	#realMessage="errormessage"
        url = "/rest/com.eLong.Hotel.Alert.Services/AlertRecordRESTService/Record?eventTypeName=" + realEventType + "&eventInstanceName=" + realEventInstance + "&alertTitle=" + realTitle + "&alertMessage=" + realMessage + "&value=" + str(value)
        try:
                print "alerturl = " + url
                httpClient.request('GET', url)
        except Exception, e:
                print e
        finally:
                httpClient.close()


