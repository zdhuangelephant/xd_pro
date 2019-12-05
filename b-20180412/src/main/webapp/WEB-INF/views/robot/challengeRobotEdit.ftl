<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form"
	action="/robot/challengeRobotDoEdit">

	<div class="form-group">
		<input type="hidden" name="id" value="${challengeRobot.id}" />
	</div>


	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-3"> 选择地域 </label> <select id="module" name="module"
			class="form-control" style="width: 300px; margin-left: 13px;">
			<option value="">全部地域</option> <#list regionList as regionEntity>
			<option value="${regionEntity.module}"<#if
				challengeRobot.module== regionEntity.module>selected</#if>
				>${regionEntity.moduleName}</option>
			
			
			</#list>
		</select>
	</div>


	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-2"> 用户ID </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg=" " readonly="readonly"
				value="${challengeRobot.userId}" id="form-field-2" name="userId"
				placeholder="用户ID" class="col-xs-10 col-sm-5" />
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-3"> 关联专业 </label> <select id="majorId"
			name="categoryId"  class="form-control"
			style="width: 300px; margin-left: 13px;">
			<option value="-1">--请选择专业--</option> <#list
			totalProductCategoryLists as productCategory>
			<option value="${productCategory.id}"<#if
				challengeRobot.categoryId== productCategory.id>selected</#if>
				>${productCategory.majorName}</option> </#list>
		</select>
	</div>
	 <div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-3"> 关联课程 </label> <select id="courseId"
			name="courseId" class="form-control"
			style="width: 300px; margin-left: 13px;"> <#list
			productsOfProductCategory as product>
			<option value="${product.id}"<#if challengeRobot.courseId ==
				product.id>selected</#if> >${product.name}</option> </#list>
		</select>
	</div>

	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right"
			for="form-field-2"> 能力值 </label>
		<div class="col-sm-9">
			<input type="text" datatype="s" sucmsg=" "
				value="${challengeRobot.targetAbility}" id="form-field-2"
				name="targetAbility" placeholder="请输入0-100内的整数"
				class="col-xs-10 col-sm-5" />
		</div>
	</div>

</form>
<script>
	$(function() {
		
		// 给地域的input加入onchange事件
		$('#module').change(
				function() {
					var module = $(this).val();
					var userId = $("#userId").val();
					$.ajax({
						type : "post",
						url : "/robot/getAllProductCategoryOfModule",
						data : {
							"module" : module
							/* "userId" : userId,
							"needModule" : 1 */
						},
						dataType : "json",
						success : function(data) {
							if (JSON.stringify(data) != '' && data != null) {
								$('#majorId').html("");
								/* $('#courseId').append(
										"<option value='2'>"
												+ '新手课程' + "</option>") */
								for ( var ele in data) {
									$('#majorId').append(
											"<option value='" + data[ele].id + "'>"
													+ data[ele].name
													+ "</option>")
								}
							}
						}
					});
				});
		
		
		// 给专业的input加入onchange事件
		$('#majorId').change(
				function() {
					var typeCode = $(this).val();
					var userId = $("#userId").val();
					var module = $("#module").val();
					$.ajax({
						type : "post",
						url : "/robot/getAllProductsOfProductCategory",
						data : {
							"typeCode" : typeCode,
							"userId" : userId,
							"module" : module,
							"needModule" : 1
						},
						dataType : "json",
						success : function(data) {
							if (JSON.stringify(data) != '' && data != null) {
								$('#courseId').html("");
								/* $('#courseId').append(
										"<option value='2'>"
												+ '新手课程' + "</option>") */
								for ( var ele in data) {
									$('#courseId').append(
											"<option value='" + data[ele].id + "'>"
													+ data[ele].name
													+ "</option>")
								}
							}
						}
					});
				});
	});

	$("#editForm").Validform({
		tiptype : 2,
		postonce : true
	});
</script>

</@htmlBody>
