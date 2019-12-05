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
        <!-- bootstrap datepicker -->
        <link rel="stylesheet" href="${baseOP}/content/plugins/datepicker/datepicker3.css">
        <style>
			.unitPortrait
			{
				width: 156px;
				height: 60px;
			}
		</style>
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-warning"></i>短信查询</a></li>
            <li class="active">短信查询</li>
        </ol>
     </section>
     <section class="form-well">
		<div class="row">
			<form id="searchForm" method="post" action="/sms/sms_log/smsLoglist">
			  <input id="pageSize" name="pageSize" type="hidden"/>
        	  <input id="pageNo" name="pageNo" type="hidden"/>
				<div class="row" style="margin-bottom: 10px;">
					<div class="col-sm-2">
						<input value="${smsLogDO.mobile}" name="mobile" id="mobile"  class="form-control"  placeholder="请输入手机号码"
							type="text">
					</div>
					<!-- Date -->
                    <div class="col-sm-3">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input value="${smsLogDO.beginDate}" name="beginDate" type="text" class="form-control pull-right" id="beginDate" placeholder="开始日期">
                        </div>
                        <!-- /.input group -->
                    </div>
                    <div class="col-sm-3">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input value="${smsLogDO.endDate}" name="endDate" type="text" class="form-control pull-right" id="endDate" placeholder="结束日期">
                        </div>
                        <!-- /.input group -->
                    </div>
					<div class="col-sm-2">
						<button type="submit" class="btn btn-default" onclick="return checkForm();">
							<span class="entypo-search"></span>&nbsp;&nbsp;搜索
						</button>
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
                                <h3 class="box-title">短信列表</h3>
                                <!-- /.box-tools -->
                                <div class="box-tools pull-right modal-open">
                                  <#if listSmsLog?? && listSmsLog?size gt 0>
                                  	<button class="btn btn-success" data-toggle="modal" onclick="exportSmsLogList();">导出</button>
                                  </#if>
                                </div>
                            </div>
                        </div>                   
                        <table class="table-scrollable table table-hover" id="table"
                               data-toggle="table"
                               data-pagination="true"
                               data-advanced-search="true"
                               data-id-table="advancedTable"
                               data-row-style="rowStyle">
                            <thead>
                                <tr>
                                	<th class="text-center" style="width:100px">编号</th>
                                	<th class="text-center" style="width:200px">手机号码</th>
                                	<th class="text-center">短信内容</th>
                                	<th class="text-center" style="width:200px">发送状态</th>
									                 <th class="text-center" style="width:180px">发送时间</th>
                                </tr>
                            </thead>
                            <tbody>
                								<#if listSmsLog??>
                								<#list listSmsLog as sms>
                								<#if sms??>
                									<tr>
                										<td class="text-center">${sms.id}</td>
                                    <td class="text-center">${sms.mobile}</td>
                										<td class="text-left">${sms.message}</td>
                                    <td class="text-center">${sms.channelSendResult}</td>
                                    <td class="text-center">${sms.createTime}</td>
                									</tr>
                								</#if>
                								</#list>
                								</#if>
                					  </tbody>
                					</table>
                      </div>
                      <#if listSmsLog?? && listSmsLog?size gt 0>
                      <div class="fixed-table-pagination">
                          <div class="pull-left pagination-detail">
                              <span class="pagination-info">
                              <#if totalCount ==0>
                                                                                              总共 ${totalCount}条记录
                              <#else>
                                                                                              显示第${(pageNo-1)*pageSize+1}到第${(pageNo-1)*pageSize +listSmsLog?size}条记录，总共 ${totalCount}条记录
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
                      </#if>
                  </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
    <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
    <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
    <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
    <script src="${baseOP}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
    <script src="${baseOP}/js/jquery.twbsPagination.js" type="text/javascript"></script>
    <script src="${baseOP}/js/common.js?${timeStamp}"></script>
    <script src="${baseOP}/js/sms/sms.js?1${timeStamp}"></script>    
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