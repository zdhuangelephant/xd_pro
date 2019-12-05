<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form"
	action="/robot/robotDoEdit">

	<div class="form-group">
		<input type="hidden" name="id" value="${robot.id}" />
	</div>

	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-2">  昵称  </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg=" "
				value="${robot.nickName}" id="form-field-2" name="nickName"
				placeholder=" 昵称 " class="col-xs-10 col-sm-5" />
		</div>
	</div>

	<div class="form-group">
	     <select name="moduleInfo" class="form-control" style="margin-left:12px;width:300px;" disabled="disabled"> 
	         <option value="2" >默认地域</option>
	         <#list regionList as regionEntity>
	           <option value=${regionEntity.module} <#if robot.moduleInfo==regionEntity.module>selected</#if>>${regionEntity.moduleName}</option>
	         </#list>
	     </select>
    </div> 
	

	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-3"> 配图</label>
		<div class="col-sm-9">
			<img class="portrait" src="${robot.portrait}" > 
			<input type="hidden" datatype="s" sucmsg="haha" 
				id="pic" name="portrait" placeholder="" value="${robot.portrait}" class="col-xs-10 col-sm-5" />
			<a onclick="uploadProtrait()">上传</a>
		</div>
		<br/>
		<div class="col-sm-9" style="color: red">*建议上传831*288像素的jpg、png、gif图片</div>
	</div>
	
</form>
<script>
	$("#editForm").Validform({
		tiptype : 2,
		postonce : true
	});
	   function uploadProtrait() {
			fileUploadCallBack(function(url) {
						$(".portrait").attr("src",url);
						$("#pic").val(url);
					},'picture',3,'png,jpg,gif');
		}
</script>
<@fileUpload></@fileUpload>
</@htmlBody>
