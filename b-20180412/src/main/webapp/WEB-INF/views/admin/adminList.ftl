<#include "/common/layout.ftl" />
<@htmlHead title="管理员列表">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        管理员
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px;" href="">列表</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;"
       href="/admin/addAdmin">添加</a>
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th style="width: 80px;">编号</th>
        <th>用户名</th>
        <th style="width: 300px;">所属角色</th>
        <th>邮件</th>
        <th>手机号</th>
        <th>真实姓名</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
        <#list admins as admin>
        <tr>
            <td>${admin.id}</td>
            <td>${admin.userName}</td>
            <td>
                <#list admin.roleList as role>
                ${role.roleName},
                </#list>
            </td>
            <td>${admin.email}</td>
            <td>${admin.telphone}</td>
            <td>${admin.realName}</td>
            <td><a onclick="showRoles(${admin.id})" style="cursor: pointer;">角色管理</a>
                <a style="padding: 5px;">|</a>
                <a onclick="showPersonPrivilege(${admin.id})" style="cursor: pointer;">权限管理</a>
                <a style="padding: 5px;">|</a>
                <a href="/admin/editAdmin?adminId=${admin.id}">修改</a>
                <a style="padding: 5px;">|</a>
                <a onclick="deleteAdmin(${admin.id},'${admin.userName}')">删除</a>
            </td>
        </tr>
        </#list>
    </tbody>
</table>

<script type="text/javascript">
    function showRoles(adminId) {
        art.dialog.open('/admin/assigenRole?adminId=' + adminId, {
            id: 'showRoles',
            title: '角色设置',
            width: 320,
            height: 300,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                }
                ;
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

    function showPersonPrivilege(adminId){
        art.dialog.open('/admin/adminPrivileges?adminId=' + adminId, {
            id: 'showPersonPrivilege',
            title: '权限设置',
            width: 320,
            height: 400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                }
                ;
                var form = iframe.document.getElementById('addForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {

            }
        });
    }

    function deleteAdmin(adminId, name) {
        if (confirm("确定要删除【" + name + "】？")) {
            $.get("/admin/deleteAdmin?adminId=" + adminId,
                    function (data) {
                        location.reload();
                    }
            );
        }
    }
</script>
</@htmlBody>

