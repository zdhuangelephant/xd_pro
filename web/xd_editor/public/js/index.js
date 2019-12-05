var baseurl = location.protocol + "//" + location.host;

/**************************************************************************************************************************/
$(function () {

    /**************************************************** zTree menu func ********************************************************/
    var zTree, rMenu0, rMenu2_new, rMenu2_saved, rMenu3, eNode, code, className = "dark";
    var setting = {
        view: {
            //dblClickExpand: false,
            enable: true,
            chkStyle: "radio",
            radioType: "level"
            //addHoverDom: addHoverDom,
            //removeHoverDom: removeHoverDom
        },
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "level"
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "prefix",
                rootPId: ""
            }
        },
        callback: {
            onRightClick: OnRightClick,
            beforeCheck: beforeCheck
        }
    };

    function refreshTree(courseId0, courseName0) {
        $.ajax({
            url: baseurl + '/courseware/list',
            data: {
                courseId: courseId0,
                courseName: courseName0,
                id: id
            },
            type: 'post',
            dataType: "json",
            success: function (res) {
                if (res.retcode == '0') {
                    courseId = courseId0;
                    $.fn.zTree.init($("#treeDemo"), setting, res.nodes);
                    zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    rMenu0 = $("#rMenu0");
                    rMenu2_new = $("#rMenu2_new");
                    rMenu3 = $("#rMenu3");
                    checkNodeSelect(res.select);
                } else if (res.retcode == '-1') {
                    alert(res.retdesc);
                } else {
                }
            }
        });

    }

    function beforeCheck(treeId, treeNode) {
        className = (className === "dark" ? "" : "dark");
        return (treeNode.doCheck !== false);
    }

    function OnRightClick(event, treeId, treeNode) {
        if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
            zTree.cancelSelectedNode();
            showRMenu("root", event.clientX, event.clientY, treeNode);
        } else if (treeNode && !treeNode.noR) {
            zTree.selectNode(treeNode);
            showRMenu("node", event.clientX, event.clientY, treeNode);
        }
    }

    function showRMenu(type, x, y, node) {
        if (type == "root") {
            $("#m_add").hide();
            $("#m_mod").hide();
            $("#m_del").hide();
        } else {
            if (node.level == '0') {
                $("#rMenu0 ul").show();
                rMenu0.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
            }
            if (node.level == '3') {
                $("#rMenu3 ul").show();
                $("#m_mod").show();
                $("#m_del").show();
                rMenu3.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
            }
            if (node.level == '2') {
                $("#rMenu2_new ul").show();
                $("#m_add").show();
                rMenu2_new.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
            }
        }
        $("body").bind("mousedown", onBodyMouseDown);
    }

    function hideRMenu() {
        if (rMenu0) rMenu0.css({"visibility": "hidden"});
        if (rMenu2_new) rMenu2_new.css({"visibility": "hidden"});
        if (rMenu3) rMenu3.css({"visibility": "hidden"});
        $("body").unbind("mousedown", onBodyMouseDown);
    }

    function onBodyMouseDown(event) {
        if (!(event.target.id == "rMenu0" || $(event.target).parents("#rMenu0").length > 0)) {
            rMenu0.css({"visibility": "hidden"});
        }
        if (!(event.target.id == "rMenu2_new" || $(event.target).parents("#rMenu2_new").length > 0)) {
            rMenu2_new.css({"visibility": "hidden"});
        }
        if (!(event.target.id == "rMenu3" || $(event.target).parents("#rMenu3").length > 0)) {
            rMenu3.css({"visibility": "hidden"});
        }
    }

    $("#m_up").click(function () {
        var selectedNode = zTree.getSelectedNodes()[0];
        var preNode = selectedNode.getPreNode();
        if (preNode && preNode.id) {
            changeNode(selectedNode, preNode, "prev");
        }
    });

    $("#m_down").click(function () {
        var selectedNode = zTree.getSelectedNodes()[0];
        var nextNode = selectedNode.getNextNode();
        if (nextNode && nextNode.id) {
            changeNode(selectedNode, nextNode, "next");
        }
    });

    function changeNode(selectedNode, changeNode, pos) {
        hideRMenu();
        $.ajax({
            url: baseurl + '/courseware/change',
            data: {
                id: selectedNode.id,
                targetId: changeNode.id
            },
            type: 'post',
            dataType: "json",
            success: function (res) {
                if (res.retcode == '0') {
                    zTree.moveNode(changeNode, selectedNode, pos, false);
                } else if (res.retcode == '-1') {
                    alert(res.retdesc);
                } else {
                }
            }
        });
    }

    $("#u_upload").click(function () {
        hideRMenu();
        var selectedNode = zTree.getSelectedNodes()[0];
        upload_node(selectedNode, function (res) {
            if (res.retcode == '0') {
                refreshTree(courseId, zTree.getNodes()[0].name);
                alert("上传成功");
            } else
                alert(res.retdesc);
        });
    });

    $("#u_upload_all").click(function () {
        hideRMenu();
        //$("#progress").css({"position": "fixed", "top": "200px", "left": "600px", "visibility": "visible"});
        var nodes = zTree.getNodes();
        for (var i in nodes) {
            upload_node(nodes[i]);
        }
        window.location.reload();
    });

    function upload_node(node, callback) {
        if (parseInt(node.level) < 2) {
            for (var child in node.children)
                upload_node(node.children[child]);
        } else {
            $.ajax({
                url: baseurl + '/chapter/upload',
                data: {
                    id: node.id,
                    courseId: courseId,
                    name: node.name
                },
                type: 'post',
                dataType: "json",
                success: function (res) {
                    if (callback)
                        callback(res);
                    else
                        console.log(res);
                }
            });
        }
    }

    $("#m_add").click(function () {
        hideRMenu();
        var selectedNode = zTree.getSelectedNodes()[0];
        $.ajax({
            url: baseurl + '/courseware/add',
            data: {
                id: selectedNode.id,
                prefix: selectedNode.prefix,
                level: selectedNode.level
            },
            type: 'post',
            dataType: "json",
            success: function (res) {
                if (res.retcode == '0') {
                    var newNode = res.nodes;
                    if (selectedNode) {
                        zTree.addNodes(selectedNode, newNode);
                        zTree.cancelSelectedNode();
                        checkNodeSelect(newNode);
                        id = newNode.id;
                        prefix = newNode.prefix;
                        url = newNode.srcUrl;
                        eNode = newNode;
                        name = '';
                        $('#courseName').val(newNode.name);
                        loadContent("template/template.html");
                    } else {
                        zTree.addNodes(null, newNode);
                    }
                } else if (res.retcode == '-1') {
                    alert(res.retdesc);
                } else {
                }
            }
        });
    });

    $("#m_mod").click(function () {
        hideRMenu();
        var nodes = zTree.getSelectedNodes();
        if (nodes && nodes.length > 0) {
            var selectedNode = nodes[0];
            $.ajax({
                url: baseurl + '/courseware/edit',
                data: {
                    id: selectedNode.id,
                    level: selectedNode.level
                },
                type: 'post',
                dataType: "json",
                success: function (res) {
                    if (res.retcode == '0') {
                        window.location.href = baseurl + '/editor/main?id={0}&courseId={1}'.format(encodeURI(selectedNode.id), encodeURI($("#courseList").val()));
                    } else if (res.retcode == '-1') {
                        alert(res.retdesc);
                    } else {
                    }
                }
            });
            //zTree.removeNode(nodes[0]);
        }
    });

    $("#m_del").click(function () {
        hideRMenu();
        var nodes = zTree.getSelectedNodes();
        if (nodes && nodes.length > 0) {
            var selectedNode = nodes[0];
            if (window.confirm("你确认要删除```" + selectedNode.name + "```吗?")) {
                $.ajax({
                    url: baseurl + '/courseware/delete',
                    data: {
                        id: selectedNode.id,
                        level: selectedNode.level
                    },
                    type: 'post',
                    dataType: "json",
                    success: function (res) {
                        if (res.retcode == '0') {
                            refreshTree(courseId, zTree.getNodes()[0].name);
                        } else if (res.retcode == '-1') {
                            alert(res.retdesc);
                        } else {
                        }
                    }
                });
            }
        }
    });

    function checkNodeSelect(node) {
        if (node && node.id) {
            var select = zTree.getNodesByParam("id", node.id, null);
            if (select && select.length > 0) {
                zTree.selectNode(select[0]);
            }
        }
        return;
    }


    var the;
    var type = "richText";
    $('#courseName').val(name);

    function initRight() {
        var yyType = the.attr("yy-type");
        type = yyType;
        if (yyType != null && yyType != "") {
            $(".choose li").removeClass('on');
            var str = "." + yyType + "-box";
            $(".rightMain").children().hide();
            $(str).show();
            $("." + yyType).addClass('on');
        }
        $("#editor").val(the.html());
        $(".simditor-body").html(the.html());
        $("#img-preview-src").css("width", "445px");
        $("#img-preview-src").attr("src", the.find("img").attr("src"));
    }

    $("#courseBtn").click(function () {
        var course = $("#courseList").find("option:selected");
        if (course == null || course == undefined) {
            alert("请先选择课程.")
            return;
        }
        refreshTree(course.val(), course.text());
    });
    $(".addPic").click(function () {
        if (!$("#img-file").val() || typeof($("#img-file").val()) == "undefined") {
            alert("File can't be null");
            return;
        }
        $("#upload-pic-form").up2Server(function (res) {
            if (res.retcode == '0') {
                $("#img-preview-src").attr("src", res.image.src);
            } else if (res.retcode == '-1') {
                alert(res.retdesc);
            } else {
            }
        });
    });
    $("#enterBtn").click(function () {
        if ($(".itemBox").val().length == 0) {
            alert("节名称不能为空");
            $(".itemBox").focus();
            return;
        } else {
            $("[yy-type=item]").html($(".itemBox").val());
        }
        if ($(".keyPointBox").val().length == 0) {
            alert("考点名不能为空");
            $(".keyPointBox").focus();
            return;
        } else {
            $("[yy-type=keyPoint]").html($(".keyPointBox").val());
        }
        if (the && typeof(the) != 'undefined') {
            if (type == "img") {
                the.find("img").attr("src", $("#img-preview-src").attr("src"));
            } else if (type == "richText") {
                the.html($('#editor').val());
            }
            the.attr("yy-type", type);
            $(".yy-click").css('border', 'none');
        }
    });
    $("#deleteBtn").click(function () {
        if (the && typeof(the) != 'undefined') {
            the.remove();
        }
    });
    $("#saveBtn").click(function () {
        $(".yy-click").css('border', 'none');
        if (!id || !prefix || typeof(id) == "undefined" || typeof(prefix) == "undefined") {
            alert("不能保存模板!");
            return;
        }
        if (!name || typeof(name) == 'undefined' || name == 'undefined' || name == 'null' || name.length == 0) {
            alert("请输入一个文件名.");
            $('#courseName').focus();
            return;
        }
        var templateId = 1;
        var data = $("#page1").html();
        $.ajax({
            url: baseurl + '/editor/save',
            data: {
                id: id,
                templateId: templateId,
                name: name,
                prefix: prefix,
                data: data
            },
            type: 'post',
            dataType: "json",
            success: function (res) {
                if (res.retcode == '0') {
                    alert("保存成功");
                    refreshTree(courseId, zTree.getNodes()[0].name);
                    // window.location.href = baseurl + "/editor/main?id={0}&courseId={1}".format(encodeURI(id), encodeURI(courseId));
                } else if (res.retcode == '-1') {
                    alert(res.retdesc);
                } else {
                }
            }
        });
    });

    var editor = new Simditor({
        textarea: $('#editor'),
        toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough',
            'color', 'table', 'link', 'hr', 'ol', 'html'],
        toolbarFloat: true,
        toolbarFloatOffset: 2
    });
    $(".courseBox").keyup(function () {
        name = $(".courseBox").val();
    });
    $(".itemBox").keyup(function () {
        if ($(".itemBox").val().length == 0) {
            alert("节名称不能为空");
            $(".itemBox").focus();
            return;
        }
        $("[yy-type=item]").html($(".itemBox").val());
    });

    $(".keyPointBox").keyup(function () {
        if ($(".keyPointBox").val().length == 0) {
            alert("考点名不能为空");
            $(".keyPointBox").focus();
            return;
        }
        $("[yy-type=keyPoint]").html($(".keyPointBox").val());
    });

    function out() {
        function click(element) {
            $(".yy-click").css('border', 'none');
            $(".yy-click").css('border-radius', 'none');
            the = element;
            element.css('border', '2px solid red');
            element.css('border-radius', '5px');
            initRight();
        }

        this.refreshEditor = function () {
            $(".itemBox").val($("[yy-type=item]").html());
            $(".keyPointBox").val($("[yy-type=keyPoint]").html());
            $(".yy-click").click(function () {
                click($(this));
            });
            $("[yy-click]").click(function () {
                click($(this));
            });
        };
    };

    var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');

    function uuid(len, radix) {
        var chars = CHARS, uuid = [], i;
        radix = radix || chars.length;

        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
        } else {
            // rfc4122, version 4 form
            var r;

            // rfc4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';

            // Fill in random data.  At i==19 set the high bits of clock sequence as
            // per rfc4122, sec. 4.1.5
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }

        return uuid.join('');
    };

    String.prototype.format = function (args) {
        var result = this;
        if (arguments.length > 0) {
            if (arguments.length == 1 && typeof (args) == "object") {
                for (var key in args) {
                    if (args[key] != undefined) {
                        var reg = new RegExp("({" + key + "})", "g");
                        result = result.replace(reg, args[key]);
                    }
                }
            }
            else {
                for (var i = 0; i < arguments.length; i++) {
                    if (arguments[i] != undefined) {
                        //var reg = new RegExp("({[" + i + "]})", "g");//这个在索引大于9时会有问题，谢谢何以笙箫的指出
                        var reg = new RegExp("({)" + i + "(})", "g");
                        result = result.replace(reg, arguments[i]);
                    }
                }
            }
        }
        return result;
    }

    var elements = {
        text: '<div class="point yy-click auto-read" yy-type="richText" id="{0}" style="border: 1px solid red;"><h2>古代小学教育</h2><ol><li>等级性</li><li>为统治阶级服务</li><li>对儿童进行管制与灌输，采用刻板的识记、背诵教育方式。</li></ol><hr><h2>近代小学教育</h2><ol><li>注重培养学生身心健康</li><li>教育方法多样化</li><li>内容世俗化，课程设置更加科学</li><li>对象普及化，女孩获得教育权利</li><li>教育体系科学化，幼稚园纳入教育体系</li></ol><hr> <h2>当代小学教育</h2> <ol> <li>教育目的注重以人为本，以培养全面发展的人为核心，学科内容的设置和教学方法更加注重儿童的身心发展</li> <li>教育对象更为普及，义务教育在许多地方得到全面实施</li> <li>重视教师发展，教师制度逐渐规范化</li> <li>教育实验涉及的方面更广泛</li> </ol> </div>',
        pic: '<div class="yy-click auto-read picture" yy-type="img" id="{0}" style="border: 1px solid red;"><img src="images/tu.png" ></div>'
    };
    var select = null;
    var location = null;
    $("#addBtn").click(function () {
        $(".elementGroup").css("display", "block");
    });

    $("[group=element]").click(function () {
        $("[group=element]").removeClass("Btn_select");
        $(this).addClass("Btn_select");
        select = elements[$(this).attr("type")];
        location = $(this).attr("loc");
        $("#addSubmitBtn").addClass("Btn_common");
    });

    $("#addSubmitBtn").click(function () {
        if (select && location) {
            add(select, location);
            cancel();
        }
    });

    $("#addResetBtn").click(function () {
        reset();
    });

    $("#addCancelBtn").click(function () {
        cancel();
    });

    function reset(){
        select = null;
        location = null;
        $("#addSubmitBtn").removeClass("Btn_common");
        $(".elementGroup .Btn_common").removeClass("Btn_select");
    }

    function cancel(){
        reset();
        $(".elementGroup").css("display", "none");
    }

    function add(type, location) {
        $(".yy-click").css('border', 'none');
        var id = uuid();
        var command = "the." + location + "(type.format(id));";
        eval(command);
        _out.refreshEditor();
        $("#" + id).click();
        return;
    }

    var _out = new out();
    _out.refreshEditor();
    window.mainjs = _out;

});

/*** zTree ***/
$(document).ready(function ($) {
    $.ajax({
        url: baseurl + '/course/list',
        data: {
            type: "moudle",
            moudle: 1,
            id: id
        },
        type: 'post',
        dataType: "json",
        success: function (res) {
            if (res.retcode == '0') {
                $("#courseList").html("");
                for (var i in res.courseList) {
                    var option = $("<option>").val(res.courseList[i].id).text(res.courseList[i].name);
                    $("#courseList").append(option);
                }
                if (courseId != null && courseId != undefined) {
                    $("#courseList").val(courseId);
                    $("#courseBtn").click();
                }
            } else if (res.retcode == '-1') {
                alert(res.retdesc);
            } else {
            }
        }
    });

    setTimeout('$(".loading-page").css("display","none");$(".box-page").fadeOut("slow");$(".box-page").attr("class", "box-capture");', 1000);
});