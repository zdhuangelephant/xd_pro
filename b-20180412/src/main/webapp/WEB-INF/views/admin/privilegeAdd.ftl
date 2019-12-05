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
                添加
            </small>
        </h1>
    </div>



    <form id="addForm" method="post" class="form-horizontal" role="form" action="/privilege/addPrivilege">


        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 上级权限 </label>
            <div class="col-sm-9">
                <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="parentId">
                    <option value="0">作为一级权限</option>
                    ${tree}
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 权限名称 </label>
            <div class="col-sm-9">
                <input type="text" datatype="s" sucmsg=" "  name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> url </label>
            <div class="col-sm-9">
                <input type="text" datatype="s" sucmsg=" " id="form-field-2" name="url" placeholder="" class="col-xs-10 col-sm-5" />
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 附加参数 </label>
            <div class="col-sm-9">
                <input type="text" datatype="s" sucmsg=" " name="params" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 是否显示 </label>
            <div class="col-sm-9">
                <div class="control-group">
                    <div class="radio">
                        <label>
                            <input name="display" type="radio" value="1" checked class="ace">
                            <span class="lbl"> 是</span>
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input name="display" type="radio" value="0" class="ace">
                            <span class="lbl"> 否</span>
                        </label>
                    </div>
                </div>


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

