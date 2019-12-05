<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">
<script type="text/javascript" src="${JS_PATH}validform/validform.js"></script>
</@htmlHead>
<@htmlBody bodyclass="page-content">
<style>
.profile-info-name {
    width: 60%;
    border-top: none!important;
}
</style>
<div class="page-header">
    <h1>
        个人信息
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            修改
        <a style="float:right" href="javascript:window.history.go(-1);">
        <i class="ace-icon fa fa-backward">返回</i>
        </a>
        </small>
    </h1>
</div>

<form id="addForm" method="post" class="form-horizontal" role="form" action="/admin/editInfo">
    <div class="profile-info-row">
        <label class="profile-info-name" for="form-field-1"> email </label>

        <div class="col-sm-3">
            <input datatype="e" ignore="ignore" style="width: 200px;" name="email" sucmsg=" "
                   value="<#if editInfo??>${admin.email}<#else >${editInfo.email}</#if>"
                   type="text" />
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="profile-info-row">
        <label class="profile-info-name" for="form-field-1"> 手机号 </label>

        <div class="col-sm-3">
            <input datatype="m" ignore="ignore" style="width: 200px;" name="telphone" sucmsg=" "
                   value="<#if editInfo??>${admin.telphone}<#else >${editInfo.telphone}</#if>"
                   type="text"  value=""/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="profile-info-row">
        <label class="profile-info-name" for="form-field-1"> 真实姓名 </label>

        <div class="col-sm-3">
            <input datatype="*2-3" errormsg="请填写真实姓名" style="width: 200px;" ignore="ignore" name="realName"
                   sucmsg=" "
                   value="<#if editInfo??>${admin.realName}<#else >${editInfo.realName}</#if>"
                   type="text"  value=""/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>
    <div class="profile-info-row">
        <label class="profile-info-name" for="form-field-1"> 学习机构 </label>

        <div class="col-sm-3">
            <input errormsg="请填写学习机构" style="width: 200px;" ignore="ignore" name="merchant"
                   sucmsg=" "
                   value="<#if editInfo??>${admin.merchant}<#else >${editInfo.merchant}</#if>"
                   type="text"  value=""/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
            <button class="btn btn-info" type="submit">
                <i class="ace-icon fa fa-check bigger-110"></i>
                提交
            </button>

            &nbsp; &nbsp; &nbsp;
            <button class="btn" type="reset">
                <i class="ace-icon fa fa-undo bigger-110"></i>
                重置
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

