<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
	<h1>
		config管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
			xiaodou-common-info 列表
		</small>
	</h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
	<div class="form-group">
		<input type="text" class="form-control" id="keyword" name="keyword"
			placeholder="信息专项" value="${infoType}" />
	</div>
	<a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;"
		onclick="searchAuthor()" <a>搜索</a>
</form>

<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th style="width: 100px;">ID</th>
			<th style="width: 250px;">信息专项</th>
			<th style="width: 100px;">信息编号</th>
			<th style="width: 250px;">信息项版本号</th>
		<!--<th style="width: 100px;">app模块编号</th>-->
			<th style="width: 100px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<#list confInfolist as confInfo>
		<tr>
			<td>${confInfo.id}</td>
			<td>${confInfo.infoType}</td>
			<td>${confInfo.infoCode}</td>
			<td>${confInfo.infoVersion}</td>
<!--			<td>${confInfo.module}</td> -->
			
			<td><a style="cursor: pointer" onclick="editAuthor(${confInfo.id})">修改</a> <a
				style="padding-left: 5px; cursor: pointer;"
				onclick="remove(${confInfo.id},'${confInfo.infoType}')">删除</a>
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
        art.dialog.open('/confInfo/edit?id='+id,{
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

    
    function remove(id,name) {
    	if (confirm("确认要删除"+name+"?")) {
            $.post("/confInfo/delete", {id:id},
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
		var infoType = $("#keyword").val();
		if(typeof(infoType)!='undefined'&&""!=infoType){
			url+="?infoType="+base64encode(utf16to8(encodeURIComponent(infoType)));
		}	
        self.location=url;
	}
	
	
</script>

 </@htmlBody>
