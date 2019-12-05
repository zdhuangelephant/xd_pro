<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/slide/doEdit">


    <input type="hidden" name="id" value="${slideModel.id}" />
    <div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-1" style="color: red;"> 注意:如若添加格式形如:	<font style="color: black;">模块ID</font><font>&</font><font style="color: black;">模块名称</font> </label></br>
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 模块 </label>
        <div class="col-sm-9">
        	 <input type="text" datatype="s" sucmsg="haha" value="${slideModel.moduleId}&amp;${slideModel.moduleName}" id="coalition-customer" list="coalition" name="coalition" placeholder="" class="col-xs-10 col-sm-5" />
        	 <datalist id="coalition">
	        	 <#list keywords as module>
					<option id="${module.moduleId}"  >${module.moduleId}&${module.moduleName}</option>
				 </#list>
			 </datalist>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${slideModel.imageUrl}"  name="imageUrl" id="imageUrl" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('imageUrl','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${slideModel.description}" id="form-field-3" name="description" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-4" style="float: left;">连接地址</label>
        <!--<div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${slideModel.linkUrl}"  name="linkUrl" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div> -->
        
        <div class="col-sm-9 col-xs-9" style="clear:both">
	        <textarea name="linkUrl" class="form-control" style="width: 100%;overflow-y:visible;height: 70px;">${slideModel.linkUrl}</textarea>
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
