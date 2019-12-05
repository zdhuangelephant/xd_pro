<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">
</@htmlHead>

<@htmlBody bodyclass="page-content">


<form id="addForm" method="post" action="/admin/doAssigenRole">
    <input type="hidden" name="adminId" value="${adminId}">
    <input type="hidden" id="needDeleteId" name="needDeleteId" value="">
    <input type="hidden" id="needAddId" name="needAddId" value="">
    <table  class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>
                角色
            </th>
        </tr>
        </thead>
        <tbody>
            <#list roles as role>
            <tr>
                <td><input type="checkbox" <#list adminRoles as adminRole><#if adminRole.id==role.id>preChecked="checked" checked</#if></#list> value="${role.id}" name="roleid">${role.roleName}</td>
            </tr>
            </#list>
        </tbody>
    </table>
</form>
<script>

    $(document).ready(function(){
        checkChange();
        $(":checkbox[name='roleid']").click(function(){
            checkChange();
        });
    });

    function checkChange(){
        var needDeleteId = "";
        var needAddId = "";
        $(":checkbox[name='roleid']").each(function(){
            var id = $(this).val();
            var preChecked = $(this).attr("preChecked");
            if($(this).is(":checked")){
                if(preChecked!="checked"){
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
</script>
</@htmlBody>

