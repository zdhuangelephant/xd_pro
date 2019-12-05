<!--2015/7/8-->
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <title>首页</title>
</head>
<body>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js"></script>
<form id="from1" action="/improtExcel"  method="post"  enctype="multipart/form-data" >    
	<input type="file" id="uploadFile" name="uploadFile"/>    
</from>
<button id="importUser">批量导入学生</button>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#importUser").click(function(){
			importEmp();
		})
	});

	function importEmp(){
		var uploadFile = document.getElementById("uploadFile").value;      
		if(uploadFile == null || uploadFile == ''){      
			alert("请选择要上传的Excel文件");      
			return;      
		}else{      
			var fileExtend = uploadFile.substring(uploadFile.lastIndexOf('.')).toLowerCase();       
			if(fileExtend == '.xls'){      
			}else{      
				alert("文件格式需为'.xls'格式");      
				return;      
			}      
		}      
		//提交表单       
		document.getElementById("from1").submit();      
	}
</script>
</html>