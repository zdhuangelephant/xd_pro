<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/userHelp/doEdit">

<div class="form-group">
        <input type="hidden" name="id" value="${userHelpModel.id}" />
        
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 标题 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${userHelpModel.title}" name="title" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 内容 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${userHelpModel.content}" id="form-field-2" name="content" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
   

</form>
<script>
    $("#editForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>

</@htmlBody>
