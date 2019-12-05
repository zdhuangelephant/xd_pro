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

										<button id="" class="btn green" onclick="getVersion('')">

										批量部署
									
										</button>
										<button id="" class="btn green" onclick="createRelease()">

										生成发行版本
									
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
                             <th>上次部署的版本号</th>  
                             <th>权重</th>                     
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
                            <td>${node.curVersion}</td>
                            <td><input value="${node.curWeight}">
                            	<a style="cursor: pointer" onclick="editWeight('${node.mac}',this)" >修改</a>
                            </td>
							<td>
								<a style="cursor: pointer" onclick="getVersion('${node.mac}')" >部署</a>
								<a style="cursor: pointer" onclick="restart('${node.mac}','${node.curVersion}')">重启</a>
								<a style="cursor: pointer" onclick="delProject('${node.mac}')">删除应用</a>
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
     <#include "/node/nodeCommands/delProject.ftl">
	    </div>
   </div>
</body>
<script>


     function getLogs(mac,serverId,serverName){
		addNewTabs({
			id:'10030',title: serverName+'部署日志',close: true,url:'/log/getLog?mac='+mac+'&serverId='+serverId+'&serverName='+serverName}
		);
	}
	
    function editWeight(mac,obj){
    	 var serverId=$("#serverId").val(); 
    	 var weight=obj.parentNode.children[0].value;
    	 var strategy="";
    	  if(weight==null){
    	      
    	    }else{  
	    	 if(checknumber(weight)) { 
	　　　　　		　alert("只允许输入数字！"); 
	　　	　　	　	　return false; 
	　　　	  }
			strategy="weight="+weight;
		  }	   
    	$.post("/node/editWeight", {serverId:serverId,strategy:strategy,mac:mac,weight:weight},
                    function(data){
                    	var u=JSON.parse(data);
                   		 alert("修改成功"); 
                    });	        
         }
    

　　function checknumber(String) { 
　　　　var Letters = "1234567890"; 
　　　　var i; 
　　　　var c; 
　　　　for( i = 0; i < Letters.length; i ++ )   {   
　　　　　　c = Letters.charAt(i); 
　　　　　　if (Letters.indexOf(c) ==-1){ 
　　　　　　　　return true; 
　　　   　　} 
　　　　} 
　　　　return false; 
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
  function createRelease(){
       $('#myModal2').modal();
  }
  function createReleaseVersion(){
      	    var serverId=$("#serverId").val();     
    	    var version=$('#releaseVersion').val(); 
    	    if(version==null){
    	   		 alert("生成发行版的版本号必须存在");
    	   		 return;
    	    }     
    	     $.post("/node/createReleaseVersion", {serverId:serverId,version:version},
				                    function(data){
				                        var u=JSON.parse(data);   
				                        if(u.msg=="success"){
				                          alert("成功");
				                        }else{
				                          alert("失败,请检查FTP状态");
				                        }
				                        location.reload();
				                    });
  }
</script>

<html>