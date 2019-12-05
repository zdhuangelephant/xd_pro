/**
 * postman添加参数
 */

	var params = [];
	var preConditions = [];
	var preSets = [];
	var testCases = [];
	var afterSets = [];
	
function doSave(obj){
	
	params.length = 0;
	preConditions.length = 0;
	preSets.length = 0;
	testCases.length = 0;
	afterSets.length = 0;
	
	var $prefix = $(obj).parent().parent().parent();
	var $t1 = $prefix.find("table[id='paramTable']");
	var $t2 = $prefix.find("table[id='PreConditionTable']");
	var $t3 = $prefix.find("table[id='PreSetTable']");
	var $t4 = $prefix.find("table[id='TestCaseTable']");
	var $t5 = $prefix.find("table[id='AfterSetTable']");
	var $t6 = $prefix.find("table[id='headerTable']");
	
	var paramArguments;
	var format;
	
	var label_flag = true;
	// 获取Header的值
	var isCheckboxSize = 0;
	for(var i=0; i <= $t6.find('tr').length; i++){ 
	    if($t6.find('tr').eq(i).find("td:first input[type='checkbox']").is(':checked')){
	    	isCheckboxSize ++;
	    }
	} 
	$t6.find('tr').each(function(index, ele){
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
	var _isClick = $prefix.parent().find("#tab-content input[type='radio']:checked").val();
	if(_isClick =='x-www-form-urlencoded'){
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
//		var req_name = $('#req_name').val();
		var req_name = $prefix.parent().attr("data-caseReqName");
		var docReqId =$prefix.parent().attr("data-docreq");
		var docId =$prefix.parent().attr("data-docId");
		$.post("/case/caseRequestAdd", {
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
		}, function(data) {
			var res = JSON.parse(data);
			if (true == res.status) {
				alert(res.msg)
				window.location.reload();
			} else {
				alert(res.msg)
			}
			
			params.length = 0;
			preConditions.length = 0;
			preSets.length = 0;
			testCases.length = 0;
			afterSets.length = 0;
		});
		
	}
}

$(document).ready(function(){

	$("#addCase").click(function() {
		var caseName = $("#caseName").val();
		var caseDesc = $("#description").val();
		var caseVersion = $("#caseVersion").val();
		var caseBusiness = $("#caseBusiness").val();
		$.post("/case/addCase", {
			"caseName" : base64encode(utf16to8(encodeURIComponent(caseName))),
			"caseDesc" : base64encode(utf16to8(encodeURIComponent(caseDesc))),
			"caseVersion" : caseVersion,
			"caseBusiness" : caseBusiness
		}, function(data) {
//			alert(JSON.stringify(data))
			var obj = JSON.parse(data);
			if (data != null && data.length > 0) {
				 $("#collection-shadow-box").hide();
				   var doc = nd.newDoc(obj.id);
				   doc.setName(obj.name);
				   doc.setVersion(obj.version);
				   nd.insertDoc(doc);
				   $("#caseName").val("");
				   $("#description").val("");
				   $("#caseVersion").val("");
				   $("#caseBusiness").val("");
			}
		});
	});
	
	
	$("#v_addReq").click(function(){
		var docId = $("#tempdocid").val();
		var newVersion = $("#newVersion").val();
		var newVersionCode = $("#newVersionCode").val();
		if (''==newVersion) {
			alert("请命名新版本")
			return;
		}
		if (''==newVersionCode) {
			alert("请键入新版本编号")
			return;
		}
		$.ajax({
			   type: "post",
			   url: "/case/generNewVersion",
			   data: {"docId":$.trim(docId),"newVersion":$.trim(newVersion),"newVersionCode":$.trim(newVersionCode)},
			   dataType: "json",
			   success: function(data){
				   $("#generate-version").hide();
				   alert(data.msg)
				   if(!jQuery.isEmptyObject(data.caseData) && !jQuery.isEmptyObject(data.caseRequests)){
					   var doc = nd.newDoc(data.caseData.id);
					   doc.setName(data.caseData.name);
					   nd.insertDoc(doc);
					   
					   $.each(data.caseRequests, function (index, obj){
						    var req = nd.newReq(obj.crId);
							req.setName(obj.crName);
							req.setDocId(obj.crDocId);
							nd.insertReq(req);
					   });
					   
				   }
			   }
		});
	});

	
	
	$("#u_doc_req").click(function(){
		var form_value = $("#update_doc_req_form").serialize();
		var name = $("#name").val();
		if (''==name) {
			alert("请命名Case-Req")
			return;
		}
		var caseId = $("#docId").val();
		if (''==caseId) {
			alert("请选择所属Case");
			return;
		}
		
		var caseReqId = $("#docReqId").val();
		
		$.ajax({
			   type: "post",
			   url: "/case/updateCaseReq",
			   data: form_value,
			   dataType: "json",
			   success: function(data){
				   $("#update-docReq").hide();
				   
				   var req = nd.getReq(caseReqId);
					req.setName(name);
					req.setDocId(caseId);
					nd.updateReq(req);
					alert(data.msg)
			   }
		});
	});
	
	
	/**
	 * 修改Case后， 点击保存按钮
	 */
	$("#u_doc").click(function(){
		var id = $("#_docId").val();
		var form_value = $("#update_doc_form").serialize();
		var name = $("#_docName").val();
		if (''==name) {
			alert("请命名Case")
			return;
		}
		var version = $("#version").val();
		if (''==version) {
			alert("请填写版本")
			return;
		}
		var businessLine = $("#businessLine").val();
		if (''==businessLine) {
			alert("请填写业务线")
			return;
		}
		$.ajax({
			   type: "post",
			   url: "/case/updateCase",
			   data: form_value,
			   dataType: "json",
			   success: function(data){
				   $("#update-doc").hide();
					if (true == data.status) {
						var doc = nd.getDoc(id);
						doc.setName(name);
						nd.updateDoc(doc);
						$("#_docName").val("");
						$("#_desc").val("");
						$("#version").val("");
						$("#businessLine").val("");
						$("#_docId").val("");
					} else {
						alert(data.msg)
					}
			   }
		});
	});
	
	
	
	
	
	/**添加Case**/
	$("#firstDoor").click(function(){
		$("#collection-shadow-box").show();
	});
	
	
	/**对CaseReq的添加部分**/
	$("#first").change(function(){
		var docId = $(this).val();
		var caseVersion = $("#caseVersion").val();
		$.ajax({
			   type: "post",
			   url: "/doc/getDocReqListByDocwithjson",
			   data: {"docId":docId,"version":caseVersion},
			   dataType: "json",
			   success: function(data){
//				   alert(JSON.stringify(data))
				   $('#second').html("");
				   if(data != null && data.length > 0){
					   $('#second').html("").html("<option value='-1'>--请选择--</option>");
						$.each(data, function (index, obj){
							$('#second').append(
									"<option value='" + obj.id + "'>"
											+ obj.name + "</option>")
						})
				   }else{
					   $('#second').append("<option value='-1'>--暂无数据--</option>");
				   }
			   }
		});
	})
	
	
	/**
	 * 生成新版本号
	 */
	$("#v_generateNewVersion").click(function(){
		var docId = $("#tempdocid").val();
		var newVersion = $("#newVersion").val();
		var newVersionCode = $("#newVersionCode").val();
		if (''==newVersion) {
			alert("请命名新版本")
			return;
		}
		if (''==newVersionCode) {
			alert("请键入新版本编号")
			return;
		}
		$.ajax({
			   type: "post",
			   url: "/case/generNewVersion",
			   data: {"docId":$.trim(docId),"newVersion":$.trim(newVersion),"newVersionCode":$.trim(newVersionCode)},
			   dataType: "json",
			   success: function(data){
				   $("#generate-version").hide();
					if (true == data.status) {
						alert(data.msg)
						window.location.reload();
					} else {
						alert(data.msg)
					}
			   }
		});
		stopEvent(event);
	});

	// 点击添加按钮添加CaseReq
	$("#addDoc").click(function() {
		var docId = $("#first").val();
		var docReqId = $("#second").val();
		var caseId = $("#caseId").val();
		var caseName = $("#caseName").val();
		if(!docReqId){
			alert("请选择一个Req");
			return;
		}
		$.ajax({
			   type: "post",
			   url: "/case/addCaseReq",
			   data: {
					   "docReqId" : $.trim(docReqId),
					   "caseId" : $.trim(caseId),
					   "caseName" : $.trim(caseName)
					},
			   dataType: "json",
			   success: function(data){
				   $("#caseId").val("");
				   $("#caseVersion").val("");
				   $("#first option").each(function(i, o){
					   if($(this).val()!=-1){
						   $(this).remove();
					   }
				   });
				   //  second
				   $("#second").val("");
				   $("#second option").each(function(i, o){
					   if($(this).val()!=-1){
						   $(this).remove();
					   }
				   });
				   
				   $("#caseName").val("");
				   
				   alert("添加成功")
				   $("#case-shadow-box").hide();
				   var req = nd.newReq(data.id);
					req.setName(data.name);
					req.setDocId(data.collectionId);
					nd.insertReq(req);
			   }
		});
		
	});
	
	
	// 点击添加按钮添加CaseReq
	$("#addDoc").click(function() {
		var docId = $("#first").val();
		var docReqId = $("#second").val();
		var caseId = $("#caseId").val();
		var caseName = $("#caseName").val();
		if(!docReqId){
			alert("请选择一个Req");
			return;
		}
		$.ajax({
			   type: "post",
			   url: "/case/addCaseReq",
			   data: {
					   "docReqId" : $.trim(docReqId),
					   "caseId" : $.trim(caseId),
					   "caseName" : $.trim(caseName)
					},
			   dataType: "json",
			   success: function(data){
				   $("#caseId").val("");
				   $("#caseVersion").val("");
				   $("#first option").each(function(i, o){
					   if($(this).val()!=-1){
						   $(this).remove();
					   }
				   });
				   //  second
				   $("#second").val("");
				   $("#second option").each(function(i, o){
					   if($(this).val()!=-1){
						   $(this).remove();
					   }
				   });
				   
				   $("#caseName").val("");
				   
				   alert("添加成功")
				   $("#case-shadow-box").hide();
				   var req = nd.newReq(data.id);
					req.setName(data.name);
					req.setDocId(data.collectionId);
					nd.insertReq(req);
			   }
		});
		
	});
	
	$(".search").keyup(function(){
		var content = $("#search").val();
		nd.searchDoc(content);
		
	})
	
}) 



function toDisplay(id, obj){
	$.ajax({
		   type: "post",
		   url: "/case/getCaseReqLogwithjson",
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
					   $("#tabContent").find("div[id='"+divId+"']").attr("reqtype","historyreq");
					   $("#tabContent").find("div[id='"+divId+"'] .save").hide();
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
						   obj.targetValue=obj.targetValue.replace(/\"/g,"&quot;");
						   
							var preConds_row = '<tr index="'+index+'" class="remove"><td><input name="key" type="text" value="'+obj.key+'" placeholder="Key"></td><td><select id="preConditionSel" class="select-style" name="condition"><option>eq</option><option>ge</option><option>le</option><option>gt</option><option>lt</option><option>in</option><option>notIn</option><option>isNull</option><option>isNotNull</option><option>isEmpty</option><option>isNotEmpty</option></select></td><td><input type="text" name="targetValue" value="'+obj.targetValue+'" placeholder="TargetValue"></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
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
							   '<tr index="'+index+'" class="remove"><td>' +
							   '<select class="select-style" name="dataSource"><option value="Local">Local</option><option value="Mysql">Mysql</option><option value="Redis">Redis</option><option value="Mongo">Mongo</option><option value="Rabbitmq">Rabbitmq</option></select></td><td><select class="select-style" name="scope"><option value="global">全局</option><option value="part">局部</option></select></td><td><input type="text" name="key" value="'+obj.key+'" placeholder="Key"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="assignment" value="'+obj.assignment+'" placeholder="Assignment"></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
						   
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
					   })	
				   }
				   
				   /// 对Param设置值
				   $("#" + divId + "  #Header .widthTable tr:not(:first)").html("");
				   $("#" + divId + "  #Param .widthTable tr:not(:first)").html("");
				   if(data.format == 'normal'){
					   // obj 是input按钮
					   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(0).parent().next().children().eq(0).show().siblings().hide();
					   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(0).attr("checked",true);
					   var _params= $.parseJSON(data.params);
					   if(_params != null && _params.length > 0){
						   $.each(_params, function (index, obj){
							   obj.paramValue=obj.paramValue.replace(/\"/g,"&quot;");
							   obj.desc=obj.desc.replace(/\"/g,"&quot;");
							   if(obj.paramType == 'QueryParam'){
								   var params_row = '<tr index="'+index+'" class="remove"><td><input type="text" name="name" value="'+obj.name+'" placeholder="Name"></td><td><input type="text" name="paramValue" value="'+obj.paramValue+'" placeholder="ParamValue"></td><td><input type="text" name="desc" value="'+obj.desc+'" placeholder="Desc"></td><td style="display:none;"><input type="text" name="paramType" value="QueryParam" placeholder="paramType" ></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
								    $("#" + divId + "  #Param .widthTable").append(params_row);
								    $("#" + divId + "  #Param .widthTable tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
						    			if($(this).val()==obj.dataType){
						    				$(this).attr("selected",true)
						    			}
								    })
							   }	
							   // 向HeaderParam里面进行填充
					    		if(obj.paramType == 'HeaderParam'){
					    			var headers_row = '<tr index="'+index+'" class="remove"><td><input type="text" name="name" value="'+obj.name+'" placeholder="参数名称"></td><td><input type="text" name="paramValue"  value="'+obj.paramValue+'" placeholder="参数值"></td><td><input type="text" name="desc" value="'+obj.desc+'" placeholder="描述"></td><td style="display:none;"><input type="text" name="paramType" value="HeaderParam" placeholder="paramType" ></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>'
									$("#" + divId + "  #Header .widthTable").append(headers_row);
					    		}
							 });
					   }
				   }else{
					   var _params= $.parseJSON(data.params);
					   if(_params != null && _params.length > 0){
						   $.each(_params, function (index, obj){
							   obj.paramValue=obj.paramValue.replace(/\"/g,"&quot;");
							   obj.desc=obj.desc.replace(/\"/g,"&quot;");
					    		// 向QueryParam里面进行填充
					    		if(obj.paramType == 'QueryParam'){
									   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(1).parent().next().children().eq(1).show().siblings().hide();
									   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(1).attr("checked",true);
									   $("#" + divId + " #tab-content #tabs-wrap .l-con-con .body-tab input").eq(0).attr("checked",false);
									   $("#" + divId + " #tab-content #tabs-wrap .body-content-wrap .body-content textarea").val("");
									   $("#" + divId + " #tab-content .body-content-wrap .body-content textarea").val(obj.paramValue)
					    		}
					    		// 向HeaderParam里面进行填充
					    		if(obj.paramType == 'HeaderParam'){
					    			var headers_row = '<tr index="'+index+'" class="remove"><td><input type="text" name="name" value="'+obj.name+'" placeholder="参数名称"></td><td><input type="text" name="paramValue"  value="'+obj.paramValue+'" placeholder="参数值"></td><td><input type="text" name="desc" value="'+obj.desc+'" placeholder="描述"></td><td style="display:none;"><input type="text" name="paramType" value="HeaderParam" placeholder="paramType" ></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>'
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
						   
							var testCase_row = '<tr index="'+index+'" class="remove"><td><input type="text" name="name" value="'+obj.name+'" placeholder="Name"></td><td><input type="text" name="testKey" value="'+obj.testKey+'" placeholder="TestKey"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="testValue" value="'+obj.testValue+'" placeholder="TestValue"></td><td style="display:none"><input type="text" name="testResult" value="Unknown"></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
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
						   
							var afterSet_row = '<tr index="'+index+'" class="remove"><td><input name="resultKey" type="text" value="'+obj.resultKey+'" placeholder="ResultKey"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="mappingKey" value="'+obj.mappingKey+'" placeholder="MappingKey"></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
							$("#" + divId + "  #AfterSet .widthTable").append(afterSet_row);
							$("#" + divId + " #AfterSet .widthTable tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
					    				if($(this).val()==obj.dataType){
						    				$(this).attr("selected",true)
						    			}
					    		})
					    		
						});
				   }
				   
			   }
		   
		   }
	});
}

/**
 * 导入接口
 */
function submitFile(){
	 var form = new FormData(document.getElementById("uploadFileForm"));
	  $.ajax({
	      url:"/case/importCase",
	      type:"post",
	      data:form,
	      processData:false,
	      contentType:false,
	      dataType: "json",
	      success:function(data){
	    	  alert(data.msg)
//	    	  alert(JSON.stringify(data))
	    	  var $tmp = data.caseReqs;
	    	  
	    	  var doc = nd.newDoc(data.caseId);
			   doc.setName(data.caseName);
			   doc.setVersion(data.caseVersion);
			   nd.insertDoc(doc);
	    	  
	    	  if($tmp != null && $tmp.length > 0){
	    		  $.each($tmp, function (index, obj){
	    			    var req = nd.newReq(obj.id);
						req.setName(obj.name);
						req.setDocId(obj.collectionId);
						nd.insertReq(req);
	    		  })
	    	  }
	    	  
	    	  $("#import-shadow-box").hide();
	      },
	      error:function(e){
	          alert("错误！！");
	      }
	  });        
//  get();//此处为上传文件的进度条
}

function toDetailReq(event,id,caseId,caseReqName){
	$.ajax({
		   type: "post",
		   url: "/case/getCaseReqwithjson",
		   data: {"id":id},
		   dataType: "json",
		   success: function(data){
			   if (JSON.stringify(data) != '' && data != null) {
				   var divId ;
				   var tmp = $("#tabContent").find("div[data-docReq='"+id+"']").attr("data-docReq");
				   if(tmp != null && tmp != '' && tmp != 'undefined'){
					   divId = $("#tabContent").find("div[data-docReq='"+id+"']").attr("id");
				   }else{
					   divId = addTab();
					   $("#tabContent").find("div[id='"+divId+"']").attr("data-docReq",id);
					   $("#tabContent").find("div[id='"+divId+"']").attr("data-docId",caseId);
					   $("#tabContent").find("div[id='"+divId+"']").attr("data-caseReqName",caseReqName);
					   
					   $("#tabContent").find("div[id='"+divId+"']").attr("reqtype","aftercheckreq");

				   }
				   
				   if(divId == null || divId == '' || typeof(divId) == 'undefined'){
					   return ;
				   }
				   
				   // 填充req的name
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
						   obj.targetValue=obj.targetValue.replace(/\"/g,"&quot;");
						   
							var preConds_row = '<tr index="'+index+'" class="remove"><td><input name="key" type="text" value="'+obj.key+'" placeholder="Key"></td><td><select id="preConditionSel" class="select-style" name="condition"><option>eq</option><option>ge</option><option>le</option><option>gt</option><option>lt</option><option>in</option><option>notIn</option><option>isNull</option><option>isNotNull</option><option>isEmpty</option><option>isNotEmpty</option></select></td><td><input type="text" name="targetValue" value="'+obj.targetValue+'" placeholder="TargetValue"></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
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
							   '<tr index="'+index+'" class="remove"><td>' +
							   '<select onchange="preSetChangeForm(this)" class="select-style" name="dataSource"><option value="Local">Local</option><option value="Mysql">Mysql</option><option value="Redis">Redis</option><option value="Mongo">Mongo</option><option value="Rabbitmq">Rabbitmq</option><option value="func">Func</option></select></td><td><select class="select-style" name="scope"><option value="global">全局</option><option value="part">局部</option></select></td><td><input type="text" name="key" value="'+obj.key+'" placeholder="Key"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option><option value="func">Func</option></select></td><td><input type="text" name="assignment" value="'+obj.assignment+'" placeholder="Assignment"></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
						   
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
//					   $("#" + divId + "  #Param .widthTable tr:not(:first)").html("");
					   var _params= $.parseJSON(data.params);
					   if(_params != null && _params.length > 0){
						   $.each(_params, function (index, obj){
							   obj.paramValue=obj.paramValue.replace(/\"/g,"&quot;");
							   obj.desc=obj.desc.replace(/\"/g,"&quot;");
							   
							   if(obj.paramType == 'QueryParam'){
								   var params_row = '<tr index="'+index+'" class="remove"><td><input type="text" name="name" value="'+obj.name+'" placeholder="Name"></td><td><input type="text" name="paramValue" value="'+obj.paramValue+'" placeholder="ParamValue"></td><td><input type="text" name="desc" value="'+obj.desc+'" placeholder="Desc"></td><td style="display:none;"><input type="text" name="paramType" value="QueryParam" placeholder="paramType" ></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
								    $("#" + divId + "  #Param .widthTable").append(params_row);
								    $("#" + divId + "  #Param .widthTable tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
						    			if($(this).val()==obj.dataType){
						    				$(this).attr("selected",true)
						    			}
								    })
							   }	
							   // 向HeaderParam里面进行填充
					    		if(obj.paramType == 'HeaderParam'){
					    			var headers_row = '<tr index="'+index+'" class="remove"><td><input type="text" name="name" value="'+obj.name+'" placeholder="参数名称"></td><td><input type="text" name="paramValue"  value="'+obj.paramValue+'" placeholder="参数值"></td><td><input type="text" name="desc" value="'+obj.desc+'" placeholder="描述"></td><td style="display:none;"><input type="text" name="paramType" value="HeaderParam" placeholder="paramType" ></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>'
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
					    			var headers_row = '<tr index="'+index+'" class="remove"><td><input type="text" name="name" value="'+obj.name+'" placeholder="参数名称"></td><td><input type="text" name="paramValue"  value="'+obj.paramValue+'" placeholder="参数值"></td><td><input type="text" name="desc" value="'+obj.desc+'" placeholder="描述"></td><td style="display:none;"><input type="text" name="paramType" value="HeaderParam" placeholder="paramType" ></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>'
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
						   
							var testCase_row = '<tr index="'+index+'" class="remove"><td><input type="text" name="name" value="'+obj.name+'" placeholder="Name"></td><td><input type="text" name="testKey" value="'+obj.testKey+'" placeholder="TestKey"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="testValue" value="'+obj.testValue+'" placeholder="TestValue"></td><td style="display:none"><input type="text" name="testResult" value="Unknown"></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
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
						   
							var afterSet_row = '<tr index="'+index+'" class="remove"><td><input name="resultKey" type="text" value="'+obj.resultKey+'" placeholder="ResultKey"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="mappingKey" value="'+obj.mappingKey+'" placeholder="MappingKey"></td><td><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
							$("#" + divId + "  #AfterSet .widthTable").append(afterSet_row);
							$("#" + divId + " #AfterSet .widthTable tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
					    				if($(this).val()==obj.dataType){
						    				$(this).attr("selected",true)
						    			}
					    		})
					    		
						});
				   }
				   
			   }
		   
		   }
	});
}


/******** 生成新的版本************/
function generateNewVersion(event,docId,docName){
	$("#generate-version").show();
	// 生成新的版本
	$("#tempdocid").val(docId);
	stopEvent(event);
}


// doc_req修改操作
function u_oper_docReq(event,id,docId,docReqName){
	$("#update-docReq").show();
	$("#docReqId").val(id);
	
	// 对下拉框进行查询
	$('#docId').html("");
	$.post("/case/getCase", function(data) {
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
		   url: "/case/getCaseReqwithjson",
		   data: {"id":id},
		   dataType: "json",
		   success: function(data){
			   if (JSON.stringify(data) != '' && data != null) {
				   $("#name").val(data.name);
				   $("#desc").val(data.caseRequestDesc);
				   $("#sort").val(data.sort);
			   }
		   }
	});
	/*******  list 修改******************/
	//stopEvent(event);
	/*******  list 修改******************/
	
}

// caseReq 删除
function d_oper_docReq(event, id,docId, docReqName){
	if(confirm("确定删除"+docReqName+" ?")){
		$.ajax({
			   type: "post",
			   url: "/case/removeCaseReq",
			   data: {"id":id},
			   dataType: "json",
			   success: function(data){
				   if(data.status==false)
					   alert(data.msg);
				   else{
					   alert(data.msg)
					   nd.deleteReq(id);
				   }
			   }
		});
	}
}

function d_oper_share(event,caseId,caseName,share){
	if(confirm("确定分享"+caseName+" ?")){
		// 分享
		$.ajax({
			   type: "post",
			   url: "/case/shareCase",
			   data: {"caseId":caseId,"share":share},
			   dataType: "json",
			   success: function(data){
				   if(data.status==false)
					   alert(data.msg);
				   else{
					   alert(data.msg)
						var doc = nd.getDoc(caseId);
						doc.setShare(data.share);
						nd.updateDoc(doc);
				   }
			   }
		});
	}
	//stopEvent(event);
}


function pull(event,caseId,caseName,version){
	if(confirm("确定拉取"+caseName+" ?")){
		//拉取
		$.ajax({
			   type: "post",
			   url: "/case/pullCase",
			   data: {"caseId":caseId},
			   dataType: "json",
			   success: function(data){
				   if(data.status==false)
					   alert(data.msg);
				   else{
					   alert(data.msg);
					   var doc = nd.newDoc(data.caseId);
					   doc.setName(data.caseName);
					   doc.setVersion(data.version);
					   nd.insertDoc(doc);		   
					   $.each(data.caseRequestList, function (index, obj){
						    var req = nd.newReq(obj.id);
							req.setName(obj.name);
							req.setDocId(obj.collectionId);
							nd.insertReq(req);
					   });
				   }
			   }
		});
	}
	//stopEvent(event);
}


function d_oper_doc(event,caseId,caseName){
	if(confirm("确定删除"+caseName+" ?")){
		// 删除
		$.ajax({
			   type: "post",
			   url: "/case/removeCase",
			   data: {"caseId":caseId},
			   dataType: "json",
			   success: function(data){
				   if(data.status==false)
					   alert(data.msg);
				   else{
					   alert(data.msg)
					   nd.deleteDoc(caseId);
				   }
			   }
		});
	}
	//stopEvent(event);
}

/**
 * 点击修改按钮, 修改Case
 * @param id
 * @param caseName
 */
/*********ul case 修改 *****************/
function u_oper_doc(event,id, caseName){
	$("#update-doc").show();
	//stopEvent(event);
	$("#_docId").val(id);
	// 修改
    // update_doc_form
	$.ajax({
		   type: "post",
		   url: "/case/getCase",
		   data: {"id":id},
		   dataType: "json",
		   success: function(data){
			   if (JSON.stringify(data) != '' && data != null) {
				   $("#_docName").val(data.name);
				   $("#_desc").val(data.caseDesc);
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

/*******************************Case-Req***********************************/

function loadMore(){
	var nh;
	var pageNo = $("#loadMore").attr("pageno");
	if(pageNo == '' || typeof(pageNo) == 'undefined'){
		pageNo = 1;
	}
	$.ajax({   
		   type: "post",
		   url: "/case/acquireHistory",
		   data: {"pageNo":pageNo},
		   dataType: "json",
		   success: function(data){
			   $("#loadMore").attr("pageno", Number(pageNo) + 1)
				if ( null != data && data.length <= 0) {
					return;
				}
				
				$.each(data, function (index, obj){
					if(obj != null && !jQuery.isEmptyObject(obj)){
						$("#history-content ul li[id!='unique']:last").after(
								"<li> "+obj.method+"&nbsp;<a href='javascript:;' onclick=\"toDisplay('"+obj.id+"',this)\">"+obj.url+"</a> </li>")
					}
					
					
				})
			   
		   }
	});
}


function addCaseReq(event,caseId, caseName, caseVersion){
	$("#case-shadow-box").show();
	$("#caseId").val(caseId);
	$("#caseVersion").val(caseVersion);
	$.ajax({   
		   type: "post",
		   url: "/doc/getDocListwithjson",
		   data: {"version":caseVersion},
		   dataType: "json",
		   success: function(data){
//			   alert(JSON.stringify(data))
				if ( null != data && data.length <= 0) {
//					alert("请先创建Document");
					return;
				}
				$('#first').html("");
				$('#first').append("<option value='-1'>--请选择--</optioin>")
				$.each(data, function (index, obj){
					$('#first').append(
							"<option value='" + obj.id + "'>"
									+ obj.name + "</option>")
				})
			   
		   }
	});
}

/**
 * 导入Case
 * @param obj
 */
function case_oper_export(obj){
	$("#import-shadow-box").show();
}

/**
 * 导出Case
 * @param caseId
 * @param caseName
 */
function case_oper_import(event,caseId, caseName){
	$.ajax({   
		   type: "post",
		   url: "/case/exportCase",
		   data: {"caseId":caseId},
		   dataType: "json",
		   success: function(data){
//			   alert(JSON.stringify(data))
			   saveJSON(data, data.name + ".json");
		   }
	});
}

/**
 * 导出json工具
 * 
 * @param data
 * @param filename
 */
function saveJSON(data, filename){
    if(!data) {
        console.error('No data')
        return;
    }
    if(!filename) filename = 'case.json'

    if(typeof data === "object"){
        data = JSON.stringify(data, undefined, 4)
    }
    var blob = new Blob([data], {type: 'text/json'}),
        e    = document.createEvent('MouseEvents'),
        a    = document.createElement('a')

    a.download = filename
    a.href = window.URL.createObjectURL(blob)
    a.dataset.downloadurl =  ['text/json', a.download, a.href].join(':')
    e.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null)
    a.dispatchEvent(e)
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
