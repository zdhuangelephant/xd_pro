<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/questionResource/doEdit">


    <input type="hidden" name="id" value="${resourceModel.id}" />


    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${resourceModel.name}" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${resourceModel.detail}" id="form-field-2" name="detail" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <input type="hidden" value="${resourceModel.courseId}">
    
	<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 关联课程 </label>
        <select id="courseId" name="courseId" class="form-control" style="width:300px; margin-left: 13px;">
            <#list allCourses as resource>
                <option value="${resource.id}" <#if resourceModel.courseId == resource.id>selected</#if> >${resource.name}</option>
                <!-- <option value="${resource.id}" <#if resourceModel.courseId == resource.id>selected</#if> >${resource.name}</option> -->
            </#list>
        </select>
    </div>    

</form>
<script>
    $("#editForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>

</@htmlBody>
