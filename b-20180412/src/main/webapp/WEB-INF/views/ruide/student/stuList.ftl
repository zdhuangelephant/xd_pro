<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        学员管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            学员列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <input type="text" class="form-control" id="keyword" name="keyword" placeholder="学员" value="${authorName}"/>
    </div>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="searchAuthor()" <a>搜索</a>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addAuthor('${authorName}')" <a>添加学员</a></form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th style="width: 200px;">学员姓名</th>
        <th style="width: 100px;">学员头像</th>
        <th style="width: 400px;">感悟简介</th>
        <th style="width: 400px;">发布时间</th>
        <th style="width: 400px;">相关专业</th>
         <th style="width: 400px;">正文图片(可选)</th>
        <th style="width: 100px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list studentList as student>
        <tr>
            <td>${student.author}</td>
            <td><img class="portrait" style="width:60px;height:60px" src="${student.portrait}" alt="${student.author}"></td>
            <td>${student.thinkDesc}</td>
            <td>${student.publishTime}</td>
            <td>${student.tutorMajorModel.title}</td>
            <td>
				<#if student.contentImage!''> 
					<img class="portrait" style="width:60px;height:60px" src="${student.contentImage}">
				<#else>
					暂无图片
				</#if>
			</td>
            <td><a style="cursor: pointer" onclick="editAuthor(${student.id})">修改</a>
            	<a style="padding-left: 5px;cursor: pointer;" onclick="remove(${student.id},'${student.author}')">删除</a>
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
        art.dialog.open('/student/edit?id='+id,{
            title: '编辑学员感悟',
            width:800,
            height:600,
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
    function addAuthor(authorName){
        art.dialog.open('/student/add', {
            title: '添加学员感悟',
            width:800,
            height:600,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                //iframe.document.getElementById('storeContent').value=iframe.document.getElementById('editor').value;
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
    
    function remove(id,name) {
    	if (confirm("确认要删除"+name+"?")) {
            $.post("/student/delete", {id:id},
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
