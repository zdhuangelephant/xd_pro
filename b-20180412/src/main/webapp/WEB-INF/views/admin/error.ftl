<#include "/common/layout.ftl" />

<@htmlHead title="错误">
<script language="javascript">
    $(function () {
        $('.error').css({'position': 'absolute', 'left': ($(window).width() - 490) / 2});
        $(window).resize(function () {
            $('.error').css({'position': 'absolute', 'left': ($(window).width() - 490) / 2});
        })
    });
</script>
<style>
    body {
        background: #edf6fa;;
    }
</style>
</@htmlHead>
<@htmlBody>
    <@breadcrumbs>
    </@breadcrumbs>
<div class="error">
    <h2>非常遗憾，您访问的页面不存在！</h2>

    <p>看到这个提示，就自认倒霉吧!</p>

    <div class="reindex"><a href="main.tpl" target="_parent">返回首页</a></div>
</div>
</@htmlBody>
