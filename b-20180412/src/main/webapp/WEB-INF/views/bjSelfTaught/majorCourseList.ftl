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
         <input value="${courseName}" name="courseName" id="queryConds" placeholder="课程名称"></input>
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
        <th class="center" style="width: 50px;">课程ID</th>
        <th class="center" style="width: 50px;">地域</th>
        <th style="width: 150px;">课程名称</th>
        <th style="width: 200px;">课程性质</th>
        <th style="width: 200px;">考核方式</th>
        <th style="width: 200px;">学分</th>
        <th style="width: 200px;">考期</th>
        <th style="width: 200px;">考试时间</th>
        <th style="width: 150px;">课程介绍</th>
        <th style="width: 200px;">创建时间</th>
    </tr>
    </thead>
    <tbody>
    <#list majorCourseList.result as majorCourse>
        <tr>
            <td>${majorCourse.id}</td>
            <td>
		         <#list regionList as regionEntity>
		            <#if majorCourse.region==regionEntity.module>
						${regionEntity.moduleName}
					</#if>
		         </#list>
            </td>
            <td>${majorCourse.name}</td>
            <td>${majorCourse.courseType}</td>
            <td>${majorCourse.mode}</td>
            <td>${majorCourse.credit}</td>
            <td>${majorCourse.examDateType}</td>
            <td>${majorCourse.examDate}</td>
            <td>${majorCourse.detail}</td>
            <td>${majorCourse.createTime}</td>
        </tr>
    </#list>
    </tbody>
</table>
<@page totalCount="${majorCourseList.totalCount}"
pageNo="${majorCourseList.pageNo}"
totalPage="${majorCourseList.totalPage}" url=""> a</@page>

<script>
      
        $("#searchCourse").click(function(){
          //搜索
          toPage(0)
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
