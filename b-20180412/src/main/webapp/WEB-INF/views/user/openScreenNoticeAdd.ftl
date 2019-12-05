<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/userNotice/doOpenScreenNoticeAdd">

<div class="form-group">
    <label class="col-sm-3 col-xs-3 control-label no-padding-right"  for="form-field-3"> 标题 </label>
    <div class="col-sm-9 col-xs-9" style="clear:both">
        <textarea  name="title" class="form-control" style="width: 100%;overflow-y:visible;height: 70px;"></textarea>
    </div>
</div>
    
    
    <div class="form-group">
	    <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 链接设置 </label>
	    <div class="col-sm-9 col-xs-9" style="clear:both">
	        <textarea  name="jumpUrl" class="form-control" style="width: 100%;overflow-y:visible;height: 70px;"></textarea>
	    </div>
	</div>
    
	<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="image" id="image" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('image','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 显示频次 </label>
        <div class="col-sm-9">
        
            <select name="type" class="form-control">
            	<option value="">--显示频次--</option>
	             <option value="0">每日首次</option>
	             <option value="1">每次启动</option>
	             <option value="2">一次性</option>
	        </select>
        </select>
        </div>
    </div>
    
    <!--<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 所属模块 </label>
        <div class="col-sm-9">
            <select name="module" id="module" class="form-control">
            <option value="">--所属模块--</option>
            <#list moduleList as module>
                <option value="${module.id}">${module.name}</option>
            </#list>
        </select>
        </div>
    </div>-->
    
    
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否显示</label>
        <div class="col-sm-9">
            <select name="isVisible" class="form-control">
	        	<option value="">--请选择--</option>
	            <option value="0" selected>不显示</option>
	            <option value="1" selected>显示</option>
        </select>
        </div>
    </div>
</div>
</form>

<script>


 $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
  
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
<@fileUpload></@fileUpload>
</@htmlBody>
