var baseurl = location.protocol + "//" + location.host;

/** *********************************************************************************************************************** */
$(function() {
	$('#title').val(name);

	$('#authorName').typeahead(
			{
				source : function(authorName, process) {
					// query是输入值
					$.post('/knowledge/author/search4Forum', {
						authorName : authorName
					}, function(data) {
						data = eval('(' + data + ')');
						if (data.retCode == 0) {
							process(data.authorList);
						}
					});
				},
				updater : function(item) {
					$('#authorId').val(
							item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$1"));
					return item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$2"); // 这里一定要return，否则选中不显示
				},
				items : 10, // 显示10条
				delay : 500
			// 延迟时间
			});

	$('#forumTagName').typeahead(
			{
				source : function(forumTagName, process) {
					// query是输入值
					$.post('/knowledge/forum/searchCourse', {
						courseName : forumTagName
					}, function(data) {
						data = eval('(' + data + ')');
						if (data.retCode == 0) {
							process(data.authorList);
						}
					});
				},
				updater : function(item) {
					$('#forumTag').val(
							item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$1"));
					return item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$2"); // 这里一定要return，否则选中不显示
				},
				items : 10, // 显示10条
				delay : 500
			// 延迟时间
			});

	$("#enterBtn").click(function() {
		$(".content").html($('#editor').val());
	});
	var mediaSetting = {
		mediaType_3 : 'audio',
		mediaType_2 : 'vedio',
		audioTemp : '<audio src="{0}" controls="controls" style="width:100%">您的浏览器不支持 audio 标签。</audio>',
		audioType : 'mp3',
		audioSize : '100',
		vedioTemp : '<video src="{0}" controls="controls" style="width:100%;height:100%">您的浏览器不支持 video 标签。</video>',
		vedioType : 'mp4',
		vedioSize : '300'
	};
	$('#modifyMedia').click(
			function() {
				if ($("#forumClassify").val().length == 0) {
					alert("请先选择媒体资源类型");
					$("#forumClassifyDesc").focus();
					return;
				} else {
					mediaType = mediaSetting['mediaType_'
							+ $("#forumClassify").val()];
				}
				if (mediaType == null || mediaType.length == 0)
					return;
				fileUploadCallBack(function(url) {
					$(".media").text("");
					$(".media").append(
							mediaSetting[mediaType + 'Temp'].format(url));
					$("#forumMedia").val(url);
				}, 'picture', mediaSetting[mediaType + 'Size'],
						mediaSetting[mediaType + 'Type']);
			});
	$("#saveBtn").click(
			function() {
				function check(checkid, focusid, msg) {
					var panelValue = $("#" + checkid).val();
					if (!panelValue || typeof (panelValue) == 'undefined'
							|| panelValue == 'undefined'
							|| panelValue == 'null' || panelValue.length == 0) {
						alert(msg);
						$('#' + focusid).focus();
						return false;
					}
					return panelValue;
				}
				var forumId = $("#forumId").val();
				var title = check("forumTitle", "forumTitle", "请输入标题.");
				if (!title)
					return;
				var region = check("region", "regionName",
						"请选择地域.");
				if (!region)
					return;
				var author = check("authorId", "authorName", "请选择作者.");
				if (!author)
					return;
				var tag = check("forumTag", "forumTagName", "请选择标签.");
				if (!tag)
					return;
				var type = check("forumType", "forumTypeDesc", "请选择分类.");
				if (!type)
					return;
				var classify = check("forumClassify", "forumClassifyDesc",
						"请选择媒体资源类型.");
				if (!classify)
					return;
				var forumCover = check("forumCover", "forumCover",
						"请上传封面图片.");
				if (!forumCover)
					return;
				var status = check("status", "statusDesc",
						"请选择是否显示.");
				if (!status)
					return;
				var forumContent = $("#forumContent").html();
				if (classify == 1) {
					if (!forumContent || typeof (forumContent) == 'undefined'
						|| forumContent == 'undefined'
						|| forumContent == 'null' || forumContent.length == 0) {
						alert("请选择媒体资源类型.");
						return;
					}
				} else {
					var media = check("forumMedia", "forumMedia",
					"请上传媒体资源.");
					if (!media)
						return;
				}

				$.ajax({
					url : baseurl + '/knowledge/forum/' + action,
					data : {
						forumId : forumId,
						forumTitle : title,
						forumType : type,
						forumClassify : classify,
						forumCover : forumCover,
						forumContent : forumContent,
						forumMedia : media,
						region: region,
						authorId : author,
						forumTag : tag,
						status: status
					},
					type : 'post',
					dataType : "json",
					success : function(res) {
						if (res.retCode == '0') {
							alert("保存成功");
							action = "doEdit";
						} else {
							alert(res.retDesc);
						}
					}
				});
			});
	$("#showBtn").click(
			function() {
				var forumId = $("#forumId").val();
				if (null == forumId || '' == forumId
						|| typeof (forumId) == 'undefined') {
					alert("请先保存后再预览。");
					return;
				}
				window.open(baseurl + '/knowledge/forum/show?forumId='
						+ forumId, "_blank");
			});
	
	$("#forumCoverButton").click(function() {
		fileUploadCallBack(function(url) {
			$(".forumCover").attr("src",url);
			$("#forumCover").val(url);
		},'picture',3,'png,jpg,gif');
	});
	var editor = new Simditor({
		textarea : $('#editor'),
		toolbar : [ 'title', 'bold', 'italic', 'underline', 'strikethrough',
				'color', 'table', 'link', 'hr', 'ol', 'html', 'image'],
		toolbarFloat:true,
		emoji: {
			   imagePath:'/static/img/emoji/'
		},
	    upload:{
	        url:"http://upload.qiniu.com",
	        fileKey:"file",
	        params:{"token":uploadToken}
	    },
		pasteImage:true,
		toolbarFloatOffset : 2
	});

	function out() {
		this.initRight = function() {
			$(".choose li").removeClass('on');
			$(".rightMain").children().hide();
			$(".richText-box").show();
			$(".richText").addClass('on');
			$("#editor").val($(".content").html());
			$(".simditor-body").html($(".content").html());
		}
	}
	;

	var _out = new out();
	_out.initRight();
	window.mainjs = _out;

	$(document).ready(function($) {
		$('.dropdown-point').click(function() {
			var oldValue = $('#' + $(this).attr("valuePanel")).val();
			var newValue = $(this).attr("value");
			$('#' + $(this).attr("showPanel")).val($(this).text());
			$('#' + $(this).attr("valuePanel")).val(newValue);
			if (oldValue != newValue)
				$(".media").text("");
		});
	});

});