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
        <link href="${baseOP}/content/ui/global/bootstrap-switch/css/bootstrap-switch.css" rel="stylesheet" />
        <link rel="stylesheet" href="${baseOP}/css/dashboard.css?1">
    </head>
    <body>
    <section class="content-header">
            <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-circle-o"></i>人员管理</a></li>
            <li class="active">报名汇总查看</li>
            </ol>
        </section>
  
    
    <section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
						
        <div class="box box-success box-solid" style="margin-bottom: 0px;">
            <div class="box-header">
                <h3 class="box-title">报名汇总</h3>
            </div>
        </div>  
        
        
        <div class="nav-tabs-custom">
    <!-- Tabs within a box -->
    <ul class="nav nav-tabs">
        <li class="text-center" style="width: 49%;"><a id="studentCollect" href="#studentCollect-chart" data-toggle="tab">学生人数统计表</a></li>
    	<li class="text-center pull-right" style="width: 49%;"><a  href="#applyCollect-chart" data-toggle="tab">报课情况统计表</a></li>
        
    </ul>
    <div class="tab-content no-padding">
        <!-- Morris chart - Sales -->
        <div class="chart tab-pane in active" id="applyCollect-chart" style="position: relative">
			<section class="content">
			    <div class="row">
			        <div class="col-md-12">
			            <!-- AREA CHART -->
			            <div class="box box-primary">
			                <div class="box-body">
			                    <div class="chart">
                           <table class="table-scrollable table table-hover" 
                               data-toggle="table"
                               data-pagination="true"
                               data-advanced-search="true"
                               data-id-table="advancedTable"
                               data-row-style="rowStyle">
		                    <thead>
		                        <tr>
		                        	<th class="text-center">${pilotUnitName}id</th>
		                        	<th class="text-center">${pilotUnitName}名称</th>
		                        	<th class="text-center">报名总人数</th>
		                            <th class="text-center">未缴费科次</th>
		                            <th class="text-center">待缴费科次</th>
		                            <th class="text-center">已缴费科次</th>
		                            <th class="text-center">后台报名完成科次</th>
		                            <th class="text-center">业务系统报名成功科次</th>
		                            <th class="text-center">重复购买科次</th>
		                            <th class="text-center">总科次 </th>
		                        </tr>
		                    </thead>
		                    <tbody>
		                    	<#if applyCollectVOList??>
								<#list applyCollectVOList as vo>
								<#if vo??>
									<tr class="tr-parent">
										<td class="text-center">${vo.pilotUnitId}</td>
										<td class="text-center">${vo.pilotUnitName}</td>
										<td class="text-center">${vo.totalApplySC}</td>
										<td class="text-center">
											<#if (vo.notPaymentAC >0)>
												<a href="/manage/download_applycollect_excel?pilotUnitId=${vo.pilotUnitId}&orderStatus=2&applyStatus=">${vo.notPaymentAC}</a>
											<#else>
												${vo.notPaymentAC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.waitingPaymentAC >0)>
												<a href="/manage/download_applycollect_excel?pilotUnitId=${vo.pilotUnitId}&orderStatus=0&applyStatus=">${vo.waitingPaymentAC}</a>
											<#else>
												${vo.waitingPaymentAC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.alreadyPaymentAC >0)>
												<a href="/manage/download_applycollect_excel?pilotUnitId=${vo.pilotUnitId}&orderStatus=1&applyStatus=">${vo.alreadyPaymentAC}</a>
											<#else>
												${vo.alreadyPaymentAC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.applyAC >0)>
												<a href="/manage/download_applycollect_excel?pilotUnitId=${vo.pilotUnitId}&orderStatus=&applyStatus=0">${vo.applyAC}</a>
											<#else>
												${vo.applyAC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.businessApplySuccessAC >0)>
												<a href="/manage/download_applycollect_excel?pilotUnitId=${vo.pilotUnitId}&orderStatus=&applyStatus=1">${vo.businessApplySuccessAC}</a>
											<#else>
												${vo.businessApplySuccessAC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.businessApplyAlreadyAC >0)>
												<a href="/manage/download_applycollect_excel?pilotUnitId=${vo.pilotUnitId}&orderStatus=&applyStatus=2">${vo.businessApplyAlreadyAC}</a>
											<#else>
												${vo.businessApplyAlreadyAC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.totalApplyAC >0)>
												<a href="/manage/download_applycollect_excel?pilotUnitId=${vo.pilotUnitId}&orderStatus=&applyStatus=">${vo.totalApplyAC}</a>
											<#else>
												${vo.totalApplyAC}
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
			                <!-- /.box-body -->
			            </div>
			            <!-- /.box -->
			        </div>
			    </div>
			    <!-- /.row -->
			</section>
        </div>
        <div class="chart tab-pane active" id="studentCollect-chart" style="position: relative;">
			<section class="content">
			    <div class="row">
			        <div class="col-md-12">
			            <!-- AREA CHART -->
			            <div class="box box-primary">
			                <div class="box-body">
			                    <div class="chart">
                        <table class="table-scrollable table table-hover" 
                               data-toggle="table"
                               data-pagination="true"
                               data-advanced-search="true"
                               data-id-table="advancedTable"
                               data-row-style="rowStyle">
		                    <thead>
		                        <tr>
		                        	<th class="text-center">${pilotUnitName}id</th>
		                        	<th class="text-center">${pilotUnitName}名称</th>
		                        	<th class="text-center">未注册学生人数</th>
		                            <th class="text-center">注册成功学生人数</th>
		                            <th class="text-center">注册失败学生人数</th>
		                            <th class="text-center">注册异常学生人数</th>
		                            <th class="text-center">成功导入学生人数</th>
		                            <th class="text-center">学生总人数</th>
		                        </tr>
		                    </thead>
		                    <tbody>
		                    	<#if studentCollectVOList??>
								<#list studentCollectVOList as vo>
								<#if vo??>
									<tr class="tr-parent">
										<td class="text-center">${vo.pilotUnitId}</td>
										<td class="text-center">${vo.pilotUnitName}</td>
										<td class="text-center">
											<#if (vo.notRegisterSC >0)>
												<a href="/manage/download_studentcollect_excel?pilotUnitId=${vo.pilotUnitId}&studentStatus=0">${vo.notRegisterSC}</a>
											<#else>
												${vo.notRegisterSC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.successRegisterSC >0)>
												<a href="/manage/download_studentcollect_excel?pilotUnitId=${vo.pilotUnitId}&studentStatus=1">${vo.successRegisterSC}</a>
											<#else>
												${vo.successRegisterSC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.failRegisterSC >0)>
												<a href="/manage/download_studentcollect_excel?pilotUnitId=${vo.pilotUnitId}&studentStatus=2">${vo.failRegisterSC}</a>
											<#else>
												${vo.failRegisterSC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.errorRegisterSC >0)>
												<a href="/manage/download_studentcollect_excel?pilotUnitId=${vo.pilotUnitId}&studentStatus=3">${vo.errorRegisterSC}</a>
											<#else>
												${vo.errorRegisterSC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.successImportSC >0)>
												<a href="/manage/download_studentcollect_excel?pilotUnitId=${vo.pilotUnitId}&studentStatus=4">${vo.successImportSC}</a>
											<#else>
												${vo.successImportSC}
											</#if>
										</td>
										<td class="text-center">
											<#if (vo.totalSC >0)>
												<a href="/manage/download_studentcollect_excel?pilotUnitId=${vo.pilotUnitId}&studentStatus=">${vo.totalSC}</a>
											<#else>
												${vo.totalSC}
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
			                <!-- /.box-body -->
			            </div>
			            <!-- /.box -->
			        </div>
			    </div>
			    <!-- /.row -->
			</section>
        </div>
    </div>
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
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script type="text/javascript">
        	$(function(){
        		$("#studentCollect").click();
        	});
        </script>
    </body>
</html>