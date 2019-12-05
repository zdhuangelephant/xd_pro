<!DOCTYPE html>
<#include "/common/style.ftl">
<html>
<head>
<title>调度</title>
</head>
<body>
<!--top begin-->
<div class="common-top">
				<div class="index-logo">
					<!-- <a herf="#"><img alt="" src=""></a> -->
					<span><a href="/" title="自动化测试">自动化测试</a></span>
				</div>
				<div class="index-nav-wrap">
					<ul class="index-nav" id="indexNavWrap">
						<li><a href="/">首页</a></li>
						<li><a href="/doc/postman">服务</a></li>
						<li><a href="/case/testcase" >用例</a></li>
						<li><a href="/run/runner" class="index-active">调度</a></li>
						<li><a href="/dataSource/list">配置</a></li>
					</ul>
				</div>
				<div class="index-login-out">
					<a href="/admin/loginOut" target="_parent" >退出 </a>
				</div>
			</div>
<!--top end-->

<!--content begin-->
<div class="content show runner-content" id="builder-content">
    <!--left begin-->
    <div class="builder-content-l runner-left w30 scrollbar" id="scroll-style">
    <div class="force-overflow"></div>
        <div class="runner-list">
            <ul>
              <#list caseList as case>
                <li>
                    <a  onclick="getResults('${case.id}', this)">        
         			   <button onclick="run()" class="import current" id="run">Run</button>
                       <p class="case-num"><span>${case_index + 1}</span></p>
                       <p><span>用例名称：</span>${case.name}</p>
                       <p><span>调度策略：</span><select  id="timingTaskDesc" name="timingTaskDesc" onchange="change('${case.id}')" >
                        	<option  <#if case.timingTaskDesc == '0' > selected = "selected" </#if> value="0">无任务</option>
                        	<option  <#if case.timingTaskDesc == '1'>selected = "selected" </#if>  value="1">每小时</option>
                        	<option <#if case.timingTaskDesc == '2'>selected = "selected" </#if>  value="2">每天</option>
                        	<option <#if case.timingTaskDesc == '3'>selected = "selected" </#if>  value="3">每周</option>    
	               		</select>
	               		</p>
                       <p><span>用例说明：</span>${case.caseDesc}</p>
                       <p><span>用例外连地址：</span></p>
                       <input style="height:30px;width:400px" type="text"  value="http://autotest.51xiaodou.com/request/aosSend?caseId=${case.id}"/>
                       <p><span>用例请求数量：</span>${case.count}</p>
                       <p><span>用例版本号：</span>${case.version}</p>      
                       <p><span>用例创建时间：</span>${case.createTime}</p>
                    </a>
                </li>
              </#list>
            </ul>
        </div>
    </div>
    <!--right begin-->
    <div class="builder-content-r w70" id="scroll-style">
        <!-- tab head begin -->
    	<div class="run-tab-header">
    		<a class="a-active" href="javascript:">Recent Runs</a>
    		<a id="runResult" href="javascript:">RunResult</a>
    	</div>
    	<!-- tab head end -->
    	<!-- tab content begin -->
    	<div class="run-tab-cont-wrap">
    		<!-- tab content1 begin -->
    		<div class="run-tab run-show">
    			<ul id="result" class="rec-run-ul-wrap"> 		
    			</ul>
    		</div>
    		<!-- tab content1 end -->
		
    		<!-- tab content2 begin -->
    		<div class="run-tab">
    			<div class="run-result-top-show">
    				<div class="run-result-top-left">
    					<div id="passedCount"><strong>0</strong><span>PASSED</span></div>
    					<div id="failedCount"><strong>0</strong><span>FAILED</span></div>
    					<div id="caseName"></div>
    				</div>
    				<a onclick="run()" class="run-result-top-retry">Retry</a>
    			</div>
    			<div class="result-run-ul-wrap-show">
    				<ul id="resultUl" class="result-run-ul-wrap">
    					
    				</ul>
    			</div>
    		</div>
    		<!-- tab content2 end -->
    	</div>
    	<!-- tab content end -->
    </div>
      <input type="hidden" class="form-control" name="caseId" id="caseId">
</div>
<!--content end-->
</body>
<script>
    var recordArray;
	function change(caseId){
		var timingTaskDesc=$('#timingTaskDesc').val();
			$.post("/run/editStrategy", {caseId:caseId,timingTaskDesc:timingTaskDesc},
				function(data){
					var u=JSON.parse(data);
					if(u.status="success"){	
					    alert("修改成功");	 
					}else{
						alert(u.data);
					}
			});	   
	}


	function getResults(caseId, obj){
		$('#caseId').val(caseId);
		$(obj).css("background", "#ccc").parent().siblings('li').children('a').css("background", "#f8f8f8");
		$.post("/run/getCase", 
				{
					caseId:caseId
				},
				function(data){
					    var u=JSON.parse(data);	
					    var apiArray=u.data;
					    var passedCount=apiArray.length-u.failCount;
						$("#passedCount strong").html(passedCount); 
						$("#failedCount strong").html(u.failCount);
						$("#caseName").html(u.name);			
					    $("#result").empty();
						$("#resultUl").empty();
						getAllResult(u);
                    	
			});
		
	}
	
	function test () {
		
	}
	
	function run(){
		var caseId=$('#caseId').val();
		if(caseId==""){
			alert("请选择要执行的用例");
			return;
		}else{
			$.post("/request/externalSend", {caseId:caseId},
				function(data){
					var u=JSON.parse(data);
					if(u.status=="success"){	
					    alert("执行成功");		 
					    var apiArray=u.data;   
					    var passedCount=apiArray.length-u.failCount;
						$("#passedCount strong").html(passedCount); 
						$("#failedCount strong").html(u.failCount);
						$("#caseName").html(u.name);			
					    $("#result").empty();
						$("#resultUl").empty();
                    	getAllResult(u);
					}else{
						alert(u.data);
					}
				});	   
		}
	}
	
	function textBtnOn(obj) {
		var outTextarea = $(obj).parents(".btn-wrap").next();
		var str = outTextarea.val().replace(/\s+/g, "");
		outTextarea.val(str);
	}

	function jsonBtnOn(obj) {
		var outTextarea = $(obj).parents(".btn-wrap").next();
		var tTextareaCont = outTextarea.val();
		var cstring = tTextareaCont.replace(/[\\]/g, '');
		if (cstring.substring(0, 1) == "\"") {
			// 字符串以"开头，去掉"
			cstring = cstring.substr(2, cstring.length - 1)
		}
		if (cstring.substring(cstring.length - 1) == "\"") {
			// 字符串以"结尾，去掉"
			cstring = cstring.substr(0, cstring.length - 2)
		}
		try {
			var jsonObj = JSON.parse(outTextarea.val());
			var strJson = JSON.stringify(jsonObj, null, 4);
			outTextarea.val(strJson);
		} catch (error) {
			outTextarea.val(cstring);
		}

	}
	
	function getAllResult(u){
			var apiArray=u.data;
			recordArray =u.recordList;
			for(var i=0;i<recordArray.length;i++){
			   var li=""
			   if(recordArray[i].record=="-1"){
			   		li="<li onclick='catResultDetail("+i+")'><a><span class='failed-size'></span><strong class='pass-name'>"+recordArray[i].name+"</strong></a><a><span class='failed-tip'>Failed</span><span class='pass-time'>"+recordArray[i].time+"</span></a></li>"
			   }else{
			   		li="<li onclick='catResultDetail("+i+")'><a><span class='pass-size'></span><strong class='pass-name'>"+recordArray[i].name+"</strong></a><a><span class='pass-tip'>PASSED</span><span class='pass-time'>"+recordArray[i].time+"</span></a></li>"
			   }
			   $("#result").append(li).attr("onclick","test(this);");
			}
			resultDetail(apiArray);	
			console.log(apiArray);
			console.log("=================");
			console.log(recordArray);
	}
	
	function catResultDetail(i){
		resultDetail(recordArray[i]);
		$("#runResult").click();
	}
	
	
	function resultDetail(apiArray){
		for(var i=0;i<apiArray.length;i++){
        	    var li="";
        	    var resultLi="";  
        	    var requestHeadersLi=""; 
        	    var detailUl = document.createElement("ul");
			    detailUl.className=""; 
			    var resultHtml="";
			    //requestHeader
			    var requestHeaderMap=apiArray[i].apiRequest.header;
			    if(!isEmptyObject(requestHeaderMap)){
				    resultHtml+="<li><p>Request Headers</p>";
					resultHtml+="<table class='run-table'><tr><th style='width: 20%;'>参数名称</th><th style='width: 80%;'>参数值</th></tr>";
					for(var t in requestHeaderMap){
						   resultHtml+="<tr><td>"+t+"</td><td>"+requestHeaderMap[t]+"</td></tr>";
					}
				    resultHtml+="</table></li>"; 
				} 
			    
			    //requestBody
			    var requestBodyFormat=apiArray[i].apiRequest.format;
			    if(requestBodyFormat=="jSon"){
			    	var requestBody=apiArray[i].apiRequest.body;
			    	if (typeof(requestBody) != "undefined"){ 
			   			resultHtml+="<li><p>Request Body</p>";
						resultHtml+="<div class='out-param'><div class='btn-wrap'><a href='javascript:' onclick='textBtnOn(this)' class='json-btn' id='text-btn'>text</a><a href='javascript:' onclick='jsonBtnOn(this)' class='json-btn jsonBtn' id='json-btn'>json</a></div><textarea class='out-param-textarea' id='outParamTextarea'>"+requestBody+"</textarea></div>"
			   		}
			    }else{
			    	var requestBodyMap=apiArray[i].apiRequest.body;
			    	if(requestBodyMap.length>0){
				   		resultHtml=resultHtml+="<li><p>Request Body</p>";
				   		resultHtml+="<table class='run-table'><tr><th style='width: 20%;'>参数名称</th><th style='width: 80%;'>参数值</th></tr>";
						for(var t in requestBodyMap){
						   resultHtml+="<tr><td>"+requestBodyMap[t].name+"</td><td>"+requestBodyMap[t].value+"</td></tr>";
						}
				    	resultHtml+="</table></li>";
				    }  
			    }
			   	
			   		    
			    
        		if(apiArray[i].status=="UnExcute"){   		    
        		    resultLi="<li><span class='pass-size pass-size-new failed-sizes'></span><span class='pass-method'>"+apiArray[i].method+"</span><span class='pass-url'>"+apiArray[i].url+"</span><span class='pass-state failed-states'><strong>UnExcute</strong><strong>UnExcute</strong><strong>UnExcute</strong></span></li>"	
        		}else {
        		    if(!apiArray[i].apiResult.hasError){             		
						resultLi="<li><span class='pass-size pass-size-new'></span><span class='pass-method'>"+apiArray[i].method+"</span><span class='pass-url'>"+apiArray[i].url+"</span><span class='pass-state'><strong>"+apiArray[i].apiResult.httpStatus + "OK" +"</strong><strong>"+apiArray[i].apiResult.costTime + "ms" +"</strong><strong>1.06kb</strong></span></li>"		
						
					}else{ 	
						resultLi="<li><span class='pass-size pass-size-new failed-sizes'></span><span class='pass-method'>"+apiArray[i].method+"</span><span class='pass-url'>"+apiArray[i].url+"</span><span class='pass-state failed-states'><strong>"+apiArray[i].apiResult.httpStatus+ "OK"+"</strong><strong>"+apiArray[i].apiResult.costTime+ "ms"+"</strong><strong>1.06kb</strong></span></li>"
					}
					
					//responseHeader
			    	var responseHeaderMap=apiArray[i].apiResult.header;
			   		if(!isEmptyObject(responseHeaderMap)){
				   		resultHtml=resultHtml+="<li><p>Response Header</p>";
				   		resultHtml+="<table class='run-table'><tr><th style='width: 20%;'>参数名称</th><th style='width: 80%;'>参数值</th></tr>";
						for(var t in responseHeaderMap){
						   resultHtml+="<tr><td>"+t+"</td><td>"+responseHeaderMap[t]+"</td></tr>";
						}
				    	resultHtml+="</table></li>";
				    }
			    	
			    	//responseBody
			    	var responseBody=apiArray[i].apiResult.srcResult;
			    	if (typeof(responseBody) != "undefined"){ 
			   			resultHtml+="<li><p>Response Body</p>";
						resultHtml+="<div class='out-param'><div class='btn-wrap'><a href='javascript:' onclick='textBtnOn(this)' class='json-btn' id='text-btn'>text</a><a href='javascript:' onclick='jsonBtnOn(this)' class='json-btn jsonBtn' id='json-btn'>json</a></div><textarea class='out-param-textarea' id='outParamTextarea'>"+responseBody+"</textarea></div>"
					}
					
					//testCase
					if(!apiArray[i].apiResult.hasError){ 
						var testArray=apiArray[i].testCases;
						if(testArray.length>0){
							resultHtml=resultHtml+="<li><p>TestCase</p>";
					    	resultHtml+="<table class='run-table'><tr><th style='width: 20%;'>用例名称</th><th style='width: 80%;'>执行结果</th></tr>";
							for(var t in testArray){
						  	 resultHtml+="<tr><td>"+testArray[t].name+"</td><td>"+testArray[t].testResult+"</td></tr>";
							}
							resultHtml+="</table></li>";
						}
					}
				}	
				detailUl.innerHTML=resultHtml;
				$("#resultUl").append(resultLi);
				$("#resultUl").append(detailUl);
					/* $("#resultUl>li").click(function () {
						$(this).next("ul").slideToggle('fast');
					}) */
				
			}		
	}
	
	function isEmptyObject(obj) {   
	　　for (var key in obj){
	　　　　return false;//返回false，不为空对象
	　　}　　
	　　return true;//返回true，为空对象
	}
//alert($("#resultUl > li").length);
</script>
</html>