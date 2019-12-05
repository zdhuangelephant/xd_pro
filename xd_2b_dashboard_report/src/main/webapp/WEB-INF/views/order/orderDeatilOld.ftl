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
        <link href="${baseOP}/css/order/pay.css" rel="stylesheet" />
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
<section class="table-well">
<div class="row">
	<div class="col-md-12">
        <#if orderDO.status ==0>  订单生成成功，请在打印报名详单核对报名信息后，尽快完成付款。
		<#elseif orderDO.status ==1>订单已完成，后台将尽快为您开通学习账号，如有疑问请及时联系客服人员。
		<#elseif orderDO.status ==-1>订单已关闭，若需再次缴费，可在报名管理页面点击“集体下单”重新生成订单。
		</#if>
	</div>
	<div class="col-md-3 col-sm-6 col-xs-12">
        <ul class="nav nav-stacked">
            <li><a href="javascript:volid(0);">订单号 ：${orderDO.orderNumber}</a></li>
            <input type="hidden" value="${orderDO.orderNumber}" id="orderNumber"/>
            <li><a href="javascript:volid(0);">报名人数 ：${orderDO.studnetCount}</a></li>
            <li><a href="javascript:volid(0);">报考科次 ：${orderDO.applyCount}</a></li>
        </ul>
	</div>
	<div class="col-md-3 col-sm-6 col-xs-12">
        <ul class="nav nav-stacked">
            <li><a href="javascript:volid(0);">创建时间 ：${orderDO.orderTime}</a></li>
            <li><a href="javascript:volid(0);">报名单位 ：${orderDO.pilotUnitName}</a></li>
        </ul>
	</div>
	<div class="col-md-12">
		<#if orderDO.status ==-1>
		<#else>
        <button class="btn btn-success" data-onum="${orderDO.orderNumber}" data-toggle="modal" data-target="#printApply">打印预览</button>
		</#if>
		<div class="pull-right">
       	 <h2>报名费总计：￥${orderDO.totalAmount}</h2>
		</div>
	</div>
</div>    
</section>
	<div class="modal-footer">
		<#if orderDO.status ==0>  
        <button onclick="closeOrder(${orderDO.id},${orderDO.orderNumber});" class="btn btn-info">关闭订单</a>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#pay">确认付款</button>
		<#elseif orderDO.status ==1>
		<#elseif orderDO.status ==-1>
		<button onclick="removeOrder(${orderDO.id});" class="btn btn-info btn-sm">删除订单</a>
		</#if>
	</div>
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
        <script src="${baseOP}/js/order/order.js?12"></script>
        <script src="${baseOP}/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="${baseOP}/js/jqprint/jquery.jqprint-0.3.js"></script>
        <script src="${baseOP}/js/pay/pay.js?1"></script>
        <script type="text/javascript" src="${baseOP}/js/pay/jquery.qrcode.min.js"></script>
        <script type="text/javascript">
			
		</script>
    </body>
</html>