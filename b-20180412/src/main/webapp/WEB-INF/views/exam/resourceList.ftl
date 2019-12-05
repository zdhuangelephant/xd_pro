<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        试题来源
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
   <!-- <a class="btn btn-primary" style="border: 0px; width: 90px;" href="#">列表</a>-->
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 2px;" onclick="addResource()" >添加</a>
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">名称</th>
        <th>描述</th>
        <th style="width: 200px">关联课程</th>
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list resourceList as resource>
        <tr>
            <td>${resource.id}</td>
            <td>${resource.name}</td>
            <td>${resource.detail}</td>
            <td>${resource.courseName}</td>
            <td>
                <a style="cursor: pointer" onclick="editResource(${resource.id})">修改</a>
                <a style="padding-left: 10px; cursor: pointer;" onclick="delResource(${resource.id},'${resource.name}')">删除</a>
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
    function editResource(id){
        art.dialog.open('/questionResource/edit?resourceId='+id,{
            title: '编辑来源',
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
    function addResource(){
        art.dialog.open('/questionResource/add',{
            title: '添加来源',
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
    function delResource(id,name){
       if (confirm("确认要删除"+name+"?")) {
        $.ajax({
            type: "POST",
            url: "/questionResource/delete",
            data: {resourceId:id},
            dataType: "json",
            success: function (data) {
                if (data.retcode == '0') {
                    alert("删除成功");
                }else{
                    alert("删除失败");
                }
                location.reload();
            }
         })
    }
    }
</script>

</@htmlBody>
