<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/forumTopic/doEdit">
<div class="form-group">
        <input type="hidden" name="id" value="${forumListModel.id}" />
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否置顶 </label>
       <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="top" id="inlineRadio1" value="1"> 置顶
            </label>
            <label class="radio-inline">
                <input type="radio" name="top" id="inlineRadio2" value="0" checked> 不置顶
            </label>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否推荐 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="recommend" id="inlineRadio1" value="1"> 推荐
            </label>
            <label class="radio-inline">
                <input type="radio" name="recommend" id="inlineRadio2" value="0" checked> 不推荐
            </label>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否精华 </label>
         <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="digest" id="inlineRadio1" value="1"> 精华
            </label>
            <label class="radio-inline">
                <input type="radio" name="digest" id="inlineRadio2" value="0" checked> 普通
            </label>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否显示</label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="status" id="inlineRadio1" value="1"> 是
            </label>
            <label class="radio-inline">
                <input type="radio" name="status" id="inlineRadio2" value="0" checked> 否
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
