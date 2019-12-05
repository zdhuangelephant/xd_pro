<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/productCategory/doEdit">

    <input type="hidden" name="id" value="${productCategory.id}" />
   <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 专业代码 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${productCategory.typeCode}" readonly="readonly" name="typeCode" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

   <!-- <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 所属模块 </label>
         <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="module">
                <option value="0">选择模块</option>
               <#list productModuleList as module>
                <option value="${module.module}" <#if productCategory.module == module.module>selected</#if> >${module.name}</option>
               </#list>
            </select>
        </div>
    </div>-->
        
     <div class="form-group">
         <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 所属地域 </label>
          <div class="col-sm-9">
             <select disabled="disabled" datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5">
                 <option value="2">选择所属地域</option>
                <#list moduleList as module>
                 <option value="${module.module}" <#if productCategory.module == module.module>selected</#if> >${module.moduleName}</option>
                </#list>
             </select>
         </div>
         <input type="hidden" datatype="s" sucmsg="haha" value="${productCategory.module}"  name="module" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
     </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 封面图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${productCategory.pictureUrl}"  name="pictureUrl" id="pictureUrl" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('pictureUrl','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 深度学习封面 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="showCover" id="showCover" value="${productCategory.showCover}"  placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('showCover','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>
    
	  <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否显示 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio1" value="1" <#if productCategory.showStatus == 1>checked</#if> > 是
            </label>
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio2" value="2" <#if productCategory.showStatus == 2>checked</#if> > 否
            </label>
        </div>
    </div>
  
  <div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否为合作专业 </label>
    <div class="col-sm-9">
        <label class="radio-inline">
            <input type="radio" name="isCooperation" id="inlineRadio1" value="1" <#if productCategory.isCooperation == 1>checked</#if> > 是
        </label>
        <label class="radio-inline">
            <input type="radio" name="isCooperation" id="inlineRadio2" value="0" <#if productCategory.isCooperation == 0>checked</#if> > 否
        </label>
    </div>
</div>

<div class="form-group">
  <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否为同步云测评 </label>
  <div class="col-sm-9">
      <label class="radio-inline">
          <input type="radio" name="isSync" id="inlineRadio1" value="1" <#if productCategory.isSync == 1>checked</#if> > 是
      </label>
      <label class="radio-inline">
          <input type="radio" name="isSync" id="inlineRadio2" value="0" <#if productCategory.isSync == 0>checked</#if> > 否
      </label>
  </div>
</div>

<div class="form-group">
  <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否为可以购买 </label>
  <div class="col-sm-9">
      <label class="radio-inline">
          <input type="radio" name="isBuy" id="inlineRadio1" value="1" <#if productCategory.isBuy == 1>checked</#if> > 是
      </label>
      <label class="radio-inline">
          <input type="radio" name="isBuy" id="inlineRadio2" value="0" <#if productCategory.isBuy == 0>checked</#if> > 否
      </label>
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
