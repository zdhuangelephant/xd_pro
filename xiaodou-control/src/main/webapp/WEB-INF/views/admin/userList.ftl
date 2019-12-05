<!DOCTYPE html>
<#include "/common/style.ftl">
<head>
<link href="${rc.contextPath}/resources/css/fileCss/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />	
<link href="${rc.contextPath}/resources/css/validator/bootstrapValidator.css" rel="stylesheet"  type="text/css"/>
</head>
<html>
<body class="page-header-fixed">

	<!-- BEGIN CONTAINER -->
	
		<div class="page-content">

			<!-- BEGIN PAGE CONTAINER-->  
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->

				<div class="row-fluid">

					<div class="span12">


						<!-- BEGIN PAGE TITLE & BREADCRUMB-->

						<h3 class="page-title">

							用户列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								用户列表


							</li>

						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i class="icon-edit"></i>用户列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">

										<button id="sample_editable_3_new" class="btn green" onclick="addUser()">

										Add New <i class="icon-plus"></i>
									
										</button>
									</div>
		

								<table class="table table-striped table-hover table-bordered">
                                <thead>
                                    <tr>
                                    	<th>id</th>
                                        <th>用户名称</th>                                     
                                       	<th>email</th> 
                                       	<th>操作</th>                       
                                    </tr>
                                </thead>
                                <tbody>
                                <#list admins as admin>
                                    <tr>
                                    	<td >${admin.id}</td>
                                        <td>${admin.userName}</td>                           
                                        <td>${admin.email}</td>    
                                         <#if admin.id!=1>                             
                                        <td><a style="cursor: pointer" onclick="showRoles('${admin.id}')">关联角色</a>
                                        | <a style="cursor: pointer" onclick="editAdmin('${admin.id}','${admin.userName}','${admin.email}','${admin.realName}','${admin.telphone}')">修改</a>
                                        | <a style="cursor: pointer" onclick="delAdmin('${admin.id}')">删除</a>
                                        </td>
                                        <#else>
                                        <td>
                                            <a style="cursor: pointer" onclick="editAdmin('${admin.id}','${admin.userName}','${admin.email}','${admin.realName}','${admin.telphone}')">修改</a>                            
                                        </td>
                                        </#if>
                                    </tr>                              
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                
               
                <!-- /.row -->
                
                   <div class="modal fade" id="addRole" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					  <form role="form" method="post" id="formRole"    enctype="multipart/form-data">
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">角色管理</h4>
	     			        </div>				         
					     	 <input type="hidden" class="form-control" name="serverIdB" id="serverIdB">
					        <div class="modal-body">	
					         <div class="form-group">
                                	    <table id="roleTable" class="table table-hover">
                                <thead>
                                    <tr>                                          
                                    </tr>
                                	</thead>                              
									</table>
                                </div>		     
						       <div class="form-group">    		                                                  				          
					        </div>
					        <div class="modal-footer">	   
					          <button type="button" onclick="eidtAdminRole()" id="btn_submit" class="btn btn-primary" data-dismiss="addFile"></span>确定</button>		
					        </div>
					      </div>
					        </form>       
					    </div>
					  </div>
					 </div>
                
                
                  <div class="modal fade" id="model2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">Server</h4>
					        </div>
					        <div class="modal-body">				     
						         <input type="hidden" class="form-control" name="adminId" id="adminId">    
	                             <div class="form-group">
                                <label>请输入用户名</label>
                                <input class="form-control" id="userName_e" name="userName_e" >
                            </div>                          
                             <div class="form-group">
                                <label>请输入密码</label>
                                <input type="password"  class="form-control" id="password_e" name="password_e" >
                            </div>  
                             <div class="form-group">
                                <label>请确认密码</label>
                                <input type="password" class="form-control" id="repeatPassword_e" name="repeatPassword_e" >
                            </div>                          
                             <div class="form-group">
                                <label>请输入邮箱地址</label>
                                <input class="form-control" id="email_e" name="email_e"> 
                            </div>  
                             <div class="form-group">
                                <label>请输入真实姓名</label>
                                <input class="form-control" id="realName_e" name="realName_e"> 
                            </div>  
                             <div class="form-group">
                                <label>请输入手机号</label>
                                <input class="form-control" id="telphone_e" name="telphone_e"> 
                            </div>                                  				          
					        </div>
					        <div class="modal-footer">
					          <button type="button" onclick="eidtUserById()" id="btn_submit" class="btn btn-primary" data-dismiss="myModal"></span>修改</button>
					        </div>
					      </div>
					    </div>
					  </div>
			
				
					     <div class="modal fade" id="userAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      
					      <div class="modal-content">
					      	 <form role="form" method="post" id="form2"  action="/server/doAdd"  >
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">新增用户</h4>
					        </div>
					        <div class="modal-body">				          
                            <div class="form-group">
                                <label>请输入用户名</label>
                                <input class="form-control" id="userName" name="userName" >
                            </div>                          
                             <div class="form-group">
                                <label>请输入密码</label>
                                <input type="password"  class="form-control" id="password" name="password" >
                            </div>  
                             <div class="form-group">
                                <label>请确认密码</label>
                                <input type="password" class="form-control" id="repeatPassword" name="repeatPassword" >
                            </div>                          
                             <div class="form-group">
                                <label>请输入邮箱地址</label>
                                <input class="form-control" id="email" name="email"> 
                            </div>  
                             <div class="form-group">
                                <label>请输入真实姓名</label>
                                <input class="form-control" id="realName" name="realName"> 
                            </div>  
                             <div class="form-group">
                                <label>请输入手机号</label>
                                <input class="form-control" id="telphone" name="telphone"> 
                            </div>  
					         <div class="modal-footer">	   
					          <button type="button"  id="btn_submit" class="btn btn-primary" onclick="userAddSubmit()"></span>确定</button>		
					        </div>
					        </form>
					      </div>
					          
					    </div>
					  </div>
					  </div>
					  		 			
            </div>
            <!-- /.container-fluid -->

      
    <input type="hidden" id="currentAdminId"name="currentAdminId" value="">
			<!-- END PAGE CONTAINER-->
	 </div>          
                  
	<!-- END CONTAINER -->
    <input type="hidden" id="serverGroupTypeId" name="serverGroupTypeId">
<!-- END BODY -->
</body> 

<script>
 function addUser(){
 	$('#userAdd').modal();	
 }
 function userAddSubmit(){
 		var userName=$('#userName').val();
 		var password=$('#password').val();
 		var repeatPassword=$('#repeatPassword').val();
 		var email=$('#email').val();	
 		var realName=$('#realName').val();	
 		var telphone=$('#telphone').val();
 		if(userName==""||password==""||repeatPassword==""||realName==""){
 			alert("用户名密码以及真实姓名均不能为空");
 			return;
 		}
 		if(password!=repeatPassword){
 	     	alert("两次输入的密码必须相同");
 			return;
 		}
 		 $.post("/admin/addAdmin", {userName:userName,password:password,email:email,realName:realName,telphone:telphone},
                    function(data){
                    	var u=JSON.parse(data);
                    	if(u.msg=="success"){
	                        alert("新增成功");
	                        location.reload();
                        }else{
                            alert(u.error);
                            
                        }
                    });	   
 	}
 	
  function delAdmin(adminId){
    if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }else{
    	$.post("/admin/deleteAdmin", {adminId:adminId},
	                    function(data){	
		                    var u=JSON.parse(data);
	                    	if(u.msg=="success"){
		                        alert("删除成功");
		                        location.reload();
	                        }else{
	                            alert(u.error);    
	                        }                  	
	                    });  
	          }
    }
         
 function editAdmin(adminId,userName,email,realName,telphone){
		$('#adminId').val(adminId);
 		$('#userName_e').val(userName);
 		$('#email_e').val(email);	
 		$('#realName_e').val(realName);	
 		$('#telphone_e').val(telphone);
 		$('#model2').modal();	
 	}
 function eidtUserById(){
 		var adminId=$('#adminId').val();
 		var userName=$('#userName_e').val();
 		var password=$('#password_e').val();
 		var repeatPassword=$('#repeatPassword_e').val();
 		var email=$('#email_e').val();	
 		var realName=$('#realName_e').val();	
 		var telphone=$('#telphone_e').val();
 		if(userName==""||password==""||repeatPassword==""){
 			alert("用户名密码均不能为空");
 			return;
 		}
 		if(password!=repeatPassword){
 	     	alert("两次输入的密码必须相同");
 			return;
 		}
 		 $.post("/admin/editAdmin", {adminId:adminId,userName:userName,password:password,email:email,realName:realName,telphone:telphone},
                    function(data){
                    	 var u=JSON.parse(data);
	                    	if(u.msg=="success"){
		                        alert("修改成功");
		                        location.reload();
	                        }else{
	                            alert(u.error);    
	                        }           
                    });	   
 	}
 	
   function showRoles(adminId) {
      $.post("/admin/assigenRole", {adminId:adminId},
                    function(data){
	                    var u=JSON.parse(data);    
	                    var adminId=u.adminId;
	                    var adminRoles=u.adminRoles;
	                    var roles=u.roles;                
                    	$("#roleTable tr").empty();
                    	$("#currentAdminId").val(adminId);
                    	for(var i=0;i<roles.length;i++){
                    	  var flag="0";  
                    	   for(var j=0;j<adminRoles.length;j++){
                    	   	   if(roles[i].id==adminRoles[j].id)
                    	   	   flag="1";
                    	   	 }
                    	   if(flag=="1"){
							 var row="<tr><td><input name='roleid' type='checkbox' preChecked='checked' checked value="+roles[i].id+">"+roles[i].roleName+"</td></tr>"
							}else{
							 var row="<tr><td><input name='roleid' type='checkbox'  value="+roles[i].id+">"+roles[i].roleName+"</td></tr>"
							}
							$("#roleTable tr:last").after(row);  
						}
                        $('#addRole').modal();       
                    });	   
    }
        
  function eidtAdminRole(){
 		var needDeleteId="";
 		var needAddId="";
 		var adminId=$('#currentAdminId').val();
 	   $(":checkbox[name='roleid']").each(function(){
            var id = $(this).val();
            var preChecked = $(this).attr("preChecked");
            if($(this).is(":checked")){
                if(preChecked!="checked"){
                    needAddId = needAddId + id + ";";
                }
            } else {
                if(preChecked=="checked"){
                    needDeleteId = needDeleteId + id + ";";
                }
            }
        });
 		 $.post("/admin/doAssigenRole", {adminId:adminId,needAddId:needAddId,needDeleteId:needDeleteId},
                    function(data){
                    	 var u=JSON.parse(data);
	                    	if(u.msg=="success"){
		                        alert("修改成功");
		                        location.reload();
	                        }else{
	                            alert(u.error);    
	                        }           
                    });	   
 	}

</script>
<script src="${rc.contextPath}/resources/js/fileJs/fileinput.js" type="text/javascript"></script>
<script src="${rc.contextPath}/resources/js/fileJs/fileinput_locale_zh.js" type="text/javascript"></script>
<html>