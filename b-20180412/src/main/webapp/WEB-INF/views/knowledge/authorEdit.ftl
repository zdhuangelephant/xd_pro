<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<style>
.portrait {
	width:60px;
	height:60px;
}
</style>
<form id="editForm" method="post" class="form-horizontal" role="form" action="/knowledge/author/doEdit">

 	<input type="hidden" name="id" value="${author.id}" />
 	<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 作者姓名 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${author.name}" id="name" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5"  />
        </div>
    </div>
	 <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 作者头像 </label>
        <div class="col-sm-9">
        	<img class="portrait" src="${author.portrait}" alt="作者头像">
            <input type="hidden" datatype="s" sucmsg="haha" value="${author.portrait}" id="pic" name="portrait" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="uploadProtrait()">上传</a>
        </div>
        <br/>
        <div class="col-sm-9" style="color:red">*建议上传200*200像素的jpg、png、gif图片</div>
    </div>
  

	<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 作者描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${author.info}" id="form-field-3" name="info" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>


</form>
<script>
    function uploadProtrait() {
		fileUploadCallBack(function(url) {
					$(".portrait").attr("src",url);
					$("#pic").val(url);
				},'picture',3,'png,jpg,gif');
	}
</script>
<@fileUpload></@fileUpload>
</@htmlBody>
