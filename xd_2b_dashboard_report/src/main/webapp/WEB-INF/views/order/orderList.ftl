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
        <!-- bootstrap datepicker -->
  		<link rel="stylesheet" href="${baseOP}/content/plugins/datepicker/datepicker3.css">
        <link href="${baseOP}/css/dashboard.css" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-inbox"></i>报名缴费</a></li>
            <li class="active">订单管理</li>
        </ol>
    </section>
     <section class="form-well">
        <div class="row"> 
        <form method="post" action="/order/order_list">
			<div class="row" style="margin-bottom: 10px;">
				<div class="col-sm-3">
					<input id="status" type="hidden" value="${orderDTO.status}"/>
					<select id="status1" name="status" class="filter-status form-control">
						<option value="">全部状态</option>
						<option value="0">待缴费</option>
						<option value="1">已缴费</option>
						<option value="-1">已关闭</option>
					</select>
				</div>
				<div class="col-sm-3">
					<input value="${orderDTO.orderNumber}" name="orderNumber" class="form-control"  placeholder="输入订单编号"
						type="text">
				</div>
				<div class="col-sm-3">
					<button type="submit" class="btn btn-default">
						<span class="entypo-search"></span>搜索
					</button>
				</div>
			</div>
			<div class="row" style="margin-bottom: 10px;">
				
				<!-- Date -->
                <div class="col-sm-3">
                    <div class="input-group date">
                        <div class="input-group-addon">
                            <i class="fa fa-calendar"></i>
                        </div>
                        <input value="${orderDTO.orderTime}" name="orderTime" type="text" class="form-control pull-right" id="orderTime" placeholder="下单时间">
                    </div>
                    <!-- /.input group -->
                </div>
                <!-- /.form group -->
                <!-- Date -->
                <div class="col-sm-3">
                    <div class="input-group date">
                        <div class="input-group-addon">
                            <i class="fa fa-calendar"></i>
                        </div>
                        <input value="${orderDTO.payTime}" name="payTime" type="text" class="form-control pull-right" id="payTime" placeholder="交易时间">
                    </div>
                    <!-- /.input group -->
                </div>
                <!-- /.form group -->
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
		                <h3 class="box-title">订单列表</h3>
		            </div>
		        </div>                      
                        <table class="table-scrollable table table-hover" id="table"
                               data-toggle="table"
                               data-pagination="true"
                               data-row-style="rowStyle">
                            <thead>
                                <tr>
                                	<th class="text-center" data-sortable="true">序号</th>
                                    <th class="text-center" data-sortable="false">
										订单状态
									</th>
                                    <th class="text-center" data-sortable="false">订单编号</th>
                                    <th class="text-center" data-sortable="false">总金额（元）</th>
                                    <th class="text-center" data-sortable="false">下单时间</th>
                                    <th class="text-center" data-sortable="false">交易时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if listOrder??>
								<#list listOrder as order>
								<#if order??>
									<tr id="st${order.id}">
										<td class="text-center">${order_index+1}</td>
										<td class="text-center">
											<#if order.status ==0>待缴费
											<#elseif order.status ==1>已缴费
											<#elseif order.status ==-1>已关闭
											</#if>
										</td>
										<td class="text-center">${order.orderNumber}</td>
										<td class="text-center">
											${order.totalAmount}
										</td>
										<td class="text-center">${order.orderTime}</td>
										<td class="text-center">${order.payTime}</td>
										<td>
											<a class="btn btn-info btn-sm" href="/order/order_detail?orderNumber=${order.orderNumber}">查看详情</a>
											<#if order.status ==0>
												<button onclick="closeOrder(${order.id},${order.orderNumber});" class="btn btn-info btn-sm">关闭订单</a>
											<#elseif order.status ==-1>
												<button onclick="removeOrder(${order.id},1);" class="btn btn-info btn-sm">刪除订单</a>
											</#if>
										</td>
									</tr>
									</#if>
									</#list>
									</#if>
							</tbody>
						</table>
                    </div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
<!-- 模态框（Modal） -->
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <!-- bootstrap datepicker -->
		<script src="${baseOP}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/order/order.js?1${timeStamp}"></script>
    </body>
</html>