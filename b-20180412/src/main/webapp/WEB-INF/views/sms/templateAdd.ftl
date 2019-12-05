<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/smsTemplate/doAdd">
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 栏目 </label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="typeId">
               <#list selectTemplateType as templateType>
                <option value="${templateType.id}" <#if catId == templateType.id>selected</#if> >${templateType.name}</option>
            </#list>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 短信模板内容 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="messageContent" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="description" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    

</form>

<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
</@htmlBody>
