<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content"> <#if errors??> ${errors} <#else>


<form name="form1" id="addForm" method="post" class="form-horizontal" role="form"
	action="/finalExam/doAdd">
	<input type="hidden" name="courseId" value="${productId}" />
	<input  type="hidden" name="sort" value="${sort}"/>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 期末测试名称 </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg="haha" id="examName" name="examName"
				id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 题目数（0以上整数）</label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg="haha" id="questionNums" name="questionNums"
				id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1">排序</label>
		<div class="col-sm-9">
			<input type="text" datatype="s" value="10000" sucmsg="haha" id="examSort" name="examSort"
				id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
		</div>
	</div>
	
</form>

</#if> <@fileUpload></@fileUpload> </@htmlBody>
