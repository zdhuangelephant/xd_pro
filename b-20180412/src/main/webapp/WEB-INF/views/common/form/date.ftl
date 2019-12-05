<#macro date inputId timeFormat mask timepicker>
    <#if DATE_INIT??>
    <#else>
    <link rel="stylesheet" type="text/css" href="${CSS_PATH}jquery.datetimepicker.css">
    <script type="text/javascript" charset="utf-8"
            src="${JS_PATH}jquery.datetimepicker.js"></script>
        <#assign DATE_INIT="true" />
    </#if>
<script>
    $(document).ready(function () {
        $('#${inputId}').datetimepicker({
            mask: '<#if mask=="">9999-19-39 29:59:00<#else>${mask}</#if>',
        timepicker:<#if timepicker=="">false<#else >true</#if>,
            format: '${timeFormat}'
        });
    });
</script>
</#macro>
