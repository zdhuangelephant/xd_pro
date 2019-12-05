<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/region/doEdit">

    <input type="hidden" name="id" value="${region.id}" />

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 地域编码 </label>
        <div class="col-sm-9">
            <input disabled="disabled" type="number" maxlength="5" datatype="s" sucmsg=" " value="${region.module}" name="module" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 地域名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${region.moduleName}" name="moduleName" id="form-field-2" placeholder="地域名称" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${region.detail}" id="form-field-2" name="detail" placeholder="描述" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 排序</label>
        <div class="col-sm-9">
            <input type="number" maxlength="4" datatype="s" sucmsg=" " value="${region.listOrder}" id="form-field-2" name="listOrder" placeholder="排序" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否为首选/推广 </label>
    <div class="col-sm-9">
          <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="firstChoice">
              <option value="0" <#if region.firstChoice==0>selected</#if>>否</option>
              <option value="1" <#if region.firstChoice==1>selected</#if>>是</option>
          </select>
      </div>
     </div>
      
      <div class="form-group">
      <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否显示 </label>
      <div class="col-sm-9">
          <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="showStatus">
              <option value="0" <#if region.showStatus==0>selected</#if>>否</option>
              <option value="1" <#if region.showStatus==1>selected</#if>>是</option>
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

</@htmlBody>
