/**
 * 
 */

function doThumbs(id, thumbsNum) {
	var text = $('#thumbsId').text().toString();
	if (text.indexOf("赞") != -1) {
		$.post("/thumbs", {
			'id' : id,
			'oper' : 1
		}, function(data) {
			if (data.retCode == "0") {
				thumbsNum = parseInt(thumbsNum) + 1;
				$("#thumbsId").css('background-image', 'url(../../resources/images/xin2.png)');
				$("#thumbsId").text('(' + thumbsNum + ')');
			} else {
				alert('点赞失败');
			}  	
		}, 'json');
	} else {
		$.post("/thumbs", {
			'id' : id,
			'oper' : -1
		}, function(data) {
			if (data.retCode == "0") {
				thumbsNum = thumbsNum;
				$("#thumbsId").text('赞(' + thumbsNum + ')');
				$("#thumbsId").css('background-image', 'url(../../resources/images/xin1.png)');
			} else {
				alert('取消点赞失败');
			}
		}, 'json');
	}
}