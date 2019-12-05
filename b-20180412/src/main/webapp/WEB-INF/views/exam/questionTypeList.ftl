<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        题目类型
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
   <!-- <a class="btn btn-primary" style="border: 0px; width: 90px;" href="#">列表</a>-->
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 2px;" onclick="addType()" >添加</a>
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">名称</th>
        <th>描述</th>
        <th>类型</th>
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list questionTypeList as questionType>
        <tr>
            <td>${questionType.id}</td>
            <td>${questionType.typeName}</td>
            <td>${questionType.mdesc}</td>
            <td><#if questionType.answerType==0>客观题<#else >主观题</#if></td>
            <td>
                <a style="cursor: pointer;" onclick="editType(${questionType.id})">修改</a>|
                <a style="cursor: pointer;" onclick="delType(${questionType.id})">删除</a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>

<script>
    /**
     * 编辑栏目
     * @param catId
     */
    function editType(id){
        art.dialog.open('/questionType/edit?id='+id,{
            title: '编辑分类',
            width:400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('editForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }

    /**
     * 添加栏目
     */
    function addType(){
        art.dialog.open('/questionType/add',{
            title: '添加栏目',
            width:400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }

    /**
     * 删除栏目
     * @param catId
     */
    function delType(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/questionType/delete", { id:id},
                    function(data){
                    data=JSON.parse(data);
                        if(data.retCode==0){
                            alert("删除成功");
                        } else {
                            alert("删除失败");
                        }
                        location.reload();
                    }
            );
        }
    }
</script>

</@htmlBody>
