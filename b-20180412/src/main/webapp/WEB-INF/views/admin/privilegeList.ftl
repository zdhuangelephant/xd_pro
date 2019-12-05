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
});
</script>
</@htmlHead>

<@htmlBody bodyclass="page-content">

<div class="page-header">
    <h1>
        权限
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px;" href="/privilege/list">列表</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" href="/privilege/addPrivilege">添加</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="order()" >排序</a>
</div>

    <table id="dnd-example" class="table table-striped table-bordered table-hover treetable">
        <thead>
        <tr>
            <th >权限名</th>
            <th >排序</th>
            <th >是否显示</th>
            <th style="width: 300px;">操作</th>
        </tr>
        </thead>
        <tbody>
        ${tree}
        </tbody>
    </table>

<script>
    function order(){
        var orders = "";
        $("input[name='sortOrder']").each(function(){
            var id = $(this).attr("id");
            var value = $(this).val();
            var preValue = $(this).attr("prevalue");
            if(value!=preValue){
                orders = orders + id + ":" + value + ";";
            }
        });
        if(orders!=""){
            $.post("/privilege/sortPrivilege", { orders:orders},
                    function(data){
                    });
        }
        alert("排序成功");
    }
</script>
</@htmlBody>

