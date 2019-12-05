<#macro editor textareaid ='content' textareaname='content' toolbar = 'basic' width="300px" height="200px" content="" >
    <#if EDITOR_INIT??>
    <#else>
    <script type="text/javascript" charset="utf-8"
            src="${JS_PATH}/editor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${JS_PATH}/editor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${JS_PATH}/editor/lang/zh-cn/zh-cn.js"></script>
        <#assign EDITOR_INIT="true" />
    </#if>
<script id="${textareaid}" name="${textareaname}" type="text/plain" style="width:${width};height:${height}">
${content}
</script>
<script type="text/javascript">
    var ue = UE.getEditor('${textareaid}');
</script>

</#macro>
