<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/courseDoc/doEdit">


    <div class="form-group">
        <input type="hidden" name="id" value="${courseDoc.id}" />
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 上级目录 </label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="chapterId">
                <option value="0">作为一级目录</option>
                ${selectTree}
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${courseDoc.name}" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${courseDoc.detail}" id="form-field-2" name="detail" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 下载地址 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${courseDoc.fileUrl}" id="docFile" name="fileUrl" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('docFile','picture',50,'pdf')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> URL </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${courseDoc.url}" id="form-field-2" name="url" placeholder="" class="col-xs-10 col-sm-5" />
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
