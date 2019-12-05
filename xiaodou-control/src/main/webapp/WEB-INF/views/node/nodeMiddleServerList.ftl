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

							${serverName}节点列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								  ${serverName}节点列表

							</li>


						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

        <div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i class="icon-edit"></i>节点列表</div>

							</div>
		
							<div class="portlet-body">
							<div class="clearfix">
									<div class="btn-group">

										<button id="" class="btn green" onclick="install('')">

										批量安装
									
										</button>
	
									</div>
									</div>

								<table class="table table-striped table-hover table-bordered">
                    <thead>
                        <tr>
                        	<th  style="display:none">id</th>
                        	<th   style="vertical-align: middle;padding: 8px 10px;"><input type="checkbox" name="selectAll" class="selectAll"/></th>
                            <th>HOST</th>  
                            <th>机器名</th>                     
                            <th>操作</th>   
                            <th>操作日志</th>                         
                        </tr>
                    </thead>
                    <tbody>
                    <#list nodeList as node>
                        <tr>
                        	<td  style="display:none"></td>
                        	<th   style="vertical-align: middle;padding: 8px 10px;"><input value="${node.mac}" type="checkbox" name="selOne" id="selOne" /></th>
                            <td>${node.ip}</td>
                            <td>${node.alias}</td>
							<td>
								<a style="cursor: pointer" onclick="install('${node.mac}')">安装</a>	
   						    </td>
   						    <td>
   						    	<a style="cursor: pointer" onclick="getLogs('${node.mac}','${serverId}','${serverName}')">操作日志</a>
   						    </td>
                        </tr>                              
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div> 
 </div>   	
   
            </div>
            <!-- /.container-fluid -->

        </div>
          </div>
        <!-- /#page-wrapper -->
    <input type="hidden" id="serverId" value="${serverId}">
    <input type="hidden" id="serverName" value="${serverName}">
	 <#include "/node/nodeCommands/modal.ftl">
     <#include "/node/nodeCommands/deploy.ftl">
     <#include "/node/nodeCommands/restart.ftl">
	    </div>
   </div>
</body>
<script>

	function install(mac){
    	    var serverId=$("#serverId").val();       
    	    if(mac==""){
	  			 var ids = "";	
    		     var nodeArray=new Array();
       			 var flag=0;
		        $("input[name='selOne']:checked").each(function() {
		          	    flag++;       	
		        	    var node=new Object();
		        	 	node.serverId=serverId;
		        	 	node.mac=$(this).val();
		        	 	node.commandName="安装";
		        	 	node.commandId="5000";
		        	 	nodeArray.push(node);      
		        });
		        if(flag>0){
	     	  	 if(nodeArray.length>0){
	        		 var nodeJson=JSON.stringify(nodeArray);
	          			  $.post("/node/allAddCommand", { nodeJson:nodeJson },
				                    function(data){
				                        alert("成功");
				                        location.reload();
				                    });
	   			    	 } 
		        }else{
		     		 alert("请选择要部署的host");
		     		 return;
		       	 }
    	    }else{
	            var commandId="5000";
	            var commandName="安装";     
		    	$.post("/node/addCommand", {serverId:serverId,commandId:commandId,commandName:commandName,mac:mac},
		                    function(data){
		                    	var u=JSON.parse(data);
	                       		 alert("命令新增成功");
	                       			 location.reload();
		                    });
		         }
    }
	
     function getLogs(mac,serverId,serverName){
		addNewTabs({
			id:'10030',title: serverName+'操作日志',close: true,url:'/log/getLog?mac='+mac+'&serverId='+serverId+'&serverName='+serverName}
		);
	}
	


     $(".selectAll").click(function(){ 			
    			if(this.checked){    
    				$("input[name='selOne']").each(function(){
    					this.checked=true;  					
    				}); 
    		    }else{    
    		    	$("input[name='selOne']").each(function(){
    		    		this.checked=false;
    		    	});
    		    }    
    			
    		});
</script>

<html>