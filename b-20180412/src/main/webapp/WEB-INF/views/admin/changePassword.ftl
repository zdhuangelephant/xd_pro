<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">
<script type="text/javascript" src="${JS_PATH}validform/validform.js"></script>
</@htmlHead>
<style>
.profile-info-name {
    width: 60%;
    border-top: none!important;
}
</style>

<@htmlBody bodyclass="page-content">

<div class="page-header">
    <h1>
        密码
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            修改
                    <a style="float:right" href="javascript:window.history.go(-1);">
        <i class="ace-icon fa fa-backward">返回</i>
        </a>
        </small>
    </h1>
</div>

<form id="addForm" method="post" class="form-horizontal" role="form" action="/admin/changePassword">

  <div class="profile-info-row">
        <label class="profile-info-name"> 老密码 </label>

        <div class="col-sm-3">
            <input name="oldPassword" datatype="*6-15" nullmsg="老密码不能为空" sucmsg=" "
                   type="text" class="dfinput" style="width: 200px;" />
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>
    
    <div class="profile-info-row">
        <label class="profile-info-name"> 新密码 </label>

        <div class="col-sm-3">
            <input name="newPassword" type="text" nullmsg="请填写密码" style="width: 200px;" sucmsg=" "
                   datatype="*6-15" errormsg="密码范围在6~15位之间！"/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="profile-info-row">
        <label class="profile-info-name"> 重复密码 </label>

        <div class="col-sm-3">
            <input name="repeatNewPassword" type="text" sucmsg=" "
                   datatype="*" recheck="newPassword" nullmsg="请确认密码"
                   errormsg="您两次输入的账号密码不一致！" style="width: 200px;" />
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

  

    <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9" style="padding-left: 0px;">
            <button class="btn btn-info" type="submit">
                <i class="ace-icon fa fa-check bigger-110"></i>
                提交
            </button>
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

