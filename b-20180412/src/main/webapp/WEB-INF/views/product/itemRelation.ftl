<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/productItem/doItemRelation">
<input type="hidden" name="itemId" value="${itemId}" />
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center">选择</th>
        <th style="width: 150px;">名称</th>
        <th style="width: 150px;">类型</th>
    </tr>
    </thead>
    <tbody>
    <#list itemList as item>
        <#if item.resourceType!=1>
        <tr>
            <td><input type="radio" value="${item.id}" name="relationItem" <#if relationId==item.id>checked</#if> /></td>
            <td>${item.name}</td>
            <td>
                <#if item.resourceType==1> 章节</#if>
                <#if item.resourceType==2> 视频</#if>
                <#if item.resourceType==3> 文档</#if>
                <#if item.resourceType==4> HTML5</#if>
                <#if item.resourceType==6> 练习</#if>
                <#if item.resourceType==7> 音频</#if>
                <#if item.resourceType==8> 微课</#if>
            </td>
        </tr>
        </#if>
    </#list>
    </tbody>
</table>
    <div>
        <a class="btn btn-primary" style="border: 0px; width: 90px;" onclick="cancelSelect()" >取消选择</a>
    </div>
</form>

<script>
    function cancelSelect(){
        $(":radio[name='relationItem']:checked").removeProp("checked");
    }
</script>

</@htmlBody>
