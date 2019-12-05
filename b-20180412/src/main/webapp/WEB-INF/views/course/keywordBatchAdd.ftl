<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/courseKeyword/batchDoAdd">

    <table class="table table-striped table-bordered table-hover">
        <tr>
            <td colspan="4">
                <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="chapterId">
                ${chapterTree}
                </select>
            </td>
        </tr>
        <tr>
            <th>编号</th>
            <th>名称</th>
            <th>重要程度</th>
            <th>描述</th>
        </tr>

        <#list 1..10 as index >
            <tr>
                <td>
                    ${index}
                </td>
                <td>
                    <input type="text"  name="name${index}" id="name${index}"  />
                </td>
                <td>
                    <input  value="0" type="number" name="importanceLevel${index}" id="importanceLevel${index}" class="rating" min=0 max=10 step=1 data-size="xs">
                </td>
                <td>
                    <input type="text" name="detail${index}" id="detail${index}" />
                </td>
            </tr>
        </#list>

    </table>

</form>

<link href="${rc.contextPath}/resources/js/starRating/star-rating.min.css" rel="stylesheet" type="text/css" />
<script src="${rc.contextPath}/resources/js/starRating/star-rating.min.js"></script>
<script>

    $('.rating').rating({
    });
</script>
</@htmlBody>
