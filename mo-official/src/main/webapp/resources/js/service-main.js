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

function toForumDeatil(forumId) {
	var pageNo = $("#getMore").attr("pageno") - 1;
	var href = document.location.href;
	if (isContains(href, "pageNo")) 
		href = href.substring(0, href.indexOf("?"));
//	history.pushState('', document.title, href + "?pageNo=" + pageNo);
	History.pushState('', document.title, "?pageNo=" + pageNo);
	window.location.href = "/moknowledge/" + forumId + ".html";
}


