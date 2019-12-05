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
        <link rel="stylesheet" href="${baseOP}/css/dashboard.css?1">
        <link rel="stylesheet" href="${baseOP}/js/jqueryTreeView/jquery.treeview.css"/>
        
        <!-- ace styles -->
	    <link href="${baseOP}/css/ace.css?${timeStamp}" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-list-alt"></i>成绩管理</a></li>
            <li class="active">成绩列表</li>
        </ol>
    </section>
    <section class="form-well">
        <div class="row">
			<form id="searchForm" method="post" action="/score/score_transfer">
				<input id="pageSize" name="pageSize" type="hidden"/>
				<input id="pageNo" name="pageNo" type="hidden"/>
				<div class="row" style="margin-bottom: 10px;">
					<div class="col-sm-2">
						<input id="examDate" type="hidden" value="${scoreDO.examDate}"/>
						<select id="examDate1" name="examDate" class="filter-status form-control">
							<option value="">全部考期</option>
							<#list listExamDate as product>      
								<option value="${product.examDate}">${product.examDate}</option>
							</#list>
						</select>
					</div>
					<div class="col-sm-2">
						<input id="catId" type="hidden" value="${scoreDO.catId}"/>
						<select id="catId1" onchange="changeCat(this.options[this.options.selectedIndex].value);" name="catId" class="filter-status form-control">
							<option value="">全部专业</option>
							<#list listCategory as category>
								<option value="${category.id}">${category.name}</option>
							</#list>
						</select>
					</div>
					<div class="col-sm-2">
						<input id="productId" type="hidden" value="${scoreDO.productId}"/>
						<select id="productId1" name="productId" class="filter-status form-control">
							<option value="">全部课程</option>
							<#list listProduct as product>
								<option value="${product.id}">${product.name}</option>
							</#list>
						</select>
					</div>
					<#if adminUser.role == 3>
					<div class="col-sm-2">
						<input id="classId" type="hidden" value="${scoreDO.classId}"/>
						<select id="classId1" name="classId" class="filter-status form-control">
							<option value="">全部班级</option>
							<#list listClass as class>
								<option value="${class.id}">${class.className}</option>
							</#list>
						</select>
					</div>
					<#else>
					<div class="col-sm-3">
						<input id="pilotUnitId" type="hidden" value="${scoreDO.pilotUnitId}"/>
						<select id="pilotUnitId1" name="pilotUnitId" class="filter-status form-control">
							<option value="">全部${pilotUnitName}</option>
							<#list listPilotUnit as pilotUnit>
								<option value="${pilotUnit.id}">${pilotUnit.unitName}</option>
							</#list>
						</select>
					</div>
					</#if>
					<div class="col-sm-2">
						<button type="submit" class="btn btn-default">
							<span class="entypo-search"></span>搜索
						</button>
					</div>
				</div>
				<div class="row" style="margin-bottom: 10px;">
					<div class="col-sm-2">
						<input value="${scoreDO.studentName}" name="studentName" class="form-control"  placeholder="输入姓名"
							type="text">
					</div>
					<div class="col-sm-2">
						<input value="${scoreDO.admissionCardCode}" name="admissionCardCode"  class="form-control" placeholder="输入准考证号"
							type="text">
					</div>
					<div class="col-sm-2">
						<input value="${scoreDO.telephone}" name="telephone"  class="form-control" placeholder="输入手机号"
							type="text">
					</div>
					
				</div>
			</form>
        </div>
    </section>
    <!-- Main content -->
    <section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">

 		<div class="box box-success box-solid" style="margin-bottom: 0px;">
            <div class="box-header">
                <h3 class="box-title">成绩合并列表</h3>
                <!-- /.box-tools -->
                <div class="box-tools pull-right modal-open">
                	<#if isShow == true>
						<button class="btn btn-success" id="initImportStudent" >批量导入线下成绩</button>
					</#if>
                	<!--<input type="hidden" value="${listSize}" id="sizeoflist" >-->
                	<input type="hidden" value="${listScore?size}" id="sizeoflist" >
                	<#if listScore?? && listScore?size gt 0>
                	<button class="btn btn-success" onclick="exportTransferScoreList();">导出全部成绩</button>
                	</#if>
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
                                	<th class="text-center" data-sortable="true">序号</th>
                                    <th class="text-center" data-sortable="false">
									<#if adminUser.role == 3>
										班级名称
									<#else>
										${pilotUnitName}名称
									</#if>
									</th>
                                    <th class="text-center" data-sortable="false">姓名</th>
                                    <th class="text-center" data-sortable="false">准考证号</th>
                                    <th class="text-center" data-sortable="false">专业</th>
                                    <th class="text-center" data-sortable="false">课程名称</th>
                                    <th class="text-center" data-sortable="false">学生端成绩</th>
                                    <th class="text-center" data-sortable="false">线上成绩</th>
                                    <th class="text-center" data-sortable="false">线下成绩</th>
                                    <th class="text-center" data-sortable="false">综合成绩</th>
									<!--  <th class="text-center" data-sortable="false">学习记录</th> -->
                                </tr>
                            </thead>
                            <tbody>
								<#if listScore??>
								<#list listScore as score>
								<#if score??>
									<tr id="st${score.id}" class="tr-parent">
										<td class="text-center">${score_index+1}</td>
										<td>
										<#if adminUser.role == 3>
											${score.className}
										<#else>
											${score.pilotUnitName}
										</#if>
										</td>
										<td class="text-center">${score.studentName}</td>
										<td class="text-center">${score.admissionCardCode}</td>
										<td>${score.catName}</td>
										<td>${score.productName}</td>
										<!-- 学生端成绩--->
										<td class="text-center">
											<a href="/score/score_detail?scoreId=${score.id}">${score.score}</a>
										</td>
										<td>${(score.discountScore)!'0'}</td>
										<td>${(score.dailyScore)!'0'}</td>
										<td>${(score.reportFinalScore)!'0'}</td>
<!-- 										<td class="text-center"> -->
<!-- 											<a class="btn btn-info" href="/score/learn_record_list?studentId=${score.studentId}&productId=${score.productId}">查看详情</a> --!>
<!-- 										</td> -->
									</tr>
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
	       		显示第${(pageNo-1)*pageSize+1}到第${(pageNo-1)*pageSize +listScore?size}条记录，总共 ${totalCount}条记录
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
<!-- 模态框（Modal） -->
<#include "/score/importScore.ftl"/>
<#include "/common/form/file.ftl"/> 
<#include "/score/printScoreList.ftl"/>
		<script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/score/score.js?1${timeStamp}"></script>
        <script src="${baseOP}/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="${baseOP}/js/jqprint/jquery.jqprint-0.3.js"></script>
        <script src="${baseOP}/js/jqexcel/excel.js"></script>
        <script src="${baseOP}/js/jquery.twbsPagination.js" type="text/javascript"></script>
        <script>
		$(function(){
			$('#pagination-demo-v1_0').twbsPagination({
				totalPages : ${totalPage} > 1 ? ${totalPage} : 1,
				startPage : ${pageNo},
				visiblePages : 5,
				version : '1.0',
				onPageClick : function(event, page) {
					$("#pageNo").val(page);
					$("#pageSize").val(${pageSize});
					$('#searchForm').submit();
				}
			});
				
		});
		function toPageSize(pageSize){
			App.blockUI({
		        target: '.pace-done'
		    });
			$("#pageSize").val(pageSize);
			$("#searchForm").submit();
		}
		
		
		</script>
		
		<!-- 模态框（Modal） -->
		
    </body>
    <@fileUpload></@fileUpload>
</html>