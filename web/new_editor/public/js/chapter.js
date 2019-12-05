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
    $("#m_add").hide();
    $("#m_mod").hide();
    $("#m_del").hide();
    if (parseInt(node.level) == '0') {
        $("#rMenu4 ul").show();
        $("#m_add").show();
        rMenu4.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
    }else if (parseInt(node.level) < '4') {
        $("#rMenu4 ul").show();
        $("#m_add").show();
        $("#m_mod").show();
        $("#m_del").show ();
        rMenu4.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
    }else{
        $("#rMenu4 ul").show();
        $("#m_mod").show();
        $("#m_del").show ();
        rMenu4.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
    }
    $("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
    if (rMenu4) rMenu4.css({"visibility": "hidden"});
    $("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event) {
    if (!(event.target.id == "rMenu4" || $(event.target).parents("#rMenu4").length > 0)) {
        rMenu4.css({"visibility": "hidden"});
    }
}
function changeNode(newNode) {
    id = newNode.id;
    prefix = newNode.prefix;
    level = newNode.level;
    eNode = newNode;
    name = newNode.name;
    $('#chapterName').val(name);
}
function addTreeNode() {
    hideRMenu();
    var selectedNode = zTree.getSelectedNodes()[0];
    $.ajax({
        url: baseurl + '/chapter/add',
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
                    zTree.selectNode(newNode);
                    changeNode(newNode);
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
        changeNode(selectedNode);
        console.log("editTreeNode");
    }
}

function deleteTreeNode() {
    hideRMenu();
    var nodes = zTree.getSelectedNodes();
    if (nodes && nodes.length > 0) {
        var selectedNode = nodes[0];
        if (window.confirm("确认要删除" + selectedNode.name + "吗?")) {
            console.log("deleteTreeNode");
            $.ajax({
                url: baseurl + '/chapter/delete',
                data: {
                    id: selectedNode.id,
                    level: selectedNode.level
                },
                type: 'post',
                dataType: "json",
                success: function (res) {
                    alert(res);
                    if (res.retcode == '0') {
                        zTree.removeNode(selectedNode);
                    } else if (res.retcode == '-1') {
                        alert(res.retdesc);
                    }
                    {
                    }
                }
            });
        }
    }
}
function checkNodeSelect(node) {
    if (node.isSelected) {
        zTree.selectNode(node);
        return;
    }
    for (var child in node.children) {
        checkNodeSelect(child);
    }
}

/**************************************************************************************************************************/

function changeId(value) {
    name = value;
}

$(function () {
    $("#saveBtn").click(function () {
        if (!id || !prefix || typeof(id) == "undefined" || typeof(prefix) == "undefined") {
            alert("不能保存模板!");
            return;
        }
        if (!name || typeof(name) == 'undefined' || name == 'undefined' || name == 'null' || name.length == 0) {
            alert("请输入一个文件名.");
            $('#chapterName').focus();
            return;
        }
        var templateId = 1;
        var data = $("#screen").html();
        $.ajax({
            url: baseurl + '/editor/save',
            data: {
                id: id,
                name: name,
                prefix: prefix,
                level: level
            },
            type: 'post',
            dataType: "json",
            success: function (res) {
                if (res.retcode == '0') {
                    alert("保存成功");
                    window.location.reload();
                    //window.location.href = baseurl + "/chapterlist.html";
                } else if (res.retcode == '-3') {
                    if (window.confirm(res.retdesc)) {
                        recoverNode();
                    }
                } else if (res.retcode == '-1') {
                    alert(res.retdesc);
                } else {
                }
            }
        });
    });
    function recoverNode() {
        $.ajax({
            url: baseurl + '/editor/recover',
            data: {
                id: id,
                name: name,
                prefix: prefix,
                level: level
            },
            type: 'post',
            dataType: "json",
            success: function (res) {
                if (res.retcode == '0') {
                    alert("章节恢复成功.");
                    window.location.reload();
                } else if (res.retcode == '-1') {
                    alert(res.retdesc);
                } else {
                }
            }
        });
    }
});

/*** zTree ***/
$(document).ready(function () {
    var html = '';
    $.ajax({
        url: baseurl + '/chapter/list',
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
                checkNodeSelect(res.nodes);
            } else if (res.retcode == '-1') {
                alert(res.retdesc);
            } else {
            }
        }
    });
});
