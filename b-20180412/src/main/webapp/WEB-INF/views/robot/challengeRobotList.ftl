<#include "/common/layout.ftl" /> <@htmlHead title="首页">
<script src="${JS_PATH}artdialog/artDialog.js?skin=blue"></script>
<script src="${JS_PATH}artdialog/plugins/iframeTools.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".click").click(function() {
			$(".tip").fadeIn(200);
		});

		$(".tiptop a").click(function() {
			$(".tip").fadeOut(200);
		});

		$(".sure").click(function() {
			$(".tip").fadeOut(100);
		});

		$(".cancel").click(function() {
			$(".tip").fadeOut(100);
		});

	});
</script>
</@htmlHead> <@htmlBody bodyclass="page-content">
<div class="page-header">
	<h1>
		机器人列表 <small> <i class="ace-icon fa fa-angle-double-right"></i>
			机器人
		</small>
	</h1>
</div>

<!--<#if module==regionEntity.module>selected</#if>-->
<form class="form-inline" style="margin-bottom: 10px;">
	 <div class="form-group">
	     <select id="module" name="module" class="form-control" style="width:180px">
	         <option value="" >全部地域</option>
	         <#list regionList as regionEntity>
	           <option value=${regionEntity.module} <#if module==regionEntity.module>selected</#if> >${regionEntity.moduleName}</option>
	         </#list>
	     </select>
    </div>
	
	<div class="form-group">
		<select id="majorId" name="majorId" class="form-control">
			<option value="">请选择专业</option> 
				<#list totalProductCategoryLists as productCategory>
					<option value="${productCategory.id}"<#if majorId==productCategory.id>selected</#if> >${productCategory.name}</option> 
				</#list>
		</select>
	</div>
	<div class="form-group">
		<select id="courseId"
			name="courseId" class="form-control"
			style="width: 300px; margin-left: 13px;">
			<option value="" >新手课程</option>
			 <#list
			totalProductOfCategorys as product>
			<option value="${product.id}"<#if courseId ==
				product.id>selected</#if> >${product.name}</option> </#list>
		</select>
	</div>
	<button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
	<button id="refreshButton" class="btn btn-primary" style="border: 0px; width: 90px;margin-left: 0px;">重置</button>
	
	<a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="dispatcherRobot()" >分配机器人</a>
</form>

<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
		    <th>地域</th>
		    <th>专业名</th>
			<th>课程名</th>
			<th>机器人ID</th>
			<th>目标能力值</th>
			<th>创建时间</th>
			<th style="width: 250px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<#list challengeRobotPage.result as challengeRobot>
		<tr>
			<td>${challengeRobot.moduleName}</td>
			<td>${challengeRobot.majorName}</td>
			<td>${challengeRobot.courseName}</td>
			<td>${challengeRobot.userId}</td>
			<td>${challengeRobot.targetAbility}</td>
			<td>${challengeRobot.createTime}</td>

			<td><a onclick="editChallengeRobot('${challengeRobot.id}')">编辑</a>
				<a style="padding: 5px;">|</a> <a
				onclick="delChallengeRobot('${challengeRobot.id}','该条信息')">删除</a>
				<a style="padding: 5px;"></td>
		</tr>
		</#list>
	</tbody>
</table>
<@page totalCount="${challengeRobotPage.totalCount}"
pageNo="${challengeRobotPage.pageNo}"
totalPage="${challengeRobotPage.totalPage}" url=""> </@page>
<script>
	$(function(){
		 // 给专业的input加入onchange事件
		 $('#majorId').click(function(){
			 var typeCode = $(this).val();
			 var module = $("#module").val();
			 if(module==''){
			 	alert("请先选择地域");
				return false;
			 }
			 $.ajax({
				   type: "post",
				   url: "/robot/getAllProductsOfProductCategory",
				   data: {"typeCode":typeCode,"module":module,needModule:0},
				   dataType: "json",
				   success: function(data){
					   if (JSON.stringify(data) != '' && data != null) {
						   $('#courseId').html("");
						   $('#courseId').append(
									"<option value=''>"
											+ '请选择课程' + "</option>")
							
						   for ( var ele in data) {
								$('#courseId option:last').after(
										"<option value='" + data[ele].id + "'>"
												+ data[ele].name + "</option>")
							}
					   }
				   }
			});
		 });

		 // 给专业的input加入onchange事件
		 $('#module').change(function(){
			 var module = $(this).val();
			 //var userId = $("#userId").val();
			 $.ajax({
				   type: "post",
				   url: "/robot/getAllProductCategoryOfModule",
				   data: {"module":module},
				   dataType: "json",
				   success: function(data){
					   if (JSON.stringify(data) != '' && data != null) {
						   $('#majorId').html("");
						   $('#majorId').append(
									"<option value=''>"
											+ '请选择专业' + "</option>")
							
						   for ( var ele in data) {
								$('#majorId option:last').after(
										"<option value='" + data[ele].id + "'>"
												+ data[ele].name + "</option>")
							}
					   }
				   }
			});
		 });
		 
		  // 给专业的input加入onchange事件
		 $('#courseId').click(function(){
			 var majorId = $('#majorId').val();
			 if(majorId==''){
			 	alert("请先选择专业");
			 }
		 });
		 
		 
	}) 
	/**
	 * 编辑机器人
	 * @param catId
	 */
	function editChallengeRobot(catId) {
		art.dialog.open('/robot/challengeRobotEdit?id=' + catId, {
			title : '编辑机器人',
			width : 400,
			ok : function() {
				var iframe = this.iframe.contentWindow;
				if (!iframe.document.body) {
					alert('iframe还没加载完毕呢')
					return false;
				};
				var form = iframe.document.getElementById('editForm');
				// majorId和courseId不为空
                if(form.majorId.value == ''){
                	alert("专业不允许为空")
            		return false;
        		}
                if(form.courseId.value == ''){
                	alert("课程不允许为空")
            		return false;
        		}
                if(form.targetAbility.value == '' || !(/^([0-9]\d?|100)$/ .test(form.targetAbility.value))){
               		alert("目标值范围是1-100")
                    return false; 
        		}
                
				form.submit();
				return false;
			},
			cancelVal : '关闭',
			cancel : true,
			close : function() {
				location.reload();
			}
		});
	}

	/**
	 * 删除机器人
	 * @param catId
	 */
	function delChallengeRobot(id, catName) {
		if (confirm("确认要删除" + catName + "?")) {
			$.post("/robot/challengeRobotDelete", {
				id : id
			}, function(data) {
				if (data.retCode == 0) {
					alert("删除失败");
				} else {
					alert("删除成功");
				}
				location.reload();
			});
		}
	}

	function toPage(page) {
		var majorId = $("#majorId").val();
		var courseId = $("#courseId").val();
		var url = window.location.pathname + "?majorId=" + majorId + "&courseId=" + courseId +  "&page=" + page;
		self.location = url;
	}
	/**
	 * 挂载机器人
	 */
		function dispatcherRobot(){
			art.dialog.open('/robot/challengeRobotAdd', {
				title : '分配机器人',
				width : 400,
				height : 450,
				ok : function() {
					var iframe = this.iframe.contentWindow;
					if (!iframe.document.body) {
						alert('iframe还没加载完毕呢')
						return false;
					}
					;
					var form = iframe.document.getElementById('addForm');
					// majorId和courseId不为空
	                if(form.userId.value == ''){
	                	alert("机器人不允许为空")
	            		return false;
	        		}
	                if(form.majorId.value == ''){
	                	alert("专业不允许为空")
	            		return false;
	        		}
	                if(form.courseId.value == ''){
	                	alert("课程不允许为空")
	            		return false;
	        		}
	                if(form.targetAbility.value == '' || !(/^([0-9]\d?|100)$/ .test(form.targetAbility.value))){
	               		alert("目标值范围是1-100")
	                    return false; 
	        		}
					
					form.submit();
					return false;
				},
				cancelVal : '关闭',
				cancel : true,
				close : function() {
					location.reload();
				}
			});
		}
	
	 $('#refreshButton').click(function(){
     	$("#module").val('');
     	$("#majorId").val('');
     	$("#courseId").val('');
     	toPage(1);
   	 });
	
</script>

</@htmlBody>
