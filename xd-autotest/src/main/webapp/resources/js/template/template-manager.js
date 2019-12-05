/**
 * 
 */
var headerParams = [];

$(function(){
	
	// 获取header头所有信息
	$("#addHeaderOk").click(function(){
		
		headerParams.length = 0;
		var $headerName = $("#headerName").val();
		if($headerName == ''){alert("须对模版命名"); return}
		
		var keyword = "";
		var label = $("#addHeaderOk").attr("label");
		
		var url = "";
		var typeId = -1; // 未知
		
		if(label=='header'){
			typeId = 0;
			checkTemplate(headerParams, "addHeaderForm" , "key");
		}
		if(label=='param'){
			typeId = 1;
			checkTemplate(headerParams, "Param" , "name");
		}
		if(label=='preCondition'){
			typeId = 2;
			checkTemplate(headerParams, "PreCondition" , "key");
		}
		if(label=='preSet'){
			typeId = 3;
			checkTemplate(headerParams, "PreSet" , "key");
		}
		if(label=='testCase'){
			typeId = 4;
			checkTemplate(headerParams, "TestCase" , "testKey");
		}
		if(label=='afterSet'){
			typeId = 5;
			checkTemplate(headerParams, "AfterSet" , "resultKey");
		}
		
		$.ajax({
			   type: "post",
			   url: "/template/saveOrUpdate",
			   data: {'groupParams':JSON.stringify(headerParams),'groupName':$headerName,typeId:typeId},
			   dataType: "json",
			   success: function(data){
					if ('0' == data.retcode) {
						alert(data.retdesc)
						$("#editPre-cancel").trigger("click");
						
						var $template = null;
						if(typeId == 0){
							$template = $("#headerTemplate0");
						}
						if(typeId == 1){
							$template = $("#paramTemplate");
						}
						if(typeId == 2){
							$template = $("#preConditionTemplate");
						}
						if(typeId == 3){
							$template = $("#preSetTemplate"); // 
						}
						if(typeId == 4){
							$template = $("#testCaseTemplate");
						}
						if(typeId == 5){
							$template = $("#afterSetTemplate");
						}
						$template.parent().find("div").trigger("click")
						
					} else {
						alert(data.retdesc)
					}
			   }
		});
	});
	
	// 修改HeaderDetail的确定按钮
	$("#eidtHeaderOk").click(function(){
		headerParams.length = 0;
		var $headerName = $("#eidtheaderName").val();
		if($headerName == ''){alert("须对模版命名"); return}
		checkTemplate(headerParams, "eidtHeaderForm" , "key");
		var groupIdAndTypeId = $("#editGroupId").val().split("|");
		$.ajax({
			   type: "post",
			   url: "/template/saveOrUpdate",
			   data: {
				   'id':groupIdAndTypeId[0],
				   'typeId':groupIdAndTypeId[1],
				   'groupParams':JSON.stringify(headerParams),
				   'groupName':$headerName
				   },
			   dataType: "json",
			   success: function(data){
					if ('0' == data.retcode) {
						alert("操作成功")
						$("#eidtHeaderForm").find('input,select,textarea').each(function(index, ele){
							$(this).val("");
						})
						$("#editPre-cancel").trigger("click");
					} else {
						alert(data.retdesc)
					}
			   }
		});
	});
})


function managePresetOn(obj) {
	$('#manage-presets-id').show();
	$("#headerListScroll").html("");
	$("#addHeaderOk").attr("label", $(obj).attr("label"));
	var url = "";
	var typeId = -1; // 未知
	if($(obj).attr("label")=='header'){
		typeId = 0;
	}
	if($(obj).attr("label")=='param'){
		typeId = 1;
	}
	if($(obj).attr("label")=='preCondition'){
		typeId = 2;
	}
	if($(obj).attr("label")=='preSet'){
		typeId = 3;
	}
	if($(obj).attr("label")=='testCase'){
		typeId = 4;
	}
	if($(obj).attr("label")=='afterSet'){
		typeId = 5;
	}
	
	$.ajax({
		   type: "post",
		   url: "/template/getGroup",
		   data: {'id':'','typeId':typeId},
		   dataType: "json",
		   success: function(data){
				if ('true' == data.status && null != data.msg && data.msg.length > 0) {
					$("#headerListScroll").append("<ul>");
					for(var e in data.msg){
						$("#headerListScroll>ul").append("<li onclick=toDetail("+ data.msg[e].id + "," + typeId + ")>" + data.msg[e].groupName + "</li>");
						if(e == (data.msg.length - 1)){
							$("#headerListScroll li:last").append("</ul>");
						}
					}
				}else{
					$("#headerListScroll").append("暂无模版");
				} 
				
		   }
	});
	
}

function toDelete(id){
	$.ajax({
		   type: "post",
		   url: "/template/header_delete",
		   data: {'id':id},
		   dataType: "json",
		   success: function(data){
				if ('true' == data.status && data.msg.length > 0) {
					alert(data.msg);
					$("#manage-presets-id").css('display','none'); 
				}
		   }
	});
}

function toDetail(groupId, typeId){
	
	var plusIcon = '';
	var empty = '';
	if(typeId == 0){
		// header
		empty = '<tr><th style="width: 315px">参数名称</th><th style="width: 315px">参数值</th><th style="width: 30px"></th></tr>';
		plusIcon = 'templateHeaderAddInput(this)';
	}
	if(typeId == 1){
		// param
		empty = '<tr><th style="width: 20%">参数名称</th><th style="width: 30%">参数值</th><th>描述</th><th style="width: 15%">数据类型</th><th style="width: 30px"></th></tr>';
		plusIcon = 'paramAddInput(this)';
	}
	if(typeId == 2){
		// preCondition
		empty = '<tr><th>目标键</th><th style="width: 10%">条件类型</th><th>目标值</th><th style="width: 30px"></th></tr>';
		plusIcon = 'perAddInput(this)';
	}
	if(typeId == 3){
		// preSet
		empty = '<tr><th style="width:15%">数据源类型</th><th style="width:15%">作用范围</th><th style="width:15%">存储键</th><th style="width:15%">数据类型</th><th>赋值语句</th></tr>';
		plusIcon = 'perSetAddInput(this)';
	}
	if(typeId == 4){
		// testCase
		empty = '<tr><th>测试名称</th><th>待测试键</th><th>数据类型</th><th>目标值</th></tr>';
		plusIcon = 'testCaseAddInput(this)';
	}
	if(typeId == 5){
		// afterSet
		empty = '<tr><th>结果键</th><th style="width: 20%">数据类型</th><th>映射键</th></tr>';
		plusIcon = 'afterSetAddInput(this)';
	}
	// 切换+号
	$("#eidtHeaderForm").parent().find("a").attr("onclick",plusIcon);
	
	// 
	$.ajax({
		   type: "post",
		   url: "/template/getDetail",
		   data: {'groupId':groupId, 'typeId':typeId},
		   dataType: "json",
		   success: function(data){
				if (null != data && data.groupInfo != null) {
					$("#eidtHeaderForm").val("");
					$("#eidtheaderName").val(data.groupInfo.groupName);
					$("#eidtHeaderForm tbody").html("");
					$("#eidtHeaderForm tbody").append(empty);
					if(data.detail != null && data.detail.length > 0){
					$.each(data.detail, function(index, obj){
						var echo = '';
						if(typeId == 0){
							// header
							echo = '<tr class="remove" onclick="templateHeaderAddInput(this)"><td><input type="text" name="key" value="'+ obj.key +'" placeholder="参数名称" readonly="readonly"></td><td><input type="text" name="value" value="'+ obj.value +'" placeholder="参数值"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>'
							$("#eidtHeaderForm tbody").append(echo);
						}
						if(typeId == 1){
							// param
						   obj.paramValue=obj.paramValue.replace(/\"/g,"&quot;");
						   obj.desc=obj.desc.replace(/\"/g,"&quot;");
						   echo = '<tr index="'+index+'" class="remove" onclick="paramAddInput(this)"><td><input type="text" name="name" value="'+obj.name+'" placeholder="Name"></td><td><input type="text" name="paramValue" value="'+obj.paramValue+'" placeholder="ParamValue"></td><td><input type="text" name="desc" value="'+obj.desc+'" placeholder="Desc"></td><td style="display:none;"><input type="text" name="paramType" value="QueryParam" placeholder="paramType" ></td><td><select onchange="preSetChangeForm(this)"  class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option><option value="func">Func</option></select></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
						   $("#eidtHeaderForm tbody").append(echo);
						   $("#eidtHeaderForm tbody tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
				    			if($(this).val()==obj.dataType){
				    				$(this).attr("selected",true)
				    			}
				    		})
							   
						}
						if(typeId == 2){
							// preCondition
							obj.key=obj.key.replace(/\"/g,"&quot;");
							echo = '<tr index="'+index+'" class="remove" onclick="perAddInput(this)"><td><input name="key" type="text" value="'+obj.key+'" placeholder="目标键"></td><td><select id="preConditionSel" class="select-style" name="condition"><option>eq</option><option>ge</option><option>le</option><option>gt</option><option>lt</option><option>in</option><option>notIn</option><option>isNull</option><option>isNotNull</option><option>isEmpty</option><option>isNotEmpty</option></select></td><td><input type="text" name="targetValue" value="'+obj.targetValue+'" placeholder="目标值"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
							$("#eidtHeaderForm tbody").append(echo);
							$("#eidtHeaderForm tbody tr[index='"+index+"'] select[name='condition'] option").each(function(i,o){
								if($(this).val()==obj.condition){
					    			$(this).attr("selected",true);
						    	}
					    	})
						}
						if(typeId == 3){
							// preSet
						   obj.key=obj.key.replace(/\"/g,"&quot;");
						   obj.assignment=obj.assignment.replace(/\"/g,"&quot;");
						   echo = 
							   '<tr index="'+index+'" class="remove" onclick="perSetAddInput(this)"><td>' +
							   '<select onchange="preSetChangeForm(this)" class="select-style" name="dataSource"><option value="Local">Local</option><option  value="Mysql">Mysql</option><option value="Redis">Redis</option><option value="Mongo">Mongo</option><option value="Rabbitmq">Rabbitmq</option><option value="Func">Func</option></select></td><td><select class="select-style" name="scope"><option value="global">全局</option><option value="part">局部</option></select></td><td><input type="text" name="key" value="'+obj.key+'" placeholder="Key"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option><option value="func">Func</option></select></td><td><input type="text" name="assignment" value="'+obj.assignment+'" placeholder="Assignment"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
						   
						   $("#eidtHeaderForm tbody").append(echo);
						   $("#eidtHeaderForm tbody tr[index='"+index+"'] select[name='dataSource'] option").each(function(i,o){
				    				if($(this).val()==obj.dataSource){
					    				$(this).attr("selected",true)
					    			}
				    		})
				    		$("#eidtHeaderForm tbody tr[index='"+index+"'] select[name='scope'] option").each(function(i,o){
					    			if($(this).val()==obj.scope){
					    				$(this).attr("selected",true)
					    			}
				    		})
				    		$("#eidtHeaderForm tbody tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
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
				    					   var tr_parent = $("#eidtHeaderForm tbody tr[index='"+index+"'] input[name='assignment']").parent();
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
						}
						if(typeId == 4){
							// testCase
							obj.testKey=obj.testKey.replace(/\"/g,"&quot;");
						    obj.testValue=obj.testValue.replace(/\"/g,"&quot;");
						   
						    echo = '<tr index="'+index+'" class="remove" onclick="testCaseAddInput(this)" onclick="afterSetAddInput(this)"><td><input type="text" name="name" value="'+obj.name+'" placeholder="Name"></td><td><input type="text" name="testKey" value="'+obj.testKey+'" placeholder="TestKey"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="testValue" value="'+obj.testValue+'" placeholder="TestValue"></td><td style="display:none"><input type="text" name="testResult" value="Unknown"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
						    $("#eidtHeaderForm tbody").append(echo);
						    $("#eidtHeaderForm tbody tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
			    				if($(this).val()==obj.dataType){
				    				$(this).attr("selected",true);
				    			}
					    	})
						}
						if(typeId == 5){
							// afterSet
						   obj.resultkey=obj.resultkey.replace(/\"/g,"&quot;");
						   obj.mappingKey=obj.mappingKey.replace(/\"/g,"&quot;");
						   
						   echo = '<tr index="'+index+'" class="remove" onclick="afterSetAddInput(this)"><td><input name="resultkey" type="text" value="'+obj.resultkey+'" placeholder="Resultkey"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="mappingKey" value="'+obj.mappingKey+'" placeholder="MappingKey"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
						   $("#eidtHeaderForm tbody").append(echo);
						   $("#eidtHeaderForm tbody tr[index='"+index+"'] select[name='dataType'] option").each(function(i,o){
			    			if($(this).val()==obj.dataType){
				    			$(this).attr("selected",true)
				    		}
					       })
						}
					})
				}
					$("#editGroupId").val(groupId + '|' + typeId);
				}
		   }
	});
	
	$('#add-list-box').hide();
	$('#eidtKeyValue-box').show();
}



/**
 * 将左边的headerGroup移动到右边的table内   headerTable
 */

function moveToRight(typeId, groupId, obj){
	var $position = null;
	if(typeId == 0){
		$position = $(obj).parent().parent().parent().parent().parent().parent().parent().parent().find("#tabs-wrap #headerTable tr:last");
	}
	if(typeId == 1){
		$position = $(obj).parent().parent().parent().parent().parent().parent().parent().parent().find("#tabs-wrap #Param tr:last");
	}
	if(typeId == 2){
		$position = $(obj).parent().parent().parent().parent().parent().parent().parent().parent().find("#tabs-wrap #PreCondition tr:last");
	}
	if(typeId == 3){
		$position = $(obj).parent().parent().parent().parent().parent().parent().parent().parent().find("#tabs-wrap #PreSet tr:last");
	}
	if(typeId == 4){
		$position = $(obj).parent().parent().parent().parent().parent().parent().parent().parent().find("#tabs-wrap #TestCase tr:last");
	}
	if(typeId == 5){
		$position = $(obj).parent().parent().parent().parent().parent().parent().parent().parent().find("#tabs-wrap #AfterSet tr:last");
	}
	
	// step1 获取所有该group下的detail
	$.ajax({
		   type: "post",
		   url: "/template/getDetail",
		   data: {'groupId':groupId, 'typeId':typeId},
		   dataType: "json",
		   success: function(data){
			   if (null != data && data.groupInfo != null) {
					if(data.detail != null && data.detail.length > 0){
						for(var index =0; index < data.detail.length; index ++){
							var echo = '';
							if(typeId == 0){
								// header
								echo = '<tr class="remove" onclick="headerAddInput(this)"><td class="header-checkbox-td" onclick="stopProp(this)"><input class="header-checkbox" name="" type="checkbox" value="" /></td><td><input type="text" name="name" value="' + data.detail[index].key + '" placeholder="参数名称"></td><td><input type="text" name="paramValue"  value="' + data.detail[index].value + '" placeholder="参数值"></td><td><input type="text" name="desc" value="" placeholder="描述"></td><td style="display:none;"><input type="text" name="paramType" value="HeaderParam" placeholder="paramType" ></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>'
								$position.after(echo);
							}
							if(typeId == 1){
								// param
								data.detail[index].paramValue=data.detail[index].paramValue.replace(/\"/g,"&quot;");
								data.detail[index].desc=data.detail[index].desc.replace(/\"/g,"&quot;");
							   echo = '<tr index="'+index+'" class="remove" onclick="paramAddInput(this)"><td><input type="text" name="name" value="'+ data.detail[index].name+'" placeholder="Name"></td><td><input type="text" name="paramValue" value="'+ data.detail[index].paramValue+'" placeholder="ParamValue"></td><td><input type="text" name="desc" value="'+ data.detail[index].desc+'" placeholder="Desc"></td><td style="display:none;"><input type="text" name="paramType" value="QueryParam" placeholder="paramType" ></td><td><select onchange="preSetChangeForm(this)"  class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option><option value="func">Func</option></select></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
							   $position.after(echo);
							   $position.find("tr[index='"+index+"'] select[name='dataType'] option").each(function(ii,oo){
					    			if($(this).val()==data.detail[index].dataType){
					    				$(this).attr("selected",true)
					    			}
					    		})
								   
							}
							if(typeId == 2){
								// preCondition
								data.detail[index].key=data.detail[index].key.replace(/\"/g,"&quot;");
								echo = '<tr index="'+index+'" class="remove" onclick="perAddInput(this)"><td><input name="key" type="text" value="'+ data.detail[index].key+'" placeholder="目标键"></td><td><select id="preConditionSel" class="select-style" name="condition"><option>eq</option><option>ge</option><option>le</option><option>gt</option><option>lt</option><option>in</option><option>notIn</option><option>isNull</option><option>isNotNull</option><option>isEmpty</option><option>isNotEmpty</option></select></td><td><input type="text" name="targetValue" value="'+ data.detail[index].targetValue+'" placeholder="目标值"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
								$position.after(echo);
								$position.find("tr[index='"+index+"'] select[name='condition'] option").each(function(iii,ooo){
									if($(this).val()==data.detail[index].condition){
						    			$(this).attr("selected",true);
							    	}
						    	})
							}
							if(typeId == 3){
								// preSet
								data.detail[index].key=data.detail[index].key.replace(/\"/g,"&quot;");
							   data.detail[index].assignment=data.detail[index].assignment.replace(/\"/g,"&quot;");
							   echo = 
								   '<tr index="'+index+'" class="remove" onclick="perSetAddInput(this)"><td>' +
								   '<select onchange="preSetChangeForm(this)" class="select-style" name="dataSource"><option value="Local">Local</option><option  value="Mysql">Mysql</option><option value="Redis">Redis</option><option value="Mongo">Mongo</option><option value="Rabbitmq">Rabbitmq</option><option value="Func">Func</option></select></td><td><select class="select-style" name="scope"><option value="global">全局</option><option value="part">局部</option></select></td><td><input type="text" name="key" value="'+ data.detail[index].key+'" placeholder="Key"></td><td><select onchange="preSetChangeForm(this)" class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option><option value="func">Func</option></select></td><td><input type="text" name="assignment" value="'+ data.detail[index].assignment+'" placeholder="Assignment"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
							   
							   $position.after(echo);
							   $position.find("tr[index='"+index+"'] select[name='dataSource'] option").each(function(iiii,oooo){
					    				if($(this).val()==data.detail[index].dataSource){
						    				$(this).attr("selected",true)
						    			}
					    		})
					    		$position.find("tr[index='"+index+"'] select[name='scope'] option").each(function(iiiii,ooooo){
						    			if($(this).val()==data.detail[index].scope){
						    				$(this).attr("selected",true)
						    			}
					    		})
					    		$position.find("tr[index='"+index+"'] select[name='dataType'] option").each(function(iiiiii,oooooo){
						    			if($(this).val()==data.detail[index].dataType){
						    				$(this).attr("selected",true)
						    			}
					    		})
					    		// 如果dataType是func    
					    		if(data.detail[index].dataSource=='Func'){
					    			// 获取所有的JS方法名
					    			var allJsFuncs = [];
					    			$.ajax({
					    				   type: "post",
					    				   url: "/dataSource/getJSFuncsWithJson",
					    				   dataType: "json",
					    				   success: function(dataObj){
					    					   var tr_parent = $position.find("tr[index='"+index+"'] input[name='assignment']").parent();
								    			tr_parent.html("");
								    			tr_parent.append("<select name='assignment'><option id='" + index + "' value='-1'>--请选择JS签名--</option></select>");
								    			if(dataObj.length > 0){
								    				for ( var ele in dataObj) {
								    					console.log(data.detail[index]);
								    					var temp = "";
								    					if(dataObj[ele].id.toString()==data.detail[index].assignment){
								    						temp = "selected='selected'";
								    					}
								    					$("#"+index+"").after("<option " + temp + " value='" + dataObj[ele].id + "'>" + dataObj[ele].funSignature + "</option>");
								    					temp = "";
								    				}
								    			}
					    				   }
					    			});
					    		}
							}
							if(typeId == 4){
								// testCase
								data.detail[index].testKey=data.detail[index].testKey.replace(/\"/g,"&quot;");
								data.detail[index].testValue=data.detail[index].testValue.replace(/\"/g,"&quot;");
							   
							    echo = '<tr index="'+index+'" class="remove" onclick="testCaseAddInput(this)" onclick="afterSetAddInput(this)"><td><input type="text" name="name" value="'+ data.detail[index].name+'" placeholder="Name"></td><td><input type="text" name="testKey" value="'+ data.detail[index].testKey+'" placeholder="TestKey"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="testValue" value="'+ data.detail[index].testValue+'" placeholder="TestValue"></td><td style="display:none"><input type="text" name="testResult" value="Unknown"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
							    $position.after(echo);
							    $position.find("tr[index='"+index+"'] select[name='dataType'] option").each(function(i1,o1){
				    				if($(this).val()==data.detail[index].dataType){
					    				$(this).attr("selected",true);
					    			}
						    	})
							}
							if(typeId == 5){
								// afterSet
								data.detail[index].resultkey=data.detail[index].resultkey.replace(/\"/g,"&quot;");
								data.detail[index].mappingKey=data.detail[index].mappingKey.replace(/\"/g,"&quot;");
							   
							   echo = '<tr index="'+index+'" class="remove" onclick="afterSetAddInput(this)"><td><input name="resultkey" type="text" value="'+ data.detail[index].resultkey+'" placeholder="Resultkey"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="mappingKey" value="'+ data.detail[index].mappingKey+'" placeholder="MappingKey"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';
							   $position.after(echo);
							   $position.find("tr[index='"+index+"'] select[name='dataType'] option").each(function(i2,o2){
				    			if($(this).val()==data.detail[index].dataType){
					    			$(this).attr("selected",true)
					    		}
						       })
							}
						}
					}
			   }
		   }
	});
}




function checkTemplate(headerParams, formId , keyword){
	$("#" + formId + " tr").each(function(index, data){
		if(index == 0){return true;}
		var row= {};
		$(this).find('input,select,textarea').each(function(){
			if($(this).attr('name')==keyword && $(this).val()==''){
				row = {}; // 把该行数据清空
				return false;
			}else{
				row[$(this).attr('name')]=$(this).val();
			}
    	});
		if(JSON.stringify(row) != '{}'){
			headerParams.push(row);
		}
    });
	if(JSON.stringify(headerParams)  == '[]'){
		alert("参数为空");
		return;
	}
}