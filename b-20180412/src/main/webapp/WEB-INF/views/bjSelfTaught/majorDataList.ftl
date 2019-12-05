<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
     专业
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            专业代码列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
         <input value="${courseName}" name="courseName" id="queryConds" placeholder="专业名称"></input>
    </div>
    <div class="form-group" >
	     <select id="region" style="width:240px">
	         <option value="2" >全部地域</option>
	         <#list regionList as regionEntity>
	           <option value=${regionEntity.module} <#if region==regionEntity.module>selected</#if>>${regionEntity.moduleName}</option>
	         </#list>
	     </select>
    </div>
    
    <a id="searchCourse" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</a>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">ID</th>
        <th class="center" style="width: 50px;"> 地域</th>
        <th style="width: 200px;">专业名称</th>
        <th style="width: 150px;">学校</th>
        <th style="width: 150px;">考察层次</th>
        <th style="width: 200px;">学力层次</th>
        <th>描述</th>
        <th style="width: 150px;">创建时间</th>
    </tr>
    </thead>
    <tbody>
    <#list majorDataList.result as majorData>
        <tr>
            <td>${majorData.id}</td>
           	<td>
		         <#list regionList as regionEntity>
		            <#if majorData.region==regionEntity.module>
						${regionEntity.moduleName}
					</#if>
		         </#list>
            </td>
            <td>${majorData.name}</td>
            <td>${majorData.examSchool}</td>
            <td>${majorData.majorLevel}</td>
            <td>${majorData.degree}</td>
            <td>${majorData.detail}</td>
            <td>${majorData.createTime}</td>
        </tr>
    </#list>
    </tbody>
</table>
<@page totalCount="${majorDataList.totalCount}"
pageNo="${majorDataList.pageNo}"
totalPage="${majorDataList.totalPage}" url=""> a</@page>


<script>

      $("#searchCourse").click(function(){
        //搜索
        toPage(0); 
      });

      function toPage(page) {
        var courseName = document.getElementById("queryConds").value;
        var region = document.getElementById("region").value;
        var url = window.location.pathname + "?courseName=" + base64encode(utf16to8(encodeURIComponent(courseName)))
            + "&page=" + page
            + "&region=" + region;
        console.log("url"+url);
        self.location = url;
      }

</script>

</@htmlBody>
