<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/questionResource/doAdd">

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="detail" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

	<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 关联课程 </label>
        <select id="courseId" name="courseId" class="form-control" style="width:300px; margin-left: 13px;">
            <option value="0">请选择课程</option>
            <#list allCourses as resource>
                <!-- <option value="${resource.id}" <#if courseId == resource.id>selected</#if> >${resource.name}</option> -->
                <option value="${resource.id}">${resource.name}</option>
            </#list>
        </select>
    </div>
</form>

<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
</@htmlBody>
