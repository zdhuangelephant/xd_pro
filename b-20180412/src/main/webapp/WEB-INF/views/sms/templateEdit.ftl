<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/smsTemplate/doEdit">

<div class="form-group">
        <input type="hidden" name="id" value="${templateModel.id}" />
        
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 短信模板内容 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${templateModel.messageContent}" name="messageContent" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${templateModel.description}" id="form-field-2" name="description" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 状态</label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="status">
                 <option value="0" selected>停用</option>
                 <option value="1">启用</option>
                
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
