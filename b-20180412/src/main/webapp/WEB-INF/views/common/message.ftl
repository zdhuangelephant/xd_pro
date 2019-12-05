<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<script>
    function closeDialog(){
        var api = art.dialog.open.api;
        api && api.close();
        
        
    }
    function forwordUrl(url){
        window.location.href = url;
    }
</script>

<#if forward!="" && forward!=null>
    <script type="application/javascript">
        setTimeout("forwordUrl('${forward}')",2000);
    </script>
<#else >
    <#if dialog==true>
    <script type="application/javascript">
        setTimeout("closeDialog()",2000);
        	
    </script>
    </#if>
</#if>

<div>
    <h2>${messageTitle}</h2>
    <p>${message}</p>
    <#if forward!="" && forward!=null>
    <div class="reindex">
        <a href="${forward}">返回</a>
    </div>
    </#if>
</div>

</@htmlBody>
