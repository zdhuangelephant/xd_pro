<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/productItem/doEdit">


    <div class="form-group">
        <input type="hidden" name="id" value="${productItem.id}" />
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 上级目录 </label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="parentId">
                <option value="0">作为一级目录</option>
                ${selectTree}
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 中文序号 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${productItem.chapterId}"  name="chapterId" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${productItem.name}" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 封面图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  name="pictureUrl" value="${productItem.pictureUrl}" id="pictureUrl" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('pictureUrl','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 重要程度 </label>
        <div class="col-sm-9">
            <input  value="${productItem.importanceLevel}" type="number" name="importanceLevel" class="rating" min=0 max=10 step=1 data-size="xs">
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 描述 </label>
        <div class="col-sm-9">
            <textarea type="text" datatype="s" sucmsg=" " id="form-field-2" name="detail" placeholder="" class="col-xs-10 col-sm-5"  style="resize:none;">${productItem.detail}</textarea>
        </div>
    </div>

    <!-- <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否显示 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio1" value="1" <#if productItem.showStatus==1>checked</#if>> 是
            </label>
            <label class="radio-inline">
                <input type="radio" name="showStatus" id="inlineRadio2" value="0" <#if productItem.showStatus==0>checked</#if>> 否
            </label>
        </div>
    </div>
 -->
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 是否免费 </label>
        <div class="col-sm-9">
            <label class="radio-inline">
                <input type="radio" name="isFree" id="" value="1" <#if productItem.isFree==1>checked</#if> > 是
            </label>
            <label class="radio-inline">
                <input type="radio" name="isFree" id="" value="0" <#if productItem.isFree==0>checked</#if>> 否
            </label>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 排序 </label>
        <div class="col-sm-9">
            <input type="number" value="${productItem.listOrder}" datatype="s" sucmsg="haha" id="form-field-3" name="listOrder" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

</form>
<link href="${rc.contextPath}/resources/js/starRating/star-rating.min.css" rel="stylesheet" type="text/css" />
<script src="${rc.contextPath}/resources/js/starRating/star-rating.min.js"></script>
<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });

    $('.rating').rating({
    });
</script>
<@fileUpload></@fileUpload>
</@htmlBody>
