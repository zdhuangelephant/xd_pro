var baseurl = location.protocol + "//" + location.host;

/**************************************************** zTree menu func ********************************************************/
var zTree, rMenu4, rMenu5, eNode, code, className = "dark";
var setting = {
    view: {
        //dblClickExpand: false,
        enable: true,
        chkStyle: "radio",
        radioType: "level"
    },
    check: {
        enable: true,
        chkStyle: "radio",
        radioType: "level"
    },
    callback: {
        onRightClick: OnRightClick,
        beforeCheck: beforeCheck
    }
};
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
        if (node.level == '5') {
            $("#rMenu5 ul").show();
            $("#m_mod").show();
            $("#m_del").show();
            rMenu5.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
        }
        if (node.level == '4') {
            $("#rMenu4 ul").show();
            $("#m_add").show();
            rMenu4.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
        }
    }
    $("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
    if (rMenu4) rMenu4.css({"visibility": "hidden"});
    if (rMenu5) rMenu5.css({"visibility": "hidden"});
    $("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event) {
    if (!(event.target.id == "rMenu4" || $(event.target).parents("#rMenu4").length > 0)) {
        rMenu4.css({"visibility": "hidden"});
    }
    if (!(event.target.id == "rMenu5" || $(event.target).parents("#rMenu5").length > 0)) {
        rMenu5.css({"visibility": "hidden"});
    }
}

function upTreeNode() {
    var selectedNode = zTree.getSelectedNodes()[0];
    var preNode = selectedNode.getPreNode();
    if (preNode && preNode.id) {
        changeNode(selectedNode, preNode, "prev");
    }
}
function downTreeNode() {
    var selectedNode = zTree.getSelectedNodes()[0];
    var nextNode = selectedNode.getNextNode();
    if (nextNode && nextNode.id) {
        changeNode(selectedNode, nextNode, "next");
    }
}
function topTreeNode() {
    var selectedNode = zTree.getSelectedNodes()[0];
    var preNode = selectedNode.getPreNode();
    while (preNode.getPreNode() && preNode.getPreNode().id) {
        preNode = preNode.getPreNode();
    }
    if (preNode && preNode.id) {
        changeNode(selectedNode, preNode, "prev");
    }
}
function footTreeNode() {
    var selectedNode = zTree.getSelectedNodes()[0];
    var nextNode = selectedNode.getNextNode();
    while (nextNode.getNextNode() && nextNode.getNextNode().id) {
        nextNode = nextNode.getNextNode();
    }
    if (nextNode && nextNode.id) {
        changeNode(selectedNode, nextNode, "next");
    }
}
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

function download() {
    hideRMenu();
    var selectedNode = zTree.getSelectedNodes()[0];
    window.open(baseurl + '/chapter/download?id=' + selectedNode.id, "_blank");
}

function addTreeNode() {
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
            console.log(res);
            if (res.retcode == '0') {
                var newNode = res.nodes;
                console.log(newNode);
                if (selectedNode) {
                    zTree.addNodes(selectedNode, newNode);
                    zTree.cancelSelectedNode();
                    checkNodeSelect(newNode);
                    id = newNode.id;
                    prefix = newNode.prefix;
                    url = newNode.srcUrl;
                    eNode = newNode;
                    name = '';
                    $('#courseName').val(name);
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
}
function editTreeNode() {
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
                    console.log(baseurl + selectedNode.url);
                    window.location.href = baseurl + '/editor/main?id=' + encodeURI(selectedNode.id);
                } else if (res.retcode == '-1') {
                    alert(res.retdesc);
                } else {
                }
            }
        });
        //zTree.removeNode(nodes[0]);
    }
}
function deleteTreeNode() {
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
                        zTree.removeNode(selectedNode);
                    } else if (res.retcode == '-1') {
                        alert(res.retdesc);
                    } else {
                    }
                }
            });
        }
    }
}
function checkNodeSelect(node) {
    if (node && node.id) {
        var select = zTree.getNodesByParam("id", node.id, null);
        if (select && select.length > 0) {
            zTree.selectNode(select[0]);
        }
    }
    return;
}

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

    function getImgList(callback) {
        $.ajax({
            url: '/chapter/imglist',
            data: {
                prefix: prefix
            },
            type: "post",
            dataType: "json",
            success: function (res) {
                if (res.retcode == "0") {
                    callback(res.list);
                } else {
                    callback(null);
                }
            }
        });
    }

    function initImgLst(data) {
        if (data && data.length > 0) {
            var imgHtml = '<option value=""></option>';
            for (var i in data) {
                imgHtml += '<option value ="' + data[i].src + '">' + data[i].name + '</option>';
            }
            $('#imgList').html(imgHtml);
        }
    }

    getImgList(initImgLst);

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
        $("#imageInput").val(the.find("img").attr("src"));
        $("#firstview").attr("src", the.find("img").attr("src"));
        var options = $('#imgList')[0].options;
        for (var option in options) {
            if (options[option].value == the.find("img").attr("src")) {
                options[option].selected = 'selected';
                break;
            }
        }
    }

    $("#test1").click(function () {
        the = $(this);
        initRight();
    });
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
    $("#deleteBtn").click(function () {
        if (the && typeof(the) != 'undefined') {
            console.log("delete:" + the)
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
                    window.location.href = baseurl + "/editor/main?id=" + encodeURI(id);
                } else if (res.retcode == '-1') {
                    alert(res.retdesc);
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
        textarea: $('#editor'),
        toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough',
            'color', 'blockquote', 'code', 'table',
            'link', 'image', 'hr', 'indent', 'outdent'],
        toolbarFloat: true,
        toolbarFloatOffset: 2
    });

    function out() {
        function click() {
            $(".yy-click").css('border', 'none');
            the = $(this);
            $(this).css('border', '1px solid red');
            initRight();
        }

        this.refreshEditor = function () {
            $(".yy-click").click(function () {
                $(".yy-click").css('border', 'none');
                the = $(this);
                $(this).css('border', '1px solid red');
                initRight();
            });
            $("[yy-click]").click(function () {
                $(".yy-click").css('border', 'none');
                the = $(this);
                $(this).css('border', '1px solid red');
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
                $(this).css('border', '1px solid red');
                $(this).after(wordTmp);
                initRight();
            });
            $("[yy-click]").click(function () {
                $(".yy-click").css('border', 'none');
                the = $(this);
                $(this).css('border', '1px solid red');
                initRight();
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

    var wordTmp = '<div class="point yy-click" id="{0}" style="border: 1px solid red;"><h2>古代小学教育</h2><ol><li>等级性</li><li>为统治阶级服务</li><li>对儿童进行管制与灌输，采用刻板的识记、背诵教育方式。</li></ol><hr><h2>近代小学教育</h2><ol><li>注重培养学生身心健康</li><li>教育方法多样化</li><li>内容世俗化，课程设置更加科学</li><li>对象普及化，女孩获得教育权利</li><li>教育体系科学化，幼稚园纳入教育体系</li></ol><hr> <h2>当代小学教育</h2> <ol> <li>教育目的注重以人为本，以培养全面发展的人为核心，学科内容的设置和教学方法更加注重儿童的身心发展</li> <li>教育对象更为普及，义务教育在许多地方得到全面实施</li> <li>重视教师发展，教师制度逐渐规范化</li> <li>教育实验涉及的方面更广泛</li> </ol> </div>';
    var imgTmp = '<div class="yy-click picture" id="{0}" yy-type="img" style="border: 1px solid red;"><img src="images/tu.png" ></div>';

    $("#addBtn").click(function () {
        var word = window.confirm("你要插入一段文字吗?");
        if (word) {
            add(wordTmp);
            return;
        }
        var img = window.confirm("你要插入一张图片吗?");
        if (img) {
            add(imgTmp);
            return;
        }
    });

    function add(type) {
        var insert = window.confirm("你要在前面插入吗?");
        if (insert) {
            $(".yy-click").css('border', 'none');
            var id = uuid();
            the.before(type.format(id));
            _out.refreshEditor();
            $("#" + id).click();
            return;
        }
        var insert = window.confirm("你要在后面插入吗?");
        if (insert) {
            $(".yy-click").css('border', 'none');
            var id = uuid();
            the.before(type.format(id));
            _out.refreshEditor();
            $("#" + id).click();
            return;
        }
    }

    $('#imgList').change(function () {
        var object = $('#imgList').val();
        $("#firstview").attr("src", object);
        $("#imageInput").val(object);
    });
    var _out = new out();
    _out.refreshEditor();
    window.mainjs = _out;

    /*** 设置图片下拉框 ***/
    console.log($("#imgList").html());
})
;

/*** zTree ***/
$(document).ready(function () {
    var html = '';
    $.ajax({
        url: baseurl + '/courseware/list',
        data: {
            type: "moudle",
            moudle: 1,
            id: id
        },
        type: 'post',
        dataType: "json",
        success: function (res) {
            if (res.retcode == '0') {
                $.fn.zTree.init($("#treeDemo"), setting, res.nodes);
                zTree = $.fn.zTree.getZTreeObj("treeDemo");
                rMenu4 = $("#rMenu4");
                rMenu5 = $("#rMenu5");
                console.log(res.select);
                checkNodeSelect(res.select);
            } else if (res.retcode == '-1') {
                alert(res.retdesc);
            } else {
            }
        }
    });
});
