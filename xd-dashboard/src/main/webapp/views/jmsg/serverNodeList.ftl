<#include "/common/common.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->

<body class="page-header-fixed">

	<!-- BEGIN CONTAINER -->

	<div class="page-container">
		<!-- BEGIN PAGE -->

		<div class="page-content">

			<!-- BEGIN PAGE CONTAINER-->        

			<div class="container-fluid">

				<!-- BEGIN PAGE HEADER-->

				<div class="row-fluid">

					<div class="span12">


						<!-- BEGIN PAGE TITLE & BREADCRUMB-->

						<h3 class="page-title">

							服务节点列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">JMSG</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>

								<a href="#">配置中心</a>

								<i class="icon-angle-right"></i>

							</li>

							<li><a href="#">服务节点列表</a></li>

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

								<div class="caption"><i class="icon-edit"></i>服务节点列表</div>

								<div class="tools">

									<a href="javascript:;" class="collapse"></a>

									<a href="#portlet-config" data-toggle="modal" class="config"></a>

									<a href="javascript:;" class="reload"></a>

									<a href="javascript:;" class="remove"></a>

								</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">

										<button id="sample_editable_1_new" class="btn green">

										Add New <i class="icon-plus"></i>

										</button>

									</div>
		

								<table class="table table-striped table-hover table-bordered" id="sample_editable_1">
									<thead>
										<tr>
											<th>服务名称</th>
											<th>是否可用(0不可用1可用)</th>
											<th>group_id</th>
											<th>更新时间</th>
											<th>修改</th>
											<th>删除</th>
										</tr>
									</thead>
									<tbody>
									  <#list serverNodeList as list>
										<tr class="">
											<td id="${list.id}">${list.serverName}</td>
											<td>${list.enable}</td>
											<td>${list.groupId}</td>
											<td class="center">${list.updateTime}</td>
											<td><a  class="edit" href="javascript:;">Edit</a></td>
											<td><a  class="delete" href="javascript:;">Delete</a></td>
										</tr>
									  </#list>
									</tbody>

								</table>

							</div>

						</div>

						<!-- END EXAMPLE TABLE PORTLET-->
						
						
				<div class="row-fluid">
					<div class="span12">
						<div class="portlet">
							<div class="portlet-title">
								<div class="caption"><i class="icon-bell"></i>分组关联关系</div>
							</div>
							<div class="portlet-body">
								<table class="table table-striped table-bordered table-advance table-hover">
									<thead>
										<tr>
											<th><i class="icon-briefcase"></i> groupId</th>
											<th class="hidden-phone"><i class="icon-user"></i> 关联queue</th>				
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="hidden-phone">1</td>
											<td><a style="cursor: pointer" onclick="showRelation('1')">关联关系</a></td>
										</tr>
										<tr>
											<td class="hidden-phone">2</td>
                                            <td><a style="cursor: pointer" onclick="showRelation('2')">关联关系</a></td>	
										</tr>
                                         <tr>
											<td class="hidden-phone">3</td>
											<td><a style="cursor: pointer" onclick="showRelation('3')">关联关系</a></td>								
										</tr>
										 <tr>
											<td class="hidden-phone">4</td>
											<td><a style="cursor: pointer" onclick="showRelation('4')">关联关系</a></td>	
										</tr>							
									</tbody>

								</table>

							</div>

						</div>
						</div>

						<!-- END SAMPLE TABLE PORTLET-->

					</div>

					</div>

				</div>

				<!-- END PAGE CONTENT -->

			</div>
				<div class="modal fade" id="addRelation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					  <form role="form" method="post" id="formRole"    enctype="multipart/form-data">
					      <div class="modal-content">
					        <div class="modal-header">		     
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"></span></button>
					          <h4 class="modal-title" id="myModalLabel">关联管理</h4>
	     			        </div>				         
					     	 <input type="hidden" class="form-control" name="groupId" id="groupId">
					        <div class="modal-body">	
					         <div class="form-group">
                                	    <table id="relationTable" class="table table-hover">
                                <thead>
                                    <tr>                                          
                                    </tr>
                                	</thead>                              
									</table>
                                </div>		     
						       <div class="form-group">    		                                                  				          
					        </div>
					        <div >	   
					          <button type="button" onclick="eidtRelation()" id="btn_submit" class="btn btn-primary" ></span>确定</button>		
					        </div>
					      </div>
					        </form>       
					    </div>
					  </div>
					 </div>
                  </div>

			<!-- END PAGE CONTAINER-->
		</div>          
                  
		<!-- END PAGE -->
	</div>

	<!-- END CONTAINER -->

<!-- END BODY -->
</body> 
	<script src="${rc.contextPath}/media/js/table-editable.js"></script>   
<script>
   function showRelation(groupId) {
      $("#groupId").val(groupId);
      $.post("/jmsg/relationList", {groupId:groupId},
                    function(data){
	                    var u=JSON.parse(data);    
	                    var queueList=u.queueList;
	                    var relationList=u.relationList;             
                    	$("#relationTable tr").empty();
                    	for(var i=0;i<queueList.length;i++){
                    	  var flag="0";  
                    	   for(var j=0;j<relationList.length;j++){
                    	   	   if(queueList[i].id==relationList[j].queueId)
                    	   	   flag="1";
                    	   	 }
                    	   if(flag=="1"){
							 var row="<tr><td><input name='queueId' type='checkbox' preChecked='checked' checked value="+queueList[i].id+">"+queueList[i].queueName+"</td></tr>"
							}else{
							 var row="<tr><td><input name='queueId' type='checkbox'  value="+queueList[i].id+">"+queueList[i].queueName+"</td></tr>"
							}
							$("#relationTable tr:last").after(row);  
						}
                        $('#addRelation').modal();       
                    });	   
    }
    
    
    function eidtRelation(){
 		var needDeleteId="";
 		var needAddId="";
 		var groupId=$("#groupId").val();
 	   $(":checkbox[name='queueId']").each(function(){
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
 		 $.post("/jmsg/editRelation", {groupId:groupId,needAddId:needAddId,needDeleteId:needDeleteId},
                    function(data){
                    	 var u=JSON.parse(data);
	                    	if(u.state=="success"){
		                        alert("修改成功");
		                        location.reload();
	                        }else{
	                            alert(u.error);    
	                        }           
                    });	   
 	}

</script>
</html>