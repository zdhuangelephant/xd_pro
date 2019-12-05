$(function() {
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
	$(".qr").hover(function() {
		$(".qr-popup").show();
	}, function() {
		$(".qr-popup").hide();
	});
	
	$(window).load(function () {
		$(".content").css("min-height", ($(window).height() - 365) + 'px');
	});
	$(window).resize(function () {
		$(".content").css("min-height", ($(window).height() - 365) + 'px');
	});
});
