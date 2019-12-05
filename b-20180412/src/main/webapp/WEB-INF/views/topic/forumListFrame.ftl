<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        话题管理
    </h1>
</div>


<div>
    
    <div style="margin-left: 230px;  min-height: 100%;padding: 0;">
        <div class="embed-responsive embed-responsive-16by9">
            <iframe id="myframe" name="myframe" class="embed-responsive-item" src="/forumTopic/list?catId=${id}"></iframe>
            
        </div>
    </div>
</div>

</@htmlBody>
