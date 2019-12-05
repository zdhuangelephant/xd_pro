<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/smsMerchant/doEdit">

<div class="form-group">
        <input type="hidden" name="id" value="${smsMerchantModel.id}" />
        
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 供应商名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${smsMerchantModel.name}" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 供应商简称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${smsMerchantModel.shortName}" id="form-field-2" name="shortName" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 地址 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${smsMerchantModel.address}" id="form-field-2" name="address" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
         <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 联系电话 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${smsMerchantModel.telephone}" id="form-field-2" name="telephone" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
         <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 联系人 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${smsMerchantModel.contactPerson}" id="form-field-2" name="contactPerson" placeholder="" class="col-xs-10 col-sm-5" />
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
