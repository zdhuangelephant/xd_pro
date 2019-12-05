
$(document).ready(function () {
    //加载更多方法
    $(function () {
        //对于每一条评论：
        $('.detail').each(function () {
            //记录评论内容
            var html = $(this).html();
            //把评论div的内容替换成三个部分：简短评论内容、完整评论内容和查看内容的超链接
            $(this).html('<span class="short">' + html.substring(0, 200) + '...</span><a class="all">' + html + '</a><a class="more">显示全文</a>');
        });
        //对于每一个评论中查看内容的超链接：
        $('.detail .more').each(function () {
            //如果点击该链接则：
            $(this).click(function () {
                //把不需要显示的内容隐藏，需要显示的内容展开。
                $(this).parent().children('.all,.short').toggle();
                //替换超链接的文字
                if ($(this).html() == '收起') {
                    $(this).html('显示全文');

                } else {
                    $(this).html('收起');
                    $(this).css("background-image", "url(images/anl2.jpg)");
                }
            });
        });
    });

    //封装margin随屏幕宽度变化方法
    function getHeight(child, parent) {
        $(child).css('margin-top', ($(parent).height() - $(child).height()) / 2 + 'px');
    }

    function getWidth(child, parent) {
        if ($(window).width() < 1000) {
            $(child).css('margin-left', ($(parent).width() - $(child).width()) / 2 + 'px');
        } else {
            $(child).css('margin-left', 0 + 'px');
        }
    }

    $(window).load(function () {
        getHeight('.vs', '.oHeight');
        getWidth('.js-li5', '.in-content4-box');
    });
    $(window).resize(function () {
        getHeight('.vs', '.oHeight');
        getWidth('.js-li5', '.in-content4-box');
    });

    //抽屉导航
    function init(){
        $(".click").click(function () {
            //导航栏高度等于当前窗口高度
            $(".phoneNav").height($(window).height());
            //展开500毫秒
            $(".phoneNav").slideToggle(500);
        });
        $(".phoneNav li").click(function () {
            $(".phoneNav").slideUp();
        });
        $(".phoneNav").click(function(){
            $(this).slideUp();
        })
    }
    init();
});