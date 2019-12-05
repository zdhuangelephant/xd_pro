<!DOCTYPE html>
<#include "/common/style.ftl">
<html>
<head>
<title>配置</title>
</head>
<style type="text/css">
  table, td {
	  font: 100% '微软雅黑';
	 
	  
}

  table {
	width: 80%;
	border-collapse: collapse;
	margin: 0 0 0 100px
}

  th, td {
	text-align: center;
	border: 1px solid #fff;
}

  th {
	background: #328aa4
}

  td {
	background: #e5f1f4;
}
</style>


<body>
	<script type="text/javascript">
	$(function(){
		$('#builder-content0').hide();
	})
	

	function toggle(obj){
		if($(obj).attr("label") == '1'){
			$(obj).removeClass("a-active").attr("label","0");
			$(obj).siblings().addClass("a-active").attr("label","1");
		}else{
			$(obj).addClass("a-active").attr("label","1");
			$(obj).siblings().removeClass("a-active").attr("label","0");
		}
		if($(obj).attr("label")=='0'){
			if($(obj).attr("name")=='datasource'){
				$('#builder-content').hide();
				$('#builder-content0').show();
			}
			if($(obj).attr("name")=='jsfunctions'){
				$('#builder-content0').hide();
				$('#builder-content').show();
			}
		}
		if($(obj).attr("label")=='1'){
			if($(obj).attr("name")=='datasource'){
				$('#builder-content').show();
				$('#builder-content0').hide();
			}
			if($(obj).attr("name")=='jsfunctions'){
				$('#builder-content0').show();
				$('#builder-content').hide();
			}
		}
	}
	
</script>
	<!--top begin-->
		<div class="common-top">
		<div class="index-logo">
			<!-- <a herf="#"><img alt="" src=""></a> -->
			<span><a href="/" title="自动化测试">自动化测试</a></span>
		</div>
		<div class="index-nav-wrap">
			<ul class="index-nav" id="indexNavWrap">
				<li><a href="/">首页</a></li>
				<li><a href="/doc/postman">服务</a></li>
				<li><a href="/case/testcase">用例</a></li>
				<li><a href="/run/runner">调度</a></li>
				<li><a href="/dataSource/list"  class="index-active">配置</a></li>
			</ul>
		</div>
		<div class="index-login-out">
			<a href="/admin/loginOut" target="_parent" >退出 </a>
		</div>
	</div>
	<div id="post-top" class="post-top-clear">
		<div id="top-tab" class="top-tab-clear" style="width: 100%;">
			<a href="javascript:" id="builder" class="a-active" label="1"
				onclick="toggle(this)" name="datasource">DataSource</a> <a
				href="javascript:" id="builder0" label="0" onclick="toggle(this)"
				name="jsfunctions">JS-Functions</a>
		</div>
		<div id="top-r"></div>
	</div>
	<!--top end-->

	<!--content begin-->
	<div class="content show runner-content runner-content-clear" id="builder-content"
		label="datasource">
		<div class="add-relative">
			<a href="javascript:" id="add_btn"
				class="AddMoreFileBox AddMoreFileBox2"></a>
			<table id="tb">
				  
				<tr id="trr">
					    
					<th style="width: 100px; display: none">Id</th>     
					<th style="width: 100px">Alias</th>     
					<th style="width: 100px">DriverUrl</th>     
					<th style="width: 100px">userName</th>     
					<th style="width: 100px">passWord</th>     
					<th style="width: 100px">操作</th>   
				</tr>
				  
				<tbody id="hide_tbody">
					  
					<tr style="display: none" id="trr">
						    
						<td style="width: 100px; display: none"><input value=''
							type='text'></td>     
						<td style="width: 100px"><input value='' type='text'></td>
						    
						<td style="width: 100px"><input value='' type='text'></td>
						    
						<td style="width: 100px"><input value='' type='text'></td>
						    
						<td style="width: 100px"><input value='' type='text'></td>
						    
						<td style="width: 200px" id="a"><a href="#" id="save_btn">保存</a><a
							href="#" id="edit_btn" style="display: none">修改</a> <a href="#"
							id="del_btn">删除</a></td>   
					</tr>
					  
				</tbody>
				  
				<tbody id="show_tbody">
					<#list dataSourceList as dataSource>   
					<tr id="trr">
						   
						<td style="width: 100px; display: none">${dataSource.id}</td>     
						<td style="width: 100px">${dataSource.alias}</td>     
						<td style="width: 100px">${dataSource.driverUrl}</td>     
						<td style="width: 100px">${dataSource.userName}</td>     
						<td style="width: 100px">${dataSource.password}</td>     
						<td style="width: 200px" id="a"><a href="#" id="save_btn"
							style="display: none">保存</a><a href="#" id="edit_btn">修改</a> <a
							href="#" id="del_btn">删除</a></td>   
					</tr>
					</#list>   
				</tbody>
			</table>
		</div>
	</div>
	<!--content end-->
	
	
	<!-- ----------------JS-Functions Begin---------------- -->
	<!--content begin-->
	<div class="content show runner-content" id="builder-content0"
		label="datasource">
		<div class="add-relative">
			<a href="javascript:" id="add_btn0"
				class="AddMoreFileBox AddMoreFileBox2"></a>
			<table id="tb0">
				<tr id="trr0">
					<th style="width: 100px; display: none">Id</th>     
					<th style="width: 100px">方法名</th>     
					<th style="width: 100px">方法体</th>     
					<th style="width: 100px">创建人</th>     
					<th style="width: 100px">创建时间</th>     
					<th style="width: 100px">操作</th>   
				</tr>
				<tbody id="hide_tbody0">
					<tr style="display: none" id="trr0">
						<td style="width: 100px; display: none"><input value=''type='text'></td>     
						<td style="width: 100px"><input value='' type='text'></td>
						<td style="width: 100px"><input value='' type='text'></td>
						<td style="width: 100px"><input readonly="readonly" value='' type='text'></td>
						<td style="width: 100px"><input readonly="readonly" value='' type='text'></td>
						<td style="width: 200px" id="a0"><a href="#" id="save_btn0">保存</a><a
							href="#" id="edit_btn0" style="display: none">修改</a> <a href="#"
							id="del_btn0">删除</a></td>   
					</tr>
					  
				</tbody>
				  
				<tbody id="show_tbody0">
				<#if jsFunList?? && (jsFunList?size > 0) >
					<#list jsFunList as func>   
					<tr id="trr0">
						<td style="width: 100px; display: none">${func.id}</td>     
						<td style="width: 100px">${func.funSignature}</td>     
						<td style="width: 100px">${func.functionBody}</td>     
						<td style="width: 100px">${func.username}</td>     
						<td style="width: 100px">${func.createTime}</td>     
						<td style="width: 200px" id="a0"><a href="#" id="save_btn0"
							style="display: none">保存</a><a href="#" id="edit_btn0">修改</a> <a
							href="#" id="del_btn0">删除</a></td>   
					</tr>
					</#list>  
				</#if> 
				</tbody>
			</table>
		</div>
	</div>
	<!--content end-->
	<!-- ----------------JS-Functions End ---------------- -->
</body>
<!-- ----------------JS-Functions---------------- -->
<script>
	$(document).ready(function(){ 
  		  var tb0 = $("#tb0"); 
    
		  //添加 
		  $("#add_btn0").click(function(){ 
		    var hideTr = $("#hide_tbody0",tb0).children().first(); 
		    var newTr = hideTr.clone().show(); 
		    $("#show_tbody0",tb0).append(newTr); 
		  }); 
    
		  //保存 
		  $("#save_btn0",tb0).die('click').live('click',function(){ 
		      var tr = $(this).parent().parent(); 
		      var id="";
		      var funSignature="";
		      var functionBody="";
		      var boolean="1";
		    $("input[type='text']",tr).each(function(i,el){ 
		      el = $(el); 
		      if((i==1||i==2)&&el.val()==""){
		      	boolean="2";
		      } 
		      if(i==0){id=el.val();}
		      if(i==1){funSignature=el.val();}
		      if(i==2){functionBody=el.val();}
		      el.parent().text(el.val()); 
		      el.remove(); 
		    }); 
		
			// 校验引号是否不配对
			var flag1 = check(funSignature);
			var flag2 = check(functionBody);
			var flag3 = checkBracketAndComma(funSignature);
			// 校验引号是否不配对
			if(!flag1 || !flag2 || !flag3){
				location.reload();
		      	return;
			}
			
		    if(boolean=="2"){
		        alert("属性不能为空");
		        location.reload();
		      	return;
		      }
			$.post("/dataSource/jsFuncAddOrUpdata", {id:id,funSignature:funSignature,functionBody:functionBody},
				function(data){
					  var u=JSON.parse(data);
					if(u.status=="1"){
						alert("成功");	
						location.reload();
					  }else{
						alert(u.data);
				}
			});	   
   
  		 }); 
    
		  //修改 
		  $("#edit_btn0",tb0).die('click').live('click',function(){ 
		    var tr = $(this).parent().parent(); 
		    $("td:not('#a0')",tr).each(function(i,el){ 
				var sep = "";
				if(i == 3 || i == 4){
					sep = " readonly='readonly' style='color:#B8B8B8'";
				}
				var text = $(el).text(); 
				// 方法体的引号转义
				if(i == 2 || i == 1){
					if(text.indexOf("\'")>-1){
						text = text.replace(/\'/g,"&apos;");
					}
					if(text.indexOf("\"")>-1){
						text = text.replace(/\"/g,"&quot;");
					}
				}
		      var html = "<input " + sep + " value='"+text+"' type='text'>"; 
		      $(el).html(html); 
		    }); 
		    $("#edit_btn0",tr).hide(); 
		    $("#save_btn0",tr).show(); 
		  }); 
		    
		  //删除 
		  $("#del_btn0",tb0).die('click').live('click',function(){ 
		    var tr = $(this).parent().parent(); 
		      var id="";
		    $("td:not('#a')",tr).each(function(i,el){ 
		      el = $(el); 
		      if(i==0){id=el.text();}
		    }); 
		   if(id==""){
		    	alert("id不能为空");
		  	 return false;
		   }
		   var msg = "您真的确定要删除吗？\n\n请确认！"; 
			if (confirm(msg)==false){ 
				return false; 
			}
			$.post("/dataSource/jsFuncDelete", {id:id},
						function(data){
							  var u=JSON.parse(data);
							if(u.status=="1"){
								alert("成功");	
								location.reload();
							  }else{
								alert(u.data);
								location.reload();
							}
			});	   
   
  		}); 
     
		  //返回 
		  $("#back_btn0").click(function(){ 
		    var sbody = $("#show_tbody0"); 
		    $("#trr0",sbody).show(); 
		    
		  }); 
	});
	
	function patch(re,s){
		  re=eval("/"+re+"/ig")
		  var arr = s.match(re);
		  if(arr == null)return 0;
		  return s.match(re).length;
	}
	
	function checkBracketAndComma(text){
		var nums1 = patch("（", text);
		var nums2 = patch("）", text);
		
		var nums3 = patch("，", text);
		if(nums1 > 0 || nums2 > 0){
			alert("不允许中文小括号")
			return false;
		}
		if(nums3 > 0){
			alert("不允许中文逗号")
			return false;
		}
		return true;
	}
	function check(functionBody){
		if(functionBody.indexOf("\“")>-1){alert("不允许中文引号");return false;}
		if(functionBody.indexOf("\'")>-1){
			var counts = patch("\'", functionBody);
			if(counts > 1){
				functionBody = functionBody.replace(/\'/g,"&apos;");
			}else{
				alert("请正确使用单双引号");
				return false;
			}
		}
		if(functionBody.indexOf("\"")>-1){
			var counts = patch("\"", functionBody);
			if(counts > 1){
				functionBody = functionBody.replace(/\"/g,"&quot;");
			}else{
				alert("请正确使用单双引号");
				return false;
			}
		}
		return true;
	}
</script>



<!-- ----------------DataSources---------------- -->
<script>
	$(document).ready(function(){ 
  var tb = $("#tb"); 
    
  //添加 
  $("#add_btn").click(function(){ 
    var hideTr = $("#hide_tbody",tb).children().first(); 
    var newTr = hideTr.clone().show(); 
    $("#show_tbody",tb).append(newTr); 
  }); 
    
  //保存 
  $("#save_btn",tb).die('click').live('click',function(){ 
      var tr = $(this).parent().parent(); 
      var id="";
      var alias="";
      var driverUrl="";
      var userName="";
      var password="";
      var boolean="1";
    $("input[type='text']",tr).each(function(i,el){ 
      el = $(el); 
      if((i==1||i==2||i==3||i==4)&&el.val()==""){
      	boolean="2";
      } 
      if(i==0){id=el.val();}
      if(i==1){alias=el.val();}
      if(i==2){driverUrl=el.val();}
      if(i==3){userName=el.val();}
      if(i==4){password=el.val();}
      el.parent().text(el.val()); 
      el.remove(); 
    }); 
    if(boolean=="2"){
        alert("属性不能为空");
        location.reload();
      	return;
      }
	$.post("/dataSource/addOrUpdata", {id:id,alias:alias,driverUrl:driverUrl,userName:userName,password:password},
		function(data){
			  var u=JSON.parse(data);
			if(u.status=="success"){
				alert("成功");	
				location.reload();
			  }else{
				alert(u.data);
		}
	});	   
   
  }); 
    
  //修改 
  $("#edit_btn",tb).die('click').live('click',function(){ 
    var tr = $(this).parent().parent(); 
    $("td:not('#a')",tr).each(function(i,el){ 
      el = $(el); 
      var html = "<input value='"+el.text()+"' type='text'>"; 
      el.html(html); 
    }); 
    $("#edit_btn",tr).hide(); 
    $("#save_btn",tr).show(); 
  }); 
    
  //删除 
  $("#del_btn",tb).die('click').live('click',function(){ 
    var tr = $(this).parent().parent(); 
      var id="";
    $("td:not('#a')",tr).each(function(i,el){ 
      el = $(el); 
      if(i==0){id=el.text();}
    }); 
   if(id==""){
    	alert("id不能为空");
  	 return false;
   }
   var msg = "您真的确定要删除吗？\n\n请确认！"; 
	if (confirm(msg)==false){ 
		return false; 
	}
	$.post("/dataSource/delete", {id:id},
				function(data){
					  var u=JSON.parse(data);
					if(u.status=="success"){
						alert("成功");	
						location.reload();
					  }else{
						alert(u.data);
						location.reload();
					}
	});	   
   
  }); 
     
  //返回 
  $("#back_btn").click(function(){ 
    var sbody = $("#show_tbody"); 
    $("#trr",sbody).show(); 
    
  }); 
});
</script>
</html>