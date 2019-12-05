<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

</style>
<form name="form1" id="editForm" method="post" class="form-horizontal" role="form" action="/finalExam/doEdit">

 	<input type="hidden" name="id" value="${finalExam.id}" />
 	<input type="hidden" name="sort" value="${finalExam.sort}" />
 	<input type="hidden" name="courseId" value="${finalExam.courseId}" />
 	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 试卷 </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg="haha" id="examName" value="${finalExam.examName}" name="examName"
				id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 题目个数 </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg="haha" id="questionNums" value="${finalExam.questionNums}" name="questionNums"
				id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
		</div>
	</div>
</form>

<@fileUpload></@fileUpload>
</@htmlBody>
