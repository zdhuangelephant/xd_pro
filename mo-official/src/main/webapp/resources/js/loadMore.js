/**
 * 
 */
$(function() {
	$("#loading27").hide();
	$("#getMore").click(function() {
		var _this = $(this), pageNo = _this.attr("pageno");// 分页码
		var url = '/loadMore';
		
		if (pageNo == 0) {
			return false;
		}
		$("#loading27").show();
		$.ajax({
			url : url,
			data : {
				pageNo : pageNo
			},
			success : function(data) {
				if (data != '') {
					$(".k-list-append:last").append(data);// 数据显示到页面
					_this.attr("pageno", Number(pageNo) + 1);// 分页+1
				} else {
					_this.text("已加载完");
					_this.removeAttr("href").css("background-color", "#d4d4d4");
					_this.attr("pageno", Number(pageNo) + 1);// 分页+1
				}
				$("#loading27").hide();
			}
		});
		return false;
	});
});
