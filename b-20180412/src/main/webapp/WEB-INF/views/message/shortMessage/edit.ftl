<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/shortMessage/doEditShort">

<div class="form-group">
    <input type="hidden" name="id" value="${shortMessage.id}">
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 短信发送服务商</label>
        <div class="col-sm-9">
        	<select name="merchantId" class="col-xs-10 col-sm-5">
	           	<#list merchantLists as merchant>
                	<option value="${merchant.id}" <#if shortMessage.merchantId == merchant.id>selected</#if> >${merchant.name}</option>
            	</#list>
        	</select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 短信模板 </label>
        <div class="col-sm-9">
        	<select name="templateId" class="col-xs-10 col-sm-5">
	           	<#list templateList as template>
                	<option value="${template.id}" <#if shortMessage.templateId == template.id>selected</#if> >${template.messageContent}</option>
            	</#list>
        	</select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 发送手机号 <span style="color:red">(eg:[12345678901,12345678901])</span> </label>
        <div class="col-sm-9">
            <input type="text" value="${shortMessage.telephone}" datatype="s" sucmsg="haha" name="telephone" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 模板变量参数 <span style="color:red">(eg:{'checked':'123456'})</span> </label>
        <div class="col-sm-9">
            <input type="text" value="${shortMessage.variables}" datatype="s" sucmsg="haha" name="variables" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
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
