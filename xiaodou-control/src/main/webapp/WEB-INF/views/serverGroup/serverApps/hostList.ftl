<div class="row-fluid">
					<div class="span12">
   					 <!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption"><i class="icon-edit"></i>主机列表</div>
							</div>
							<div class="portlet-body">
								<div class="clearfix">
									<div class="btn-group">

										<button id="sample_editable_3_new" class="btn green" onclick="addHost()">

										关联新主机 <i class="icon-plus"></i>
									
										</button>
									</div>
		
								<table class="table table-striped table-hover table-bordered" id="hostList">
									<thead>
                                    <tr>
                                    	<th  style="display:none">id</th>
                                        <th>HOST</th> 
                                        <th>MAC</th> 
                                        <th>机器名</th>   
                                        <th>操作</th>                                                                   
                                    </tr>
                                </thead>
                                <tbody>
                                <#list nodeList as node>
                                    <tr>
                                    	<td  style="display:none"></td>
                                        <td>${node.ip}</td>
                                        <td>${node.mac}</td>
                                        <td>${node.alias}</td>
                                        <td><a style="cursor: pointer" onclick="removeHost('${node.mac}')">解除关联</a>
                                        </td>
                                    </tr>                              
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
					  
	<script>
		function addHost(){
	 		$('#host').modal();
	 	}
	 	function hostAdd(){
		 	var groupId=$('#groupId').val();
			var mac=$("#mac").val();
			$.post("/serverGroupDetail/relationNode", {groupId:groupId,mac:mac},
		                function(data){
		                	var u=JSON.parse(data);   
		                	alert("关联成功");               
		                    location.reload();
		                });	   
	 	}
	 	function removeHost(mac){
	 	     if (!confirm("确认要删除？")) {
            	window.event.returnValue = false;
       		}else{
	 			var groupId=$("#groupId").val();
	 			$.post("/serverGroupDetail/delRelationNode", {groupId:groupId,mac:mac},
	                    function(data){
	                    	var u=JSON.parse(data);                 
	                        location.reload();
	                    });	  
	                    } 
	 	}
 
  </script>