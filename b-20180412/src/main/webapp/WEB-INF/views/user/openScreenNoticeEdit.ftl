<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/userNotice/doBlankNoteceEdit">

	<div class="form-group">
	    <input type="hidden" name="id" value="${blankNotice.id}" />
	</div>
	<div class="form-group">
	    <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 标题 </label>
	    <div class="col-sm-9 col-xs-9" style="clear:both">
	        <textarea name="title" class="form-control" style="width: 100%;overflow-y:visible;height: 70px;">${blankNotice.title}</textarea>
	    </div>
	</div>
    
    <div class="form-group">
	    <label class="col-sm-3 col-xs-3 control-label no-padding-right"  for="form-field-3"> 链接设置 </label>
	    <div class="col-sm-9 col-xs-9" style="clear:both">
	        <textarea name="jumpUrl" class="form-control" style="width: 100%;overflow-y:visible;height: 70px;">${blankNotice.jumpUrl}</textarea>
	    </div>
	</div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="image" value="${blankNotice.image}" id="image" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('image','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>
    
 <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 显示频次 </label>
        <div class="col-sm-9">
        	<select id="status" name="type" style="width:182px;" >
            	<option value="">---请选择---</option>
            	<#if blankNotice.type = 0>
            	<option value="0" selected>每日首次</option>
            	<option value="1">每次启动 </option>
            	<option value="2">一次性</option>
            	<#elseif blankNotice.type = 1>
            	<option value="0">每日首次</option>
            	<option value="1" selected>每次启动 </option>
            	<option value="2">一次性</option>
            	<#elseif blankNotice.type = 2>
            	<option value="0">每日首次</option>
            	<option value="1">每次启动 </option>
            	<option value="2" selected>一次性</option>
            	<#else>
            		<option value="-1">UNKNOW</option>
            	</#if>
            </select>
        </div>
    </div>
    
    <!-- <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 所属模块 </label>
        <div class="col-sm-9">
            <select name="module" id="module" class="form-control">
            <option value="">--所属模块--</option>
            <#list moduleList as module>
                <option value="${module.id}" <#if blankNotice.module == module.id>selected</#if>>${module.name}</option>
            </#list>
        </select>
        </div>
    </div>-->
    
    
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否显示</label>
        <div class="col-sm-9">
            <select name="isVisible" class="form-control">
	        	<#if blankNotice.isVisible = 0>
	           	<option value="">--请选择-- </option>
	           	<option value="0" selected>不显示 </option>
	           	<option value="1">显示</option>
	           	<#elseif blankNotice.isVisible = 1>
	           	<option value="">--请选择-- </option>
	           	<option value="0">不显示</option>
	           	<option value="1" selected>显示 </option>
	           	<#elseif blankNotice.isVisible = ''>
	           	<option value="" selected>--请选择-- </option>
	           	<option value="0">不显示</option>
	           	<option value="1" >显示 </option>
           		</#if>
            </select>
        </div>
    </div>
   

</form>
<script>
    $("#editForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
<@fileUpload></@fileUpload>
</@htmlBody>
