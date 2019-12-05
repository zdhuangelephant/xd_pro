<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">
<script type="text/javascript" src="${JS_PATH}validform/validform.js"></script>
</@htmlHead>

<@htmlBody bodyclass="page-content">

<div class="page-header">
    <h1>
        管理员
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            修改
        </small>
    </h1>
</div>

<form id="addForm" method="post" class="form-horizontal" role="form" action="/admin/editAdmin">
    <input type="hidden" name="adminId" value="${admin.id}"/>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 用户名 </label>

        <div class="col-sm-3">
            <input name="userName" type="text" value="${admin.userName}" class="dfinput"
                   datatype="s5-16" sucmsg=" " nullmsg="请填写用户名"/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 密码 </label>

        <div class="col-sm-3">
            <input name="password" type="password" value="" ignore="ignore" class="dfinput" datatype="*8-16"
                   sucmsg=" " nullmsg=" "/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 重复密码 </label>

        <div class="col-sm-3">
            <input name="repeatPassword" value="" type="password" ignore="ignore"  class="dfinput" sucmsg=" "
                   datatype="*" recheck="password" nullmsg=" "
                   errormsg="您两次输入的账号密码不一致！"/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> email </label>

        <div class="col-sm-3">
            <input name="email" type="text" value="${admin.email}" class="dfinput"
                   datatype="e" sucmsg=" " ignore="ignore" nullmsg="请输入邮箱"/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 手机号 </label>

        <div class="col-sm-3">
            <input name="telphone" type="text" class="dfinput" value="${admin.telphone}"
                   datatype="m" sucmsg=" " ignore="ignore" nullmsg="请输入手机号"/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 真实姓名 </label>

        <div class="col-sm-3">
            <input name="realName" type="text" class="dfinput" value="${admin.realName}"
                   datatype="s2-8" sucmsg=" "  nullmsg="请输入真实姓名"/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

	<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 学习机构 </label>

        <div class="col-sm-3">
            <input name="merchant" type="text" class="dfinput" value="${admin.merchant}"
                    sucmsg=" "  nullmsg="请输入学习机构 "/>
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

