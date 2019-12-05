<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/userNotice/doEdit">

<div class="form-group">
        <input type="hidden" name="id" value="${userNoticeModel.id}" />
        <input type="hidden" name="type" value="${userNoticeModel.type}" />
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 标题 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${userNoticeModel.title}" name="title" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 内容 </label>
        <div class="col-sm-9">
            <input id="content" type="text" datatype="s" sucmsg=" " value="${userNoticeModel.content}" id="form-field-2" name="content" placeholder="" class="col-xs-10 col-sm-5" />
        	<button style="cursor: pointer" href="javascript:;" onclick="toH5();">预览</button> 
        </div>
    </div>
   

</form>
<script>
    $("#editForm").Validform({
        tiptype: 2,
        postonce: true
    });
     var toH5 = function(){
    	window.open($("#content").val());
    }
</script>

</@htmlBody>
