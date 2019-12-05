<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">
</@htmlHead>

<@htmlBody bodyclass="page-content">

<div class="page-header">
    <h1>
        角色
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px;" href="/role/list">列表</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;"
       href="/role/addRole">添加</a>
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th style="width: 80px;">编号</th>
        <th>角色名称</th>
        <th>角色描述</th>
        <th>状态</th>
        <th style="width: 300px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list roles as role >
        <tr>
            <td>${role.id}</td>
            <td>${role.roleName}</td>
            <td>${role.roleDescription}</td>
            <td>
                <#if role.disabled ==1 >
                    <font color="red">√</font>
                <#else>
                    <font color="red">X</font>
                </#if>
            </td>
            <td>
                <#if role.id!=1>
                <a style="cursor: pointer;" onclick="showPrivilege('${role.id}','${role.roleName}')">权限设置</a>
                <a style="padding: 5px;">|</a>
                </#if>
                <a style="cursor: pointer;" onclick="showAdmins(${role.id})">成员管理</a>
                <#if role.id!=1>
                <a style="padding: 5px;">|</a>
                <a href="/role/editRole?roleId=${role.id}">修改</a>
                <a style="padding: 5px;">|</a>
                <a onclick="deleteRole(${role.id},'${role.roleName}')">删除</a>
                </#if>
            </td>
        </tr>
        </#list>

    </tbody>
</table>


<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
    function showPrivilege(roleId, roleName) {
        art.dialog.open('/role/setPrivilege?roleId=' + roleId, {
            id: 'showPrivilege',
            title: '设置《' + roleName + '》权限',
            width: 320,
            height: 400,
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
            cancel: true
        });
    }
    function showAdmins(roleId) {
        art.dialog.open('/role/admins?roleId=' + roleId, {
            id: 'showAdmins',
            title: '角色管理员管理',
            width: 320,
            height: 300,
            cancel: true
        });
    }

    function deleteRole(roleId, roleName) {
        if (confirm("确定要删除【" + roleName + "】？")) {
            $.get("/role/deleteRole?roleId=" + roleId,
                    function (data) {
                        location.reload();
                    }
            );
        }
    }
</script>
</@htmlBody>

