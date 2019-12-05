/**
 * postman添加参数
 */

 			//全局的ajax访问，处理ajax清求时sesion超时  

	var params = [];
	var preConditions = [];
	var preSets = [];
	var testCases = [];
	var afterSets = [];
	var outParams = "";
	
function doSave(obj){
	params.length = 0;
	preConditions.length = 0;
	preSets.length = 0;
	testCases.length = 0;
	afterSets.length = 0;
	outParams = '';
	
	var $prefix = $(obj).parent().parent().parent();
	var $t1 = $prefix.find("table[id='paramTable']");
	var $t2 = $prefix.find("table[id='PreConditionTable']");
	var $t3 = $prefix.find("table[id='PreSetTable']");
	var $t4 = $prefix.find("table[id='TestCaseTable']");
	var $t5 = $prefix.find("table[id='AfterSetTable']");
	var $t6 = $prefix.find("textarea[id='out-j-textarea']");
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
	var _isClick = $prefix.parent().find('#tab-content .tabs-wrap .l-con-con .body-tab input:radio[name="body"]:checked').val();
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

    // preConditions
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
	
    // OutParams
	outParams=$t6.val();
	
	// 2. 判断是添加还是修改
	// 请求id
    var temp = $prefix.parent().attr("data-docreq");
	if(typeof(temp)!="undefined" && temp != ""){
		// 修改
		var method = $(obj).parent().parent().find("#dropdown p").html();
		method = method.toLowerCase();
		var req_url = $(obj).parent().parent().find("#req_url").val();
		if(req_url==''){
			alert("请填写URL")
			return
		}
		
		var req_name = $('#req_name').val();
		var docReqId =$prefix.parent().attr("data-docreq");
		var docId =$prefix.parent().attr("data-docId");
		$.post("/doc/requestAdd", {
			"reqName" : $.trim(req_name),
			"method" : $.trim(method),
			"docId" :  $.trim(docId),
			"reqUrl" : $.trim(req_url),
			"docReqId": $.trim(docReqId),
			"format" : format,
			"params":paramArguments,
			"preConditions":preConditions.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(preConditions)))),
			"preSets":preSets.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(preSets)))),
			"testCases":testCases.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(testCases)))),
			"afterSets":afterSets.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(afterSets)))),
			"outParams":base64encode(utf16to8(encodeURIComponent(outParams)))
		}, function(data) {
			var res = JSON.parse(data);
			if (true == res.status) {
				alert(res.msg)
				var docReq = nd.getReq($.trim(docReqId));
				docReq.setName($.trim($prefix.parent().attr("data-docReqName")));
				docReq.setDocId($.trim(docId));
				nd.updateReq(docReq);
				
			} else {
				alert(res.msg)
			}
			
			params.length = 0;
			preConditions.length = 0;
			preSets.length = 0;
			testCases.length = 0;
			afterSets.length = 0;
			outParams = '';
		});
		
	}else{
		// 添加
		var req_url = $(obj).parent().parent().find("#req_url").val();
		if(req_url==''){
			alert("请填写URL")
			return
		}
		$('#save-shadow-box').show();
		$('#colls').html("");
		$.post("/doc/getDocuments", function(data) {
			var u = JSON.parse(data);
			if (u == '' || u.length <= 0) {
				alert("请先创建Document即Collection");
				return;
			}
			for ( var ele in u) {
				$('#colls').append(
						"<option value='" + u[ele].id + "'>"
								+ u[ele].name + "</option>")
			}
		});
	}
}

window.onload = function() {
	// 添加操作
	$("#addReq").click(function() {
		params.length = 0;
		preConditions.length = 0;
		preSets.length = 0;
		testCases.length = 0;
		afterSets.length = 0;
		outParams = '';
		
		// 获取激活状态的tab
		$("#tabContent").find("div[name='parentDiv']").each(function(){
			if( $(this).css("display")=='block' ) {
				var label_flag = true;
				var req_url = $(this).find("#req_url").val();
				if(req_url==''){
					alert("请填写URL")
					return;
				}
				var req_name = $("#req_name").val();
				if(req_name==''){
					alert("请命名req");
					return;
				}
				
				// 获取Header的值
				var isCheckboxSize = 0;
				for(var i=0; i <= $(this).find("#tab-content").find("#tabs-wrap").find("#Header .widthTable").find('tr').length; i++){ 
				    if($(this).find("#tab-content").find("#tabs-wrap").find("#Header .widthTable").find('tr').eq(i).find("td:first input[type='checkbox']").is(':checked')){
				    	isCheckboxSize ++;
				    }
				} 
				$(this).find("#tab-content").find("#tabs-wrap").find("#Header .widthTable").find('tr').each(function(index, ele){
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
				
				// AfterSet
				$(this).find("#tab-content").find("#tabs-wrap").find("#AfterSet .widthTable").find('tr').each(function(index, ele){
//			       
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
				
				var format;
				// Param 当选择raw时
				var _isClick = $(this).find("#tab-content input[type='radio']:checked").val();
				if(_isClick=='x-www-form-urlencoded'){
					// Param
					$(this).find("#tab-content").find("#tabs-wrap").find("#Param .widthTable").find('tr').each(function(index, ele){
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
					format = 'normal';
				}else{
					var textAreaV = $(this).find('.body-content-wrap .body-content textarea').val() ;
					format = 'raw';
					
					var row = {};
					row['name'] =  'xxx'
					row['paramValue'] = textAreaV  
					row['desc'] =  'xxx'
					row['paramType'] =  'QueryParam'
					params.push(row);
					alert(JSON.stringify(params))
				}
				paramArguments = params.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(params))));
				
				
			    // PreCondition
				$(this).find("#tab-content").find("#tabs-wrap").find("#PreCondition .widthTable").find('tr').each(function(index, ele){   // PreCondition
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
				$(this).find("#tab-content").find("#tabs-wrap").find("#PreSet .widthTable").find('tr').each(function(index, ele){
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
				$(this).find("#tab-content").find("#tabs-wrap").find("#TestCase .widthTable").find('tr').each(function(index, ele){
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
				
			    // OutParams
				outParams = $(this).find("#tab-content").find("#tabs-wrap").find("#out-j-textarea").val();
				
				var method = $(this).find("#dropdown p").html();
				    method = method.toLowerCase();
				
				var docId = $("#colls").val();
				var desc = $("#descId").val();
				$.post("/doc/requestAdd", {
					"desc" : $.trim(desc),
					"reqName" : $.trim(req_name),
					"method" : $.trim(method),
					"docId" : $.trim(docId),
					"format" : format,
					"reqUrl" : $.trim(req_url),
					"params":paramArguments,
					"preConditions":preConditionsTemp.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(preConditions)))),
					"preSets":preSetsTemp.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(preSets)))),
					"testCases":testCasesTemp.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(testCases)))),
					"afterSets":afterSetsTemp.length==0?'':base64encode(utf16to8(encodeURIComponent(JSON.stringify(afterSets)))),
					"outParams":base64encode(utf16to8(encodeURIComponent(outParams)))
				}, function(data) {
					$("#paramsTemp").val("");
					$("#preConditionsTemp").val("");
					$("#preSetsTemp").val("");
					$("#testCasesTemp").val("");
					$("#afterSetsTemp").val("");
					$("#outParamsTemp").val("");
					
					$("#tempStoreId").val("");
					$("#tempStoreDocId").val("");
					
					var res = JSON.parse(data);
					if (true == res.status) {
						$('#save-shadow-box').hide();
						alert(res.msg)
						
						var req = nd.newReq(res.docReqId);
						req.setName(res.docReqName);
						req.setDocId(res.docId);
						nd.insertReq(req);
					} else {
						alert(res.msg)
					}
				});
				
			}
		})
		
	});

	$("#addDoc").click(function() {
		var docName = $("#docName").val();
		var desc = $("#description").val();
		
		var docVersion = $("#docVersion").val();
		var docBusiness = $("#docBusiness").val();
		
		$.ajax({
			   type: "post",
			   url: "/doc/addDoc",
			   data: {
				       "docName" : base64encode(utf16to8(encodeURIComponent(docName))),
					   "desc" : base64encode(utf16to8(encodeURIComponent(desc))),
					   "docVersion" : docVersion,
					   "docBusiness" : docBusiness
					},
			   dataType: "json",
			   success: function(data){
				   var doc = nd.newDoc(data.id);
				   doc.setName(data.name);
				   nd.insertDoc(doc);
				   $("#collection-shadow-box").hide();
				   
				   $("#docName").val("");
				   $("#description").val("");
				   $("#docVersion").val("");
				   $("#docBusiness").val("");
			   }
		});
		
	});
	
	/**
	 * 搜索
	 */
	
	$(".search").keyup(function(){
		var content = $("#search").val();
		nd.searchDoc(content);
	})
	
	$("#v_generateNewVersion").click(function(){
		var docId = $("#tempdocid").val();
		var newVersion = $("#newVersion").val();
		var newVersionCode = $("#newVersionCode").val();
		if (''==newVersion) {
			alert("请命名新版本");
			return;
		}
		if (''==newVersionCode) {
			alert("请键入新版本编号");
			return;
		}
		$.ajax({
			   type: "post",
			   url: "/doc/generNewVersion",
			   data: {"docId":$.trim(docId),"newVersion":$.trim(newVersion),"newVersionCode":$.trim(newVersionCode)},
			   dataType: "json",
			   success: function(data){
				   $("#generate-version").hide();
					
				   alert(data.msg)
				   if(!jQuery.isEmptyObject(data.docData) && !jQuery.isEmptyObject(data.docRequests)){
					   var doc = nd.newDoc(data.docData.id);
					   doc.setName(data.docData.name);
					   nd.insertDoc(doc);
					   
					   $.each(data.docRequests, function (index, obj){
						    var req = nd.newReq(obj.drId);
							req.setName(obj.drName);
							req.setDocId(obj.drDocId);
							nd.insertReq(req);
					   });
					   
				   }
				   
			   }
		});
	});

	// u_doc_req
	$("#u_doc_req").click(function(){
		var name = $("#name").val();
		if (''==name) {
			alert("请命名Doc-Req")
			return;
		}
		var docIdV = $("#docId").val();
		if (''==docIdV) {
			alert("请选择所属Doc")
			return;
		}
		var id = $("#docReqId").val();
		var form_value = $("#update_doc_req_form").serialize();
		
		$.ajax({
			   type: "post",
			   url: "/doc/updateDocReq",
			   data: form_value,
			   dataType: "json",
			   success: function(data){
				   $("#update-docReq").hide();
					var req = nd.getReq(id);
					req.setName(name);
					req.setDocId(docIdV);
					nd.updateReq(req);
					alert(data.msg)
			   }
		});
	});
	
	
	// u_doc
	$("#u_doc").click(function(){
		var form_value = $("#update_doc_form").serialize();
		var id = $("#_docId").val();
		var name = $("#_docName").val();
		if (''==name) {
			alert("请命名Doc")
			return;
		}
		var version = $("#version").val();
		if (''==version) {
			alert("请选择版本")
			return;
		}
		var businessLine = $("#businessLine").val();
		if (''==businessLine) {
			alert("请选择业务线")
			return;
		}
		$.ajax({
			   type: "post",
			   url: "/doc/updateDoc",
			   data: form_value,
			   dataType: "json",
			   success: function(data){
				   $("#update-doc").hide();
					if (true == data.status) {
						alert(data.msg)
						var doc = nd.getDoc(id);
						doc.setName(name);
						nd.updateDoc(doc);
						$("#name").val("");
						$("#docId").val("");
						$("#desc").val("");
						$("#sort").val("");
						$("#docReqId").val("");
					} else {
						alert(data.msg)
					}
			   }
		});
	});
}

function toDetailReq(event,id,docId,docReqName){
	//stopEvent(event);
	
	
	
	$.ajax({
		   type: "post",
		   url: "/doc/getDocReqwithjson",
		   data: {"id":id},
		   dataType: "json",
		   success: function(data){
//			   alert(JSON.stringify(data))
			   if (JSON.stringify(data) != '' && data != null) {
				   var divId ;
				   var tmp = $("#tabContent").find("div[data-docReq='"+id+"']").attr("data-docReq");
				   if(tmp != null && tmp != '' && tmp != 'undefined'){
					   divId = $("#tabContent").find("div[data-docReq='"+id+"']").attr("id");
				   }else{
					   divId = addTab();
					   $("#tabContent").find("div[id='"+divId+"']").attr("data-docReq",id);
					   $("#tabContent").find("div[id='"+divId+"']").attr("data-docId",docId);
					   $("#tabContent").find("div[id='"+divId+"']").attr("data-docReqName",docReqName);
				   }
				   
				   if(divId == null || divId == '' || typeof(divId) == 'undefined'){
					   return ;
				   }
				   
				   
				   $("#myTab span[id='button"+divId.substring(11, divId.length)+"']").html(data.name);
				   $("#tabContent").find("div[name='parentDiv']").each(function(index, data){
					   $(this).css('display','none'); 
				   });
				   $("#tabContent").find("div[id='"+divId+"']").css("display","block");
				   $("#" + divId + " input[id='req_url']").val(data.url);
				   var tmpMethod = data.method;
				   $("#" + divId + "  #dropdown p").html((tmpMethod+"").toUpperCase());
				   
				   $("#button" + divId.substring(11, 26)).click();
				   
				   
				   /// 对PreCondition设置值
				   $("#" + divId + " #PreCondition .widthTable tr:not(:first)").html("");
				   var _preConds= $.parseJSON(data.preConds);
				   if(_preConds != null && _preConds.length > 0){
					   $.each(_preConds, function (index, obj){
						   obj.key=obj.key.replace(/\"/g,"&quot;");
							var preConds_row = '<tr index="'+index+'" class="remove" onclick="perAddInput(this)"><td><input name="key" type="text" value="'+obj.key+'" placeholder="目标键"></td><td><select id="preConditionSel" class="select-style" name="condition"><option>eq</option><option>ge</option><option>le</option><option>gt</option><option>lt</option><option>in</option><option>notIn</option><option>isNull</option><option>isNotNull</option><option>isEmpty</option><option>isNotEmpty</option></select></td><td><input type="text" name="targetValue" value="'+obj.targetValue+'" placeholder="目标值"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
							$("#" + divId + " #PreCondition .widthTable").append(preConds_row);
							$("#" + divId + " #PreCondition .widthTable tr[index='"+index+"'] select[name='condition'] option").each(function(i,o){
								if($(this).val()==obj.condition){
					    			$(this).attr("selected",true);
						    	}
					    	})
						});
				   }
				   
				// 为PreSet赋值
				   $("#" + divId + "  #PreSet .widthTable tr:not(:first)").html("");
				   var _preSets= $.parseJSON(data.preSets);
				   if(_preSets != null && _preSets.length > 0){
					   $.each(_preSets, function (index, obj){
						   obj.key=obj.key.replace(/\"/g,"&quot;");
						   obj.assignment=obj.assignment.replace(/\"/g,"&quot;");
						   var preSet_row = 
							   '<tr index="'+index+'" class="remove" onclick="perSetAddInput(this)"><td>' +
							   '<select onchange="preSetChangeForm(this)" class="select-style" name="dataSource"><option value="Local">Local</option><option  value="Mysql">Mysql</option><option value="Redis">Redis</option><option value="Mongo">Mongo</option><option value="Rabbitmq">Rabbitmq</option><option value="func">Func</option></select></td><td><select class="select-style" name="scope"><option value="global">全局</option><option value="part">局部</option></select></td><td><input type="text" name="key" value="'+obj.key+'" placeholder="Key"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option><option value="func">Func</option></select></td><td><input type="text" name="assignment" value="'+obj.assignment+'" placeholder="Assignment"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
						   
						   $("#" + divId + " #PreSet .widthTable").append(preSet_row);
						   $("#" + divId + "  #PreSet .widthTable tr[index='"+index+"'] select[name='dataSource'] option").each(function(i,o){
				    				if($(this).val()==obj.dataSource){
					    				$(this).attr("selected",true)
					    			}
				    		})
				    		
				    		$("#" + divId + " #PreSet .widthTable tr[index='"+index+"'] select[name='scope'] option").each(function(i,o){
					    			if($(this).val()==obj.scope){
					    				$(this).attr("selected",true)
					    			}
				    		})
				    		
				    		$("#" + divId + " #PreSet .widthTable tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
					    			if($(this).val()==obj.dataType){
					    				$(this).attr("selected",true)
					    			}
				    		})
				    		
				    		// 如果dataType是func    
				    		if(obj.dataSource=='Func'){
				    			// 获取所有的JS方法名
				    			var allJsFuncs = [];
				    			$.ajax({
				    				   type: "post",
				    				   url: "/dataSource/getJSFuncsWithJson",
				    				   dataType: "json",
				    				   success: function(data){
				    					   var tr_parent = $("#" + divId + " #PreSet .widthTable tr[index='"+index+"'] input[name='assignment']").parent();
							    			tr_parent.html("");
							    			tr_parent.append("<select name='assignment'><option id='" + index + "' value='-1'>--请选择JS签名--</option></select>");
							    			if(data.length > 0){
							    				for ( var ele in data) {
							    					var temp = "";
							    					if(data[ele].id==obj.assignment){
							    						temp = "selected='selected'";
							    					}
							    					$("#"+index+"").after("<option " + temp + " value='" + data[ele].id + "'>" + data[ele].funSignature + "</option>");
							    					temp = "";
							    				}
							    			}
				    				   }
				    			});
				    		}
					   })	
				   }
				   
				   /// 对Param设置值
				   $("#" + divId + "  #Header .widthTable tr:not(:first)").html("");
				   $("#" + divId + "  #Param .widthTable tr:not(:first)").html("");
				   if(data.format == 'normal'){
					   // obj 是input按钮
					   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(0).parent().next().children().eq(0).show().siblings().hide();
					   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(0).attr("checked",true);
					   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(1).attr("checked",false);
//					   $("#" + divId + "  #Param .widthTable tr:not(:first)").html("");
					   var _params= $.parseJSON(data.params);
					   if(_params != null && _params.length > 0){
						   $.each(_params, function (index, obj){
							   obj.paramValue=obj.paramValue.replace(/\"/g,"&quot;");
							   obj.desc=obj.desc.replace(/\"/g,"&quot;");
							   if(obj.paramType == 'QueryParam'){
								   var params_row = '<tr index="'+index+'" class="remove" onclick="paramAddInput(this)"><td><input type="text" name="name" value="'+obj.name+'" placeholder="Name"></td><td><input type="text" name="paramValue" value="'+obj.paramValue+'" placeholder="ParamValue"></td><td><input type="text" name="desc" value="'+obj.desc+'" placeholder="Desc"></td><td style="display:none;"><input type="text" name="paramType" value="QueryParam" placeholder="paramType" ></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
								   $("#" + divId + "  #Param .widthTable").append(params_row);
						    		
						    		$("#" + divId + "  #Param .widthTable tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
							    			if($(this).val()==obj.dataType){
							    				$(this).attr("selected",true)
							    			}
						    		})
							   }
					    		
					    		// 向HeaderParam里面进行填充
					    		if(obj.paramType == 'HeaderParam'){
					    			var headers_row = '<tr index="'+index+'" class="remove" onclick="headerAddInput(this)"><td class="header-checkbox-td" onclick="stopProp(this)"><input class="header-checkbox" name="" type="checkbox" value="" /></td><td><input type="text" name="name" value="'+obj.name+'" placeholder="参数名称"></td><td><input type="text" name="paramValue"  value="'+obj.paramValue+'" placeholder="参数值"></td><td><input type="text" name="desc" value="'+obj.desc+'" placeholder="描述"></td><td style="display:none;"><input type="text" name="paramType" value="HeaderParam" placeholder="paramType" ></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>'
									   $("#" + divId + "  #Header .widthTable").append(headers_row);
					    		}
							 });
					   }
				   }else{
					   var _params= $.parseJSON(data.params);
//					   alert(JSON.stringify(_params))
					   if(_params != null && _params.length > 0){
						   $.each(_params, function (index, obj){
							   obj.paramValue=obj.paramValue.replace(/\"/g,"&quot;");
							   obj.desc=obj.desc.replace(/\"/g,"&quot;");
					    		// 向QueryParam里面进行填充
					    		if(obj.paramType == 'QueryParam'){
					    			// obj是input按钮
									   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(1).parent().next().children().eq(1).show().siblings().hide();
									   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(1).attr("checked",true);
									   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(0).attr("checked",false);
									   $("#" + divId + " #tab-content #tabs-wrap .body-content-wrap .body-content textarea").val("");
									   $("#" + divId + " #tab-content .body-content-wrap .body-content textarea").val(obj.paramValue)
					    		}
					    		// 向HeaderParam里面进行填充
					    		if(obj.paramType == 'HeaderParam'){
					    			var headers_row = '<tr index="'+index+'" class="remove" onclick="headerAddInput(this)"><td class="header-checkbox-td" onclick="stopProp(this)"><input class="header-checkbox" name="" type="checkbox" value="" /></td><td><input type="text" name="name" value="'+obj.name+'" placeholder="参数名称"></td><td><input type="text" name="paramValue"  value="'+obj.paramValue+'" placeholder="参数值"></td><td><input type="text" name="desc" value="'+obj.desc+'" placeholder="描述"></td><td style="display:none;"><input type="text" name="paramType" value="HeaderParam" placeholder="paramType" ></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>'
									   $("#" + divId + "  #Header .widthTable").append(headers_row);
					    		}
							 });
					   }

				   }
				   
				   /// 对TestCase设置值
				   $("#" + divId + "  #TestCase .widthTable tr:not(:first)").html("");
				   var _testCases= $.parseJSON(data.testCases);
				   if(_testCases != null && _testCases.length > 0){
					   $.each(_testCases, function (index, obj){
						   obj.testKey=obj.testKey.replace(/\"/g,"&quot;");
						   obj.testValue=obj.testValue.replace(/\"/g,"&quot;");
						   
							var testCase_row = '<tr index="'+index+'" class="remove" onclick="testCaseAddInput(this)" onclick="afterSetAddInput(this)"><td><input type="text" name="name" value="'+obj.name+'" placeholder="Name"></td><td><input type="text" name="testKey" value="'+obj.testKey+'" placeholder="TestKey"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="testValue" value="'+obj.testValue+'" placeholder="TestValue"></td><td style="display:none"><input type="text" name="testResult" value="Unknown"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
							$("#" + divId + "  #TestCase .widthTable").append(testCase_row);
							$("#" + divId + " #TestCase .widthTable tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
			    				if($(this).val()==obj.dataType){
				    				$(this).attr("selected",true);
				    			}
					    	})
						});
				   }
				   
				   
				   /// 对AfterSet设置值
				   $("#" + divId + "  #AfterSet .widthTable tr:not(:first)").html("");
				   var _afterSets= $.parseJSON(data.afterSets);
				   if(_afterSets != null && _afterSets.length > 0){
					   $.each(_afterSets, function (index, obj){
						   obj.resultKey=obj.resultKey.replace(/\"/g,"&quot;");
						   obj.mappingKey=obj.mappingKey.replace(/\"/g,"&quot;");
						   
							var afterSet_row = '<tr index="'+index+'" class="remove" onclick="afterSetAddInput(this)"><td><input name="resultKey" type="text" value="'+obj.resultKey+'" placeholder="ResultKey"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="mappingKey" value="'+obj.mappingKey+'" placeholder="MappingKey"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
							$("#" + divId + "  #AfterSet .widthTable").append(afterSet_row);
							$("#" + divId + " #AfterSet .widthTable tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
					    				if($(this).val()==obj.dataType){
						    				$(this).attr("selected",true)
						    			}
					    		})
					    		
						});
				   }
				   
				   
				   /// 对OutParam设置值
				   var cstring= data.outParams.replace(/[\\]/g,'');
				   if(cstring.substring(0,1)=="\"")
				   {
					   //字符串以"开头，去掉"
					   cstring=cstring.substr(2,cstring.length-1)
				   }
				   if(cstring.substring(cstring.length-1)=="\"")
				   {
					   //字符串以"结尾，去掉"
					   cstring=cstring.substr(0,cstring.length-2)
				   }
				   $("#" + divId).find("textarea[id='out-j-textarea']").val(cstring);
			   }
		   }
	});
}



function generateNewVersion(event,docId,docName){
	$("#generate-version").show();
	stopEvent(event);
	// 生成新的版本
	$("#tempdocid").val(docId)
}


// doc_req修改操作
function u_oper_docReq(event,id,docId,docReqName){
	$("#update-docReq").show();
	stopEvent(event);
	
	$("#docReqId").val(id);
	
	// 对下拉框进行查询
	$('#docId').html("");
	$.post("/doc/getDocuments", function(data) {
		var u = JSON.parse(data);
		if (u == '' || u.length <= 0) {
			alert("请先创建Document即Collection")
			return;
		}
		var temp = "";
		for ( var ele in u) {
			if(u[ele].id==docId){
				temp = "selected='selected'";
			}
			$('#docId').append(
					"<option " + temp + " value='" + u[ele].id + "'>"
							+ u[ele].name + 
					"</option>");
			temp = "";
		}
	});
	
	$.ajax({
		   type: "post",
		   url: "/doc/getDocReqwithjson",
		   data: {"id":id},
		   dataType: "json",
		   success: function(data){
			   if (JSON.stringify(data) != '' && data != null) {
				   $("#name").val(data.name);
				   $("#desc").val(data.desc);
				   $("#sort").val(data.sort);
			   }
		   }
	});
}

// docReq 删除
function d_oper_docReq(event,id,docId, docReqName){
	if(confirm("确定删除"+docReqName+" ?")){
		$.ajax({
			   type: "post",
			   url: "/doc/removeDocReq",
			   data: {"id":id},
			   dataType: "json",
			   success: function(data){
				   if(data.status==false)
					   alert(data.msg);
				   else
					   alert(data.msg)
					   nd.deleteReq(id);
			   }
		});
	}
	//stopEvent(event);
	
}

function d_oper_doc(event,id,docName){
	if(confirm("确定删除"+docName+" ?")){
		// 删除
		$.ajax({
			   type: "post",
			   url: "/doc/removeDoc",
			   data: {"id":id},
			   dataType: "json",
			   success: function(data){
				   if(data.status==false)
					   alert(data.msg);
				   else
					   alert(data.msg)
					   nd.deleteDoc(id);
			   }
		});
	}
	//stopEvent(event);
	
}

function u_oper_doc(event,id, docName){
	$("#update-doc").show();
	//stopEvent(event);
	$("#_docId").val(id);
	
	// 修改
    // update_doc_form
	$.ajax({
		   type: "post",
		   url: "/doc/getDocuments",
		   data: {"id":id},
		   dataType: "json",
		   success: function(data){
			   if (JSON.stringify(data) != '' && data != null) {
				   $("#_docName").val(data.name);
				   $("#_desc").val(data.desc);
				   $("#version").val(data.version);
				   $("#businessLine").val(data.businessLine);
			   }
		   }
	});
}


function q_oper_doc(event,docId, docName){
	window.open("/run/doc?docId=" + docId,'_blank');
	//stopEvent(event);
}

var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62,
		-1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1,
		-1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
		15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1,
		26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
		43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
// 编码的方法
function base64encode(str) {
	var out, i, len;
	var c1, c2, c3;
	len = str.length;
	i = 0;
	out = "";
	while (i < len) {
		c1 = str.charCodeAt(i++) & 0xff;
		if (i == len) {
			out += base64EncodeChars.charAt(c1 >> 2);
			out += base64EncodeChars.charAt((c1 & 0x3) << 4);
			out += "==";
			break;
		}
		c2 = str.charCodeAt(i++);
		if (i == len) {
			out += base64EncodeChars.charAt(c1 >> 2);
			out += base64EncodeChars.charAt(((c1 & 0x3) << 4)
					| ((c2 & 0xF0) >> 4));
			out += base64EncodeChars.charAt((c2 & 0xF) << 2);
			out += "=";
			break;
		}
		c3 = str.charCodeAt(i++);
		out += base64EncodeChars.charAt(c1 >> 2);
		out += base64EncodeChars.charAt(((c1 & 0x3) << 4)
				| ((c2 & 0xF0) >> 4));
		out += base64EncodeChars.charAt(((c2 & 0xF) << 2)
				| ((c3 & 0xC0) >> 6));
		out += base64EncodeChars.charAt(c3 & 0x3F);
	}
	return out;
}

function utf16to8(str) {
	var out, i, len, c;
	out = "";
	len = str.length;
	for (i = 0; i < len; i++) {
		c = str.charCodeAt(i);
		if ((c >= 0x0001) && (c <= 0x007F)) {
			out += str.charAt(i);
		} else if (c > 0x07FF) {
			out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
			out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
			out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
		} else {
			out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
			out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
		}
	}
	return out;
}

function stopEvent(event){ //阻止冒泡事件
    //取消事件冒泡
    var e=arguments.callee.caller.arguments[0]||event; //若省略此句，下面的e改为event，IE运行可以，但是其他浏览器就不兼容
    if (e && e.stopPropagation) {
        e.stopPropagation();
    } else if (window.event) {
        window.event.cancelBubble = true;
    }
}