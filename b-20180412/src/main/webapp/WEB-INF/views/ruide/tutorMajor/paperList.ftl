<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        论文/文章管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            论文列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <input type="text" class="form-control" id="keyword" name="keyword" placeholder="论文标题" value="${titleName}"/>
    </div>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="searchAuthor()" <a>搜索</a>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addAuthor('${titleName}')" <a>添加导师</a></form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th style="width: 400px;">论文标题</th>
        <th style="width: 400px;">作者姓名</th>
        <th style="width: 400px;">发布时间</th>
        <th style="width: 100px;">正文图片(可选)</th>
        <th style="width: 100px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list paperList as paper>
        <tr>
            <td>${paper.title}</td>
            <td>${paper.author}</td>
            <td>${paper.publishTime}</td>
            <td>
				<#if paper.contentImage!''> 
					<img class="portrait" style="width:60px;height:60px" src="${paper.contentImage}">
				<#else>
					暂无图片
				</#if>
			</td>
            <td><a style="cursor: pointer" onclick="editAuthor(${paper.id})">修改</a>
            	<a style="padding-left: 5px;cursor: pointer;" onclick="remove(${paper.id},'${paper.title}')">删除</a>
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
        art.dialog.open('/tutorMajor/paper/edit?id='+id,{
            title: '编辑文章',
            width:600,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                //iframe.document.getElementById('storeContent').value=iframe.document.getElementById('editor').value;
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
    function addAuthor(titleName){
    	/* if(typeof(titleName)=='undefined'||""==titleName){
    		alert("添加前请先查询导师是否存在");
    		return;
    	} */
        art.dialog.open('/tutorMajor/paper/add', {
            title: '添加文章',
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
            $.post("/tutorMajor/paper/delete", {id:id},
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
		var titleName = $("#keyword").val();
		if(typeof(titleName)!='undefined'&&""!=titleName){
			url+="?titleName="+base64encode(utf16to8(encodeURIComponent(titleName)));
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
