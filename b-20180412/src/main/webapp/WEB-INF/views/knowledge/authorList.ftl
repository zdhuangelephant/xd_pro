<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        作者管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            作者列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <input type="text" class="form-control" id="keyword" name="keyword" placeholder="作者" value="${authorName}"/>
    </div>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="searchAuthor()" <a>搜索</a>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addAuthor('${authorName}')" <a>添加作者</a></form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 200px;">
            ID
        </th>
        <th style="width: 200px;">作者姓名</th>
        <th style="width: 100px;">作者头像</th>
        <th style="width: 400px;">作者描述</th>
        <th style="width: 100px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list authorList as author>
        <tr>
            <td>${author.id}</td>
            <td>${author.name}</td>
            <td><img class="portrait" style="width:60px;height:60px" src="${author.portrait}" alt="${author.name}"></td>
            <td>${author.info}</td>
            <td><a style="cursor: pointer" onclick="editAuthor(${author.id})">修改</a>
            	<#if author.worksNum == 0><a style="padding-left: 5px;cursor: pointer;" onclick="remove(${author.id},'${author.name}')">删除</a></#if>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<script>
    /**
     * 编辑栏目
     * @param catId
     */
    function editAuthor(id){
        art.dialog.open('/knowledge/author/edit?id='+id,{
            title: '编辑作者信息',
            width:400,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('editForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }

    /**
     * 添加栏目
     */
    function addAuthor(authorName){
    	if(typeof(authorName)=='undefined'||""==authorName){
    		alert("添加前请先查询作者是否存在。");
    		return;
    	}
        art.dialog.open('/knowledge/author/add', {
            title: '添加作者信息',
            width:400,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }
    
    function remove(id,name) {
    	if (confirm("确认要删除"+name+"?")) {
            $.post("/knowledge/author/delete", {id:id},
                function(data){
                	data=eval('(' + data + ')');
                    if(data.retCode==0){
                        alert("删除成功");
                    	location.reload();
                    } else {
                        alert("删除失败");
                    }
                }
            );
        }
    }
    
    function cancel() {
    	editor.back();
    }
    
    function searchAuthor(){
    	var url = window.location.pathname;
		var authorName = $("#keyword").val();
		if(typeof(authorName)!='undefined'&&""!=authorName){
			url+="?authorName="+base64encode(utf16to8(encodeURIComponent(authorName)));
		}	
        
        self.location=url;
	}
	
	function uploadProtrait() {
		fileUploadCallBack(function(url) {
					$(".portrait").attr("src",url);
				},'picture',3,'jpg,jpeg');
	}
</script>

	<@fileUpload></@fileUpload>

</@htmlBody>
