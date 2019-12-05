<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">
<script type="text/javascript" src="${JS_PATH}validform/validform.js"></script>
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form>
    <table cellspacing="0" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>id</th>
            <th>用户名</th>
            <th>姓名</th>
        </tr>
        </thead>
        <tbody>
            <#list admins as admin>
            <tr>
                <td>${admin.id}</td>
                <td>${admin.userName}</td>
                <td>${admin.realName}</td>
            </tr>
            </#list>
        </tbody>
    </table>
</form>
</@htmlBody>

