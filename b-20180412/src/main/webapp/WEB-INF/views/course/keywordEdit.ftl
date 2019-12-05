<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/courseKeyword/doEdit">

    <input name="id" type="hidden" value="${keyword.id}" />
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 上级目录 </label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="chapterId">
                ${chapterTree}
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  value="${keyword.name}" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 重要程度 </label>
        <div class="col-sm-9">
            <input  value="${keyword.importanceLevel}" type="number" name="importanceLevel" class="rating" min=0 max=10 step=1 data-size="xs">
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${keyword.detail}" id="form-field-3" name="detail" placeholder="" class="col-xs-10 col-sm-5" />
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
</@htmlBody>
