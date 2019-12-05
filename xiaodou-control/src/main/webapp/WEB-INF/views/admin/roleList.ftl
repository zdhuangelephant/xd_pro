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

							角色列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								角色列表


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

								<div class="caption"><i class="icon-edit"></i>角色列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">

										<button id="sample_editable_3_new" class="btn green" onclick="addRole()">

										Add New <i class="icon-plus"></i>
									
										</button>
									</div>
		

								<table class="table table-striped table-hover table-bordered">
                                <thead>
                                    <tr>
                                    	<th>id</th>
                                        <th>角色名称</th>                                     
                                       	<th>角色描述</th> 
                                       	<th>操作</th>                       
                                    </tr>
                                </thead>
                                <tbody>
                                <#list roles as role>
                                    <tr>
                                    	<td >${role.id}</td>
                                        <td>${role.roleName}</td>                           
                                        <td>${role.roleDescription}</td>                    
                                        <#if role.id!=1>                     
	                                       <td><a style="cursor: pointer" onclick="showPrivilege('${role.id}','${role.roleName}')">权限设置</a>
	                                        | <a style="cursor: pointer" onclick="showAdmins(${role.id})">角色成员查看</a> 
	                                        | <a style="cursor: pointer" onclick="editRole('${role.id}','${role.roleName}','${role.roleDescription}')">修改</a>
	                                        | <a style="cursor: pointer" onclick="delRole('${role.id}')">删除</a>
	                                        </td>
	                                        <#else>
	                                        <td>
	                                           <a style="cursor: pointer" onclick="showAdmins(${role.id})">角色成员查看</a> 
	                                      (谨慎给用户关联此角色，关联此角色的成员将拥有所有权限)
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
					          <h4 class="modal-title" id="myModalLabel">权限管理</h4>
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
					          <button type="button" onclick="eidtPrivilege()" id="btn_submit" class="btn btn-primary" data-dismiss="addFile"></span>确定</button>		
					        </div>
					      </div>
					        </form>       
					    </div>
					  </div>
					 </div>
                
                 <div class="modal fade" id="userRole" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					  <form role="form" method="post" id="formRole"    enctype="multipart/form-data">
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">权限管理</h4>
	     			        </div>				         
					     	 <input type="hidden" class="form-control" name="serverIdB" id="serverIdB">
					        <div class="modal-body">	
					         <div class="form-group">
                                	    <table id="userTable" class="table table-hover">
                                <thead>
                                    <tr>                                          
                                    </tr>
                                	</thead>                              
									</table>
                                </div>		     
						       <div class="form-group">    		                                                  				          
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
						         <input type="hidden" class="form-control" name="roleId" id="roleId">    
	                             <div class="form-group">
                                <label>请输入角色名</label>
                                <input class="form-control" id="roleName_e" name="roleName_e" >
                            </div>                          
                             <div class="form-group">
                                <label>请输入描述</label>
                                <input class="form-control" id="roleDescription_e" name="roleDescription_e" >
                            </div>     				          
					        </div>
					        <div class="modal-footer">
					          <button type="button" onclick="eidtRoleById()" id="btn_submit" class="btn btn-primary" data-dismiss="myModal"></span>修改</button>
					        </div>
					      </div>
					    </div>
					  </div>
			
				
					     <div class="modal fade" id="roleAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      
					      <div class="modal-content">
					      	 <form role="form" method="post" id="form2"  action="/server/doAdd"  >
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">新增角色</h4>
					        </div>
					        <div class="modal-body">				          
                            <div class="form-group">
                                <label>请输入角色名称</label>
                                <input class="form-control" id="roleName" name="roleName" >
                            </div>                          
                             <div class="form-group">
                                <label>请输入角色描述</label>
                                <input class="form-control" id="roleDescription" name="roleDescription" >
                            </div>                
					         <div class="modal-footer">	   
					          <button type="button"  id="btn_submit" class="btn btn-primary" onclick="roleAddSubmit()"></span>确定</button>		
					        </div>
					        </form>
					      </div>
					          
					    </div>
					  </div>
					  </div>
					  		 			
            </div>
            <!-- /.container-fluid -->

    <input type="hidden" id="currentRoleId"name="currentRoleId" value="">
			<!-- END PAGE CONTAINER-->
	 </div>          
                  
	<!-- END CONTAINER -->
    <input type="hidden" id="serverGroupTypeId" name="serverGroupTypeId">
<!-- END BODY -->
</body> 
<script>
 function showPrivilege(roleId, roleName) {
     $.post("/role/setPrivilege", {roleId:roleId},
                    function(data){
	                    var u=JSON.parse(data);    
	                    var roleId=u.roleId;
	                    var rolePrivileges=u.rolePrivileges;
	                    var privileges=u.privileges;                
                    	$("#roleTable tr").empty();
                    	$("#currentRoleId").val(roleId);
                    	for(var i=0;i<privileges.length;i++){
                    	  var flag="0";  
                    	   for(var j=0;j<rolePrivileges.length;j++){
                    	   	   if(privileges[i].id==rolePrivileges[j].privilegeId){
                    	   	  	 flag="1";
                    	   	   }
                    	   	 }
                    	   if(flag=="1"){
							 var row="<tr><td><input name='roleId' type='checkbox' preChecked='checked' checked value="+privileges[i].id+">"+privileges[i].name+"</td></tr>"
							}else{
							 var row="<tr><td><input name='roleId' type='checkbox'  value="+privileges[i].id+">"+privileges[i].name+"</td></tr>"
							}
							$("#roleTable tr:last").after(row);  
						}
                        $('#addRole').modal();       
                    });	    
    }
   function showAdmins(roleId) {
     $.post("/role/admins", {roleId:roleId},
                    function(data){
	                    var u=JSON.parse(data);    
	                    var admins=u.admins;
	                    $("#userTable tr").empty();
                    	for(var i=0;i<admins.length;i++){
							 var row="<tr><td>"+admins[i].userName+"</td><td>"+admins[i].realName+"</td></tr>"			
							$("#userTable tr:last").after(row);  
						}
                        $('#userRole').modal();       
                    });	    
    }
    

    function eidtPrivilege(){
 		var needDeleteId="";
 		var needAddId="";
 		var roleId=$('#currentRoleId').val();
 	   $(":checkbox[name='roleId']").each(function(){
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
 		 $.post("/role/doSetPrivilege", {roleId:roleId,needAddId:needAddId,needDeleteId:needDeleteId},
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
    
 function addRole(){
 	$('#roleAdd').modal();	
 }
 function roleAddSubmit(){
 		var roleName=$('#roleName').val();
 		var roleDescription=$('#roleDescription').val();
 		if(roleName==""){
 			alert("角色名称不能为空");
 			return;
 		}	
 		 $.post("/role/addRole", {roleName:roleName,roleDescription:roleDescription},
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
 	
  function delRole(roleId){
    if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }else{
    	$.post("/role/deleteRole", {roleId:roleId},
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
         
 function editRole(roleId,roleName,roleDescription){
		$('#roleId').val(roleId);
 		$('#roleName_e').val(roleName);
 		$('#roleDescription_e').val(roleDescription);	
 		$('#model2').modal();	
 	}
 function eidtRoleById(){
 		var roleId=$('#roleId').val();
 		var roleName=$('#roleName_e').val();
 		var roleDescription=$('#roleDescription_e').val();
 		if(roleName==""){
 			alert("角色名不能为空");
 			return;
 		}
 		 $.post("/role/editRole", {roleId:roleId,roleName:roleName,roleDescription:roleDescription},
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