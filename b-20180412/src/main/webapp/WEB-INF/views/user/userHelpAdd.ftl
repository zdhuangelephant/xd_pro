<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/userHelp/doAdd">

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 标题</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="title" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 内容 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="content" placeholder="" class="col-xs-10 col-sm-5" />
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
