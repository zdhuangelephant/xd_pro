<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content"> <#if errors??> ${errors} <#else>
<style>
.portrait {
	width: 60px;
	height: 60px;
}
.portrait1 {
	width: 60px;
	height: 60px;
}
.portrait2 {
	width: 60px;
	height: 60px;
}
</style>

<script src="/resources/laydate/laydate.js"></script>

<form name="form1" id="addForm" method="post" class="form-horizontal" role="form"
	action="/majorCategory/doAdd">

	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 专业类别名称 </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg="haha" id="name" name="majorCategory"
				id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 备注 </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg="haha" id="name" name="remark"
				id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-3"> 配图</label>
		<div class="col-sm-9">
			<img class="portrait" src="${rc.contextPath}/resources/assets/images/default_portrait.png" alt="学员头像"> 
			<input type="hidden" datatype="s" sucmsg="haha" 
				id="pic" name="image" placeholder="" class="col-xs-10 col-sm-5" />
			<a onclick="uploadProtrait()">上传</a>
		</div>
		<br />
		<div class="col-sm-9" style="color: red">*建议上传831*288像素的jpg、png、gif图片</div>
	</div>
	
</form>
<script>
	function uploadProtrait() {
		fileUploadCallBack(function(url) {
			$(".portrait").attr("src", url);
			$("#pic").val(url);
		}, 'picture', 3, 'png,jpg,gif');
	}
</script>

</#if> <@fileUpload></@fileUpload> </@htmlBody>
