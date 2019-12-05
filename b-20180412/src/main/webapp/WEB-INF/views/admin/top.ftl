<#include "/common/layout.ftl" />
<@htmlHead title="面板">
<script type="text/javascript">
    $(function () {
        //顶部导航切换
        $(".nav li a").click(function () {
            $(".nav li a.selected").removeClass("selected")
            $(this).addClass("selected");
        })
    })
</script>
<style>
    body {
        background: url(${IMG_PATH}topbg.gif) repeat-x;
    }
</style>
</@htmlHead>
<@htmlBody>
<div class="topleft">
    <a href="/" target="_parent"><img src="${IMG_PATH}logo.png" title="系统首页"/></a>
</div>
<ul class="nav">
    <@category parentId="0" return="data">
        <#list data as cat>
            <li>
                <a href="/left?parentId=${cat.privilegeId}" target="leftFrame"
                   <#if cat_index==0>class="selected"</#if> >
                    <img src="${IMG_PATH}${cat.icon}" title="${cat.name}"/>

                    <h2>${cat.name}</h2>
                </a>
            </li>
        </#list>
    </@category>
</ul>

<div class="topright">
    <ul>
        <li><span><img src="${IMG_PATH}help.png" title="帮助" class="helpimg"/></span><a
                href="#">帮助</a></li>
        <li><a href="#">关于</a></li>
        <li><a href="/admin/loginOut" target="_parent">退出</a></li>
    </ul>

    <div class="user">
        <span><#if admin.realName?? >${admin.realName}<#else>${admin.userName}</#if></span>
    </div>

</div>
</@htmlBody>
