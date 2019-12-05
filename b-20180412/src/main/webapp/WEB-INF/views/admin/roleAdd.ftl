<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">
<script type="text/javascript" src="${JS_PATH}validform/validform.js"></script>
</@htmlHead>
<@htmlBody bodyclass="page-content">

<div class="page-header">
    <h1>
        角色
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            修改
        </small>
    </h1>
</div>


<form id="addForm" method="post" class="form-horizontal" role="form" action="/role/addRole">

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 角色名称 </label>

        <div class="col-sm-3">
            <input name="roleName" type="text"  datatype="s2-16" sucmsg=" " nullmsg="请填写角色名称"/>
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 角色描述 </label>

        <div class="col-sm-3">
            <input name="roleDescription" type="text" />
        </div>
        <div class="col-sm-6" style="color: red;"></div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否有效 </label>

        <div class="col-sm-3">
            <cite><input name="disabled" type="radio" value="1" checked="checked"/>是&nbsp;&nbsp;&nbsp;&nbsp;<input
                    name="disabled" type="radio" value="0"/>否</cite>
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

