<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/slide/doAdd">

    <div class="form-group">
    	<label class="col-sm-3 control-label no-padding-right" for="form-field-1" style="color: red;"> 注意:如若添加格式形如:	<font style="color: black;">模块ID</font><font>&</font><font style="color: black;">模块名称</font> </label></br>
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 模块 </label>
        <div class="col-sm-9">
        	<input type="text" datatype="s" sucmsg="haha" id="coalition-customer" list="coalition" name="coalition" placeholder="" class="col-xs-10 col-sm-5" />
        	 <datalist id="coalition">
	        	 <#list keywords as module>
					<option id="${module.moduleId}">${module.moduleId}&${module.moduleName}</option>
				 </#list>
			 </datalist>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="imageUrl" id="imageUrl" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('imageUrl','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="description" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-4">连接地址</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="linkUrl" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

</form>


    <@fileUpload></@fileUpload>
</@htmlBody>
