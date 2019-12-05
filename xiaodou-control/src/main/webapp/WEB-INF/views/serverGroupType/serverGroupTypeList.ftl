<#include "/common/style.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->
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

							 服务组类型管理

						</h3>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
									 服务组类型管理
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

								<div class="caption"><i class="icon-edit"></i>服务组类型列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">

										<button id="" class="btn green" onclick="addServerGroupType()">

										Add New <i class="icon-plus"></i>

										</button>

									</div>
		
								<table class="table table-striped table-hover table-bordered" id="">
									<thead>
                                    <tr>
                                    	<th>id</th>
                                        <th>类型名称</th>                            
                                        <th>操作</th>                                                     
                                    </tr>
                                </thead>
                                <tbody>
                                <#list serverGroupTypeList as serverGroupType>
                                    <tr>
                                        <td>${serverGroupType.serverGroupTypeId}</td>
                                        <td>${serverGroupType.serverGroupTypeName}</td>
           								<td><a style="cursor: pointer" onclick="editServerGroupType('${serverGroupType.serverGroupTypeId}','${serverGroupType.serverGroupTypeName}')">修改 |</a>
               						    	<a style="cursor: pointer" onclick="removeServerGroupType('${serverGroupType.serverGroupTypeId}')">删除</a>
               						    </td>
               						    </td>
                                    </tr>                              
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
					  <div class="modal fade" id="serverGroupTypeEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">修改ServerGroupType</h4>
					        </div>
					        <div class="modal-body">				     
					            <div class="form-group">
                                <label>请输入服务组类型名称</label>
                                <input class="form-control" id="serverGroupTypeNameEdit" name="serverGroupTypeNameEdit">
                            </div> 					          
					        </div>
					        <div class="modal-footer">
					          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					          <button type="button" onclick="doEidtserverGroupType()" id="btn_submit" class="btn btn-primary" data-dismiss="modal"></span>新增</button>
					        </div>
					      </div>
					    </div>
					  </div>
					  
					  
					  
					  
					  <div class="modal fade" id="serverGroupTypeAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      <div class="modal-content">
					      	 <form role="form" method="post" id="form2"  action="/server/doAdd"  >
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">新增ServerGroupType</h4>
					        </div>
					        <div class="modal-body">				          
                            <div class="form-group">
                                <label>请输入服务组类型名称</label>
                                <input class="form-control" id="serverGroupTypeName" name="serverGroupTypeName">
                            </div>                                                 
					         <div class="modal-footer">	   
					          <button type="button"  id="btn_submit" class="btn btn-primary" onclick="serverGroupTypeSubmit()"></span>确定</button>		
					        </div>
					        </form>
					      </div>     
					  </div>
  			</div>
			<!-- END PAGE CONTAINER-->
			    <input type="hidden" id="serverGroupTypeId" name="serverGroupTypeId">
		</div>          
                  
	<!-- END CONTAINER -->

<!-- END BODY -->
</body> 

<script> 	
 	function removeServerGroupType(serverGroupTypeId){
     if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }else{
    	$.post("/serverGroupType/remove", {serverGroupTypeId:serverGroupTypeId},
                    function(data){
                    	var u=JSON.parse(data);   
		                 if(u.msg=="success"){
		                     alert("删除成功");
		                 }else{
		                   alert(u.msg);
		                 }
                        location.reload();
                    });	   
	        }
    }

	function addServerGroupType(){
 		$('#serverGroupTypeAdd').modal();	
 	}
	function serverGroupTypeSubmit(){
 		var serverGroupTypeName=$('#serverGroupTypeName').val();
 		if(serverGroupTypeName==""){
 			alert("类型名称不能为空");
 			return;
 		}
 		 $.post("/serverGroupType/doAdd", {serverGroupTypeName:serverGroupTypeName},
                    function(data){
                    	var u=JSON.parse(data);   
		                 if(u.msg=="success"){
		                     alert("新增成功");
		                 }else{
		                   alert(u.msg);
		                 }
                        location.reload();
                    });	   
 	}
 	 function editServerGroupType(serverGroupTypeId,serverGroupTypeName){
 	    $("#serverGroupTypeId").val(serverGroupTypeId);
 	    $("#serverGroupTypeNameEdit").val(serverGroupTypeName);
 		$('#serverGroupTypeEdit').modal();
 	}
 	function doEidtserverGroupType(){
 		var serverGroupTypeId=$("#serverGroupTypeId").val();
 		var serverGroupTypeName=$("#serverGroupTypeNameEdit").val();
 		if(serverGroupTypeName==""){
 			alert("类型名称不能为空");
 			return;
 		}
 		$.post("/serverGroupType/edit", {serverGroupTypeId:serverGroupTypeId,serverGroupTypeName:serverGroupTypeName},
                    function(data){
                    	var u=JSON.parse(data);   
		                 if(u.msg=="success"){
		                     alert("修改成功");
		                 }else{
		                   alert(u.msg);
		                 }
                        location.reload();
                    });	   
 	}
  
</script>

<html>