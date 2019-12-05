currentPageId = "spage1";
$(document).ready(function() {
	window.stageWidth = parseInt(document.documentElement.clientWidth);
	window.stageHeight = parseInt(document.documentElement.clientHeight);
	$('#mstage').height(window.stageHeight);
	// alert($('#spage1').height());
	$("div").find(".back").each(function(index) {// 隐藏除第一页外的其它page
		if (index != 0) {
			$(this).hide();
		}
	});
	var page = {};
	page.id = "spage1";
	page.onReady = function() {
		if ($('#spage1').height() > window.stageHeight) {
			$('#mstage').height($("#spage1").height());
		}
	};
	page.onEnter = function() {
		setTimeout(function() {
			director.lock = false;
		}, 300);
	};
	page.onExit = function() {
	};
	director.replaceScene(page);
	for ( var ii = 1; ii <= 5; ii++) {
		var rightAnswerId = $("#rightAnswer" + ii).attr("value");
		var rightAnswer = rightAnswerId.substr(rightAnswerId.length - 1);// 从后面往前面截取,截取一位
		if (rightAnswer == 1)
			$("#true" + ii).text("A");
		if (rightAnswer == 2)
			$("#true" + ii).text("B");
		if (rightAnswer == 3)
			$("#true" + ii).text("C");
		if (rightAnswer == 4)
			$("#true" + ii).text("D");
	}
	$(".orderli").click(function() { // 选择答案
		var currentId = $(this).parents(".back").attr('id');
		$("#" + currentId + " .order").removeClass("choose");
		$(this).children(".order").addClass("choose");
		var answerId = $(this).children(".order").attr("value");// 所选答案id
		var myAnswer = $(this).children(".order").text();// 所选答案选项
		var count = $(this).children(".order").attr("valueid");
		$("#myAnswer" + count).text(myAnswer);
		var rightAnswerId = $("#rightAnswer" + count).attr("value");
		if (answerId == rightAnswerId) {
			$("#result" + count).html("答对");
			$("#right" + count).attr("class", "list right");
			$("#right" + count).attr("name", "right");
			$("#myAnswer" + count).attr("class", "true");
		} else {
			$("#result" + count).html("答错");
			$("#right" + count).attr("class", "list wrong");
			$("#right" + count).attr("name", "wrong");
			$("#myAnswer" + count).attr("class", "false");
		}
		
		$("#scoreTitle").text($(".right").length);
		if (($(".right").length) == 5)
			$("#scoreDetail").attr("class", "scoreDetail s");
		if (($(".right").length) == 4)
			$("#scoreDetail").attr("class", "scoreDetail a");
		if (($(".right").length) == 3)
			$("#scoreDetail").attr("class", "scoreDetail c");
		if (($(".right").length) <= 2)
			$("#scoreDetail").attr("class", "scoreDetail d");

		setTimeout(function() {
			nextPage();
		}, 200);
	});
	$(".resolve").parents('li').click(function() {
		if ($(this).next().css('display') == "none") {
			$(this).next().show();
			$("#mstage").height($("#spage6").height());
		} else {
			$(this).next().hide();
			$("#mstage").height($("#spage6").height());
		}
	});
});