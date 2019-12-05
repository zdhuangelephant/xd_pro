<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/smsChannel/doEdit">

<div class="form-group">
        <input type="hidden" name="id" value="${channelModel.id}" />
        
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 通道名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${channelModel.name}" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 用户名 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${channelModel.userName}" id="form-field-2" name="userName" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 密钥 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${channelModel.secretKey}" id="form-field-2" name="secretKey" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
         <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 通道URL </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${channelModel.channelURL}" id="form-field-2" name="channelURL" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
         <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 通道端口号 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${channelModel.port}" id="form-field-2" name="port" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
     <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 优先级</label>
        <div class="col-sm-9">
            <input type="number" datatype="s" value="${channelModel.priority}" sucmsg="haha" name="priority" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否关闭 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="status" id="inlineRadio1" value="1" <#if channelModel.status==1>checked</#if> > 否
            </label>
            <label class="radio-inline">
                <input type="radio" name="status" id="inlineRadio2" value="0" <#if channelModel.status==0>checked</#if> > 是
            </label>
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
