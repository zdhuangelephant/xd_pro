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
        <link href="${baseOP}/css/order/pay.css?1" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-inbox"></i>报名缴费</a></li>
            <li>订单管理</li>
            <li class="active">订单详情</li>
        </ol>
    </section>
    <!-- Main content -->

<#if orderDO??>    
<section class="table-well">
<div class="row">
    <div class="col-md-12">
        <!-- BEGIN SAMPLE TABLE PORTLET-->
        <div class="box">
            <div class="box-body">
       			<div class="box box-success box-solid" style="margin-bottom: 0px;">
		            <div class="box-header">
		                <h3 class="box-title">订单信息</h3>
		            </div>
		        </div> 
				<div class="col-md-6 col-sm-6 col-xs-12">
			        <ul class="nav nav-stacked">
			            <li><a href="javascript:volid(0);">订单编号：${orderDO.orderNumber}</a></li>
			        </ul>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-12">
			        <ul class="nav nav-stacked">
			            <li><a href="javascript:volid(0);">下单时间：${orderDO.orderTime}</a></li>
			        </ul>
				</div>
            </div>
        </div>
        <!-- END SAMPLE TABLE PORTLET-->
    </div>
</div>
</section>

<section class="table-well">
<div class="row">
    <div class="col-md-12">
        <!-- BEGIN SAMPLE TABLE PORTLET-->
        <div class="box">
            <div class="box-body">
       			<div class="box box-success box-solid" style="margin-bottom: 0px;">
		            <div class="box-header">
		                <h3 class="box-title">确认订单信息</h3>
		                <!-- /.box-tools -->
		                <div class="box-tools pull-right modal-open">
		                	<#if orderDO.status !=-1>
		                	<button class="btn btn-success" data-toggle="modal" data-target="#printApply">查看明细列表</button>
		                	</#if>
		                </div>
		            </div>
		        </div> 
                <table class="table-scrollable table table-hover" id="table_1"
                       data-toggle="table"
                       data-advanced-search="true"
                       data-id-table="advancedTable"
                       data-row-style="rowStyle">
                    <thead>
                        <tr>
                            <th class="text-center" data-sortable="false">报名单位</th>
                            <th class="text-center" data-sortable="false">操作人</th>
                            <th class="text-center" data-sortable="false">折扣</th>
                            <th class="text-center" data-sortable="false">科次</th>
                            <th class="text-center" data-sortable="false">报名人数</th>
                        </tr>
                    </thead>
                    <tbody>
				        <tr>
							<td class="text-center">${orderDO.pilotUnitName}</td>
							<td class="text-center">${orderDO.adminRealName}</td>
							<td class="text-center">
							<#if orderDO.priceRate == 0>
							免费
							<#elseif orderDO.priceRate ==1>
							无
							<#else>
							${orderDO.priceRate*10}折
							</#if>
							</td>
							<td class="text-center">${orderDO.applyCount}</td>
							<td class="text-center">${orderDO.studentCount}</td>
				        </tr>
					</tbody>
                </table>
            </div>
        </div>
        <!-- END SAMPLE TABLE PORTLET-->
    </div>
</div>

<div class="row ">
	<div class="pull-right">
	    <ul class="nav nav-stacked">
	        <li><a href="javascript:volid(0);">订单合计：￥${orderDO.totalAmount}</a></li>
	        <#assign totalAmount = orderDO.totalAmount>
	        <#assign priceRate = orderDO.priceRate>
	        <li><a href="javascript:volid(0);">折扣减免：-￥${totalAmount - totalAmount*priceRate}</a></li>
	    </ul>
	</div>
</div>

<div class="row">
	<div class="pull-right">
	    <ul class="nav nav-stacked">
	        <li><a href="javascript:volid(0);">实付金额：￥${totalAmount * priceRate}</a></li>
	    </ul>
	</div>
</div>
<div class="row">
<div class="pull-right">
       	 您可通过点击表格右上角“查看明细列表”了解具体报名信息，报名详单可直接打印
</div>
</div>
</section>

<#else>
订单已经被删除
</#if>	
<div class="modal-footer">
	<#if orderDO.status ==0>  
    <button onclick="closeOrder(${orderDO.id},${orderDO.orderNumber});" class="btn btn-info">关闭订单</a>
    <!--<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#pay">确认付款</button>-->
    <button onclick="defaultApply(${orderDO.orderNumber});" class="btn btn-primary">确认报名</a>
	<#elseif orderDO.status ==1>
	<#elseif orderDO.status ==-1>
	<button onclick="removeOrder(${orderDO.id},2);" class="btn btn-info btn-sm">删除订单</a>
	</#if>
</div>
<input type="hidden" id="studentCount" value="${studentCount}">
<input type="hidden" id="applyCount" value="${applyCount}">
<input type="hidden" id="applyIds" value="${applyIds}"/>
<input type="hidden" id="totalAmount" value="${totalAmount}"/> 
<!-- 模态框（Modal） -->
<#include "/order/printApply.ftl"/>
<#include "/order/pay.ftl"/>
<#include "/order/wxPay.ftl"/>

        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="${baseOP}/js/common.js"></script>
        <script src="${baseOP}/js/order/order.js?12${timeStamp}"></script>
        <script src="${baseOP}/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="${baseOP}/js/jqprint/jquery.jqprint-0.3.js"></script>
        <script src="${baseOP}/js/pay/pay.js?${timeStamp}"></script>
        <script type="text/javascript" src="${baseOP}/js/pay/jquery.qrcode.min.js"></script>
    </body>
</html>