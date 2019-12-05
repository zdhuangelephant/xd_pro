<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        专业管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
           专业列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <input type="text" class="form-control" id="keyword" name="keyword" placeholder="作者" value="${authorName}"/>
    </div>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="searchAuthor()" <a>搜索</a>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addAuthor('${authorName}')" <a>添加专业</a></form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th style="width: 200px;">作者</th>
        <th style="width: 100px;">专业名称</th>
        <th style="width: 100px;">专业类别</th>
        <th style="width: 100px;">专业配图</th>
        <th style="width: 400px;">标题</th>
        <th style="width: 400px;">副标题</th>
        <th style="width: 400px;">发布时间</th>
        <th style="width: 100px;">正文图片(可选)</th>
        <th style="width: 100px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list professionList as profession>
        <tr>
            <td>${profession.author}</td>
            <td>${profession.majorName}</td>
            <td>${profession.majorCategoryModel.majorCategory}</td>
            <td><img class="portrait" style="width:60px;height:60px" src="${profession.image}" alt="${profession.author}"></td>
            <td>${profession.title}</td>
            <td>${profession.subtitle}</td>
            <td>${profession.publishTime}</td>
            <td>
				<#if profession.contentImage!''> 
					<img class="portrait" style="width:60px;height:60px" src="${profession.contentImage}">
				<#else>
					暂无图片
				</#if>
			</td>
            <td><a style="cursor: pointer" onclick="editAuthor(${profession.id})">修改</a>
            	<a style="padding-left: 5px;cursor: pointer;" onclick="remove(${profession.id},'${profession.author}')">删除</a>
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
        art.dialog.open('/tutorMajor/profession/edit?id='+id,{
            title: '编辑专业',
            width:600,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
               // iframe.document.getElementById('storeContent').value=iframe.document.getElementById('editor').value;
                var form = iframe.document.getElementById('editForm');
             // 进行非空判断
                if(!myCheck(iframe)){
                	return false;
                };
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
    	/* if(typeof(authorName)=='undefined'||""==authorName){
    		alert("添加前请先查询导师是否存在");
    		return;
    	} */
        art.dialog.open('/tutorMajor/profession/add', {
            title: '添加专业',
            width:800,
            height:600,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
               // iframe.document.getElementById('storeContent').value=iframe.document.getElementById('editor').value;
             // 进行非空判断
                if(!myCheck(iframe)){
                	return false;
                };
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
    function myCheck(iframe){
    	var flag = true;
    	$("input[name]",iframe.document).each(function(){
    		if($(this).attr("name")=="contentImage") return true;
    		if($(this).val()==""){
 				alert("表单不允许有空选项,请填写完整");
 				$(this).focus();
 				flag= false;
 				return flag;
 		  	}
    	});
 	   return flag;
 	} 
    function remove(id,name) {
    	if (confirm("确认要删除"+name+"?")) {
            $.post("/tutorMajor/profession/delete", {id:id},
                function(data){
            		data=JSON.parse(data);
                    if(data.retCode==0){
                        alert("删除成功");
                    } else {
                        alert("删除失败");
                    }
                    location.reload();
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
