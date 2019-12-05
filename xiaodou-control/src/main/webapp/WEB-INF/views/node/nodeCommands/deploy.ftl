 <script>
  function getVersion(mac){
     		$('#curMac').val(mac);
	    	$('#myModal1').modal();
    }
 function addCommand(){
    	    var mac=$('#curMac').val();
    	    var serverId=$("#serverId").val();     
    	    var version=$('#version').val(); 
    	    if(version==null){
    	   		 alert("要部署的版本不能为空");
    	   		 return;
    	    }     
    	    if(mac==""){
	  			 var ids = "";	
    		     var nodeArray=new Array();
       			 var flag=0;
		        $("input[name='selOne']:checked").each(function() {
		          	    flag++;       	
		        	    var node=new Object();
		        	 	node.serverId=serverId;
		        	 	node.mac=$(this).val();
		        	 	node.commandName="部署";
		        	 	node.version=version;
		        	 	node.commandId="1";
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
	            var commandId="1";
	            var commandName="部署";            
		    	$.post("/node/addCommand", {serverId:serverId,commandId:commandId,commandName:commandName,mac:mac,version:version},
		                    function(data){
		                    	var u=JSON.parse(data);
	                       		 alert("命令新增成功");
	                       			 location.reload();
		                    });
		         }
    }
     </script>