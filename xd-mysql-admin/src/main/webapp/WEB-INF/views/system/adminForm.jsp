<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx }/system/permission/i/roleUpdate"
			method="post">
			<table class="formTable">
				<input type="hidden" id="rid" name="rid" value="${role.rid }"/>

				<tr>
					<td>用户名：</td>
					<td><input id="userName" name="userName"
						type="text" value="${role.name }"
						class="easyui-validatebox"
						data-options="width: 150,required:'required'" /></td>
				</tr>
				<c:if test="${role.userId==null }">
					<tr>
						<td>密码：</td>
						<td><input id="password" name="password" type="password"
						value="${role.password }" class="easyui-validatebox"
						data-options="width: 150,required:'required'" /></td>
					</tr>	 
				</c:if>
				<tr>
					<td>姓名：</td>
					<td><input id="name" name="name" type="text" value="${role.name }"
						class="easyui-validatebox"
						data-options="width: 150,required:'required',validType:'length[1,100]'" /></td>
				</tr>
				<tr>
					<td>角色：</td>
					<td><select id="type" name="type" style="font-size:12px;" class="easyui-validatebox"
						data-options="width: 150">
							<option value="1" <c:if test="${role.type=='1' }"> selected </c:if> >普通用户</option>
							<option value="2" <c:if test="${role.type=='2' }"> selected </c:if> >开发</option>
							<option value="3" <c:if test="${role.type=='3' }"> selected </c:if> >管理员</option>
					</select></td>
				</tr>

				<tr>
					<td></td>
					<td><span id="mess2"> </span></td>
				</tr>

			</table>


		</form>
	</div>

	<script type="text/javascript">
		//提交表单
$('#mainform').form({    
    onSubmit: function(){    
    	var isValid = $(this).form('validate');
		return isValid;	// 返回false终止表单提交
    },    
    success:function(data){  
    	var obj = eval('(' + data + ')');
    	parent.$.messager.show({ title : "提示",msg: obj.mess , position: "bottomRight" });
    	setTimeout(function () {
           config.panel('close');
           $("#dg3").datagrid('reload');
            
        }, 2000);
    	// successTip(data,dg,d);
    	
    }    
});   


 

//测试连接
 function  testConn(){
	  $.ajax({
			type:'POST',
		   	contentType:'application/json;charset=utf-8',
            url:"${ctx}/system/permission/i/testConn",
            data: JSON.stringify({ 'databaseName':$("#databaseName").val(),'userName':$("#userName").val(),'passwrod':$("#passwrod").val(),'port':$("#port").val(),'ip':$("#ip").val() } ),
            datatype: "json", 
            //成功返回之后调用的函数             
            success:function(data){
            	var status = data.status ;
            	if(status == 'success' ){
            		
            		$("#mess2").html(data.mess );
            		//alert( data.mess );
            	}else{
            		$("#mess2").html("连接失败！" );
            	}
            }  
     });
 }
	</script>
</body>
</html>