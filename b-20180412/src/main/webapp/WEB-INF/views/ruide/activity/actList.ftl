<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
	<h1>
		线下活动管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
			活动列表
		</small>
	</h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
	<div class="form-group">
		<input type="text" class="form-control" id="keyword" name="keyword"
			placeholder="活动标题" value="${title}" />
	</div>
	<a class="btn btn-primary"
		style="border: 0px; width: 90px; margin-left: 10px;"
		onclick="searchAuthor()" <a>搜索</a>
		<a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addAuthor('${title}')" <a>添加活动</a>
</form>

<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th style="width: 400px;">标题</th>
			<th style="width: 400px;">副标题</th>
			<th style="width: 200px;">活动时间</th>
			<th style="width: 100px;">截至报名时间</th>
			<th style="width: 200px;">活动地点</th>
			<th style="width: 100px;">导师</th>
			<th style="width: 100px;">正文图片(可选)</th>
			<th style="width: 100px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<#list activityList as activity>
		<tr>
			<td>${activity.title}</td>
			<td>${activity.subtitle}</td>
			<td>${activity.activityTime}</td>
			<td>${activity.deadlineTime}</td>
			<td>${activity.activityPlace}</td>
			<td>${activity.tutorMajorModel.author}</td>
			<td><#if activity.contentImage!''> <img class="portrait"
				style="width: 60px; height: 60px" src="${activity.contentImage}">
				<#else> 暂无图片 </#if>
			</td>
			<td><a style="cursor: pointer"
				onclick="editAuthor(${activity.id})">修改</a> <a
				style="padding-left: 5px; cursor: pointer;"
				onclick="remove(${activity.id},'${activity.title}')">删除</a></td>
		</tr>
		</#list>
	</tbody>
</table>
<script type="text/javascript"
	src="/resources/ruide/js/jquery-2.1.1.min.js"></script>
<script>
    /**
     * 编辑栏目
     * @param catId
     */
    function editAuthor(id){
        art.dialog.open('/activity/edit?id='+id,{
            title: '编辑活动',
            width:800,
            height:600,
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
    	
        art.dialog.open('/activity/add', {
            title: '添加活动',
            width:800,
            height:600,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                //
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
            $.post("/activity/delete", {id:id},
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
		var title = $("#keyword").val();
		if(typeof(title)!='undefined'&&""!=title){
			url+="?title="+base64encode(utf16to8(encodeURIComponent(title)));
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
