<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<#if errors??>
${errors}
<#else>

<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/css/jquery-ui.css" /> 
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/css/laydateplus.css" /> 
<script src="${rc.contextPath}/resources/js/laydate/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript" src="${rc.contextPath}/resources/js/laydate/jquery-ui.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/resources/js/laydate/jquery-ui-slide.min.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/resources/js/laydate/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="${rc.contextPath}/resources/js/laydate/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${rc.contextPath}/resources/js/laydate/jquery-ui-timepicker-zh-CN.js"></script>

<form id="addForm" method="post" class="form-horizontal" role="form" action="/product/doAdd">
	<!--hidden type for input-->
    <input type="hidden" name="type" value="${type}" />
    
    <div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-3">所属地域 </label>
	    <div class="col-sm-9">
	        <select class="col-xs-10 col-sm-5" id="module" name="module" >
	        <option value="">选择所属区域</option>    
	        <#list regionList as region>
	                <option value="${region.module}">${region.moduleName}</option>
	            </#list>
	        </select>
	    </div>
	</div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 课程代码 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="courseCode" name="courseCode" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5"  />
             <button onclick="getCourseName()">检测</button> 
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 课程考期 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="examDate" name="examDate" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">  
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 课程名称 </label>
        <div class="col-sm-9">
        	<!--disabled ="true"-->
            <input type="text" datatype="s" sucmsg="haha" id="courseName" name="name" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 封面 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="pic" name="imageUrl" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('pic','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 简介 </label>
        <div class="col-sm-9">
            <input type="text" oninput="if(value.length>100)value=value.slice(0,100)" datatype="s" sucmsg="haha" id="form-field-3" name="briefInfo" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>


    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 原价 </label>
        <div class="col-sm-9">
            <input type="number" datatype="s" sucmsg="haha" name="originalAmount" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 优惠价 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="payAmount" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3">开始时间</label>
        <div class="col-sm-9" >
            <input id="begin" type="text" datatype="s" sucmsg="haha" name="beginApplyTime" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3">结束时间</label>
        <div class="col-sm-9" >
            <input id="end" type="text" datatype="s" sucmsg="haha" name="endApplyTime" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <#-- <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否显示 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio1" value="1"> 是
            </label>
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio2" value="0" checked> 否
            </label>
        </div>
    </div> -->

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 关联资源 </label>
        <div class="col-sm-9">
            <select class="col-xs-10 col-sm-5" name="resourceSubject">
                <#list subjectList as subject>
                    <option value="${subject.id}">${subject.name}</option>
                </#list>
            </select>
        </div>
    </div>

</form>

<script>
	$(function(){
		 $('#begin').datetimepicker({
			   showSecond: true,
			   timeFormat: 'hh:mm:ss'
		 });
		 $('#end').datetimepicker({
			   showSecond: true,
			   timeFormat: 'hh:mm:ss'
		 });
		 
	});
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
    function getCourseName(){
     	var courseCode=$("#courseCode").val();
     	var region=$("#module").val();//地域
     	if(region==null||region==''){
     		alert("请先选择地域");
     		return flase;
     	}
     	if(courseCode==null||courseCode==''){
     		alert("请输入课程代码");
     		return flase;
     	}
	    $.post("/product/getCourseName", { courseCode:courseCode,region:region},
	            function(data){
	           		var u=JSON.parse(data);
	           		console.log(u);
	           		if(u.retCode !='0'){
	           			alert("未检测到结果");
	           		}else{
	                	$("#courseName").val(u.courseName);
	           		}
	            });
	    }
	       
</script>
</#if>

<@fileUpload></@fileUpload>
</@htmlBody>
