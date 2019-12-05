/**
 * 
 */
$(function() {
	$("#loading27").hide();
	$("#getMore").click(function() {
		var _this = $(this), typeId=_this.attr("typeid"),pageNo = _this.attr("pageno");// 分页码
		var url = '';
		if((typeId == '1_tutor') || (typeId == '1_major')){
			url = '/loadMore/tutorMajor';
		}
		if(typeId == 2){
			url = '/loadMore/activity';
		}
		if(typeId == 3){
			url = '/loadMore/student';
		}
		if (pageNo == 0) {
			return false;
		}
		$("#loading27").show();
		$.ajax({
			url : url,
			data : {
				typeId : typeId,
				pageNo : pageNo
			},
			success : function(data) {
				if (data != '') {
					$(".list-content:last").append(data);// 数据显示到页面
					_this.attr("pageno", Number(pageNo) + 1);// 分页+1
				} else {
					_this.text("已加载完");
					_this.removeAttr("href").css("background-color", "#d4d4d4");
					//_this.attr("pageno", 0);
					_this.attr("pageno", Number(pageNo) + 1);// 分页+1
				}
				$("#loading27").hide();
			}
		});
		return false;
	});
});
