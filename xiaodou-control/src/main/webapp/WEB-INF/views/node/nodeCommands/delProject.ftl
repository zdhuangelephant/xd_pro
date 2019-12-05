 <script>
 function delProject(mac){
             var commandId="3";
	         var commandName="删除应用";  
	         var serverId=$("#serverId").val();           
		    	$.post("/node/addCommand", {serverId:serverId,commandId:commandId,commandName:commandName,mac:mac},
		                    function(data){
		                    	var u=JSON.parse(data);
	                       		 alert("命令新增成功");
	                       			 location.reload();
		                    });
		 }
 </script>