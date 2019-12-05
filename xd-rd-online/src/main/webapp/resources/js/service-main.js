$(function() {
	$('#close_im').bind('click', function() {
		$('#main-im').css("height", "0");
		$('#im_main').hide();
		$('#open_im').show();
	});
	$('#open_im, #now, .now1').bind('click', function(e) {
		_MEIQIA('showPanel');
	});
	$('.now1').bind('click', function(e) {
		e.preventDefault();
	});
});


function isContains(str, substr) {
	return str.indexOf(substr) >= 0;
}

function savePage() { // 操作浏览器的历史记录
	var nowTop = $(window).scrollTop();
	history.replaceState('', document.title, location.href.replace(
			location.hash, "")
			+ "#nowTop=" + nowTop);
}

function toTutorDeatil(tutorId) {
	var pageNo = $("#getMore").attr("pageno") - 1;
	var href = document.location.href;
	if (isContains(href, "pageNo")) 
		href = href.substring(0, href.indexOf("?"));
	history.pushState('', document.title, href + "?pageNo=" + pageNo);
	//window.location.href = "/tutorMajor/tutor/detail?majortutorId=" + tutorId;
	window.location.href = "/zyds/" + tutorId + ".html";
}

function toMajorDeatil(majorId) {
	var pageNo = $("#getMore").attr("pageno") - 1;
	var href = document.location.href;
	if (isContains(href, "pageNo")) 
		href = href.substring(0, href.indexOf("?"));
	history.pushState('', document.title, href + "?pageNo=" + pageNo);
	//window.location.href = "/tutorMajor/major/detail?majortutorId=" + majorId;
	window.location.href = "/zyjs/" + majorId + ".html";
}

function toActivityDeatil(activityId) {
	var pageNo = $("#getMore").attr("pageno") - 1;
	var href = document.location.href;
	if (isContains(href, "pageNo")) 
		href = href.substring(0, href.indexOf("?"));
	history.pushState('', document.title, href + "?pageNo=" + pageNo);
	//window.location.href = "/activity/detail?activityId=" + activityId;
	window.location.href = "/xxhd/" + activityId + ".html";
	
}


