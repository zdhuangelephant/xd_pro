<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/shortMessage/doAddShort">

<div class="form-group">
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 短信发送服务商</label>
        <div class="col-sm-9">
        	<select name="merchantId" class="col-xs-10 col-sm-5">
	           	<#list merchantLists as merchant>
                	<option value="${merchant.id}" >${merchant.name}</option>
            	</#list>
        	</select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 短信模板 </label>
        <div class="col-sm-9">
        	<select name="templateId" class="col-xs-10 col-sm-5">
	           	<#list templateList as template>
                	<option value="${template.id}" >${template.messageContent}</option>
            	</#list>
        	</select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 发送手机号 <span style="color:red">(eg:[12345678901,12345678901])</span> </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="telephone" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 模板变量参数 <span style="color:red">(eg:{'checked':'123456'})</span> </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="variables" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
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
