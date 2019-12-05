<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<form name="form1" id="editForm" method="post" class="form-horizontal" role="form" action="/confInfo/doEdit">
 	<input type="hidden" name="id" value="${confInfo.id}" />
 	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 信息专项 </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg="haha" id="name" value="${confInfo.infoType}" name="infoType"
				id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> 信息编号 </label>
		<div class="col-sm-9">
			<input type="text" disabled="disabled" datatype="s" sucmsg="haha" id="name" value="${confInfo.infoCode}" name="infoCode"
				id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
			<input type="hidden" name="infoCode" value="${confInfo.infoCode}">
		</div>
	</div>

	<div class="form-group">
        <label class="col-sm-2 col-xs-2 control-label no-padding-right" for="form-field-3"> 信息项版本号 </label>
        <div class="col-sm-8 col-xs-8">
            <textarea name="infoVersion" class="form-control" style="width: 100%;overflow-y:visible;height: 160px;">${confInfo.infoVersion}</textarea>
        </div>
    </div>
	
<!--	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-1"> app模块编号 </label>
		<div class="col-sm-9">
			<input type="text" disabled="disabled" datatype="s" sucmsg="haha" id="name" value="${confInfo.module}" name="module"
				id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
			<input type="hidden" name="module" value="${confInfo.module}">
		</div>
	</div>-->

</form>

</@htmlBody>
