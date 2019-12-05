<!DOCTYPE html>
<#include "/common/style.ftl">
<html>
<head>
<title>查看文档</title>
</head>
<body>
<!--top begin-->
<div id="post-top" class="doc-view-top">
    <div id="top-tab" style="width: 100%;">
        <a href="javascript:" id="builder" class="a-active">文档展示</a>
    </div>
    <div id="top-r"></div>
</div>
<!--top end-->

<!--content begin-->
<div class="content show runner-content" id="builder-content">
	<div class="doc-titles">
		<div class="view-search">
			<ul>
				<#list docRequestList as request>
				<li><a href="#point${request_index}">${request.name}</a></li>
				</#list>
			</ul>
		</div>
		<div>
			<h3>文档名称：</h3>${doc.name}
		</div>
		<div>
			<h3>文档描述：</h3>${doc.desc}
		</div>
		<div>
			<h3>文档模拟访问地址：</h3>${doc.mockUrl}
		</div>
		<div class="doc-title-font">			
			<h3>版本号：</h3>${doc.version}
			<h3>业务线：</h3>${doc.businessLine}
			<h3>创建时间：</h3>${doc.createTime}
			<h3>上次修改时间：</h3>${doc.updateTime}
		</div>
	</div>
	<div class="doc-content">
	    <#list docRequestList as request>
	      <div class="doc-content-list" id="point${request_index}">
	         <div class="doc-list-top">
		         <div class="doc-list-style">
		         	<p><span>名称：</span>${request.name}</p>
		         	<p><span>url：</span><i>${request.url}</i></p>
		         	<p><span>请求方式：</span>${request.method}</p>
		         	<span class="list-number">${request_index + 1}</span>
		         </div>
		         <p class="new-style"><span>创建时间：</span>${request.createTime}</p>
		         <p class="new-style"><span>上次修改时间：</span>${request.updateTime}</p>
	         </div>
	          <p><span class="param-stye">Header：</i></span></p>
	         <table id="resultTable" class="table table-hover docTable">
                 <thead>
                     <tr>                         
                         <th style="width: 20%;">参数名</th> 
                         <th style="width: 20%;">参数意义</th>                                    
                         <th style="width: 20%;">参数值</th>      
                         <th style="width: 20%;">传递方式</th>
                         <th style="width: 20%;">数据类型</th>                   
                     </tr>
                	</thead> 
	        	 <#list request.paramList as param>
	        	 	<#if param.paramType == 'HeaderParam'>
         		     <tr>                         
                          <td>${param.name}</td> 
                          <td>${param.desc}</td>                                    
                          <td>${param.paramValue}</td>      
                          <td>${param.paramType}</td>   
                          <td>${param.dataType?lower_case?cap_first}</td>   
                      </tr>    
                    </#if>      
	          	</#list>
	          </table> 
	        <#if request.format=="normal">
	         <p><span class="param-stye">入参：</i></span></p>
	         <table id="resultTable" class="table table-hover docTable">
                 <thead>
                     <tr>                         
                         <th style="width: 10%;">参数名</th> 
                         <th style="width: 10%;">参数意义</th>                                    
                         <th style="width: 60%;">参数值</th>      
                         <th style="width: 10%;">传递方式</th>
                         <th style="width: 10%;">数据类型</th>                   
                     </tr>
                	</thead> 
	        	 <#list request.paramList as param>
	        	 	<#if param.paramType=='QueryParam'>
         		     <tr>                         
                          <td>${param.name}</td> 
                          <td>${param.desc}</td>                                    
                          <td>${param.paramValue}</td>      
                          <td>${param.paramType}</td>   
                          <td>${param.dataType}</td>                      
                      </tr>
                    </#if>                         
	          	</#list>
	          </table>
	          <#else>
	            <div class="out-param">
	          	<p><span class="param-stye">入参：</span></p><br>
	          	<div class="btn-wrap">
		          	<a href="javascript:" onclick="textBtnOn(this)" class="json-btn" id="text-btn">text</a>
					<a href="javascript:" onclick="jsonBtnOn(this)" class="json-btn jsonBtn" id="json-btn">json</a>
	         	</div>	
	         	<textarea class="out-param-textarea" id="ParamTextarea">${request.params}</textarea>
	          </div>        
              </#if>    
	          <div class="out-param">
	          	<p><span class="param-stye">出参：</span></p><br>
	          	<div class="btn-wrap">
		          	<a href="javascript:" onclick="textBtnOn(this)" class="json-btn" id="text-btn">text</a>
					<a href="javascript:" onclick="jsonBtnOn(this)" class="json-btn jsonBtn" id="json-btn">json</a>
	         	</div>	
	         	<textarea class="out-param-textarea" id="outParamTextarea">${request.outParams}</textarea>
	          </div>
	         <p><span>描述：</span><strong class="desc-style">${request.desc}</strong></p>
	      </div>
	      <hr>
	    </#list>
	</div>
      <input type="hidden" class="form-control" name="caseId" id="caseId">
</div>
<!--content end-->
<script>
$(function (){
	$(".jsonBtn").click();
	autosize(document.querySelectorAll('textarea'));
})

//text btn onclick
function textBtnOn(obj) {
	var tTextareaCont = $(obj).parent().next()[0].innerHTML;
	tTextareaCont= tTextareaCont.replace(/\s+/g,"");
	$(obj).parent().next()[0].innerHTML = tTextareaCont;
	return tTextareaCont;
}

 //json btn onclick
function jsonBtnOn(obj) {
	var tTextareaCont = $(obj).parent().next()[0].innerHTML;
	var cstring= tTextareaCont.replace(/[\\]/g,'');
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
	try {		
		var jsonObj=JSON.parse(cstring);
		var strJson = JSON.stringify(jsonObj, null, 4);
		$(obj).parent().next()[0].innerHTML = strJson;
	} catch (error) {
		$(obj).parent().next()[0].innerHTML = cstring;
	} 
}
</script>
</body>
</html>