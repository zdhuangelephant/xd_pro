<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/region/doAdd">

        
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 地域编码 </label>
            <div class="col-sm-9">
                <input type="number" maxlength="5" oninput="if(value.length>5)value=value.slice(0,5)" datatype="s" 
                	placeholder="请输入数字" sucmsg=" " name="module" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
            </div>
        </div>
        
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 地域名称 </label>
            <div class="col-sm-9">
                <input type="text" maxlength="10" datatype="s" sucmsg=" "  name="moduleName" id="form-field-2" placeholder="地域名称" class="col-xs-10 col-sm-5" />
            </div>
        </div>
        
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 描述 </label>
            <div class="col-sm-9">
                <input type="text" datatype="s" sucmsg=" "  id="form-field-2" name="detail" placeholder="描述" class="col-xs-10 col-sm-5" />
            </div>
        </div>
        
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 排序</label>
            <div class="col-sm-9">
                <input type="number" maxlength="4" datatype="s" sucmsg=" " id="form-field-2" name="listOrder" placeholder="排序" class="col-xs-10 col-sm-5" />
            </div>
        </div>
      
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否为首选/推广 </label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="firstChoice">
                <option value="0" >否</option>
                <option value="1" >是首选</option>
            </select>
        </div>
    </div>
</form>

<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
</@htmlBody>
