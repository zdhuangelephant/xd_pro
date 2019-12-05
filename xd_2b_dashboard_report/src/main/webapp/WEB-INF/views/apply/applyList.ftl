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
        <link href="${baseOP}/css/dashboard.css" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
	    <ol class="breadcrumb">
	        <li><a href="#"><i class="fa fa-inbox"></i>报名缴费</a></li>
	        <li class="active">报名管理</li>
	    </ol>
	</section>
    <section class="form-well">
       <div class="row">
       	<form id="searchForm" method="post" action="/apply/apply_list">
       		<input id="pageSize" name="pageSize" type="hidden"/>
			<input id="pageNo" name="pageNo" type="hidden"/>
       		<hidden type="hidden" id="type" name="type">
			<div class="row" style="margin-bottom: 10px;">
				<div class="col-sm-2">
					<input id="orderStatus" type="hidden" value="${applyDO.orderStatus}"/>
					<select id="orderStatus1" name="orderStatus" class="filter-status form-control">
						<option value="">全部状态</option>
						<option value="0">待缴费</option>
						<option value="1">已缴费</option>
						<option value="2">未缴费</option>
					</select>
				</div>
				<div class="col-sm-2">
					<input id="examDate" type="hidden" value="${applyDO.examDate}"/>
					<select id="examDate1" name="examDate" class="filter-status form-control">
						<option value="">全部考期</option>
						<#list listExamDate as product>
							<option value="${product.examDate}">${product.examDate}</option>
						</#list>
					</select>
				</div>
				<div class="col-sm-2">
					<input id="catId" type="hidden" value="${applyDO.catId}"/>
					<select id="catId1" onchange="changeCat(this.options[this.options.selectedIndex].value);" name="catId" class="filter-status form-control">
						<option value="">全部专业</option>
						<#list listCategory as category>
							<option value="${category.id}">${category.name}</option>
						</#list>
					</select>
				</div>
				<div class="col-sm-2">
					<input id="productId" type="hidden" value="${applyDO.productId}"/>
					<select id="productId1" name="productId" class="filter-status form-control">
						<option value="">全部课程</option>
						<#list listProduct as product>
							<option value="${product.id}">${product.name}</option>
						</#list>
					</select>
				</div>
				<div class="col-sm-2">
					<input id="classId" type="hidden" value="${applyDO.classId}"/>
					<select id="classId1" name="classId" class="filter-status form-control">
						<option value="">班级</option>
						<#list listClass as class>
							<option value="${class.id}">${class.className}</option>
						</#list>
					</select>
				</div>
				<div class="col-sm-2">
					<button type="submit" class="btn btn-default">
						<span class="entypo-search"></span>搜索
					</button>
				</div>
			</div>
			<div class="row" style="margin-bottom: 10px;">
				<div class="col-sm-2">
					<input value="${applyDO.studentName}" name="studentName" class="form-control"  placeholder="输入姓名"
						type="text">
				</div>
				<div class="col-sm-2">
					<input value="${applyDO.admissionCardCode}" name="admissionCardCode"  class="form-control" placeholder="输入准考证号"
						type="text">
				</div>
				<div class="col-sm-2">
					<input value="${applyDO.telephone}" name="telephone"  class="form-control" placeholder="输入手机号"
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
		                <h3 class="box-title">报名列表</h3>
		                <!-- /.box-tools -->
		                <div class="box-tools pull-right modal-open">
		                	<!--<button class="btn btn-success" data-toggle="modal" data-target="#order">集体下单</button>-->
		                	<#if applyDO.orderStatus ==2 && listApply?? && (listApply?size>0)>
							<form id="saveOrderDetailForm" method="post" action="/order/save_order_detail">
							<input type="hidden" id="studentCount" name="studentCount" value="${studentCount}">
							<input type="hidden" id="applyCount" name="applyCount" value="${listApply?size}">
							<input type="hidden" id="applyIdsJson" name="applyIds" value="${applyIdsJson}"/>
							<input type="hidden" id="totalAmount" name="totalAmount" value="${totalAmount}"/>
							<a href="javascript:void(0)" class="btn btn-success" onclick="batchDelApply(${applyDO.orderStatus});">批量删除</a>
							<button type="submit" class="btn btn-success" id="saveOrderDetail">集体下单</button>
							</form>
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
										班级名称
									</th>
                                    <th class="text-center" data-sortable="false">姓名</th>
                                    <th class="text-center" data-sortable="false">准考证号</th>
                                    <th class="text-center" data-sortable="false">考期</th>
                                    <th class="text-center" data-sortable="false">专业名称</th>
                                    <th class="text-center" data-sortable="false">课程名称</th>
                                    <th class="text-center" data-sortable="false">状态</th>
                                    <th class="text-center" data-sortable="false">订单号</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if listApply??>
								<#list listApply as apply>
								<#if apply??>
									<tr id="${apply.id}">
										<td class="text-center">${apply_index+1}</td>
										<td class="text-center">
											${apply.className}
										</td>
										<td class="text-center">${apply.studentName}</td>
										<td class="text-center">${apply.admissionCardCode}</td>
										<td class="text-center">${apply.examDate}</td>
										<td class="text-center">${apply.catName}</td>
										<td class="text-center">${apply.productName}</td>
										<td class="text-center">
											<#if apply.orderStatus ==0>待缴费
											<#elseif apply.orderStatus ==1>已缴费
											<#elseif apply.orderStatus ==2>未缴费
											</#if>
										</td>
										<td class="text-center">
											<#if apply.orderStatus ==2>
												-
											<#else>
												<a class="btn btn-info btn-sm" href="/order/order_detail?orderNumber=${apply.orderNumber}">${apply.orderNumber}</a>
											</#if>
										</td>
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
	       		显示第${(pageNo-1)*pageSize+1}到第${(pageNo-1)*pageSize +listApply?size}条记录，总共 ${totalCount}条记录
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
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/apply/apply.js?1${timeStamp}2"></script>
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
    </body>
</html>