# http response class
class httpresponse(object):
	# constractor function
	def __init__(self):
		self.__exitcode = 0
		self.__responsecode = 200
		self.__exceptiontitle = ""
		self.__exceptionmsg = ""
	
	def getexitcode(self):
		return self.__exitcode
	def setexitcode(self, value):
		self.__exitcode = value

	def getresponsecode(self):
                return self.__responsecode
        def setresponsecode(self, value):
                self.__responsecode = value


	def getexceptiontitle(self):
                return self.__exceptiontitle
        def setexceptiontitle(self, value):
                self.__exceptiontitle = value


	def getexceptionmsg(self):
                return self.__exceptionmsg
        def setexceptionmsg(self, value):
                self.__exceptionmsg = value


