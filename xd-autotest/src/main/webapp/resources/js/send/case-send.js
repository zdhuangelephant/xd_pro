
function docSend(obj) {
	var params = new Array();
    var preConditions = new Array();
    var preSets = new Array();
    var testCases = new Array();
    var afterSets = new Array();
    
    // 获取标识符,辨别是history发送，还是case查看后发送
    var reqType = $(obj).parent().parent().parent().parent().parent().attr("reqtype");
    if('historyreq'==reqType){
    	//历史记录提交
    	
    }else if('aftercheckreq' == reqType){
    	// case查看提交
    	
    }
	var method = $(obj).parent().parent().parent().find("#dropdown p").html();
	method = method.toLowerCase( );
	var url = $(obj).parent().parent().parent().find("#req_url").val();
	if(url==''){
		alert("请填写URL")
		return;
	}
	
	var $prefix = $(obj).parent().parent().parent().parent();
	var reqName = $prefix.parent().attr("data-caseReqName");
	var $t1 = $prefix.find("table[id='paramTable']");
	var $t2 = $prefix.find("table[id='PreConditionTable']");
	var $t3 = $prefix.find("table[id='PreSetTable']");
	var $t4 = $prefix.find("table[id='TestCaseTable']");
	var $t5 = $prefix.find("table[id='AfterSetTable']");
	
	var $t7 = $prefix.find("table[id='headerTable']");

	
	// 1. 获取5个table的值
	
	var paramArguments;
	var format;
	
	var label_flag = true;
	// 获取Header的值
	var isCheckboxSize = 0;
	for(var i=0; i <= $t7.find('tr').length; i++){ 
	    if($t7.find('tr').eq(i).find("td:first input[type='checkbox']").is(':checked')){
	    	isCheckboxSize ++;
	    }
	} 
	$t7.find('tr').each(function(index, ele){
		if(index == 0){return true;}
        var row= new Object();
        $(this).find('input,select,textarea').each(function(){
        	
        	// type="checkbox"
        	if($(this).attr('type')=='checkbox' && (isCheckboxSize > 0) && !$(this).is(':checked')){
        		return false;
        	}
        	if($(this).attr('type')=='checkbox'){
        		return true;
        	}
        	
        	if($(this).attr('name')=='name' && $(this).val()==''){
				return false;
			}else{
				row[$(this).attr('name')]=$(this).val();
			}
        });
        if(JSON.stringify(row) != '{}'){
        	params.push(row);
		}
    });
	// Param 当选择raw时
	var _isClick = $prefix.parent().parent().find('#tab-content .tabs-wrap .l-con-con .body-tab input:radio[name="body"]:checked').val();
	if(_isClick =='x-www-form-urlencoded' || typeof(_isClick)=='undefined'){
		// Param
		$t1.find('tr').each(function(index, ele){
			if(index == 0){return true;}
	        var row= new Object();
	        $(this).find('input,select,textarea').each(function(){
	        	if($(this).attr('name')=='name' && $(this).val()==''){
					return false;
				}else{
					row[$(this).attr('name')]=$(this).val();
				}
	        });
	        if(JSON.stringify(row) != '{}'){
	        	params.push(row);
			}
	    });
		format = "normal";
	}else{
		var textAreaV = $prefix.parent().find('.body-content-wrap .body-content textarea').val() ;
		format = 'raw';
		var row = {};
		row['name'] =  'xxx'
		row['paramValue'] = textAreaV;  
		row['desc'] =  'xxx';
		row['paramType'] =  "HeaderParam";
		params.push(row);
	}
	
	paramArguments = params.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(params))));

    // PreConditions
	$t2.find('tr').each(function(index, ele){
		if(index == 0){return true;}
        var row= new Object();
        $(this).find('input,select,textarea').each(function(){
        	if($(this).attr('name')=='key' && $(this).val()==''){
				return false;
			}else{
				row[$(this).attr('name')]=$(this).val();
			}
        });
        
        if(JSON.stringify(row) != '{}'){
        	preConditions.push(row);
		}
    });
	
    // PreSet
	$t3.find('tr').each(function(index, ele){
		if(index == 0){return true;}
        var row= new Object();
        $(this).find('input,select,textarea').each(function(){
        	if($(this).attr('name')=='key' && $(this).val()==''){
        		row = {};
				return false;
			}else{
				row[$(this).attr('name')]=$(this).val();
			}
        });
        if(JSON.stringify(row) != '{}'){
        	preSets.push(row);
		}
    });
	
    // TestCase
	$t4.find('tr').each(function(index, ele){
		if(index == 0){return true;}
        var row= new Object();
        $(this).find('input,select,textarea').each(function(){
        	if($(this).attr('name')=='testKey' && $(this).val()==''){
				return false;
			}else{
				row[$(this).attr('name')]=$(this).val();
			}
        });
        if(JSON.stringify(row) != '{}'){
        	testCases.push(row);
		}
    });
	
    // AfterSet
	$t5.find('tr').each(function(index, ele){
		if(index == 0){return true;}
        var row= new Object();
        $(this).find('input,select,textarea').each(function(){
        	if($(this).attr('name')=='resultKey' && $(this).val()==''){
				return false;
			}else{
				row[$(this).attr('name')]=$(this).val();
			}
        });
        if(JSON.stringify(row) != '{}'){
        	afterSets.push(row);
		}
    });
   
	
	$(obj).removeAttr('href');
	$(obj).removeAttr('onclick');
	$(obj)[0].innerHTML="执行中";
	$(obj).css('background','red');
	
	
	$.post("/request/caseSend", {
		"reqName" : $.trim(reqName) ,
		"method" :$.trim(method) ,
		"url" : $.trim(url),
		"params":paramArguments,
		"format" : format,
		"preConds":preConditions.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(preConditions)))),
		"preSets":preSets.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(preSets)))),
		"testCases":testCases.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(testCases)))),
		"afterSets":afterSets.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(afterSets))))
	}, function(data) {
		//$('#loading', window.parent.document).hide();
			var u = JSON.parse(data);
			if('aftercheckreq' == reqType)
				refreshHistory(u);
			$(obj).attr('href',"javascript:");
			$(obj).attr('onclick',"docSend(this)");
			$(obj)[0].innerHTML="Send";
			$(obj).css('background','#097bed');		
			if (u.status == "success") {			
				var srcResult=u.srcResult;
				var jsonBtn = $(obj).parents("#tab-content").next().find("#json-btn");
				var textBtn = $(obj).parents("#tab-content").next().find("#text-btn");
				var tTextarea = $(obj).parents("#tab-content").next().find("#j-textarea");
				var strText = "";
				var strJson = "";
				var errmsg = "";
				if(srcResult==""){	
					errmsg = "请求超时无响应: <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.地址不存在 <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.请求超时";
					strJson=errmsg;
				    strText=errmsg;
				}else{
					try {		　
						var json = JSON.parse(srcResult);  
//						strText ='<pre>' + JSON.stringify(json) + '</pre>';
//						strJson = '<pre>' +JSON.stringify(json, null, 4) + '</pre>';
						strText =JSON.stringify(json);
						strJson = JSON.stringify(json, null, 4);
					} catch(error) {
						strJson=srcResult;
					    strText=srcResult;
					}
				}
				if(errmsg == ""){
					strJson = "<xmp>" + strJson + "</xmp>";
					strText = "<xmp>" + strText + "</xmp>";
				}
				
				tTextarea[0].innerHTML =strJson;
				jsonBtn[0].onclick = function () {
				tTextarea[0].innerHTML =strJson;
				    };
			    textBtn[0].onclick = function () {
				     tTextarea[0].innerHTML =strTex;
				};	   
				var testCaseArray=u.testCase;
				var testCaseWrap = $(obj).parents("#tab-content").next().find("#testCaseTables");
				testCaseWrap.empty();
				var row="<tr><th>name</th><th>result</th></tr>";
				testCaseWrap.append(row); 
				if(testCaseArray!=null){
					for(var i=0;i<testCaseArray.length;i++){ 
						  var row="<tr><td>"+testCaseArray[i].name+"</td><td>"+testCaseArray[i].testResult+"</td></tr>";  
						  testCaseWrap.append(row); 
					 }
				}		  
				
				
				// RequestBody > Header
				var requestBody=u.requestBody;
				var requestBodyTablesHeaderWrap = $(obj).parents("#tab-content").next().find("#requestBodyTablesHeader");
				requestBodyTablesHeaderWrap.empty();
				var row="<tr><th>请求头</th><th>请求值</th></tr>";
				requestBodyTablesHeaderWrap.append(row); 
				if(null != requestBody && requestBody.header != null){
					for(var key in requestBody.header){
						var row="<tr><td>" + key + "</td><td>" + requestBody.header[key] + "</td></tr>";
						requestBodyTablesHeaderWrap.append(row);
					}
					requestBodyTablesHeaderWrap.append("</br>");
				}
				
				// RequestBody > Body  if(NameValuePair)
				if(null != requestBody && requestBody.format != null && requestBody.format == 'NameValuePair'){
					var requestBodyTablesBodyArray=u.requestBody.body;
					var requestBodyTablesBodyWrap = $(obj).parents("#tab-content").next().find("#requestBodyTablesBody");
					requestBodyTablesBodyWrap.empty();
					var row="<tr><th style='width:120px'>参数名</th><th>参数值</th></tr>";
					requestBodyTablesBodyWrap.append(row); 
					if(requestBodyTablesBodyArray!=null){
						for(var i=0;i<requestBodyTablesBodyArray.length;i++){ 
							  var row="<tr><td>"+requestBodyTablesBodyArray[i].name+"</td><td>"+requestBodyTablesBodyArray[i].value+"</td></tr>";  
							  requestBodyTablesBodyWrap.append(row); 
						 }
					}
				// RequestBody > Body  if(jSon)
				}else if(null != requestBody && requestBody.format != null && requestBody.format == 'jSon'){
					var srcResult0=u.requestBody.body;
					var jsonBtn0 = $(obj).parents("#tab-content").next().find("#json-btn0");
					var textBtn0 = $(obj).parents("#tab-content").next().find("#text-btn0");
					var tTextarea0 = $(obj).parents("#tab-content").next().find("#j-textarea0");    
					var strText0 = "";
					var strJson0 = "";
					var emsg = "";
					if(srcResult0==""){
						emsg = "暂无参数";
						strJson0=emsg;
					    strText0=emsg;
					}else{
						// jsoneditor0
						document.getElementById("jsoneditor0").style.display="";
						try{
							var json0 = JSON.parse(srcResult0);  
							strText0 =JSON.stringify(json0);
							strJson0 =JSON.stringify(json0, null, 4);
						} catch(error) {
							strJson0=srcResult0;
						    strText0=srcResult0;
						　}
					}
					if(emsg == ""){
						strJson0 = "<xmp>" + strJson0 + "</xmp>";
						strText0 = "<xmp>" + strText0 + "</xmp>";
					}
					tTextarea0[0].innerHTML =strJson0;
					jsonBtn0[0].onclick = function () {
						tTextarea0[0].innerHTML =strJson0;
					};
				    textBtn0[0].onclick = function () {
				    	tTextarea0[0].innerHTML =strText0;
					};	   
				}
				
				
				
			  
			} else {
				alert(u.data);
			}
		});
	}

function refreshHistory(obj){
	var $html = $("#history-content ul").html();
	$("#history-content ul").html("");
	if (obj != null && !jQuery.isEmptyObject(obj) && !jQuery.isEmptyObject(obj.log)) {
		$('#history-content ul').append(
				"<li> "+obj.log.method+"&nbsp;<a href='javascript:;' onclick=\"toDisplay('"+obj.log.id+"',this)\">"+obj.log.url+"</a> </li>")
					.append($html);
	}
	
}
	