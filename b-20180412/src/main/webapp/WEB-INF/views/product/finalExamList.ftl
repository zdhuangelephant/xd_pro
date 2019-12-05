<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        产品
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            期末测试列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    
    <a class="btn btn-primary pull-right" style="border: 0px; width:104px; margin-left: 10px;" onclick="addProduct(${productId})" <a>添加期末考试</a></form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center">
            ID
        </th>
        <th>试卷</th>
        <th>试题个数</th>
        <th>排序</th>
        <th>创建时间</th>
        <th style="width:150px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list examList as exam>
        <tr>
            <td>${exam.id}</td>
            <td>${exam.examName}</td>
            <td>${exam.questionNums}</td>
            <td><input class="listOrder" data-id="${exam.id}" value="${exam.sort}"  style="width: 50px; text-align: center;"/></td>
            <td>${exam.createTime}</td>
            <td><a style="cursor: pointer" onclick="editCourse(${exam.id})">修改</a>
                <a style="padding-left: 5px;cursor: pointer;" onclick="delCourse(${exam.id},'${exam.examName}')">删除</a>
        </tr>
    </#list>
    </tbody>
</table>
<a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="order()">排序</a>
<script>
function order(){
    var orders = "";
    $(".listOrder").each(function(){
        orders = orders + $(this).attr("data-id") + ":" + $(this).val() + ";";
    })
    $.post("/finalExam/order", { orders:orders},
            function(data){
                alert("排序成功");
                location.reload();
            }
    );
}
    /**
     * 编辑栏目
     * @param catId
     */
    function editCourse(id){
        art.dialog.open('/finalExam/edit?id='+id,{
            title: '编辑期末测试',
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
    function addProduct(productId){
    	var nextSort = getNextSort();
        art.dialog.open('/finalExam/add?productId='+productId + '&sort='+nextSort,{
            title: '添加期末考试',
            width:400,
            height:400,
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

    /**
     * 删除栏目
     * @param catId
     */
    function delCourse(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/finalExam/delete", {id:id},
                function(data){
            	    data=JSON.parse(data);
                    if(data.retCode=='0'){
                        alert("删除成功");
                    } else {
                        alert("删除失败,有用户已开启过考试");
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
    
    // 获取排序id
    function getNextSort(){
    	var nextOrder = 0;
    	var hasOrder = [];
    	$(".listOrder").each(function(){
             hasOrder.push($(this).val());
        })
        for (var i=0; i<hasOrder.length; i++){
			if(hasOrder[i]==nextOrder){
				nextOrder += 1;
			}
		}
    	
    	return nextOrder;
    }
</script>

</@htmlBody>
