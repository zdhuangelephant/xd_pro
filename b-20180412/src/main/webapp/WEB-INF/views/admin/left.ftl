<#include "/common/layout.ftl" />
<@htmlHead title="面板">
<script type="text/javascript">
    $(function () {
        //导航切换
        $(".menuson .header").click(function () {
            var $parent = $(this).parent();
            $(".menuson>li.active").not($parent).removeClass("active open").find('.sub-menus').hide();

            $parent.addClass("active");
            if (!!$(this).next('.sub-menus').size()) {
                if ($parent.hasClass("open")) {
                    $parent.removeClass("open").find('.sub-menus').hide();
                } else {
                    $parent.addClass("open").find('.sub-menus').show();
                }
            }
        });

        // 三级菜单点击
        $('.sub-menus li').click(function (e) {
            $(".sub-menus li.active").removeClass("active")
            $(this).addClass("active");
        });

        $('.title').click(function () {
            var $ul = $(this).next('ul');
            $('dd').find('.menuson').slideUp();
            if ($ul.is(':visible')) {
                $(this).next('.menuson').slideUp();
            } else {
                $(this).next('.menuson').slideDown();
            }
        });
    })
</script>
<style>
    body {
        background: #f0f9fd;
    }
</style>
</@htmlHead>
<@htmlBody>
<div class="lefttop"><span></span>${topMenu.name}</div>

<dl class="leftmenu">

    <#list menus as menu>
        <dd>
            <div class="title">
                <span><img src="${IMG_PATH}leftico01.png"/></span>${menu.name}
            </div>
            <ul class="menuson">
                <@category parentId="${menu.privilegeId}" return="data">
                    <#list data as cat>
                        <li>
                            <div class="header">
                                <cite></cite>
                                <a href="${cat.url}" target="rightFrame">${cat.name}</a>
                                <i></i>
                            </div>
                        </li>
                    </#list>
                </@category>
            </ul>
        </dd>
    </#list>

</dl>
</@htmlBody>
