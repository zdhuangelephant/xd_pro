<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/smsTemplateType/doAdd">
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 栏目 </label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="channelIds">
               <#list selectChannel as channel>
                <option value="${channel.id}" <#if catId == channel.id>selected</#if> >${channel.name}</option>
            </#list>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="description" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 重试次数</label>
        <div class="col-sm-9">
            <input type="number" datatype="s" sucmsg="haha" name="retryTime" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 失效时间 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="cacheTime" placeholder="" class="col-xs-10 col-sm-5" />
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
