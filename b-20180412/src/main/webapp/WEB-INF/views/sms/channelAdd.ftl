<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/smsChannel/doAdd">

<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 栏目 </label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="merchantId">
               <#list selectMerchant as merchant>
                <option value="${merchant.id}" <#if catId == merchant.id>selected</#if> >${merchant.name}</option>
            </#list>
            </select>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 通道名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 用户名 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="userName" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 密钥</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="secretKey" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 通道URL </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="channelURL" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 通道端口号</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="port" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
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
