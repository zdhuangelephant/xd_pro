

function uploaD(scope, size, type) {
	fileUploadCallBack(function(url) {
		if (isEmpty(url))
			return;
		$("#url").val(url);
		$("#download").hide();
		$("#upload").show();
		$("#step2").addClass("active");
		$("#step1").removeClass("active");
		$("#upload").hide();
		$("#success").show();
	}, scope, size, type);
}


$.fn.modalOpen = function(options) {
	var defaults = {
		id : null,
		title : '系统窗口',
		width : "100px",
		height : "100px",
		url : '',
		shade : 0.3,
		btn : [ '确认', '关闭' ],
		btnclass : [ 'btn btn-primary', 'btn btn-danger' ],
		callBack : null
	};
	var options = $.extend(defaults, options);
	var _width = top.$(window).width() > parseInt(options.width.replace('px',
			'')) ? options.width : top.$(window).width() + 'px';
	var _height = top.$(window).height() > parseInt(options.height.replace(
			'px', '')) ? options.height : top.$(window).height() + 'px';
	layer.open({
		id : options.id,
		type : 2,
		shade : options.shade,
		title : options.title,
		fix : false,
		area : [ _width, _height ],
		content : options.url,
		btn : options.btn,
		btnclass : options.btnclass,
		yes : function() {
			options.callBack(options.id);
		},
		cancel : function() {
			return true;
		}
	});
};

function importExcel() {
	layui.use('layer', function() {
		var layer = layui.layer;
		var index = layer.load(0, {
			shade : false
		}); // 0代表加载的风格，支持0-2
		var type = $("#importType").val();
		var URL;
		if(type==1){
			URL = "/course/import_from_excel";
		}else if(tyoe == 2){
			URL = "/course/import_product";
		}
		$.post(URL, {
			"url" : $("#url").val()
		}, function(data) {
			layer.close(index);
			if (!isEmpty(data) && "true" == data) {
				$("#success").show();
				$("#importButton").hide();
				$("#finish").show();
				$("#import_msg").html("导入成功");
			} else {
				layer.msg('添加失败', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
			}
		});
	});
}

