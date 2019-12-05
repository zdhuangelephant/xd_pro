/**
 * Created by zedong.Huang on 2017/09/08
 */
if (typeof jQuery === 'undefined') {
    throw new Error('HistoryPlugin\'s JavaScript requires jQuery')
} else if (typeof StringUtil === 'undefined') {
    throw new Error('HistoryPlugin\'s JavaScript requires StringUtil')
}


var HistoryP = function (containerId, reqList, option) {
    var version = "1.0.0",
        _container = null,
        reqArr = {},
        reqLength = 0,
        _option = {},
        HistoryReq = function (req) {
            var _reqId = req.id,
                _reqUrl = req.url,
                _reqMethod = req.method,
                _HistoryReq = {
            		v: version,
                    type: "HistoryReq",
                    getId: function () {
                        return _reqId;
                    },
                    getMethod:function(){
                    	return _reqMethod;
                    },
                    setMethod:function(reqMethod){
                    	_reqMethod = reqMethod;
                    },
                    setUrl: function(reqUrl) {
                    	_reqUrl = reqUrl;
                    },
                    getUrl: function() {
                    	return _reqUrl;
                    },
                    getHtml: function () {
                    	return '<li>' + _reqMethod + ' <span href="javascript:" onclick="toDetailReq(\'' + _reqId + '\')">'+ _reqUrl +'</span></li>';
                    }
                }
            return _HistoryReq;
        },
       
        HistoryPlugin = function (containerSelector, reqList, option) {
            if (StringUtil.isBlank(containerSelector))
                throw new Error("HistoryPlugin can't init without container.");
            _container = $(containerSelector);
            if (null == _container || typeof(_container) == 'undefined')
                throw new Error("HistoryPlugin can't init without container.");
            if (null != reqList && typeof (reqList) != 'undefined') {
                for (var req in reqList) {
                    var __req = reqList[req];
                    if (__req.type != HistoryReq)
                    	__req = new HistoryReq(__req);
                    reqArr[__req.getId()] = __req;
                    reqLength++;
                }
            }
            var _HistoryPlugin = {
        		v: version,
                type: "HistoryPlugin",
                newHistoryReq: function (reqId) {
                    return new HistoryReq({id: reqId, url: "", method: ""});
                },
                insertHistoryReq: function (req) {
                    if (null == req)
                        throw new Error("Err insertHistoryReq: Target HistoryReq shoud not be null");
                    var __req = req;
                    if (req.type != "HistoryReq") {
                    	__req = new HistoryReq(__req);
                    }
                    if (StringUtil.isBlank(req.getId()))return;
                    reqArr[req.id] = req;
                    this.refreshView();
                },
                insertHistoryReqList: function (reqList) {
                	if (null != reqList && typeof (reqList) != 'undefined') {
                        for (var req in reqList) {
                            var __req = reqList[req];
                            if (__req.type != "HistoryReq")
                            	__req = new HistoryReq(__req);
                            reqArr[__req.getId()] = __req;
                            reqLength++;
                        }
                        this.refreshView();
                    }
                },
                deleteHistoryReq: function (reqId) {
                    if (StringUtil.isBlank(reqId))
                        throw new Error("Err deleteHistoryReq: Target reqId shouldn't be null");
                    var __req = reqArr[reqId];
                    if (null == __req || typeof (__req) == 'undefined')
                        throw new Error("Err deleteHistoryReq: Target HistoryReq didn't existed");
                    var __olddoc = docArr[__req.getDocId()];
                    if (null != __olddoc && typeof (__olddoc) != 'undefined') {
                    	delete reqArr[reqId];
                        reqLength--;
                        this.refreshView();
                    }
                },
                getHistoryReq: function (reqId) {
                    if (StringUtil.isBlank(reqId)) return null;
                    var req = reqArr[reqId];
                    if (null == req || typeof (req) == "undefined") return null;
                    return req;
                },
                getHistoryReqArr: function() {
                  return reqArr;
                },
                getHtml: function () {
                	var _html = '<ul>';
                    if (reqLength > 0) {
                        for (var __reqId in reqArr) {
                        	_html += reqArr[__reqId].getHtml();
                        }
                    } else {
                    	_html += '没有历史记录';
                    }
                    _html += '</ul>';
                    return _html;
                },
                refreshView: function () {
                    var _html = this.getHtml();
                    _container.html(_html);
                    _container.Fold(); 
                },
                searchHistory: function (searchKey) {
                	var _html = '';
                    if (reqLength > 0) {
                        for (var __reqId in reqArr) {
                        	var showreq = reqArr[__reqId];
                        	if(StringUtil.contain(showreq.getUrl(), searchKey)) {
                        		_html += reqArr[__reqId].getHtml();
                        	}
                        }
                    } else {
                    	_html += '没有历史记录';
                    }
                    _container.html(_html);
                    _container.Fold(); 
                }
            };
            _HistoryPlugin.refreshView();
            return _HistoryPlugin;
        };
    return new HistoryPlugin(containerId, reqList, option);
}