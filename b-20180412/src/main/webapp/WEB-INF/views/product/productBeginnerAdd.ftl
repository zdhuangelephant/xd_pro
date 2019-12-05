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


<form id="addForm" method="post" class="form-horizontal" role="form" action="/product/doAddBeginner">
	<!-- hidden input-->
    <input type="hidden" name="type" value="${type}" />
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 所属地域 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" value="${regionModule.moduleName}" disabled="disabled" sucmsg="haha"   placeholder="" class="col-xs-10 col-sm-5" />
            <input type="hidden" datatype="s" value="${regionModule.module}"  sucmsg="haha" name="module" id="module" placeholder="" class="col-xs-10 col-sm-5" />
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
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="briefInfo" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

	<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3">开始时间</label>
        <div class="col-sm-9" >
            <input id="begin" required="required" type="text" datatype="s" sucmsg="haha" name="beginApplyTime" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3">结束时间</label>
        <div class="col-sm-9" >
            <input id="end" required="required" type="text" datatype="s" sucmsg="haha" name="endApplyTime" placeholder="" class="col-xs-10 col-sm-5" />
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
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });

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
</script>
</#if>

<@fileUpload></@fileUpload>
</@htmlBody>
