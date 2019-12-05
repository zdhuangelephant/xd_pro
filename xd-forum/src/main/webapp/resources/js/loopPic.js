$(document).ready(function(){
	window.stageWidth = parseInt(document.documentElement.clientWidth);
	window.stageHeight = parseInt(document.documentElement.clientHeight);
	$(".pictureBig").height(window.stageHeight);
	$(".bigBox").height(window.stageHeight);
	$(".bigBox").width(window.stageWidth);
	var h = $("body").height();
	$(".pictureBox img").click(function(){
		var s=  $(this).attr('src');
		$(".pictureBig img").attr('src',s);
		$(".pictureBig").show();
		$("body").height(window.stageHeight);
	});
	$(".pictureBig").click(function(){
		$(this).hide();
		$("body").height(h);
	})
})