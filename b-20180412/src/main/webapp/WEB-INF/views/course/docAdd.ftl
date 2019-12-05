<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/courseDoc/doAdd">
    <input type="hidden" name="courseId" value="${courseId}" />
    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-1"> 上级目录 </label>
        <div class="col-xs-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-xs-5" name="chapterId">
                ${selectTree}
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha"  name="name" id="form-field-2" placeholder="" class="col-xs-10 col-xs-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 描述 </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="detail" placeholder="" class="col-xs-10 col-xs-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 下载地址 </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha" id="docFile" name="fileUrl" placeholder="" class="col-xs-10 col-xs-5" />
            <a onclick="fileUpload('docFile','picture',50,'pdf')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> URL </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="url" placeholder="" class="col-xs-10 col-xs-5" />
        </div>
    </div>



</form>

<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
    <@fileUpload></@fileUpload>
</@htmlBody>
