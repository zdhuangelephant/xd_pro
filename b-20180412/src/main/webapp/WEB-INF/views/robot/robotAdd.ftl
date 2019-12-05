<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form"
	action="/robot/robotDoAdd">
	
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-2"> 昵称 </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg=" "
				 id="form-field-2" name="nickName"
				placeholder="昵称" class="col-xs-10 col-sm-5" />
		</div>
	</div>
	
	
    <div class="form-group">
     <select name="module" class="form-control" style="margin-left:12px;width:257px">
         <option value="2" >默认地域</option>
         <#list regionList as regionEntity>
           <option value=${regionEntity.module}>${regionEntity.moduleName}</option>
         </#list>
     </select>
    </div> 
	
	
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-3"> 配图</label>
		<div class="col-sm-9">
			<img class="portrait" src="${rc.contextPath}/resources/assets/images/default_portrait.png" alt="机器人头像"> 
			<input type="hidden" datatype="s" sucmsg="haha" 
				id="pic" name="portrait" placeholder="" class="col-xs-10 col-sm-5" />
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

	$("#addForm").Validform({
		tiptype : 2,
		postonce : true
	});
</script>
<@fileUpload></@fileUpload>
</@htmlBody>
