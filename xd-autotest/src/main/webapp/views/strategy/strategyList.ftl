<!DOCTYPE html>
<#include "/common/style.ftl">
<html>
<head>
<title>调度策略</title>
</head>
<body>
<!--top begin-->
<div id="post-top">
    <div id="top-tab" style="width: 100%;">
        <a href="javascript:" id="builder" class="a-active">Strategy</a>
    </div>
    <div id="top-r"></div>
</div>
<!--top end-->

<!--content begin-->
<div class="content show runner-content" id="builder-content">
    <!--left begin-->
    <div class="">
        <div >
            <table>
               <tr>
                  <th>ID</th>
                  <th>用例名称</th>
                  <th>用例说明</th>
                  <th>用例请求数量</th>
                  <th>定时策略</th>
                  <th>用例版本号</th>
                  <th>用例创建时间</th>
                  <th style="width:10%">保存定时策略</th> 
                </tr>
              <#list caseList as case>
          	    <tr>
    			  <td>${case.id}</td>
       			  <td>${case.name}</td>
      			  <td>${case.caseDesc}</td>
       			  <td>${case.count}</td>
       		      <td>
       		        <select class="select-style" name="timingTaskDesc" >
                        <option  <#if case.timingTaskDesc == '0' > selected = "selected" </#if> value="0">无任务</option>
                        <option  <#if case.timingTaskDesc == '1'>selected = "selected" </#if>  value="1">每小时</option>
                        <option <#if case.timingTaskDesc == '2'>selected = "selected" </#if>  value="2">每天</option>
                        <option <#if case.timingTaskDesc == '3'>selected = "selected" </#if>  value="3">每周</option>    
	                </select>
	       		  </td>
          		  <td>${case.version}</td>
            	  <td>${case.createTime}</td>
            	  <td><a style="cursor: pointer;color:blue" onclick="saveCase('${case.id}',this)">保存</a> 
                 </td>   
              </tr>
                  </#list>
            </table>
        </div>
    </div>
</div>
<!--content end-->
</body>
<script>
	function saveCase(caseId,obj){
		var timingTaskDesc=obj.parentNode.parentNode.children[4].getElementsByTagName("select")[0].value;
			$.post("/run/editStrategy", {caseId:caseId,timingTaskDesc:timingTaskDesc},
				function(data){
					var u=JSON.parse(data);
					if(u.status="success"){	
					    alert("修改成功");
					    location.reload();	
					}else{
						alert(u.data);
					}
			});	   
	}
</script>
</html>