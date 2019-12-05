<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/css/jquery-ui.css" /> 
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/resources/css/laydateplus.css" /> 
<script src="${rc.contextPath}/resources/js/laydate/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript" src="${rc.contextPath}/resources/js/laydate/jquery-ui.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/resources/js/laydate/jquery-ui-slide.min.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/resources/js/laydate/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="${rc.contextPath}/resources/js/laydate/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${rc.contextPath}/resources/js/laydate/jquery-ui-timepicker-zh-CN.js"></script>

<form id="editForm" method="post" class="form-horizontal" role="form" action="/product/doEdit">

 	<input type="hidden" name="id" value="${productModel.id}" />
	 <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 封面图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${productModel.imageUrl}" id="pic" name="imageUrl" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('pic','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>
  

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 简介 </label>
        <div class="col-sm-9">
            <input oninput="if(value.length>100)value=value.slice(0,100)" type="text" datatype="s" sucmsg="haha" value="${productModel.briefInfo}" id="form-field-3" name="briefInfo" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>




    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 原价 </label>
        <div class="col-sm-9">
            <input type="number" datatype="s" value="${productModel.originalAmount}" sucmsg="haha" name="originalAmount" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 优惠价 </label>
        <div class="col-sm-9">
            <input type="number" datatype="s"  value="${productModel.payAmount}" sucmsg="haha" name="payAmount" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3">开始时间</label>
        <div class="col-sm-9" >
            <input id="begin" value="${productModel.beginApplyTime}" type="text" datatype="s" sucmsg="haha" name="beginApplyTime" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3">结束时间</label>
        <div class="col-sm-9" >
            <input id="end" value="${productModel.endApplyTime}" type="text" datatype="s" sucmsg="haha" name="endApplyTime" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>


    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否显示 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio1" value="1" <#if productModel.showStatus==1>checked</#if> > 是
            </label>
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio2" value="0" <#if productModel.showStatus==0>checked</#if> > 否
            </label>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 关联资源 </label>
        <input type="text" id="resourceSubjectName" class="col-xs-10 col-sm-5" value="${productModel.resourceSubjectName}" data-provide="typeahead" autocomplete="off" placeholder="待关联课程资源名称">
        <input type="hidden" id="resourceSubject" name="resourceSubject" class="form-control" value="${productModel.resourceSubject}">
    </div>

    <div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-3">所属地域 </label>
    <div class="col-sm-9">
        <select disabled="disabled" class="col-xs-10 col-sm-5" name="module">
        <option value="2">选择所属地域</option>    
        <#list regionList as region>
                <option value="${region.module}" <#if productModel.module == region.module>selected</#if>>${region.moduleName}</option>
            </#list>
        </select>
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
    $("#editForm").Validform({
        tiptype: 2,
        postonce: true
    });
	
	$('#resourceSubjectName').typeahead(
			{
				source : function(resourceSubjectName, process) {
					// query是输入值
					$.post('/product/search_subject_name', {
						resourceSubjectName : resourceSubjectName
					}, function(data) {
						data = eval('(' + data + ')');
						if (data.retCode == 0) {
							process(data.subjectNameList);
						}
					});
				},
				updater : function(item) {
					$('#resourceSubject').val(
							item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$1"));
					return item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$2"); // 这里一定要return，否则选中不显示
				},
				items : 10, // 显示10条
				delay : 500
			// 延迟时间
			});
</script>
<@fileUpload></@fileUpload>
</@htmlBody>
