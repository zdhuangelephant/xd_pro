<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/productCategory/doAdd">
	<input type="hidden" name="classify" id="classify" value="${classify}" />
	
	<div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 所属地域</label>
    <div class="col-sm-9">
        <select datatype="n" sucmsg=" " id="module" class="col-xs-10 col-sm-5" name="module">
            <option value="">选择地域</option>
            <#list moduleList as entity>
                <option value="${entity.module}" <#if module==entity.module>selected</#if>>${entity.moduleName}</option>
            </#list>
        </select>
    </div>
    </div>
    
    
     <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 专业代码 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="typeCode" id="typeCode" placeholder="" class="col-xs-10 col-sm-5" />
        	  <button onclick="getMajorName()">检测</button>
        </div>
    </div>
    
     <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 专业名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="majorName"  name="majorName" id="form-field-2" placeholder=""  readonly="readonly" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <!--<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 所属模块 </label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="module">
                <option value="0">选择模块</option>
               	<#list productModuleList as entity>
                	<option value="${entity.module}" <#if module==entity.module>selected</#if>>${entity.moduleName}</option>
            	</#list>
            </select>
        </div>
    </div>-->

    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 封面图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="pictureUrl" id="pictureUrl" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('pictureUrl','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>

<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 深度学习封面 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="showCover" id="showCover" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('showCover','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>

  	<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否显示 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio1" value="1"> 是
            </label>
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio2" value="2" checked> 否
            </label>
        </div>
    </div>

    <div class="form-group">
      <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否合作专业 </label>
      <div class="col-sm-9">
          <label class="radio-inline">
              <input type="radio" name="isCooperation" id="inlineRadio1" value="1"> 是
          </label>
          <label class="radio-inline">
              <input type="radio" name="isCooperation" id="inlineRadio2" value="0" checked> 否
          </label>
      </div>
    </div>
    <div class="form-group">
      <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否同步云测评 </label>
      <div class="col-sm-9">
          <label class="radio-inline">
              <input type="radio" name="isSync" id="inlineRadio1" value="1"> 是
          </label>
          <label class="radio-inline">
              <input type="radio" name="isSync" id="inlineRadio2" value="0" checked> 否
          </label>
      </div>
    </div>
    
    <div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否可购买 </label>
    <div class="col-sm-9">
        <label class="radio-inline">
            <input type="radio" name="isBuy" id="inlineRadio1" value="1"> 是
        </label>
        <label class="radio-inline">
            <input type="radio" name="isBuy" id="inlineRadio2" value="0" checked> 否
        </label>
      </div>
    </div>
</form>

<script>
  	 function getMajorName(){
	         var majorCode=$("#typeCode").val();
	         var module=$("#module").val();
	         if(module == ''){
	        	 alert("请先选择地域");
	        	 return;
	         }
	         if(majorCode == ''){
	        	 alert("请键入专业代码");
	        	 return;
	         }
	            $.post("/productCategory/getMajorName", { majorCode:majorCode, module:module},
	                    function(data){
	                       var u=JSON.parse(data);
	                       console.log(u);
	                       if(u.retCode!=0){
	                       		alert("该地域查不到此课程");
	                       }
	                       $("#majorName").val(u.majorName);
	                    });
	            }
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
<@fileUpload></@fileUpload>
</@htmlBody>
