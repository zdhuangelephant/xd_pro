 <script>
 function restart(mac,version){
             var commandId="2";
	         var commandName="重启";  
	         var serverId=$("#serverId").val();           
		    	$.post("/node/addCommand", {serverId:serverId,commandId:commandId,commandName:commandName,mac:mac,version:version},
		                    function(data){
		                    	var u=JSON.parse(data);
	                       		 alert("命令新增成功");
	                       			 location.reload();
		                    });
		 }
 </script>