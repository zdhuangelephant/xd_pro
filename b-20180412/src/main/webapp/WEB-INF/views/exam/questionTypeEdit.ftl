<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/questionType/doEdit">

    <input type="hidden" name="id" value="${questionType.id}" />


    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${questionType.typeName}" name="typeName" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${questionType.mdesc}" id="form-field-2" name="desc" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 题目类型 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="answerType"  <#if questionType.answerType==0>checked</#if>  value="0"> 客观题
            </label>
            <label class="radio-inline">
                <input type="radio" name="answerType"  <#if questionType.answerType==1>checked</#if>  value="1"> 主观题
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
