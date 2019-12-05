$(document).ready(function () {
	
	//文字垂直居中
    $(window).load(function () {
        $('.coo-img-wrap li a').each(
            function () {
                $(this).css('padding-top', ($('.coo-img-wrap li').height() - $(this).height()) / 2 + 'px');
            }
        )
    });

    $(window).resize(function () {
        $('.coo-img-wrap li a').each(
            function () {
                $(this).css('padding-top', ($('.coo-img-wrap li').height() - $(this).height()) / 2 + 'px');
            }
        )
    });
	
    //抽屉导航
    function init() {
        $(".click").click(function () {
            //导航栏高度等于当前窗口高度
            $(".phoneNav").height($(window).height());
            //展开500毫秒
            $(".phoneNav").slideToggle(500);
        });
        $(".phoneNav li").click(function () {
            $(".phoneNav").slideUp();
        });
        $(".phoneNav").click(function () {
            $(this).slideUp();
        })
    }

    init();
    
    //返回顶部
	$(window).scroll(function() {
		var scrolltop = $(this).scrollTop();
		if (scrolltop >= 500) {
			$("#elevator_item").show();
		} else {
			$("#elevator_item").hide();
		}
	});
	$("#elevator").click(function() {
		$("html,body").animate({
			scrollTop : 0
		}, 500);
	});
});
