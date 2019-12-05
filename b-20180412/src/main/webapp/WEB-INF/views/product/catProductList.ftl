<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
       	 产品
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            产品列表
        </small>
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
    		${majorData.name}
        </small>
    </h1>
</div>


<form class="form-inline" style="margin-bottom: 10px;">
	<input type="hidden" class="form-control" name="catId" id="catId" placeholder="专业码" value="${category.id}"/>
    <div class="form-group">
        <select name="showStatus" id="showStatus" class="form-control">
            <#if reqConds.showStatus == ''>
	            <option value="" selected>请选择显示状态</option>
	           	<option value="1" >显示</option>
	           	<option value="0">不显示</option>
           	<#elseif reqConds.showStatus=='1'>
	           	<option value="">请选择显示状态</option>
	           	<option value="1" selected>显示</option>
	           	<option value="0">不显示</option>
           	<#elseif reqConds.showStatus=='0'>
	           	<option value="">请选择显示状态</option>
	           	<option value="1">显示</option>
	           	<option value="0" selected>不显示</option>
           	</#if>
        </select>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="courseCode" id="courseCode" placeholder="请输入课程代码" value="${reqConds.courseCode}"/>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="name" id="name" placeholder="请输入课程名称" value="${reqConds.name}"/>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="courseId" id="courseId" placeholder="请输入课程ID" value="${reqConds.courseId}"/>
    </div>
    <div class="form-group">
        <select name="isExpired" id="isExpired" class="form-control">
            <#if reqConds.isExpired == ''>
	            <option value="" >请选择是否过期</option>
	           	<option value="1" >已过期</option>
	           	<option value="0" selected>未过期</option>
           	<#elseif reqConds.isExpired=='1'>
	           	<option value="">请选择显示状态</option>
	           	<option value="1" selected>已过期</option>
	           	<option value="0">未过期</option>
           	<#elseif reqConds.isExpired=='0'>
	           	<option value="">请选择显示状态</option>
	           	<option value="1">已过期</option>
	           	<option value="0" selected>未过期</option>
           	</#if>
        </select>
    </div>
    <a class="btn btn-primary" style="border: 0px; width: 90px;" id= "searchLesson">搜索</a>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addProduct('${category.id}')" >挂载课程</a>
</form>
<#if reqConds.isExpired=='1'>
	<span >共添加<font style="color: red">${hasAttachCounts }</font>门已过期课程</span>
<#else>	
	<span >共添加<font style="color: red">${hasAttachCounts }</font>门当期课程</span>
</#if>


<table class="table table-striped table-bordered table-hover">
    <thead>
 <tr>
        <th class="center" style="width: 50px;">挂载状态</th>
        <th >课程ID</th>
        <th>课程代码</th>
        <th style="width: 120px;">课程名称</th>
        <th style="width: 150px;">开课与结课时间</th>
        <th>性质</th>
        <th>简介</th>
        <th style="width: 50px;">课程学分</th>
        <th style="width: 50px;">考核方式</th>
       <!--<th>考试安排</th>-->
        <th style="width: 50px;">报名人数</th>
        <th style="width: 50px;">是否显示</th>
        <th>原价</th>
        <th>优惠价</th>
        <th>封面</th>
        <th>所属地域</th>
        <th style="width:100px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list subjectList.result as subject>
        <tr>
        	<#if category.id==subject.productCategoryId>
        		<td>已挂载</td>
        	<#else>	
        		<td><input name="productId" type="checkbox" value="${subject.id}" onclick="clickBox(this)" /></td>
        	</#if>
        	<td>${subject.id}</td>
            <td>${subject.courseCode}</td>
            <td>${subject.name}</td>
            <td>${subject.beginApplyTime}  ${subject.endApplyTime}</td>
            <td>${subject.majorCourseInfo.courseType}</td>
            <td>${subject.briefInfo}</td>
            <td>${subject.majorCourseInfo.credit}</td>
            <td>${subject.majorCourseInfo.mode}</td>
            <!--<td>${subject.majorCourseInfo.examDate}</td>-->
            <td>${subject.currApplyCount}</td>
            <td>
                <#if subject.showStatus==1>显示<#else><span style="color: red;">不显示</span></#if>
            </td>
            <td>￥${subject.originalAmount}</td>
            <td>￥${subject.payAmount}</td>
            <td>
            	<#if subject.imageUrl!'暂无图片'><img src="${subject.imageUrl}" style="width: 50px; height: 50px;" ></#if>
            </td>
            <td>
            	<#list regionList as regionEntity>
          			 <#if subject.module==regionEntity.module>${regionEntity.moduleName}</#if>
         		</#list>
            </td>
            <td>
		        	<a style="padding-left: 5px;cursor: pointer;" href="/productItem/list?productId=${subject.id}&isExpired=${reqConds.isExpired}">
		        			<#if reqConds.isExpired=='1'><font style="color: red">内容预览</font><#else>	内容管理</#if></a>
		            <a style="padding-left: 5px;cursor: pointer;" onclick="delCourse(${reqConds.catId},${subject.id},'${subject.name}')">解挂</a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<#if subjectList?? && (subjectList?size > 0) >
	<@page totalCount="${subjectList.totalCount}"
	pageNo="${subjectList.pageNo}"
	totalPage="${subjectList.totalPage}" url=""> </@page>
</#if>


<script>
	var courseIds = new HashMap();
	function allClickBox(cbox){
	   var acbox = document.getElementsByName("productId");
	   if(cbox.checked){
	     for(x in acbox){
			acbox[x].checked=true;
			courseIds.put(acbox[x].value, acbox[x].value);
		 }
	   }else{
	     for(x in acbox){
			acbox[x].checked=false;
			courseIds.remove(acbox[x].value);
		 }
	   }
	}
	function clickBox(cbox){
	   if(cbox.checked)
	     courseIds.put(cbox.value, cbox.value);
	   else
	     courseIds.remove(cbox.value);
	}

    /**
     * 添加栏目
     */
    function addProduct(catId){
        if(courseIds.size()==0){
        	alert("请先选择需要添加的课程");
        	return;
        }
    	var json_data = JSON.stringify(courseIds.values());
    	$.post("/productCategory/add_course", 
                {
                	catId:catId,
                	courseIdList:json_data
                },
                function(data){
					data=eval('(' + data + ')');
                    if(data.retCode==0){
                        alert_br(".</br>"+data.retDesc);
                    } else {
                        alert("添加失败.");
                    }
                    location.reload();
                }
            );
    }

	function getOs()   
	{    
		if(navigator.userAgent.indexOf("MSIE")>0) {   
			 return "IE"; //InternetExplor  
		}   
		else if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){   
			 return "FF"; //firefox  
		}   
		else if(isSafari=navigator.userAgent.indexOf("Safari")>0) {   
			 return "SF"; //Safari  
		}    
		else if(isCamino=navigator.userAgent.indexOf("Camino")>0){   
			 return "C"; //Camino  
		}   
		else if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){   
			 return "G"; //Gecko  
		}   
		else if(isMozilla=navigator.userAgent.indexOf("Opera")>=0){  
			 return "O"; //opera  
		}else{  
			return 'Other';  
		}  
    } 

	function alert_br(msg){  
        var os=getOs();
		var replaceStr = "</br>";
        if(os=='FF' || os=='SF'){  //FireFox、谷歌浏览器用这个  
            alert(msg.split(replaceStr).join('\n'));  
        }else{   //IE系列用这个  
            alert(msg.split(replaceStr).join('\r\n'));  
        }  
	} 

    /**
     * 删除栏目
     * @param catId
     */
    function delCourse(catId,courseId,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/productCategory/delete_course", 
                {
                	catId:catId,
                	courseId:courseId
                },
                function(data){
                	var json_data = JSON.parse(data); 
                    if(json_data.retCode===0){
                       alert("删除成功");
                    } else {
                         alert("删除失败:"+" "+json_data.retDesc);
                    }
                    location.reload();
                }
            );
        }
    }
    function toPage(page) { 
    	var catIdDoc = document.getElementById("catId");
    	var showStatusDoc = document.getElementById("showStatus");
    	var courseCodeDoc = document.getElementById("courseCode");
    	var nameDoc = document.getElementById("name");
    	var courseIdDoc=document.getElementById("courseId");
    	var isExpiredDoc = document.getElementById("isExpired");
    	
		var url = window.location.pathname + "?catId=" + catIdDoc.value + 
					"&showStatus=" + showStatusDoc.value + 
					"&courseCode="+  base64encode(utf16to8(encodeURIComponent(courseCodeDoc.value ))) + 
					"&name=" + base64encode(utf16to8(encodeURIComponent(nameDoc.value))) + 
					"&courseId=" + courseIdDoc.value +
					"&isExpired=" + isExpiredDoc.value + 
					"&page=" + page;
		self.location = url;
		return false;
	}
	
	 $("#searchLesson").click(function(){
          toPage(1) //搜索
        });
	
</script>

</@htmlBody>
