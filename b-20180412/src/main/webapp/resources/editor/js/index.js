var baseurl = location.protocol + "//" + location.host;

/**************************************************************************************************************************/

function changeId(value) {
    name = value;
}

function changeImg(object) {
    mainjs.changeImg(object);
}

function modifyChapter() {
    var url = baseurl + "/chapterlist.html";
    window.open(url, "_blank");
}

$(function () {
    var the;
    var type = "content";
    $('#courseName').val(name);
	
    function initRight() {
        var yyType = the.attr("yy-type");
        type = yyType;
        if (yyType != null && yyType != "") {
            $(".choose li").removeClass('on');
            var str = "." + yyType + "Box";
            $(".rightMain").children().hide();

            console.log("show:" + str)
            $(str).show();
            $("." + yyType).addClass('on');
        }

        $("#contentInput").val(the.html());
        $(".simditor-body").html(the.html());
    }
    $("#enterBtn").click(function () {
        console.log("type:" + type)
        if (the && typeof(the) != 'undefined') {
            if (type == "content") {
                the.html($("#contentInput").val());
            } else if (type == "img") {
                the.find("img").attr("src", $("#imageInput").val());
            } else if (type == "richText") {
                the.html($('#editor').val());
            }
            the.attr("yy-type", type);
            $(".yy-click").css('border', 'none');
        }
    });
    $("#saveBtn").click(function () {
		if (!action || typeof(action) == "undefined") {
			alert("操作类型无法识别,请从新打开编辑!");
            return;
		}
        if (!id || typeof(id) == "undefined") {
            alert("公告无法识别,请从新打开编辑!");
            return;
        }
        if (!name || typeof(name) == 'undefined' || name == 'undefined' || name == 'null' || name.length == 0) {
            alert("公告名不能为空.");
            $('#courseName').focus();
            return;
        }
        var templateId = 1;
        var data = $("#page").html();
        $.ajax({
            url: baseurl + '/userNotice/'+action,
            data: {
                id: id,
                title: name,
                content: data
            },
            type: 'post',
            dataType: "json",
            success: function (res) {
                if (res.retCode == '0') {
                    alert("保存成功");
                } else if (res.retCode == '-1') {
                    alert(res.retDesc);
                } else {
                }
            }
        });
    });

    //******************************选项卡
    $(".choose li").click(function () {
        type = $(this).attr("data-type");
        console.log("click:" + type);
        $(".choose li").removeClass('on');
        var str = "." + $(this).attr('class') + "Box";
        $(".rightMain").children().hide();
        $(str).show();
        $(this).addClass('on');
    });

	var editor = new Simditor({
		textarea : $('#editor'),
		toolbar : ['title', 'bold', 'italic', 'underline', 'strikethrough',
		            'color', 'blockquote', 'code', 'table',
		            'link', 'image', 'hr', 'indent', 'outdent'],
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
        function click() {
            $(".yy-click").css('border', 'none');
            the = $(this);
            initRight();
        }

        this.refreshEditor = function () {
            $(".yy-click").click(function () {
                $(".yy-click").css('border', 'none');
                the = $(this);
                initRight();
            });
            $("[yy-click]").click(function () {
                $(".yy-click").css('border', 'none');
                the = $(this);
                initRight();
            });
            $("#title").click(function () {
                the = $(this);
                initRight();
            });
            $("#knowledgePoint").click(function () {
                the = $(this);
                initRight();
            });
        };
        function refreshEditor() {
            $(".yy-click").click(function () {
                $(".yy-click").css('border', 'none');
                the = $(this);
                $(this).after(wordTmp);
                initRight();
            });
            $("[yy-click]").click(function () {
                $(".yy-click").css('border', 'none');
                the = $(this);
                initRight();
            });
        };
    };

    var _out = new out();
    _out.refreshEditor();
    window.mainjs = _out;
	
	//loadContent(url);
})
;