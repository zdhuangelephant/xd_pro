<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8" />
        <#include "/common/title.ftl"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- Bootstrap 3.3.6 -->
        <link rel="stylesheet" href="${baseOP}/content/ui/global/bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link href="${baseOP}/content/ui/global/font-awesome/css/font-awesome.css" rel="stylesheet" />
        <!-- Theme style -->
        <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/AdminLTE.css">
        <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/skins/_all-skins.min.css">
        <link href="${baseOP}/content/min/css/supershopui.common.min.css" rel="stylesheet"/>
        <link href="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
        <link href="${baseOP}/css/dashboard.css?12${timeStamp}" rel="stylesheet" />
        <link rel="stylesheet" href="${baseOP}/js/jqueryTreeView/jquery.treeview.css"/>
        
	    <!-- ace styles -->
	    <link href="${baseOP}/css/ace.css?${timeStamp}" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-users"></i>学籍管理</a></li>
            <li class="active">学生管理</li>
        </ol>
    </section>
	<section class="form-well">
		<div class="row">
			<form id="searchForm" method="post" action="/student/student_list">
				<input id="pageSize" name="pageSize" type="hidden" value="10"/>
				<input id="pageNo" name="pageNo" type="hidden" value="1"/>
					<div class="row" style="margin-bottom: 10px;">
						<div class="col-sm-2">
							<input id="classId" type="hidden" value="${studentDO.classId}"/>
							<select id="classId1" name="classId" class="filter-status form-control">
								<#if adminUser.childRole !=1>
								<option value="">全部班级</option>
								</#if>
								<#list listClass as class>
									<option value="${class.id}">${class.className}</option>
								</#list>
							</select>
						</div>
						<div class="col-sm-2">
							<input value="${studentDO.realName}" name="realName" class="form-control"  placeholder="输入姓名"
								type="text">
						</div>
						<div class="col-sm-2">
							<input value="${studentDO.admissionCardCode}" name="admissionCardCode"  class="form-control" placeholder="输入准考证号"
								type="text">
						</div>
						<div class="col-sm-2">
							<input value="${studentDO.telephone}" name="telephone"  class="form-control" placeholder="输入手机号"
								type="text">
						</div>
						<div class="col-sm-2">
							<button type="submit" class="btn btn-default">
								<span class="entypo-search"></span>搜索
							</button>
						</div>
					</div>
				</form>
		</div>
	</section>
    <!-- Main content -->
    <section class="form-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
						
			 		<div class="box box-success box-solid" style="margin-bottom: 0px;">
			            <div class="box-header">
			                <h3 class="box-title">学生列表</h3>
			                <!-- /.box-tools -->
			                <div class="box-tools pull-right modal-open">
			                	<button class="btn btn-success" data-toggle="modal" data-target="#save">添加学生</button>
			                	<button class="btn btn-success" id="initImportStudent">批量导入学生</button>
			                	<button class="btn btn-success" id="initBatchApply">批量报课</button>
			                </div>
			            </div>
			        </div>    
                    
                        <table class="table-scrollable table table-hover" id="table"
                               data-toggle="table"
                               data-advanced-search="true"
                               data-id-table="advancedTable"
                               data-row-style="rowStyle">
                            <thead>
                                <tr>
                                	<!--<th class="text-center">
					                    <a style="cursor: pointer;"
					                       onClick="javascript:$('input[name=\'batchStudent\']').prop('checked', true);">全选</a>/
					                    <a style="cursor: pointer;"
					                       onClick="javascript:$('input[name=\'batchStudent\']').prop('checked', false);">取消</a>
					                </th>-->
					                <th class="text-center" data-sortable="false">
									<input type="checkbox" id="checkAll"/>
									</th>
                                    <th data-sortable="false">所在班级</th>
                                    <th data-sortable="false">姓名</th>
                                    <th data-sortable="false">性别</th>
                                    <th data-sortable="false">手机号</th>
                                    <th data-sortable="false">准考证号</th>
                                    <th data-sortable="false">状态</th>
                                    <th data-sortable="false">报名课程</th>
                                    <th data-field="state" data-sortable="false">操作</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if listStudent??>
								<#list listStudent as student>
								<#if student??>
									<tr id="st${student.id}" class="tr-parent">
										<td><input type="checkbox" name="batchStudent" value="${student.id}"/></td>
										<td style="text-align:left;">${student.className}</td>
										<td>${student.realName}</td>
										<td class="text-center">
										<#if student.gender == 1>
										男
										<#elseif student.gender == 2>
										女
										<#else>
										不明
										</#if>
										</td>
										<td class="text-center">${student.telephone}</td>
										<td class="text-center">${student.admissionCardCode}</td>
										<td class="text-center">
										<#if student.studentStatus ==0>未注册
										<#elseif student.studentStatus ==1>注册成功
										<#elseif student.studentStatus ==2>注册失败
										<#elseif student.studentStatus ==3>注册异常
										<#elseif student.studentStatus ==4>信息完善
										</#if>
										</td>
										<td class="text-center">
											<#if student.applyList?? && (student.applyList?size>0)>
											<a data-toggle="collapse" data-parent="#accordion" 
											   href="#collapse_${student.id}" class="btn btn-info">
												查看
											</a>
											<#else>
											<a href="javascript:volid(0);" disabled="true" class="btn btn-info">
												查看
											</a>
											</#if>
											<button type="button" class="btn btn-primary" 
												data-toggle="modal" data-target="#apply" data-sid="${student.id}" data-applyList="1">报课</button>
											<!--<#if student.studentStatus!=1>disabled="true"</#if>-->
										</td>
										<td class="text-center">
											<button <#if student.studentStatus ==4>disabled="disabled"</#if> class="btn btn-info" data-toggle="modal" data-target="#update" data-sid="${student.id}" data-status="${student.studentStatus}">编辑</button>
											<button onclick="removeStudent(${student.id},${student.classId});" type="button" class="btn btn-danger"
												<#if student.deleteStatus==1>disabled="true"</#if>>删除</button>
										</td>
									</tr>
									
									<#if student.applyList??>
									<tr id="collapse_${student.id}" class="tr-child panel-collapse collapse">
										<td colspan="10">
											<table class="table table-bordered cf">
												<tbody>
												<#list student.applyList as applyModel>
													<tr>
														<td>${applyModel_index+1}</td>
														<td>${applyModel.catName}</td>
														<td>${applyModel.productName}</td>
														<td align="center">${applyModel.examDate}</td>
														<td align="center">
														<#if applyModel.orderStatus?? && applyModel.orderStatus==0>
														<#assign selectStatus="false">
														待缴费
														<#elseif applyModel.orderStatus?? && applyModel.orderStatus==1 >
														<#assign selectStatus="false">
														已缴费
														<#elseif applyModel.orderStatus?? && applyModel.orderStatus==2 >
														未缴费
														</#if>
														</td>
														<td align="center">
														<button onclick="javascript:removeApply(${applyModel.id});" type="button" class="btn btn-danger"
																<#if applyModel.orderStatus?? && applyModel.orderStatus==2><#else>disabled="true"</#if>>删除</button>
														</td>
													</tr>
												</#list>
												</tbody>
											</table>
											</td>
										</tr>
									</#if>
									
									</#if>
									</#list>
									</#if>
								</tbody>
						</table>
                    </div>
       	<div class="fixed-table-pagination">
	       	<div class="pull-left pagination-detail">
	       		<span class="pagination-info">
	       		<#if totalCount ==0>
	       		总共 ${totalCount}条记录
	       		<#else>
	       		显示第${(pageNo-1)*pageSize+1}到第${(pageNo-1)*pageSize +listStudent?size}条记录，总共 ${totalCount}条记录
	       		</#if>
	         	</span>
	       		<span class="page-list">
	       		每页显示 
	       		<span class="btn-group dropup">
	       			<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
		       			<span class="page-size">${pageSize}</span>
		       			<span class="caret"></span>
	       			</button>
	       			<ul class="dropdown-menu" role="menu">
	       				<li <#if pageSize==10>class="active"</#if>><a onclick="toPageSize(10);" href="javascript:void(0)">10</a></li>
	       				<li <#if pageSize==25>class="active"</#if>><a onclick="toPageSize(25);" href="javascript:void(0)">25</a></li>
	       				<li <#if pageSize==100>class="active"</#if>><a onclick="toPageSize(100);" href="javascript:void(0)">100</a></li>
	       			</ul>
	       		</span>
	       		 条记录
	       		</span>
	       	</div>
	       	<div class="pull-right pagination">
	        <ul id="pagination-demo-v1_0" class="pagination"></ul>
	       	</div>
        </div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
       
    </section>

        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js?"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/student/student.js?${timeStamp}"></script>
        <script src="${baseOP}/js/jquery.twbsPagination.js" type="text/javascript"></script>
        <script src="${baseOP}/js/jqexcel/excel.js?${timeStamp}"></script>
<!-- 模态框（Modal） -->
<#include "/student/saveStudent.ftl"/>
<#include "/student/updateStudent.ftl"/>
<#include "/student/apply.ftl"/>
<#include "/student/importStudent.ftl"/>
<#include "/common/form/file.ftl"> 
<#include "/student/importStudentTable.ftl">
<#include "/student/batchApply.ftl">
    </body>
	<script>
		$(function(){
				$('#pagination-demo-v1_0').twbsPagination({
					totalPages : ${pageCount} > 1 ? ${pageCount} : 1,
					startPage : ${studentDO.pageNo},
					visiblePages : 5,
					version : '1.0',
					onPageClick : function(event, page) {
						$("#pageNo").val(page);
						$("#pageSize").val(${pageSize});
						$('#searchForm').submit();
					}
				});
		});
	</script>
	<@fileUpload></@fileUpload>
</html>