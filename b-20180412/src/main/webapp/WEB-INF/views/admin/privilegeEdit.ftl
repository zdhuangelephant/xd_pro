<#include "/common/layout.ftl" />

<@htmlHead title="登陆页">
<script type="text/javascript" src="${JS_PATH}validform/validform.js"></script>
</@htmlHead>

<@htmlBody bodyclass="page-content">

<div class="formbody">

    <div class="page-header">
        <h1>
            权限
            <small>
                <i class="ace-icon fa fa-angle-double-right"></i>
                编辑
            </small>
        </h1>
    </div>


    <form id="addForm" method="post" class="form-horizontal" role="form" action="/privilege/editPrivilege">

        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 上级权限 </label>
            <div class="col-sm-9">
                <input type="hidden" value="${privilege.id}" name="id"/>
                <select datatype="n" sucmsg=" " class="select2" name="parentId">
                    <option value="0">作为一级权限</option>${tree}</select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 权限名称 </label>
            <div class="col-sm-9">
                <input datatype="s" sucmsg=" " name="name" type="text" value="${privilege.name}"
                       class="dfinput"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> url </label>
            <div class="col-sm-9">
                <input datatype="*" sucmsg=" " name="url" value="${privilege.url}" type="text"
                       class="dfinput"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 附加参数 </label>
            <div class="col-sm-9">
                <input datatype="s" ignore="ignore" sucmsg=" " name="params"
                       value="${privilege.params}" type="text" class="dfinput" value=""/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否显示 </label>
            <div class="col-sm-9">
                <cite><input name="display" type="radio" value="1"
                             <#if privilege.display==1>checked="checked"</#if>  />是&nbsp;&nbsp;&nbsp;&nbsp;<input
                        name="display" <#if privilege.display==0>checked="checked"</#if>
                        type="radio" value="0"/>否</cite>
            </div>
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



</div>
</@htmlBody>

