<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">

<link href="${JS_PATH}jqueryTableTreeView/jquery.treetable.css" rel="stylesheet" type="text/css" />
<link href="${JS_PATH}jqueryTableTreeView/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css" />
<script src="${JS_PATH}jqueryTableTreeView/jquery.treetable.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#dnd-example").treetable({
            expandable: true
        });
        checkChange();
    });

    function checkChange(){
        var needDeleteId = "";
        var needAddId = "";
        $(":checkbox[name='menuid']:enabled").each(function(){
            var preChecked = $(this).attr("prechecked");
            var id = $(this).attr("id");
            if($(this).is(":checked")){
                if(preChecked==""){
                    needAddId = needAddId + id + ";";
                }
            } else {
                if(preChecked=="checked"){
                    needDeleteId = needDeleteId + id + ";";
                }
            }
        });
        $("#needDeleteId").val(needDeleteId);
        $("#needAddId").val(needAddId);
    }

    function checknode(obj) {
        var chk = $("input[type='checkbox']");
        var count = chk.length;
        var num = chk.index(obj);
        var level_top = level_bottom = chk.eq(num).attr('level')
        for (var i = num; i >= 0; i--) {
            var le = chk.eq(i).attr('level');
            if (eval(le) < eval(level_top)) {
                chk.eq(i).prop("checked", true);
                var level_top = level_top - 1;
            }
        }
        for (var j = num + 1; j < count; j++) {
            var le = chk.eq(j).attr('level');
            if (chk.eq(num).prop("checked") == true) {
                if (eval(le) > eval(level_bottom)) chk.eq(j).prop("checked", true);
                else if (eval(le) == eval(level_bottom)) break;
            }
            else {
                if (eval(le) > eval(level_bottom)) chk.eq(j).prop("checked", false);
                else if (eval(le) == eval(level_bottom)) break;
            }
        }
        checkChange();
    }
</script>
</@htmlHead>

<@htmlBody bodyclass="page-content">

    <form id="addForm" method="post" action="/admin/doAdminPrivileges">

        <input type="hidden" value="${adminId}" name="adminId">
        <input type="hidden" id="needDeleteId" value="" name="needDeleteId">
        <input type="hidden" id="needAddId" value="" name="needAddId">

        <table cellspacing="0" id="dnd-example" class="table table-striped table-bordered table-hover treetable">
            <thead>
            <tr>
                <th>
                    权限
                </th>
            </tr>
            </thead>
            <tbody>
            ${table}
            </tbody>
        </table>
    </form>

</@htmlBody>

