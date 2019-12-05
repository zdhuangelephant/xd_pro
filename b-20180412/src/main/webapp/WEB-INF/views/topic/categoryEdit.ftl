<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/forumCategory/doEdit">

<div class="form-group">
        <input type="hidden" name="id" value="${forumCategory.id}" />
        
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 话题标题 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${forumCategory.title}" name="title" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 话题描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${forumCategory.outline}" id="form-field-2" name="outline" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 话题内容 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${forumCategory.content}" id="form-field-2" name="content" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
         <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 图片URL </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${forumCategory.images}" id="form-field-2" name="images" placeholder="" class="col-xs-10 col-sm-5" />
            <a class="uploadUrl" onclick="fileUpload('images','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>
    <div class="form-group">
         <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 话题简称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${forumCategory.shortName}" id="form-field-2" name="shortName" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

</form>
<script>
    $("#editForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
<@fileUpload></@fileUpload>
</@htmlBody>
