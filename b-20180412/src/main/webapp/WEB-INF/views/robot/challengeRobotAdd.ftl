<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form"
	action="/robot/challengeRobotDoAdd">

	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-3"> 机器人 </label> 
			<select id="userId" name="userId"
			class="form-control" style="width: 300px; margin-left: 13px;">
			<option value="">请选择机器人</option>
			<#list robots as robot>
			<option value="${robot.id}">${robot.id} & ${robot.nickName}</option> </#list>
		</select>
	</div>

	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-3"> 选择地域 </label> 
	     <select id="module" name="module" class="form-control" style="width: 300px; margin-left: 13px;">
	         <option value="" >全部地域</option>
	         <#list regionList as regionEntity>
	           <option value=${regionEntity.module}>${regionEntity.moduleName}</option>
	         </#list>
	     </select>
    </div>

	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-3"> 关联专业 </label> <select id="majorId" name="categoryId"
			class="form-control" style="width: 300px; margin-left: 13px;">
			<#list totalProductCategoryLists as productCategory>
				<option value="${productCategory.id}">${productCategory.majorName}</option> 
			</#list>
		</select>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-3"> 关联课程 </label> 
			<select id="courseId" name="courseId" class="form-control" style="width: 300px; margin-left: 13px;"> 
				<option value="">--请选择--</option> 
		</select>
	</div>
	
	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-2"> 能力值 </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg=" "
				 id="form-field-2" name="targetAbility"
				placeholder="请输入0-100内的整数" class="col-xs-10 col-sm-5" />
		</div>
	</div>

</form>
<script>
		$(function(){
			 // 给专业的input加入onchange事件
			 $('#majorId').change(function(){
				 var typeCode = $(this).val();
				 var userId = $("#userId").val();
				 $.ajax({
					   type: "post",
					   url: "/robot/getAllProductsOfProductCategory",
					   data: {"typeCode":typeCode,"userId":userId,needModule:1},
					   dataType: "json",
					   success: function(data){
						   if (JSON.stringify(data) != '' && data != null) {
							   $('#courseId').html("");
							   /* $('#courseId').append(
										"<option value='2'>"
												+ '新手课程' + "</option>") */
							   for ( var ele in data) {
									$('#courseId').append(
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
				   url: "/robot/getAllCategoryOfModule",
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
			 
		});

	$("#addForm").Validform({
		tiptype : 2,
		postonce : true
	});
</script>

</@htmlBody>
