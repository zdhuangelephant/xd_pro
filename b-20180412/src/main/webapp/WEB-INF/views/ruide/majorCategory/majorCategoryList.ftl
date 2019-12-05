<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
	<h1>
		专业类别管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
			专业类别列表
		</small>
	</h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
	<div class="form-group">
		<input type="text" class="form-control" id="keyword" name="keyword"
			placeholder="专业类别" value="${majorCategory}" />
	</div>
	<a class="btn btn-primary"
		style="border: 0px; width: 90px; margin-left: 10px;"
		onclick="searchAuthor()" <a>搜索</a>
		<a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addAuthor('${majorCategory}')" <a>添加专业类别</a>
</form>

<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th style="width: 200px;">专业类别名称</th>
			<th style="width: 100px;">配图</th>
			<th style="width: 200px;">备注</th>
			<th style="width: 400px;">创建时间</th>
			<th style="width: 100px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<#list majorCategoryList as majorCategory>
		<tr>
			<td>${majorCategory.majorCategory}</td>
			<td><img class="portrait" style="width: 60px; height: 60px"
				src="${majorCategory.image}"></td>
			<td>${majorCategory.remark}</td>
			<td>${majorCategory.createTime}</td>
			<td><a style="cursor: pointer"
				onclick="editAuthor(${majorCategory.id})">修改</a> <a
				style="padding-left: 5px; cursor: pointer;"
				onclick="remove(${majorCategory.id},'${majorCategory.majorCategory}')">删除</a>
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
        art.dialog.open('/majorCategory/edit?id='+id,{
            title: '编辑专业类别',
            width:400,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
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
    function addAuthor(majorCategory){
    	
        art.dialog.open('/majorCategory/add', {
            title: '添加专业类别',
            width:400,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
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
            $.post("/majorCategory/delete", {id:id},
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
    function cancel() {
    	editor.back();
    }
    
    function searchAuthor(){
    	var url = window.location.pathname;
		var majorCategory = $("#keyword").val();
		if(typeof(majorCategory)!='undefined'&&""!=majorCategory){
			url+="?majorCategory="+base64encode(utf16to8(encodeURIComponent(majorCategory)));
		}	
        
        self.location=url;
	}
	
	function uploadProtrait() {
		fileUploadCallBack(function(url) {
					$(".portrait").attr("src",url);
				},'picture',3,'jpg,jpeg');
	}
</script>

<@fileUpload></@fileUpload> </@htmlBody>
