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

<form id="quickCopyForm" method="post" class="form-horizontal" role="form" action="/product/doQuickCopy">
    <input type="hidden" name="id" value="${productModel.id}" />
    <input type="hidden" name="type" value="${type}" />
    
    
    <div class="form-group">
    	<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 所属地域 </label>
     <div class="col-sm-9">
	        <select datatype="n" sucmsg=" " id="module" class="col-xs-10 col-sm-5" name="module">
	           <option value="">选择所属地域</option>
	           <#list regionList as module>
	            <option value="${module.module}" <#if productModel.module==module.module>selected</#if> >${module.moduleName}</option>
	           </#list>
	        </select>
     </div>
	</div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 课程代码 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="courseCode" readonly="readonly" value="${productModel.courseCode}" name="courseCode" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5"  />
<!--              <button onclick="getCourseName()">检测</button>  -->
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
            <input value="${findCourseById.name}" type="text" datatype="s" sucmsg="haha" id="name" name="name"  placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 封面 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="pic" name="imageUrl" value="${productModel.imageUrl}" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('pic','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 简介 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="briefInfo" value="${productModel.briefInfo}" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>


    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 原价 </label>
        <div class="col-sm-9">
            <input type="number" datatype="s" sucmsg="haha" name="originalAmount" value="${productModel.originalAmount}" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 优惠价 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="payAmount" value="${productModel.payAmount}" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3">开始时间</label>
        <div class="col-sm-9" >
            <input id="begin" type="text" datatype="s" sucmsg="haha" value="${productModel.beginApplyTime}" name="beginApplyTime" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3">结束时间</label>
        <div class="col-sm-9" >
            <input id="end" type="text" datatype="s" sucmsg="haha" value="${productModel.endApplyTime}" name="endApplyTime" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否显示 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio1" value="1"> 是
            </label>
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio2" value="0" checked> 否
            </label>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 关联资源 </label>
        <div class="col-sm-9">
            <select class="col-xs-10 col-sm-5" name="resourceSubject">
                <#list subjectList as subject>
                    <option value="${subject.id}" <#if productModel.resourceSubject==subject.id>selected</#if> >${subject.name}</option>
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
    $("#quickCopyForm").Validform({
        tiptype: 2,
        postonce: true
    });
    
    
    
    
	       
</script>
</#if>

<@fileUpload></@fileUpload>
</@htmlBody>
