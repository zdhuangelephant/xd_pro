/**
 * Created by zhaodan on 2017/8/31.
 */
if (typeof jQuery === 'undefined') {
    throw new Error('DocPlugin\'s JavaScript requires jQuery')
} else if (typeof StringUtil === 'undefined') {
    throw new Error('DocPlugin\'s JavaScript requires StringUtil')
}

var DocP = function (containerId, docList, option) {
    var version = "1.0.0",
        _container = null,
        docArr = {},
        showDocArr = {},
        docLength = 0,
        reqArr = {},
        reqLength = 0,
        _option = {},
        Req = function (req) {
            var _reqId = req.id,
                _reqName = req.name,
                _reqVersion = req.version,
                _docId = null,
                _Req = {
            		v: version,
                    type: "Req",
                    getId: function () {
                        return _reqId;
                    },
                    setName: function (reqName) {
                        _reqName = reqName;
                    },
                    getName: function () {
                        return _reqName;
                    },
                    setVersion: function(reqVersion) {
                    	_reqVersion = reqVersion;
                    },
                    getVersion: function() {
                    	return _reqVersion;
                    },
                    setDocId: function (docId) {
                        if (null != docId && typeof (docId) != 'undefined')
                            _docId = docId;
                    },
                    getDocId: function () {
                        return _docId;
                    },
                    
                    //用例
                    getHtml: function () {
                    	var methodHtml = '';
                    	if(_option && _option.reqAMethod) {
                    		methodHtml += '<a onclick="'+ _option.reqAMethod.name +'(event,\'' + _reqId + '\',\'' + _docId + '\',\'' + _reqName + '\')">' + _option.reqAMethod.showName+'</a>';
                    	}
                    	//列表增删改
                    	if(_option && _option.reqSpanMethod && _option.reqSpanMethod.length > 0) {
                    		for(var methodIndex in _option.reqSpanMethod) {
                    			var method = _option.reqSpanMethod[methodIndex];
                    			methodHtml += '<li><span href="javascript:" onclick="'+ method.name +'(event,\'' + _reqId + '\',\'' + _docId + '\',\'' + _reqName + '\')">'+ method.showName +'</span></li>'
                    		}
                    	}
                        return '<div class="blue">' 
                            + '<span class="req-name req-name-display" onclick="'+ "toDetailReq" +'(event,\'' + _reqId + '\',\'' + _docId + '\',\'' + _reqName + '\')">' + _reqName +'</span>'
                        	+ '<a class="more-option">' + '···' + '</a>'
                        	+ '<ul class="option-list-wrap">' + methodHtml + '</ul>'
                            + '</div>';
                    }
                }
            return _Req;
        },
        Doc = function (doc) {
            var _docId = doc.id,
                _docName = doc.name,
                _docVersion = doc.version,
                _reqArr = {},
                _reqLength = 0,
                _Doc = {
            		v: version,
                    type: "Doc",
                    getId: function () {
                        return _docId;
                    },
                    setName: function (docName) {
                        _docName = docName;
                    },
                    getName: function () {
                        return _docName;
                    },
                    setVersion: function(docVersion) {
                    	_docVersion = docVersion;
                    },
                    getVersion: function() {
                    	return _docVersion;
                    },
                    pushReq: function (req) {
                        if (null == req || typeof (req) == 'undefined')return;
                        var __req = req;
                        if (__req.type != "Req")
                            __req = new Req(__req);
                        _reqArr[__req.getId()] = __req;
                        _reqArr[__req.getId()].setDocId(_docId);
                        _reqArr[__req.getId()].setVersion(_docVersion);
                        _reqLength++;
                        reqArr[__req.getId()] = __req;
                        reqLength++;
                    },
                    removeReq: function (reqId) {
                        delete _reqArr[reqId];
                        _reqLength--;
                        delete reqArr[reqId];
                        reqLength--;
                    },
                    getReqArr: function () {
                        return _reqArr;
                    },
                    getHtml: function () {
                    	var methodHtml = '';
                    	var methodA = '';
                    	var methodB ='';
                    	if(_option && _option.docAMethod) {
                    		methodA += '<a class="new-verson" onclick="'+ _option.docAMethod.name +'(event,\'' + _docId + '\',\'' + _docName + '\',\'' + _docVersion + '\')">'+'</a>';
                    	}
                    	if(_option && _option.docBMethod) {
                    		methodB += '<a class="new-share" onclick="'+ _option.docBMethod.name +'(event,\'' + _docId + '\',\'' + _docName + '\',\'' + _docVersion + '\')">'+'</a>';
                    	}
                    	if(_option && _option.docSpanMethod && _option.docSpanMethod.length > 0) {
                    		for(var methodIndex in _option.docSpanMethod) {
                    			var method = _option.docSpanMethod[methodIndex];
                    			methodHtml += '<span href="javascript:" onclick="'+ method.name +'(event,\'' + _docId + '\',\'' + _docName + '\',\'' + _docVersion + '\')">'+ method.showName +'</span>'
                    		}
                    	}
                        var html = '<p class="green">'  
                        	+ '<span class="req-name">' + _docName + '</span>'
                        	+ '<span class="r-option-wrap">'
                        	+ methodA
                        	+ methodB
                        	+ '<a class="r-more-option">' + '···' 
                        	+ '<span class="option-list-wrap option-list-wrap-doc">' + methodHtml + '</span>'
                        	+ '</a>'
                            + '</span>'
                            + '</p>'
                            + '<ul>';
                        if (_reqLength > 0) {
                            for (var reqId in _reqArr)
                                html += _reqArr[reqId].getHtml();
                        } else {
                            html += '该Doc下暂无用例数据';
                        }
                        html += '</ul>';
                        return html;
                    }
                };
            if (null != doc.reqList && typeof (doc.reqList) != 'undefined') {
                for (var req in doc.reqList)
                    _Doc.pushReq(doc.reqList[req]);
            }
            return _Doc;
        },
        DocPlugin = function (containerSelector, docList, option) {
        	_option.docAMethod = option && option.docAMethod?option.docAMethod:undefined;
        	_option.docBMethod = option && option.docBMethod?option.docBMethod:undefined;
        	_option.reqAMethod = option && option.reqAMethod?option.reqAMethod:undefined;
        	_option.docSpanMethod = option && option.docSpanMethod?option.docSpanMethod:[];
        	_option.reqSpanMethod = option && option.reqSpanMethod?option.reqSpanMethod:[];
            if (StringUtil.isBlank(containerSelector))
                throw new Error("DocPlugin can't init without container.");
            _container = $(containerSelector);
            if (null == _container || typeof(_container) == 'undefined')
                throw new Error("DocPlugin can't init without container.");
            if (null != docList && typeof (docList) != 'undefined') {
                for (var doc in docList) {
                    var __doc = docList[doc];
                    if (__doc.type != Doc)
                        __doc = new Doc(__doc);
                    docArr[__doc.getId()] = __doc;
                    docLength++;
                }
            }
            var _DocPlugin = {
        		v: version,
                type: "DocPlugin",
                newReq: function (reqId) {
                    return new Req({id: reqId, name: "", version: ""});
                },
                newDoc: function (docId) {
                    return new Doc({id: docId, name: "", version: ""});
                },
                insertReq: function (req) {
                    if (null == req || req.type != "Req")
                        throw new Error("Err insertReq: Target Req must be Req Type");
                    if (StringUtil.isBlank(req.getId()) || StringUtil.isBlank(req.getDocId()))return;
                    var __req = reqArr[req.getId()];
                    if (null != __req && __req.type == "Req")
                        throw new Error("Err insertReq: Target Req has existed");
                    var __newdoc = docArr[req.getDocId()];
                    if (null == __newdoc || typeof (__newdoc) == 'undefined')
                        throw new Error("Err insertReq: Target Doc didn't existed");
                    __newdoc.pushReq(req);
                    this.refreshView();
                },
                insertDoc: function (doc) {
                    if (null == doc || doc.type != "Doc")
                        throw new Error("Err insertDoc: Target Doc must be Doc Type");
                    if (StringUtil.isBlank(doc.getId()))return;
                    docArr[doc.getId()] = doc;
                    docLength++;
                    this.refreshView();
                },
                updateReq: function (req) {
                    if (null == req || req.type != "Req")
                        throw new Error("Err updateReq: Target Req must be Req Type");
                    if (StringUtil.isBlank(req.getId()) || StringUtil.isBlank(req.getDocId()))return;
                    var __req = reqArr[req.getId()];
                    if (null == __req || typeof (__req) == 'undefined')
                        throw new Error("Err updateReq: Target Req didn't existed");
                    var __olddoc = docArr[__req.getDocId()],
                        __newdoc = docArr[req.getDocId()];
                    if (null != __olddoc && typeof (__olddoc) != 'undefined') {
                        __olddoc.removeReq(req.getId());
                    }
                    if (null == __newdoc || typeof (__newdoc) == 'undefined')return;
                    __newdoc.pushReq(req);
                    this.refreshView();
                },
                updateDoc: function (doc) {
                    if (null == doc || doc.type != "Doc")
                        throw new Error("Err updateDoc: Target Doc must be Doc Type");
                    if (StringUtil.isBlank(doc.getId()))return;
                    var __doc = docArr[doc.getId()];
                    if (null == __doc || typeof (__doc) == 'undefined')
                        throw new Error("Err updateDoc: Target Doc didn't existed");
                    docArr[doc.getId()] = doc;
                    this.refreshView();
                },
                deleteReq: function (reqId) {
                    if (StringUtil.isBlank(reqId))
                        throw new Error("Err deleteReq: Target reqId shouldn't be null");
                    var __req = reqArr[reqId];
                    if (null == __req || typeof (__req) == 'undefined')
                        throw new Error("Err deleteReq: Target Req didn't existed");
                    var __olddoc = docArr[__req.getDocId()];
                    if (null != __olddoc && typeof (__olddoc) != 'undefined') {
                        __olddoc.removeReq(reqId);
                        this.refreshView();
                    }
                },
                deleteDoc: function (docId) {
                    if (StringUtil.isBlank(docId))
                        throw new Error("Err deleteDoc: Target docId shouldn't be null");
                    var __doc = docArr[docId];
                    if (null == __doc || typeof (__doc) == 'undefined')
                        throw new Error("Err deleteDoc: Target Doc didn't existed");
                    if (null != __doc.getReqArr()) {
                        for (var req in __doc.getReqArr()) {
                            delete reqArr[__doc.getReqArr()[req].getId()];
                            reqLength--;
                        }
                    }
                    delete docArr[docId];
                    docLength--;
                    this.refreshView();
                },
                getDoc: function (docId) {
                    if (StringUtil.isBlank(docId)) return null;
                    var doc = docArr[docId];
                    if (null == doc || typeof (doc) == "undefined") return null;
                    return new Doc({id: doc.getId(), name: doc.getName()});
                },
                getReq: function (reqId) {
                    if (StringUtil.isBlank(reqId)) return null;
                    var req = reqArr[reqId];
                    if (null == req || typeof (req) == "undefined") return null;
                    var _req = new Req({id: req.getId(), name: req.getName()});
                    _req.setDocId(req.getDocId());
                    return _req;
                },
                getDocLength: function() {
                  return docLength;
                },
                getReqLength: function() {
                  return reqLength;
                },
                getDocArr: function() {
                  return docArr;
                },
                getReqArr: function() {
                  return reqArr;
                },
                getHtml: function () {
                	var _html = '';
                    if (docLength > 0) {
                        for (var __docId in docArr) {
                        	_html += docArr[__docId].getHtml();
                        }
                    } else {
                    	_html += 'Doc暂无数据';
                    }
                    return _html;
                },
                getOption: function() {
                	return _option;
                },
                setOption: function(option) {
                	_option.docAMethod = option && option.docAMethod?option.docAMethod:undefined;
                	_option.docBMethod = option && option.docBMethod?option.docBMethod:undefined;
                	_option.reqAMethod = option && option.reqAMethod?option.reqAMethod:undefined;
                	_option.docSpanMethod = option && option.docSpanMethod?option.docSpanMethod:[];
                	_option.reqSpanMethod = option && option.reqSpanMethod?option.reqSpanMethod:[];
                },
                refreshView: function () {
                    var _html = this.getHtml();
                    _container.html(_html);
                    _container.Fold(); 
                },
                searchDoc: function (searchKey) {
                	var _html = '';
                    if (docLength > 0) {
                        for (var __docId in docArr) {
                        	var showdoc = docArr[__docId];
                        	if(StringUtil.contain(showdoc.getName(), searchKey)) {
                        		_html += docArr[__docId].getHtml();
                        	}
                        }
                    } else {
                    	_html += 'Doc暂无数据';
                    }
                    _container.html(_html);
                    _container.Fold(); 
                }
            };
            _DocPlugin.refreshView();
            return _DocPlugin;
        };
    return new DocPlugin(containerId, docList, option);
}