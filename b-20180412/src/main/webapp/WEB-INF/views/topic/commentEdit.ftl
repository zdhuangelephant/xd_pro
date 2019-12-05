<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/forumComment/doEdit">
<div class="form-group">
        <input type="hidden" name="id" value="${forumCommentModel.id}" />
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否关闭</label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="status">
                 <option value="0" selected>关闭</option>
                 <option value="1">正常</option>
                
            </select>
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
